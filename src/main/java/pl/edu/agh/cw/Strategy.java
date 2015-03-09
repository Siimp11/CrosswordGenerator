package pl.edu.agh.cw;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Interfejs do implementacji r�nych strategii krzy��wki
 * @author JakubSzczepankiewicz
 *
 */
public abstract class Strategy {
	/**
	 * Wyszukuje has�o, kt�re mo�na doda� do krzy��wki
	 * @throws FailedToGenerateCrosswordException
	 * @param cw - docelowa krzy��wka
	 * @return znalezione has�o, null je�li nie mo�na doda� nowego has�a do krzy��wki
	 */
	public abstract CwEntry findEntry(Crossword cw) throws FailedToGenerateCrosswordException;
	/**
	 * Aktualizuje stan tablicy p�l krzy��wki, dodaje wpis do obiektu krzy��wki i zmienia atrybuty p�l
	 * @param b - tablica p�l
	 * @param e - wpis
	 */
	public abstract void updateBoard(Board b, CwEntry e);
}
