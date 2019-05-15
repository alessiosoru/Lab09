package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	SimpleGraph<Country, DefaultEdge> grafo;
	List<Country> countries;
	Map<Integer, Country> countryIdMap;
	Map<Country, Country> visita;
	

	public Model() {
		this.grafo = new SimpleGraph(DefaultEdge.class);
		this.countryIdMap = new HashMap<Integer, Country>();
		this.visita = new HashMap<Country, Country>();
	}

	public void creaGrafo(int anno) {
		
		BordersDAO dao = new BordersDAO();
		countries = dao.loadAllCountries(countryIdMap); // carico direttamente i country, vertici del grafo, nell'idMap
		
		// Aggiungo i vertici
		Graphs.addAllVertices(grafo, countryIdMap.values());
		
		// Aggiungo gli archi con l'opzione 3, adiacenze come Border
		List<Border> borders = dao.listBorders(anno);
		
		for(Border b : borders) {
			Country sourceCountry = countryIdMap.get(b.getState1Id());
			Country targetCountry = countryIdMap.get(b.getState2Id());
			if(!this.grafo.containsEdge(sourceCountry, targetCountry) ||
					!this.grafo.containsEdge(targetCountry, sourceCountry)) { // verifico di non aver già inserito il bordo al contrario
//				System.out.println("Confine tra:\n"+sourceCountry.toString()+"\n"+targetCountry.toString());
				this.grafo.addEdge(sourceCountry, targetCountry);				
			}
			
		}
		
	}
	
	public int getVertexSize() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return this.grafo.edgeSet().size();
	}

	public List<Country> getCountries() {
//		List<Country> countries = new ArrayList<>();
//		for(Country c : this.countryIdMap.values()) {
////			System.out.println(c.toString());
//			countries.add(c);
//		}
		return this.countries;
	}

	public Map<Country, Integer> getCountryCounts() {
		// statistiche sui confini di ogni stato (grado del vertice dello stato)
		Map<Country, Integer> stats = new TreeMap<Country, Integer>();
		for(Country c : this.countries) {
			stats.put(c, this.grafo.degreeOf(c));
		}
		return stats;
	}

	public int getNumberOfConnectedComponents() {
		ConnectivityInspector<Country, DefaultEdge> ci =  new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		return ci.connectedSets().size();
	}
	
	public List<Country> trovaStatiRaggiungibili(Country sourceCountry){
		
		// versione con BreadthFirstIterator
		List<Country> statiRaggiungibili = new ArrayList<Country>();
		GraphIterator<Country, DefaultEdge> it = new BreadthFirstIterator(this.grafo);
		visita.put(sourceCountry, null);
		
		it.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
				Country sourceVertex = grafo.getEdgeSource(ev.getEdge());
				Country targetVertex = grafo.getEdgeTarget(ev.getEdge());
				
				// in visita, la chiave è il figlio, l'elemento è il padre
				// esploro il grafo aggiungendo le istanze di padri e figli, dato che il grafo
				// non è orinetato, verifico quale risulta il padre e quale il figlio dell'arco
				// tra quello che ho salvato come sorgente e destinazione dell'evento
				// la prima istanza che ho salvato in precedenza ha il figlio che è la sorgente primaria
				// da cui faccio partire l'attraversamento, ma non ha ancora il padre
				// quindi nel primo salvataggio istanzierò un nuovo componente con chiave (figlio)
				// come il target dell'arco del grafo che ha la sorgente primaria come origini
				// e come padre la sorgente primaria e il processo andrà così via avanti
				if(!visita.containsKey(targetVertex) && visita.containsKey(sourceVertex)) {
					visita.put(targetVertex, sourceCountry);
				} else if (!visita.containsKey(sourceVertex) && visita.containsKey(targetVertex)) {
					visita.put(sourceVertex, targetVertex);
				}
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		// a questo punto posso iterare per aggiungere gli elementi della lista di nodo raggiungibiili
		while(it.hasNext()) {
			statiRaggiungibili.add(it.next());
		}


		// fare anche una versione iterativa del metodo, implementata senza GraphIterators
		
		
		
		return statiRaggiungibili;
	}

	public void reset() {
		this.grafo = new SimpleGraph(DefaultEdge.class);
		this.countryIdMap = new HashMap<Integer, Country>();
		this.visita = new HashMap<Country, Country>();
		
	}
	
}
