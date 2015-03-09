package pl.edu.agh.cw.board;

import java.util.LinkedList;
/**
 * Implementacja zbioru pól krzy¿ówki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class Board {
	/**
	 * tablica pola krzy¿ówki
	 */
	private BoardCell[][] cells;

	/**
	 * Konstruktor
	 * 
	 * @param w - szerokoœæ krzy¿ówki (w iloœci pól)
	 * @param h - wysokoœæ krzy¿ówki (w iloœci pól)
	 */
	public Board(int w, int h) {
		cells = new BoardCell[w][h];
		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; ++j)
				cells[i][j] = new BoardCell();
	}

	/**
	 * Konstruktor kopiuj¹cy
	 *
	 */
	public Board(Board b) {
		this.cells = new BoardCell[b.getWidth()][b.getHeight()];

		cells = b.getAllCells();
	}

	/**
	 * Getter
	 * 
	 * @return szerokoœæ krzy¿ówki (w iloœci pól)
	 */
	public int getWidth() {
		return cells.length;
	}

	/**
	 * Getter
	 * 
	 * @return wysokoœæ krzy¿ówki (w iloœci pól)
	 */
	public int getHeight() {
		return cells[0].length;
	}

	public BoardCell[][] getAllCells() {
		return cells;
	}

	/**
	 * Getter
	 * 
	 * @param x - wspó³rzêdna pozioma
	 * @param y - wspó³rzêdna pionowa
	 * @return pole krzy¿ówki o wspó³rzêdnych (x,y)
	 */
	public BoardCell getCell(int x, int y) {
		return cells[x][y];
	}

	/**
	 * Setter
	 * @param x - pozioma wspó³rzêdna zmienianego pola
	 * @param y - pionowa wspó³rzêdna zmienianego pola
	 * @param c - nowe pole krzy¿ówki, które bêdzie na wspó³rzêdnych x,y
	 */
	public void setCell(int x, int y, BoardCell c) {
		this.cells[x][y] = c;
	}

	/**
	 * Getter
	 * 
	 * @return lista pól krzy¿ówki, które mog¹ byæ polami startowymi
	 */
	public LinkedList<BoardCell> getStartCells() {
		LinkedList<BoardCell> startcells = new LinkedList<BoardCell>();

		for (int i = 0; i < getWidth(); i++)
			for (int j = 0; j < getHeight(); j++)
				if (cells[i][j].isHorizStart() || cells[i][j].isVertStart())
					startcells.add(cells[i][j]);
		return startcells;
	}

	/**
	 * Getter
	 * 
	 * @return lista pól krzy¿owki, które mog¹ byæ polami startowymi poziomo
	 */
	public LinkedList<BoardCell> getHorizStartCells() {
		LinkedList<BoardCell> startcells = new LinkedList<BoardCell>();

		for (int i = 0; i < getWidth(); i++)
			for (int j = 0; j < getHeight(); j++)
				if (cells[i][j].isHorizStart())
					startcells.add(cells[i][j]);
		return startcells;
	}
	
	/**
	 * Getter
	 * 
	 * @return lista pól krzy¿owki, które mog¹ byæ polami startowymi pionowo
	 */
	public LinkedList<BoardCell> getVertStartCells() {
		LinkedList<BoardCell> startcells = new LinkedList<BoardCell>();

		for (int i = 0; i < getWidth(); i++)
			for (int j = 0; j < getHeight(); j++)
				if (cells[i][j].isVertStart())
					startcells.add(cells[i][j]);
		return startcells;
	}
	
    /**
     * Zwraca pionow¹ pozycjê pola krzy¿ówki
     * @param cell - pole krzy¿ówki
     * @return pionowa pozycja pola
     */
    public int getVerPosition(BoardCell cell) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (cells[i][j] == cell) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    /**
     * Zwraca poziom¹ pozycjê pola krzy¿ówki
     * @param cell - pole krzy¿ówki
     * @return pozioma pozycja pola krzy¿ówki
     */
    public int getHorPosition(BoardCell cell) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (cells[i][j] == cell) {
                    return i;
                }
            }
        }
        return -1;
    }
    
	/*public String createPattern(int fromx, int fromy, int tox, int toy) {
		StringBuffer pattern = new StringBuffer();
		if (fromx == tox) { //pionowo
			int x = toy - fromy;
			
			pattern.append("^");
			for (int i = 0; i <= x; i++)
				pattern.append(cells[fromx][fromy + i].getContent() != "" ? cells[fromx][fromy + i].getContent().charAt(0) : ".");

			pattern.append("$");
			return pattern.toString();
		} else if (fromy == toy) { //poziomo
			int x = tox - fromx;
			
			pattern.append("^");
			for (int i = 0; i <= x; i++)
				pattern.append(cells[fromx + i][fromy].getContent() != "" ? cells[fromx + i][fromy].getContent().charAt(0) : ".");

			pattern.append("$");
			return pattern.toString();
		} else
			// throw RuntimeException;
			return null; 
	}*/
    /**
     * Metoda tworz¹ca wzorzec (wyra¿enie regularne) z pól krzy¿ówki
     * @param fromx - pocz¹tkowa wspó³rzêdna pozioma
     * @param fromy - pocz¹tkowa wspó³rzêdna pionowa
     * @param tox - koñcowa wspó³rzêdna pozioma
     * @param toy - koñcowa wspó³rzêdna pionowa
     * @return wzorzec - wyra¿enie regularne jako String
     */
    public String createPattern(int fromx, int fromy, int tox, int toy) {
        String pattern = "";
        if (fromx == tox) {
            for (int i = fromy; i < toy; i++) {
                if (getCell(fromx, i).checkContent()) {
                    pattern += getCell(fromx, i).getContent();
                } else {
                    pattern += ".";
                }
            }
        } else if (fromy == toy) {
            for (int i = fromx; i < tox; i++) {
                if (getCell(i, fromy).checkContent()) {
                    pattern += getCell(i, fromy).getContent();
                } else {
                    pattern += ".";
                }
            }
        } 
        return pattern;
    }
}
