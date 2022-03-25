package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	// ATTRIBUTI
	private String parola;
	private boolean corretta;
	
	// COSTRUTTORE
	public RichWord(String parola)
	{
		this.parola = parola;
		this.corretta = false;
	}

	// GETTER & SETTER
	public String getParola() 
	{
		return parola;
	}

	public boolean isCorretta() 
	{
		return corretta;
	}

	public void setCorretta(boolean corretta) 
	{
		this.corretta = corretta;
	}
	
	
	
	

}
