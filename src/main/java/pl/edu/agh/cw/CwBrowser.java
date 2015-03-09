package pl.edu.agh.cw;

import java.io.IOException;
import java.util.LinkedList;

import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Klasa, w której przechowywane s¹ wszystkie w danym momencie za³adowane do programu krzy¿ówki
 * @author JakubSzczepankiewicz
 *
 */
public class CwBrowser {
	private CrosswordReader reader;
	private CrosswordWriter writer;
	private String dir;
	/**
	 * Lista za³adowanych krzy¿ówek
	 */
	private LinkedList<Crossword> cwlist = new LinkedList<Crossword>();
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public CwBrowser(String dir) {
		this.dir = dir;

		reader = new CrosswordReader(dir);
		writer = new CrosswordWriter(dir);
		
		index = 0;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;

		reader.setDir(dir);
		writer.setDir(dir);
	}

	/**
	 * Generuje now¹ krzy¿ówkê wed³ug SimpleStrategy
	 * @see SimpleStrategy
	 * @param width - szerokoœæ krzy¿ówki (w iloœæi pól)
	 * @param height - wysokoœæ krzy¿ówki (w iloœci pól)
	 * @param cwDB - baza danych, z której maj¹ byæ wybierane s³owa
	 * @return wygenerowana krzy¿ówka
	 */
	public Crossword generateSimple(int width, int height, InteliCwDB cwDB) throws FailedToGenerateCrosswordException{
		SimpleStrategy strat = new SimpleStrategy();

		Crossword cw = new Crossword(cwDB, width, height, -1);
		cw.generate(strat);

		cwlist.add(cw);
		this.index = cwlist.indexOf(cw)+1;
		return cw;
	}

	/**
	 * Generuje now¹ krzy¿ówkê wed³ug MultiCrossStrategy
	 * @see MultiCrossStrategy
	 * @param width - szerokoœæ krzy¿ówki (w iloœæi pól)
	 * @param height - wysokoœæ krzy¿ówki (w iloœci pól)
	 * @param cwDB - baza danych, z której maj¹ byæ wybierane s³owa
	 * @return wygenerowana krzy¿ówka
	 */
	public Crossword generateMultiCross(int width, int height, InteliCwDB cwDB) throws FailedToGenerateCrosswordException{
		MultiCrossStrategy strat = new MultiCrossStrategy();

		Crossword cw = new Crossword(cwDB, width, height, -1);
		cw.generate(strat);

		cwlist.add(cw);
		return cw;
	}

	/**
	 * Wczytanie wszystkich krzy¿ówek z modu³u CrosswordReader
	 * @see CrosswordReader
	 * @throws IOException
	 */
	public void loadAll() throws IOException {
		cwlist.addAll( reader.getAllCws() );
		index=cwlist.size();
	}

	/**
	 * Zapisanie krzy¿ówki do pliku za pomoc¹ modu³u CrosswordWriter
	 * @see CrosswordWriter
	 * @param cw - krzy¿ówka do zapisu
	 * @throws IOException
	 */
	public void save(Crossword cw) throws IOException {
		writer.write(cw);
	}
	
	/**
	 * Getter
	 * @return iloœæ za³adowanych krzy¿ówek
	 */
	public int getCwCount() {
		return cwlist.size();
	}
	
	/**
	 * Getter
	 * @param index - indeks
	 * @return krzy¿ówka o danym indeksie w liœcie
	 */
	public Crossword getCrossword(int index) {
		return cwlist.get(index-1);
	}
	
}
