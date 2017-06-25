/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;
import com.sun.org.omg.CORBA.ExceptionDescriptionHelper;

import it.polito.tdp.artsmia.model.Exhibition;
import it.polito.tdp.artsmia.model.Model;
import it.polito.tdp.artsmia.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	private Model model;
	private int annoDelGrafo;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldStudenti"
    private TextField txtFieldStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	
    	if( boxAnno.getValue()==null){
    		txtResult.appendText("Scegliere una anno\n");
    		return;
    	}
    	this.annoDelGrafo= boxAnno.getValue();
    	model.createGraph(annoDelGrafo);

    	if(model.isGraphConnected())
    		txtResult.appendText("Il grafo è connesso\n");
    	else 
    		txtResult.appendText("Il grafo non è connesso\n");
    	
    	Exhibition e = model.getExhibitionWithMoreObject(annoDelGrafo);
    	
    	if(e==null){
    		txtResult.appendText("Non esiste una mostra in quell'anno\n");
    	} else {
    		txtResult.appendText(e.toString());
    	}
    }

    @FXML
    void handleSimula(ActionEvent event) {
    	
    	if(boxAnno.getValue()==null){
    		txtResult.appendText("Scegliere un anno.\n");
    		return;
    	}
    	
    	if(txtFieldStudenti.getText().length()==0){
    		txtResult.appendText("Inserire numero di studenti.\n");
    		return;
    	}
    	
    	String numero = txtFieldStudenti.getText();
    	
    	try {
    		int numS = Integer.parseInt(numero);
    		int anno = boxAnno.getValue();
    		
    		if(numS<1){
    			txtResult.appendText("Inserire numero >0\n");
    			return;
    		}
    		if(anno!=annoDelGrafo){
    			annoDelGrafo= anno;
    			txtResult.appendText("Non c'è il grafo per quest'anno, lo genero !\n");
    			model.createGraph(anno);
    		}
    		
    		List<Integer> studenti= model.avviaSimulazione(anno, numS);
    		for(int i=1; i<=numS; i++)
    			txtResult.appendText("Studente "+i+" num opere: "+studenti.get(i-1)+"\n");
    		
    		
    	} catch (NumberFormatException e){
    		txtResult.appendText("Inserire numero di studenti solo con cifre.\n");
    		return;
    	}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
		this.boxAnno.getItems().addAll(model.getAnniMostre());
	}
}
