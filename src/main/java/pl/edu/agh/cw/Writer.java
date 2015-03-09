package pl.edu.agh.cw;

import java.io.IOException;

/**
 * 
 * @author JakubSzczepankiewicz
 *
 */
public interface Writer {
	/**
	 * Zapisuje krzy��wk� do pliku
	 * @param cw - obiekt krzy��wki do zapisu
	 * @throws IOException
	 */
	public void write(Crossword cw) throws IOException;
	/**
	 * Zwraca unikalny identyfikator krzy��wki, czas w milisekundach
	 * @return unikalny identyfikator
	 */
	public long getUniqueID();
}
