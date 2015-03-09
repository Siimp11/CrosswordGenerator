package pl.edu.agh.cw.dictionary;

import java.util.Comparator;

/**
 * Komparator do wpisów w bazie danych do krzy¿ówki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class EntryComparator implements Comparator<Entry>{
	@Override
	public int compare(Entry a, Entry b){
		if( a.getWord().compareTo(b.getWord()) >= 0 ){
			return 1;
		}else if( a.getWord().compareTo(b.getWord()) == 0 ){
			return 0;
		}
		return -1;
	}


}
