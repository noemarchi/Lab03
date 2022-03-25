package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {
	
	// ATTRIBUTI
	Set<String> dizionario = new HashSet<String>();
	List<String> linked = new LinkedList<String>();
	List<String> array = new ArrayList<String>();
	
	// METODI
	public void loadDictionary(String language)
	{
		try 
		{
			FileReader fr = new FileReader("src/main/resources/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr); String word;
			while ((word = br.readLine()) != null) 
			{
				//dizionario.add(word);
				//linked.add(word);
				array.add(word);
			}
            br.close();
		} 
		catch (IOException e)
		{
			System.out.println("Errore nella lettura del file");
		}
		
	}

	
	public List<RichWord> spellCheckText(List<String> inputTextList)
	{
		List<RichWord> ret = new LinkedList<RichWord>();
		
		for(String s: inputTextList)
		{
			RichWord rw = new RichWord(s);
			
			if(array.contains(s))
			{
				rw.setCorretta(true);
			}
			else
			{
				rw.setCorretta(false);
			}
			
			ret.add(rw);
		}
		
		return ret;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList)
	{
		List<RichWord> ret = new LinkedList<RichWord>();
		
		for(String s: inputTextList)
		{
			RichWord rw = new RichWord(s);
			
			for(String wordDizionario: linked)
			{
				if(s.compareTo(wordDizionario)==0)
				{
					rw.setCorretta(true);
				}
			}
			
			ret.add(rw);
		}
		
		return ret;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList)
	{
		List<RichWord> ret = new LinkedList<RichWord>();
		
		for(String s: inputTextList)
		{
			RichWord rw = new RichWord(s);
			
			boolean trovato = false;
			int low = 0;
			int high = array.size() - 1;
			
			while (low<=high) 
			{
				int mid = (low+high)/2;
				if(array.get(mid).compareTo(s) ==0) 
				{
					trovato = true; //valore trovato nella posizione mid
					break;
		        }
				else if (array.get(mid).compareTo(s) <0 ) 
				{
					low = mid + 1;
				}
				else 
				{
					high = mid - 1;
				}
			}
			
			if(trovato == true)
			{
				rw.setCorretta(true);
			}
			
			ret.add(rw);
		}
		
		return ret;
	}
}
