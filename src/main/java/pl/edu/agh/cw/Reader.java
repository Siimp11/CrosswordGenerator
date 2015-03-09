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
	 * Wczytuje wszystkie krzy¿ówki do jednej bazy danych
	 * @return lista krzy¿ówek
	 * @throws IOException
	 */
	public LinkedList<Crossword> getAllCws() throws IOException;
}
