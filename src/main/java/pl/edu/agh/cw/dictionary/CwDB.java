package pl.edu.agh.cw.dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Implementacja bazy s³ów krzy¿ówki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class CwDB {
	protected LinkedList<Entry> dictionary;
	protected int maxlength = 0;
	protected int minlength = 0;
	protected LinkedList<Integer> possibleLengths;
	
	/**
	 * Konstruktor
	 * 
	 * @param filename - nazwa pliku z baz¹ danych w formacie: "s³owo\n podpowiedŸ"
	 */
	public CwDB(String filename) {
		dictionary = new LinkedList<Entry>();
		possibleLengths = new LinkedList<Integer>();
		try{
			createDB(filename);
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		maxlength = this.findMaxWordLength();
		minlength = this.findMinWordLength();
	}
	/**
	 * Szuka d³ugoœci najd³u¿szego s³owa na liœcie wpisów.
	 * 
	 * @return d³ugoœæ najd³u¿szego s³owa w bazie
	 */
	public int findMaxWordLength(){
		if(dictionary.size()>0){
			int max_length = this.dictionary.getFirst().getWord().length();
	
			
			for(int i=1; i<this.dictionary.size(); i++){
				if(this.dictionary.get(i).getWord().length() > max_length){
					max_length = this.dictionary.get(i).getWord().length();
				}
			}
			
			return max_length;
		}
		return 0;
	}
	/**
	 * Szuka d³ugoœci najkrótszego s³owa na liœcie wpisów.
	 * 
	 * @return d³ugoœæ najkrótszego s³owa w bazie
	 */
	public int findMinWordLength(){
		if (dictionary.size()>0) {
			int min_length = this.dictionary.getFirst().getWord().length();
			for (int i = 1; i < this.dictionary.size(); i++) {
				if (this.dictionary.get(i).getWord().length() < min_length) {
					min_length = this.dictionary.get(i).getWord().length();
				}
			}
			return min_length;
		}
		return 0;
	}
	
	/**
	 * Dodaje nowy rekord do bazy wpisów
	 * 
	 * @param word - s³owo
	 * @param clue - podpowiedŸ
	 */
	public void add(String word, String clue){
		this.dictionary.add(new Entry(word,clue));
		//System.out.println(word);
		
		if(!this.possibleLengths.contains(word.length())){
			this.possibleLengths.add(word.length());
		}
		if(word.length() > this.maxlength){
			maxlength = word.length();
		}else if(word.length() < this.maxlength){
			minlength = word.length();
		}
	}
	/**
	 * Zwraca wpis w bazie danych z podanym s³owem
	 * 
	 * @param word - szukane s³owo
	 * @return wpis z podanym s³owem, null jeœli nie znaleziono rekordu zwi¹zanego z danym s³owem
	 */
	public Entry get(String word){
        ListIterator<Entry> iter = dictionary.listIterator(0);
        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp.getWord().equals(word)) {
                return temp;
            }
        }
        return null;
	}
	
	/**
	 * Usuwa wpis z podanym s³owem z bazy. Dodatkowo jeszcze raz przelicza najkrótsze i najd³u¿sze s³owo w bazie
	 * 
	 * @param word - s³owo
	 */
	public void remove(String word){
        ListIterator<Entry> iter = dictionary.listIterator(0);
        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp.getWord().equals(word)) {
                iter.remove();
            }
        }
		maxlength = this.findMaxWordLength();
		minlength = this.findMinWordLength();
	}
	/**
	 * Zapisuje bazê danych do pliku
	 * 
	 * @param filename - nazwa pliku wyjœciowego
	 * @throws IOException
	 */
	public void saveDB(String filename) throws IOException{
        try (FileWriter outputDB = new FileWriter(filename)) {
            ListIterator<Entry> iter = dictionary.listIterator(0);
            while (iter.hasNext()) {
                Entry temp = iter.next();
                outputDB.write(temp.toString());
            }
        }
	}
	
	/**
	 * Getter
	 * 
	 * @return rozmiar bazy (s³ownika)
	 */
	public int getSize(){
		return dictionary.size();
	}
	
	/**
	 * Getter
	 * 
	 * @return d³ugoœæ najd³u¿szego s³owa w bazie
	 */
	public int getMaxLength(){
		return maxlength;
	}
	
	/**
	 * Getter
	 * 
	 * @return d³ugoœæ najkrótszego s³owa w bazie
	 */
	public int getMinLength(){
		return minlength;
	}
	
	/**
	 * Tworzy w programie bazê danych z pliku
	 * 
	 * @param filename - nazwa pliku
	 * @throws IOException
	 */
	protected void createDB(String filename) throws FileNotFoundException{
		BufferedReader brIn=null;
		
		try {
			brIn = new BufferedReader(new FileReader(filename));
			String line;
			
			while((line = brIn.readLine()) != null)
			{
				add(line.toUpperCase(), brIn.readLine());
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
