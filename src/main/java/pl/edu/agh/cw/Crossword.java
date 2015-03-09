package pl.edu.agh.cw;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.board.BoardCell;
import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Implementacja pojedynczej krzy¿ówki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class Crossword {
	/**
	 * Typ krzy¿ówki
	 * @author JakubSzczepankiewicz
	 * SIMPLE - @see SimpleStrategy
	 * MULTIC - @see MultiCrossStrategy
	 */
	public enum CwType {
		SIMPLE, MULTIC
	}

	/**
	 * Lista wpisów z bazy danych krzy¿ówki
	 */
	private LinkedList<CwEntry> entries;
	/**
	 * tablica pól krzy¿ówki
	 */
	private Board board;
	/**
	 * za³adowana baza danych
	 */
	private InteliCwDB database;
	/**
	 * unikalny identyfikator
	 */
	private final long ID;
	/**
	 * typ krzy¿ówki @see CwType
	 */
	private CwType type;

	/**
	 * Konstruktor
	 * @param db - baza danych
	 * @param width - szerokoœæ (w iloœci pól)
	 * @param height - wysokoœæ (w iloœci pól)
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
	 * @return iterator listy wpisów, tylko do odczytu
	 */
	public Iterator<CwEntry> getROEntryIter() {
		return entries.iterator();
		// return Collections.unmodifiableList(entries).iterator();
	}

	public LinkedList<CwEntry> getEntriesList() {
		return entries;
	}

	/**
	 * Zwraca kopiê tablicy pól krzy¿ówki
	 * @return kopia tablicy pól krzy¿ówki
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
	 * @return tablica pól krzy¿ówki
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
	 * @return identyfikator krzy¿ówki
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Getter
	 * @return typ krzy¿ówki @see CwType
	 */
	public CwType getType() {
		return type;
	}

	/**
	 * Setter
	 * @param t - typ krzy¿ówki @see CwType
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
	 * Sprawdza czy krzy¿ówka zawiera dane s³owo
	 * @param word - szukane s³owo
	 * @return true jeœli zawiera, false jeœli nie zawiera
	 */
	public boolean contains(String word) {
		ListIterator<CwEntry> i = entries.listIterator();
		while (i.hasNext())
			if (i.next().getWord().equals(word))
				return true;

		return false;
	}

	/**
	 * Dodaje nowe s³owo do krzy¿ówki i aktualizuje tablicê pól
	 * @param cwe - wpis krzy¿ówki 
	 * @param s - strategia, typ krzy¿ówki
	 */
	public final void addCwEntry(CwEntry cwe, Strategy s) {
		entries.add(cwe);
		s.updateBoard(board, cwe);
	}

	/**
	 * Funkcja generuj¹ca krzy¿ówkê wed³ug danej strategii
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
	 * Pomocnicza metoda wypisuj¹ca krzy¿ówkê do konsoli
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
	 * Pomocnicza metoda wypisuj¹ca podpowiedzi do hase³ do konsoli
	 */
	public void printSimpleStrategyClues() {
		for (int i = 1; i <= entries.size() - 1; i++) {
			System.out.println(i + ". " + entries.get(i).getClue() + " - "
					+ entries.get(i).getWord());
		}
	}
}