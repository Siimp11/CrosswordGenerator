package pl.edu.agh.cw.dictionary;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
/**
 * Implementacja inteligentnej bazy danych krzy¿ówki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class InteliCwDB extends CwDB {
	
	/**
	 * Konstruktor
	 * 
	 * @param filename - nazwa pliku z baz¹ danych
	 */
	public InteliCwDB(String filename) {
		super(filename);
	}
	
	/**
	 * Zwraca wszystkie wpisy w bazie danych, których s³owa pasuj¹ do wzorca (wyra¿enia regularnego)
	 * 
	 * @param pattern - wzorzec
	 * @return lista wpisów pasuj¹cych do wzorca
	 */
	public LinkedList<Entry> findAll(String pattern){
		LinkedList<Entry> matching = new LinkedList<Entry>();
		
		for(int i=0; i < dictionary.size();i++){
			if(dictionary.get(i).getWord().matches(pattern)){
				matching.add(dictionary.get(i));
			}
		}
		return matching;
	}
	
	/**
	 * Zwraca losowy wpis
	 * 
	 * @return losowy wpis
	 */
	public Entry getRandom(){
		Random rand = new Random();
		if(dictionary.size() > 0)
			return dictionary.get(rand.nextInt(dictionary.size()-1));
		return null;
	}
	
	/**
	 * Zwraca losowy wpis ze s³owej o danej d³ugoœci
	 * 
	 * @param length - d³ugoœæ s³owa
	 * @return losowy wpis o danej d³ugoœci, null jeœli nie znaleziono
	 */
	public Entry getRandom(int length){
		LinkedList<Entry> matching = new LinkedList<Entry>();
		
		for(int i=0; i < dictionary.size();i++){
			if(dictionary.get(i).getWord().length() == length){
				matching.add(dictionary.get(i));
			}
		}
		
		Random rand = new Random();
		if(matching.size() > 0)
			return matching.get(rand.nextInt(matching.size()-1));
		return null;
	}
	
	/**
	 * Zwraca losowy wpis pasuj¹cy do wzorca (wyra¿enia regularnego)
	 * 
	 * @param pattern - wzorzec (wyra¿enie regularne)
	 * @return wpis pasuj¹cy do wzorca, null jeœli nie znaleziono
	 */
	public Entry getRandom(String pattern){
		LinkedList<Entry> matching = new LinkedList<Entry>();
		
		matching = findAll(pattern);
		
		Random rand = new Random();
		if(matching.size() > 0)
			return matching.get(rand.nextInt(matching.size())); //-1
		return null;
	}
	
	@Override
	public void add(String word, String clue){
		this.dictionary.add(new Entry(word,clue));
		//System.out.println(word)
		Collections.sort(dictionary, new EntryComparator());
		
		if(!this.possibleLengths.contains(word.length())){
			this.possibleLengths.add(word.length());
		}
		if(word.length() > this.maxlength){
			maxlength = word.length();
		}else if(word.length() < this.maxlength){
			minlength = word.length();
		}
	}
}
