package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="cmbLanguage"
    private ComboBox<String> cmbLanguage; // Value injected by FXMLLoader
    @FXML // fx:id="txtCheck"
    private TextArea txtCheck; // Value injected by FXMLLoader
    @FXML // fx:id="txtNumberErrors"
    private Label lblNumberErrors; // Value injected by FXMLLoader
    @FXML // fx:id="lblTime"
    private Label lblTime; // Value injected by FXMLLoader
    @FXML // fx:id="lblWrong"
    private TextArea txtWrong; // Value injected by FXMLLoader
    
    private Dictionary model;

    @FXML
    void doClearText(ActionEvent event) {
    	
    	this.cmbLanguage.setValue(null);
		this.txtCheck.clear();
		this.lblNumberErrors.setText(null);
		this.lblTime.setText(null);
		this.txtWrong.clear();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	// 1) acquisizione dei dati
    	String lingua = this.cmbLanguage.getValue();
    	String testo = this.txtCheck.getText();
    	
    	// 2) controllo dei dati
    	if(lingua.equals("") || lingua.equals(null) ||testo.equals(""))
    	{
    		this.txtWrong.setText("ERROR! yuo have to chose the language and insert the text!");
    		this.cmbLanguage.setValue(null);
    		this.txtCheck.clear();
    		
    		return;
    	}
    	
    	testo = testo.replaceAll("\\p{Punct}", "");
    	String[] array = testo.split(" ");
    	List<String> lista = new LinkedList<String>();
    	for(String s: array)
    	{
    		lista.add(s.toLowerCase());
    	}
    	
    	// 3) esecuzione dell'operazione --> chiedere al model di farla
    	long start, end;
    	start = System.currentTimeMillis();
    	model.loadDictionary(lingua);
    	List<RichWord> errori = model.spellCheckText(lista);
		end = System.currentTimeMillis();
		long time = end - start;
    	
    	// 4) visualizzazione/aggiornamento del risultato
		String err = "";
		int nmbErr = 0;
		for(RichWord s: errori)
		{
			if(s.isCorretta() == false)
			{
				if(err != "")
				{
					err = err + "\n";
				}
				
				err = err + s.getParola();
				nmbErr++;
			}
		}
		
		this.txtWrong.setText(err);
		this.lblNumberErrors.setText("The text contains " + nmbErr + " errors!");
		this.lblTime.setText("Spell check completed in " + time + " milliseconds!");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCheck != null : "fx:id=\"txtCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblNumberErrors != null : "fx:id=\"lblNumberErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtWrong != null : "fx:id=\"txtWrong\" was not injected: check your FXML file 'Scene.fxml'.";

        this.cmbLanguage.getItems().clear();
        this.cmbLanguage.getItems().add("Italian");
        this.cmbLanguage.getItems().add("English");
    }
    
    void setModel(Dictionary model)
    {
    	this.model = model;
    }
    	

}


