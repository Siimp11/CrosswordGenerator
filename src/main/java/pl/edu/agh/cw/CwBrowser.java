package pl.edu.agh.cw;

import java.io.IOException;
import java.util.LinkedList;

import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Klasa, w kt�rej przechowywane s� wszystkie w danym momencie za�adowane do programu krzy��wki
 * @author JakubSzczepankiewicz
 *
 */
public class CwBrowser {
	private CrosswordReader reader;
	private CrosswordWriter writer;
	private String dir;
	/**
	 * Lista za�adowanych krzy��wek
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
	 * Generuje now� krzy��wk� wed�ug SimpleStrategy
	 * @see SimpleStrategy
	 * @param width - szeroko�� krzy��wki (w ilo��i p�l)
	 * @param height - wysoko�� krzy��wki (w ilo�ci p�l)
	 * @param cwDB - baza danych, z kt�rej maj� by� wybierane s�owa
	 * @return wygenerowana krzy��wka
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
	 * Generuje now� krzy��wk� wed�ug MultiCrossStrategy
	 * @see MultiCrossStrategy
	 * @param width - szeroko�� krzy��wki (w ilo��i p�l)
	 * @param height - wysoko�� krzy��wki (w ilo�ci p�l)
	 * @param cwDB - baza danych, z kt�rej maj� by� wybierane s�owa
	 * @return wygenerowana krzy��wka
	 */
	public Crossword generateMultiCross(int width, int height, InteliCwDB cwDB) throws FailedToGenerateCrosswordException{
		MultiCrossStrategy strat = new MultiCrossStrategy();

		Crossword cw = new Crossword(cwDB, width, height, -1);
		cw.generate(strat);

		cwlist.add(cw);
		return cw;
	}

	/**
	 * Wczytanie wszystkich krzy��wek z modu�u CrosswordReader
	 * @see CrosswordReader
	 * @throws IOException
	 */
	public void loadAll() throws IOException {
		cwlist.addAll( reader.getAllCws() );
		index=cwlist.size();
	}

	/**
	 * Zapisanie krzy��wki do pliku za pomoc� modu�u CrosswordWriter
	 * @see CrosswordWriter
	 * @param cw - krzy��wka do zapisu
	 * @throws IOException
	 */
	public void save(Crossword cw) throws IOException {
		writer.write(cw);
	}
	
	/**
	 * Getter
	 * @return ilo�� za�adowanych krzy��wek
	 */
	public int getCwCount() {
		return cwlist.size();
	}
	
	/**
	 * Getter
	 * @param index - indeks
	 * @return krzy��wka o danym indeksie w li�cie
	 */
	public Crossword getCrossword(int index) {
		return cwlist.get(index-1);
	}
	
}
