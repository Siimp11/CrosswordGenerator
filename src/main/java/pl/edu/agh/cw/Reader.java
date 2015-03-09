package pl.edu.agh.cw;

import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 * @author JakubSzczepankiewicz
 *
 */
public interface Reader {
	/**
	 * Wczytuje wszystkie krzy��wki do jednej bazy danych
	 * @return lista krzy��wek
	 * @throws IOException
	 */
	public LinkedList<Crossword> getAllCws() throws IOException;
}
