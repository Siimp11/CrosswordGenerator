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
	 * @param word - s³owo krzy¿ówki
	 * @param clue - podpowiedŸ do s³owa krzy¿ówki
	 */
	public Entry(String word, String clue) {
		this.word = word;
		this.clue = clue;
	}
	/**
	 * Getter
	 * 
	 * @return s³owo krzy¿ówki
	 */
	public String getWord() {
		return word;
	}
	/**
	 * Getter
	 * 
	 * @return podpowiedŸ do s³owa krzy¿ówki
	 */
	public String getClue() {
		return clue;
	}

}
