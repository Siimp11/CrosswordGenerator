package pl.edu.agh.cw;

import pl.edu.agh.cw.dictionary.Entry;

/**
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class CwEntry extends Entry{
	/**
	 * Kierunek wpisu
	 * HORIZ - poziomo
	 * VERT - pionowo
	 * 
	 * @author Simp
	 *
	 */
	public enum Direction {
		HORIZ, VERT
	}
	
	/**
	 * pozioma wspó³rzêdna wpisu na tablicy krzy¿ówki
	 */
	private int x; //pozioma wsp
	/**
	 * pionowa wspó³rzêdna wpisu na tablicy krzy¿ówki
	 */
	private int y; //pionowa wsp
	/**
	 * kierunek wpisu
	 */
	private Direction d;
	
	/**
	 * Konstruktor
	 * @param word - s³owo
	 * @param clue - podpowiedŸ
	 * @param _x - pozioma wspó³rzêdna
	 * @param _y - pionowa wspo³rzêdna
	 * @param _d - kierunek wpisu @see Direction
	 */
	public CwEntry(String word, String clue, int _x, int _y, Direction _d){
		super(word,clue);
		x = _x;
		y = _y;
		d = _d;
	}
	
	/**
	 * Konstruktor
	 * @param e - wpis @see Entry
	 * @param _x - pozioma wspó³rzêdna
	 * @param _y - pionowa wspó³rzêdna
	 * @param _d - kierunek wpisu @see Direction
	 */
	public CwEntry(Entry e, int _x, int _y, Direction _d){
		super(e.getWord(), e.getClue());
		x = _x;
		y = _y;
		d = _d;
	}

	/**
	 * Getter
	 * 
	 * @return pozioma wspó³rzêdna
	 */
	public int getX() {
		return x;
	}
	/**
	 * Getter
	 * 
	 * @return pionowa wspó³rzêdna
	 */
	public int getY() {
		return y;
	}
	/**
	 * Getter
	 * 
	 * @return kierunek wpisu @see Direction
	 */
	public Direction getD() {
		return d;
	}
}
