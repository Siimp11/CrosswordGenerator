package pl.edu.agh.cw;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Interfejs do implementacji ró¿nych strategii krzy¿ówki
 * @author JakubSzczepankiewicz
 *
 */
public abstract class Strategy {
	/**
	 * Wyszukuje has³o, które mo¿na dodaæ do krzy¿ówki
	 * @throws FailedToGenerateCrosswordException
	 * @param cw - docelowa krzy¿ówka
	 * @return znalezione has³o, null jeœli nie mo¿na dodaæ nowego has³a do krzy¿ówki
	 */
	public abstract CwEntry findEntry(Crossword cw) throws FailedToGenerateCrosswordException;
	/**
	 * Aktualizuje stan tablicy pól krzy¿ówki, dodaje wpis do obiektu krzy¿ówki i zmienia atrybuty pól
	 * @param b - tablica pól
	 * @param e - wpis
	 */
	public abstract void updateBoard(Board b, CwEntry e);
}
