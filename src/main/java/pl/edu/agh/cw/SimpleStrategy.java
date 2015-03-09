package pl.edu.agh.cw;

import java.util.Random;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.dictionary.Entry;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Implementacja prostej strategii krzy¿ówki - jedno pionowe has³o po lewej stronie i dopasowane poziome has³a
 * @author JakubSzczepankiewicz
 *
 */
public class SimpleStrategy extends Strategy{
	/**
	 * licznik wygenerowanych hase³
	 */
	private int generated = 0;
	
	/**
	 * wysokoœæ has³a g³ównego
	 */
	private int vert_length;
	private Random rand = new Random();
	
	/**
	 * @see Strategy#findEntry(Crossword)
	 */
	public CwEntry findEntry(Crossword cw) throws FailedToGenerateCrosswordException{
        if (cw.getDatabase().getSize() == 0) {
            throw new FailedToGenerateCrosswordException("Nie mo¿na wygenerowaæ krzy¿ówki. Baza s³ów jest pusta!");
        }
		if(generated == 0) {
			generated++;
			
			if(cw.getBoard().getHeight() > cw.getDatabase().getMaxLength()){
				return new CwEntry(cw.getDatabase().getRandom(cw.getDatabase().getMaxLength()), 0, 0, CwEntry.Direction.VERT );
			} else {
				Entry tmp = cw.getDatabase().getRandom(cw.getBoard().getHeight());
				for(int i=1; tmp==null ; i++){
					tmp = cw.getDatabase().getRandom(cw.getBoard().getHeight()-i);
					if(cw.getDatabase().getMaxLength()-i==0) throw new FailedToGenerateCrosswordException("W bazie danych znajduj¹ siê tylko s³owa d³u¿sze od podanej wysokoœci!");
				}
				vert_length = tmp.getWord().length();
				return new CwEntry(tmp, 0, 0, CwEntry.Direction.VERT );
			}
			
		} else if(generated > vert_length){
			return null;
		} else {
			generated++;
			int length = cw.getBoard().getWidth() > cw.getDatabase().getMaxLength() ? cw.getDatabase().getMaxLength() : cw.getBoard().getWidth();
			
			if(cw.getBoard().getWidth() > cw.getDatabase().getMaxLength()){
				return new CwEntry(cw.getDatabase().getRandom( cw.getBoard().createPattern(0, generated-2, rand.nextInt(length-cw.getDatabase().getMinLength())+cw.getDatabase().getMinLength(), generated-2)), 0, 0, CwEntry.Direction.HORIZ );
			} else {
				Entry tmp = cw.getDatabase().getRandom( cw.getBoard().createPattern(0, generated-2, rand.nextInt(length-cw.getDatabase().getMinLength())+cw.getDatabase().getMinLength(), generated-2) );
				int i=0;
				while(tmp==null || cw.contains(tmp.getWord())){ //ponowne losowanie
					i++;
					//System.out.println("losuje ponownie");
					tmp = cw.getDatabase().getRandom( cw.getBoard().createPattern(0, generated-2, rand.nextInt(length-cw.getDatabase().getMinLength())+cw.getDatabase().getMinLength(), generated-2) );
					if(i>50) throw new FailedToGenerateCrosswordException("Wyst¹pi³ problem podczas generowania krzy¿ówki! Spróbuj ponownie.");
				}
				return new CwEntry(tmp, 0, generated-2, CwEntry.Direction.HORIZ );
			}

		}
		
	}
	
	/**
	 * @see Strategy#findEntry(Crossword)
	 */
	public void updateBoard(Board b, CwEntry e){
		
		if(generated == 1){
			for(int i=0; i < e.getWord().length(); i++){
				b.getCell(e.getX(), e.getY()+i).setContent(e.getWord().substring(i,i+1));
			}
		} else {
			for(int i=0; i < e.getWord().length(); i++){
				b.getCell(e.getX()+i, e.getY()).setContent(e.getWord().substring(i,i+1));
			}
		}
		
		
	}
}
