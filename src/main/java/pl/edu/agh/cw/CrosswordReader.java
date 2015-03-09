package pl.edu.agh.cw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import pl.edu.agh.cw.Crossword.CwType;
import pl.edu.agh.cw.CwEntry.Direction;

/**
 * Implementacja interfejsu Reader dla krzy¿ówek
 * @see Reader
 * @author JakubSzczepankiewicz
 *
 */
public class CrosswordReader implements Reader {
	/**
	 * Œcie¿ka katalogu do wczytania WSZYSTKICH krzy¿ówek, które zawiera
	 */
	private String dir;
	
	/**
	 * Konstruktor
	 * @param dir - Œcie¿ka katalogu do wczytania WSZYSTKICH krzy¿ówek, które zawiera
	 */
	public CrosswordReader(String dir){
		this.dir = dir;
	}
	
	/**
	 * Getter
	 * @return - Œcie¿ka katalogu do wczytania WSZYSTKICH krzy¿ówek, które zawiera
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * Setter
	 * @param dir - Œcie¿ka katalogu do wczytania WSZYSTKICH krzy¿ówek, które zawiera
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	/**
	 * @see Reader#getAllCws()
	 */
	@Override
	public LinkedList<Crossword> getAllCws() throws IOException {
		LinkedList<Crossword> list = new LinkedList<Crossword>();
		File[] files = (new File(dir)).listFiles();
		
		if(files == null){
			throw new FileNotFoundException();
		}
		
		for(int i=0; i<files.length; i++){
			if(!files[i].toString().endsWith(".cwd"))
			{
				continue;
			}
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(files[i]));
				
				String[] parts = br.readLine().split(".");
				
				if(!parts[0].equals("TYPE")){
					continue;
				}
				
				long id = Long.parseLong(files[i].getName());
				CwType type = (parts[1].equals("s") ? CwType.SIMPLE : CwType.MULTIC);
				
				parts = br.readLine().split(".");
				
				if(!parts[0].equals("BOARD")){
					continue;
				}
				
				Crossword cw = new Crossword(null, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), id);
				
				String line;
				while((line=br.readLine()) != null){
					parts = line.split(".");
					
					CwEntry cwe = new CwEntry(parts[3], parts[4], Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), (parts[2].equals("h") ? Direction.HORIZ : Direction.VERT) );

					cw.addCwEntry(cwe, type.equals(CwType.SIMPLE) ? new SimpleStrategy() : new MultiCrossStrategy() );
				}
				br.close();
				
				list.add(cw);
			} catch(Exception ex) {
				continue;
			}
		}
		return list;
	}

}
