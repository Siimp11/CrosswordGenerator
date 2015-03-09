package pl.edu.agh.cw;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.board.BoardCell;
import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Implementacja pojedynczej krzy��wki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class Crossword {
	/**
	 * Typ krzy��wki
	 * @author JakubSzczepankiewicz
	 * SIMPLE - @see SimpleStrategy
	 * MULTIC - @see MultiCrossStrategy
	 */
	public enum CwType {
		SIMPLE, MULTIC
	}

	/**
	 * Lista wpis�w z bazy danych krzy��wki
	 */
	private LinkedList<CwEntry> entries;
	/**
	 * tablica p�l krzy��wki
	 */
	private Board board;
	/**
	 * za�adowana baza danych
	 */
	private InteliCwDB database;
	/**
	 * unikalny identyfikator
	 */
	private final long ID;
	/**
	 * typ krzy��wki @see CwType
	 */
	private CwType type;

	/**
	 * Konstruktor
	 * @param db - baza danych
	 * @param width - szeroko�� (w ilo�ci p�l)
	 * @param height - wysoko�� (w ilo�ci p�l)
	 * @param ID - identyfikator
	 */
	public Crossword(InteliCwDB db, int width, int height, long ID) {
		entries = new LinkedList<CwEntry>();
		board = new Board(width, height);
		database = db;
		this.ID = ID;
	}

	/**
	 * Getter
	 * @return iterator listy wpis�w, tylko do odczytu
	 */
	public Iterator<CwEntry> getROEntryIter() {
		return entries.iterator();
		// return Collections.unmodifiableList(entries).iterator();
	}

	public LinkedList<CwEntry> getEntriesList() {
		return entries;
	}

	/**
	 * Zwraca kopi� tablicy p�l krzy��wki
	 * @return kopia tablicy p�l krzy��wki
	 */
	public Board getBoardCopy() {
		Board copy = new Board(board.getWidth(), board.getHeight());
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				BoardCell bc = new BoardCell();

					bc.setContent(board.getCell(i, j).getContent());

				if (!board.getCell(i, j).isHorizStart())
					bc.disableHorizStart();

				if (!board.getCell(i, j).isHorizInner())
					bc.disableHorizInner();

				if (!board.getCell(i, j).isHorizEnd())
					bc.disableHorizEnd();

				if (!board.getCell(i, j).isVertStart())
					bc.disableVertStart();

				if (!board.getCell(i, j).isVertInner())
					bc.disableVertInner();

				if (!board.getCell(i, j).isVertEnd())
					bc.disableVertEnd();

				copy.setCell(i, j, bc);
			}
		}
		return copy;
	}

	/**
	 * Getter
	 * @return tablica p�l krzy��wki
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Getter
	 * @return baza danych z pliku
	 */
	public InteliCwDB getDatabase() {
		return database;
	}

	/**
	 * Getter
	 * @return identyfikator krzy��wki
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Getter
	 * @return typ krzy��wki @see CwType
	 */
	public CwType getType() {
		return type;
	}

	/**
	 * Setter
	 * @param t - typ krzy��wki @see CwType
	 */
	public void setType(CwType t) {
		type = t;
	}

	/**
	 * Setter
	 * @param db - baza danych z pliku
	 */
	public void setDatabase(InteliCwDB db) {
		this.database = db;
	}

	/**
	 * Sprawdza czy krzy��wka zawiera dane s�owo
	 * @param word - szukane s�owo
	 * @return true je�li zawiera, false je�li nie zawiera
	 */
	public boolean contains(String word) {
		ListIterator<CwEntry> i = entries.listIterator();
		while (i.hasNext())
			if (i.next().getWord().equals(word))
				return true;

		return false;
	}

	/**
	 * Dodaje nowe s�owo do krzy��wki i aktualizuje tablic� p�l
	 * @param cwe - wpis krzy��wki 
	 * @param s - strategia, typ krzy��wki
	 */
	public final void addCwEntry(CwEntry cwe, Strategy s) {
		entries.add(cwe);
		s.updateBoard(board, cwe);
	}

	/**
	 * Funkcja generuj�ca krzy��wk� wed�ug danej strategii
	 * @param s - strategia
	 */
	public final void generate(Strategy s) throws FailedToGenerateCrosswordException {
		if (s instanceof SimpleStrategy) {
			this.setType(Crossword.CwType.SIMPLE);
		} else if (s instanceof MultiCrossStrategy) {
			this.setType(Crossword.CwType.MULTIC);
		}

		CwEntry e = null;
		while ((e = s.findEntry(this)) != null) {
			addCwEntry(e, s);
		}
	}

	/**
	 * Pomocnicza metoda wypisuj�ca krzy��wk� do konsoli
	 */
	public void printCrossword() {
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				if (board.getCell(j, i).getContent() == "") {
					System.out.print("[   ]");
				} else {
					System.out.print("[" + " "
							+ board.getCell(j, i).getContent() + " " + "]");
				}

			}
			System.out.println();
		}
	}

	/**
	 * Pomocnicza metoda wypisuj�ca podpowiedzi do hase� do konsoli
	 */
	public void printSimpleStrategyClues() {
		for (int i = 1; i <= entries.size() - 1; i++) {
			System.out.println(i + ". " + entries.get(i).getClue() + " - "
					+ entries.get(i).getWord());
		}
	}
}