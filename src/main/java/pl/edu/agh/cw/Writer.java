package pl.edu.agh.cw;

import java.io.IOException;

/**
 * 
 * @author JakubSzczepankiewicz
 *
 */
public interface Writer {
	/**
	 * Zapisuje krzy¿ówkê do pliku
	 * @param cw - obiekt krzy¿ówki do zapisu
	 * @throws IOException
	 */
	public void write(Crossword cw) throws IOException;
	/**
	 * Zwraca unikalny identyfikator krzy¿ówki, czas w milisekundach
	 * @return unikalny identyfikator
	 */
	public long getUniqueID();
}
