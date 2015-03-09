package pl.edu.agh.cw.board;

/**
 * Implementacja pojedynczego pola krzy��wki
 * 
 * @author JakubSzczepankiewicz
 *
 */
public class BoardCell {
	/**
	 * Zawarto�� pola
	 */
	private String content;
	/**
	 * Atrybuty pola krzy��wki
	 * Tablica zawieraj�ca informacje, czy dane pole mo�e by� pocz�tkiem/�rodkiem/ko�cem s�owa poziomego/pionowego
	 */
	private boolean[][] attributes;
	
	/**
	 * Zmienne pomocnicze to tablicy atrybut�w pola
	 */
    public static final int beg = 0;
    public static final int in = 1;
    public static final int end = 2;
    public static final int hor = 0;
    public static final int ver = 1;
    
    /**
     * Konstruktor
     */
	public BoardCell(){
		content = new String();
		content = "";
		
		attributes = new boolean[2][3]; // [HORIZ/VERT] x [START/INNER/END]
		
		for(int i=0;i<2;i++)
			for(int j=0;j<3;j++)
				attributes[i][j]=true;
	}

	/**
	 * Getter
	 * @return zawarto�� pola
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Setter
	 * @param content - docelowa zawarto�� pola
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * Setter
	 * @param content - docelowa zawarto�� pola jako char
	 */
    public void setContent(char content) {
        this.content = "" + content;
    }
    /**
     * Zwraca dany atrybut pola
     * @param pos - pozycja: 0 - pocz�tek, 1 - �rodek, 2 - koniec
     * @param dir - kierunek: 0 - poziomo, 1 - pionowo
     * @return warto�� logiczna danego atrybutu
     */
    public Boolean getAbility(int pos, int dir) {
        return attributes[dir][pos];
    }

    public void setAbility(boolean[][] attributes){
    	this.attributes = attributes;
    }
    
    /**
     * Sprawdza czy jest jaka� zawarto�� w polu
     * @return true, gdy pole ma zawarto��; false, gdy pole nie ma zawarto�ci
     */
    public boolean checkContent() {
        return getContent().length() > 0;
    }
    
    /**
     * Ustawia wszystkie atrybuty pola na false
     */
    public void disableAll(){
		for(int i=0;i<2;i++)
			for(int j=0;j<3;j++)
				attributes[i][j]=false;
    }
	public boolean isHorizStart(){
		return attributes[0][0];
	}
	public boolean isHorizInner(){
		return attributes[0][1];
	}
	public boolean isHorizEnd(){
		return attributes[0][2];
	}
	public boolean isVertStart(){
		return attributes[1][0];
	}
	public boolean isVertInner(){
		return attributes[1][1];
	}
	public boolean isVertEnd(){
		return attributes[1][2];
	}

	public void disableHorizStart(){
		attributes[0][0]=false;
	}
	public void enableHorizStart(){
		attributes[0][0]=true;
	}
	public void disableHorizInner(){
		attributes[0][1]=false;
	}
	public void enableHorizInner(){
		attributes[0][1]=true;
	}
	public void disableHorizEnd(){
		attributes[0][2]=false;
	}
	public void enableHorizEnd(){
		attributes[0][2]=true;
	}
	public void disableVertStart(){
		attributes[1][0]=false;
	}
	public void enableVertStart(){
		attributes[1][0]=true;
	}
	public void disableVertInner(){
		attributes[1][1]=false;
	}
	public void enableVertInner(){
		attributes[1][1]=true;
	}
	public void disableVertEnd(){
		attributes[1][2]=false;
	}
	public void enableVertEnd(){
		attributes[1][2]=true;
	}
    /**
     * Ustawia atrybuty pionowe na false
     */
    public void disableVert() {
        attributes[ver][beg] = false;
        attributes[ver][in] = false;
        attributes[ver][end] = false;
    }

    /**
     * Ustawia atrybuty poziome na false
     */
    public void disableHoriz() {
    	attributes[hor][beg] = false;
    	attributes[hor][in] = false;
    	attributes[hor][end] = false;
    }
}
