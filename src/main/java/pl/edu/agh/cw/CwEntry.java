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
	 * pozioma wsp�rz�dna wpisu na tablicy krzy��wki
	 */
	private int x; //pozioma wsp
	/**
	 * pionowa wsp�rz�dna wpisu na tablicy krzy��wki
	 */
	private int y; //pionowa wsp
	/**
	 * kierunek wpisu
	 */
	private Direction d;
	
	/**
	 * Konstruktor
	 * @param word - s�owo
	 * @param clue - podpowied�
	 * @param _x - pozioma wsp�rz�dna
	 * @param _y - pionowa wspo�rz�dna
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
	 * @param _x - pozioma wsp�rz�dna
	 * @param _y - pionowa wsp�rz�dna
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
	 * @return pozioma wsp�rz�dna
	 */
	public int getX() {
		return x;
	}
	/**
	 * Getter
	 * 
	 * @return pionowa wsp�rz�dna
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
