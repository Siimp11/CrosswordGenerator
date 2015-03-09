package pl.edu.agh.cw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import pl.edu.agh.cw.Crossword.CwType;
import pl.edu.agh.cw.CwEntry.Direction;

/**
 * Implementacja interfejsu Writer dla krzy¿ówek
 * @see Writer
 * @author JakubSzczepankiewicz
 *
 */
public class CrosswordWriter implements Writer {
	/**
	 * œcie¿ka katalogu do zapisu
	 */
	private String dir;
	
	/**
	 * Konstruktor
	 * @param dir - œcie¿ka katalogu do zapisu
	 */
	public CrosswordWriter(String dir){
		this.dir = dir;
	}
	
	/**
	 * Getter
	 * @return - œcie¿ka katalogu do zapisu
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * Setter
	 * @param dir - œcie¿ka katalogu do zapisu
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @see Writer#write(Crossword)
	 */
	@Override
	public void write(Crossword cw) throws IOException {
		long ID = getUniqueID();
		
		String filename = String.format("%d.cwd", ID);
		
		FileWriter fw = new FileWriter(new File(filename));
		fw.write(String.format("TYPE.%s\n", cw.getType().equals(CwType.SIMPLE) ? "s" : "m" ));
		fw.write(String.format("BOARD.%d.%d\n", cw.getBoard().getWidth(), cw.getBoard().getWidth()));
		
		Iterator<CwEntry> iter = cw.getROEntryIter();
		
		CwEntry e;
		while(iter.hasNext()){
			e=iter.next();
			fw.write(String.format("%d.%d.%s.%s.%s\n", e.getX(), e.getY(), (e.getD().equals(Direction.HORIZ) ? "h" : "v"), e.getWord(), e.getClue() ));
		}
		fw.close();
	}

	/**
	 * @see Writer#getUniqueID()
	 */
	@Override
	public long getUniqueID() {
		return System.currentTimeMillis();
	}

}
