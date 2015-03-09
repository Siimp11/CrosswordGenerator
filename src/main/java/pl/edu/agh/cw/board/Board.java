package pl.edu.agh.cw.board;

import java.util.LinkedList;
/**
 * Implementacja zbioru p�l krzy��wki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class Board {
	/**
	 * tablica pola krzy��wki
	 */
	private BoardCell[][] cells;

	/**
	 * Konstruktor
	 * 
	 * @param w - szeroko�� krzy��wki (w ilo�ci p�l)
	 * @param h - wysoko�� krzy��wki (w ilo�ci p�l)
	 */
	public Board(int w, int h) {
		cells = new BoardCell[w][h];
		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; ++j)
				cells[i][j] = new BoardCell();
	}

	/**
	 * Konstruktor kopiuj�cy
	 *
	 */
	public Board(Board b) {
		this.cells = new BoardCell[b.getWidth()][b.getHeight()];

		cells = b.getAllCells();
	}

	/**
	 * Getter
	 * 
	 * @return szeroko�� krzy��wki (w ilo�ci p�l)
	 */
	public int getWidth() {
		return cells.length;
	}

	/**
	 * Getter
	 * 
	 * @return wysoko�� krzy��wki (w ilo�ci p�l)
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
	 * @param x - wsp�rz�dna pozioma
	 * @param y - wsp�rz�dna pionowa
	 * @return pole krzy��wki o wsp�rz�dnych (x,y)
	 */
	public BoardCell getCell(int x, int y) {
		return cells[x][y];
	}

	/**
	 * Setter
	 * @param x - pozioma wsp�rz�dna zmienianego pola
	 * @param y - pionowa wsp�rz�dna zmienianego pola
	 * @param c - nowe pole krzy��wki, kt�re b�dzie na wsp�rz�dnych x,y
	 */
	public void setCell(int x, int y, BoardCell c) {
		this.cells[x][y] = c;
	}

	/**
	 * Getter
	 * 
	 * @return lista p�l krzy��wki, kt�re mog� by� polami startowymi
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
	 * @return lista p�l krzy�owki, kt�re mog� by� polami startowymi poziomo
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
	 * @return lista p�l krzy�owki, kt�re mog� by� polami startowymi pionowo
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
     * Zwraca pionow� pozycj� pola krzy��wki
     * @param cell - pole krzy��wki
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
     * Zwraca poziom� pozycj� pola krzy��wki
     * @param cell - pole krzy��wki
     * @return pozioma pozycja pola krzy��wki
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
     * Metoda tworz�ca wzorzec (wyra�enie regularne) z p�l krzy��wki
     * @param fromx - pocz�tkowa wsp�rz�dna pozioma
     * @param fromy - pocz�tkowa wsp�rz�dna pionowa
     * @param tox - ko�cowa wsp�rz�dna pozioma
     * @param toy - ko�cowa wsp�rz�dna pionowa
     * @return wzorzec - wyra�enie regularne jako String
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
