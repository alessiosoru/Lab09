package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		System.out.format("Creati %d vertici e %d archi\n", model.getVertexSize(),
				model.getEdgeSize());
		List<Country> countries = model.getCountries();
//		for(Country c : countries) {
//			System.out.println(c.toString()+"\n");
//		}
		System.out.format("Trovate %d nazioni\n", countries.size());

		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			System.out.format("%s %d\n", country, stats.get(country));	
		
		Country c = countries.get(115);
		
		List<Country> statiRaggiungibili = model.trovaStatiRaggiungibili(c);
		String ragg = "Paesi raggiungibili, con diversi percorsi\n"
				+ "nei vari stati a partire da: "+c.toString()+"\n";
		for(Country p : statiRaggiungibili) {
			ragg = ragg +p.toString()+"\n";
		}
		
		System.out.println("\n *********************** \n");
		System.out.println(ragg);
		System.out.println(statiRaggiungibili.size());
	}

}
