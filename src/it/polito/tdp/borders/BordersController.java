/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    private Button calcolaConfiniButton;

    @FXML
    private Button numeroConfiniButton;

    @FXML
    private ComboBox<Country> statiBox;

    @FXML
    private Button trovaViciniButton;
    
    @FXML
    private Button componentiConnesseButton;

    @FXML
    private Button resetButton;

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		int anno = Integer.parseInt(this.txtAnno.getText());
		model.creaGrafo(anno);
		txtResult.appendText("Creo il grafo relativo al "+anno+"\n");
		txtResult.appendText("Creati "+model.getVertexSize()+
				" vertici (nazioni) e "+model.getEdgeSize()+" archi (collegamenti via terra)\n"); 
		
		List<Country> countries = model.getCountries();
//		for(Country c : countries) {
//			txtResult.appendText(c.toString()+"\n");
//		}
		txtResult.appendText(("Trovate "+countries.size()+" nazioni\n"));
		
		this.statiBox.getItems().addAll(countries);
		this.componentiConnesseButton.setDisable(false);
		this.numeroConfiniButton.setDisable(false);
		this.trovaViciniButton.setDisable(false);
		this.statiBox.setDisable(false);
		this.txtAnno.setDisable(true);
		this.calcolaConfiniButton.setDisable(true);
	}
	
	@FXML
    void handleReset(ActionEvent event) {

		model.reset();
		this.txtResult.clear();
		this.componentiConnesseButton.setDisable(true);
		this.numeroConfiniButton.setDisable(true);
		this.trovaViciniButton.setDisable(true);
		this.statiBox.setDisable(true);
		this.txtAnno.setDisable(false);
		this.calcolaConfiniButton.setDisable(false);
    }
	
	 @FXML
	 void handleComponentiConnesse(ActionEvent event) {
		 txtResult.appendText("Numero componenti connesse: "+ 
				 model.getNumberOfConnectedComponents()+"\n");
			
	 }
	
	@FXML
    void handleNumeroConfini(ActionEvent event) {
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			this.txtResult.appendText(country+" confina con "+stats.get(country)+" stati\n");	
		
	}
	
	@FXML
	void handleTrovaVicini(ActionEvent event) {
		Country c = this.statiBox.getValue();
		List<Country> statiRaggiungibili = model.trovaStatiRaggiungibili(c);
		String ragg = "Paesi raggiungibili, con diversi percorsi\n"
				+ "nei vari stati a partire da: "+c.toString()+"\n";
		for(Country p : statiRaggiungibili) {
			ragg = ragg +p.toString()+"\n";
		}
		
		txtResult.appendText("\n *********************** \n");
		txtResult.appendText(ragg);
		
	}
	@FXML
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert calcolaConfiniButton != null : "fx:id=\"calcolaConfiniButton\" was not injected: check your FXML file 'Borders.fxml'.";
		assert numeroConfiniButton != null : "fx:id=\"numeroConfiniButton\" was not injected: check your FXML file 'Borders.fxml'.";
		assert statiBox != null : "fx:id=\"statiBox\" was not injected: check your FXML file 'Borders.fxml'.";
		assert trovaViciniButton != null : "fx:id=\"trovaViciniButton\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;
		
	}
}
