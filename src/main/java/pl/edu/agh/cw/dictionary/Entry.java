package pl.edu.agh.cw.dictionary;

/**
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class Entry {
	private String word;
	private String clue;
	/**
	 * Konstruktor
	 * 
	 * @param word - s�owo krzy��wki
	 * @param clue - podpowied� do s�owa krzy��wki
	 */
	public Entry(String word, String clue) {
		this.word = word;
		this.clue = clue;
	}
	/**
	 * Getter
	 * 
	 * @return s�owo krzy��wki
	 */
	public String getWord() {
		return word;
	}
	/**
	 * Getter
	 * 
	 * @return podpowied� do s�owa krzy��wki
	 */
	public String getClue() {
		return clue;
	}

}
