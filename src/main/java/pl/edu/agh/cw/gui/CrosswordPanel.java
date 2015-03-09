package pl.edu.agh.cw.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JPanel;

import pl.edu.agh.cw.Crossword;
import pl.edu.agh.cw.CwEntry;
import pl.edu.agh.cw.board.Board;

@SuppressWarnings("serial")
public class CrosswordPanel extends JPanel implements MouseListener, KeyListener{
	private Crossword cwd;
	private Board board;
	private Font cwfont = new Font("Arial Narrow", Font.PLAIN, 9);

	private int currentX, currentY;

	public void solveCurrentCell(){
		board.getCell(currentX, currentY).setContent(cwd.getBoard().getCell(currentX, currentY).getContent());
		repaint();
	}

	public void solveCrossword(){
		for(int i=0; i<board.getWidth(); i++){
			for(int j=0; j<board.getHeight(); j++){
				if (board.getCell(i, j).getContent().equals("")){
					board.getCell(i, j).setContent(cwd.getBoard().getCell(i, j).getContent());
				}
			}
		}
		repaint();
	}
	public CrosswordPanel() {
		cwd = null;
		board = null;
		currentX = 0;
		currentY = 0;

		setRequestFocusEnabled(true);
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
	}

	public void setCrossword(Crossword cwd) {
		this.cwd = cwd;
		board = cwd.getBoardCopy();
		
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.getCell(i, j).getContent().equals("")) {
					board.getCell(i, j).setContent("#");
				} else {
					board.getCell(i, j).setContent("");
				}
			}
		}
	}

	public Crossword getCrossword() {
		return cwd;
	}

	public void setCurrentCell(int x, int y) {
		if(x < 0 || x > board.getWidth()-1 && y < 0 || y > board.getHeight()-1)
		{
			return;
		}
		
		currentX = x;
		currentY = y;
		
		System.out.println("x " + x);
		System.out.println("y " + y);
		
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		g2.setFont(cwfont);
		if (cwd != null) {
			for (int i = 0; i < cwd.getBoard().getWidth(); i++) {
				for (int j = 0; j < cwd.getBoard().getHeight(); j++){
					if (!cwd.getBoard().getCell(i, j).getContent().equals("")) {
						if (i==0 && (cwd.getType().equals(Crossword.CwType.SIMPLE)) ) { //szare haslo w prostej krzyzowce
							g2.setColor(Color.GRAY);
							g2.fillRect(20 + 20 * i, 20 + 20 * j, 20, 20);
							g2.setColor(Color.BLACK);
							g2.drawString(new Integer(j + 1).toString(), 10,
									35 + 20 * j);
						}
						if(i==currentX && j ==currentY){
							g2.setColor(Color.CYAN);
							g2.fillRect(20 + 20 * i, 20 + 20 * j, 20, 20);
							g2.setColor(Color.BLACK);
						}else if(board.getCell(i,j).getContent().equals("")){
								g2.drawRect(20 + 20 * i, 20 + 20 * j, 20, 20);
						}else if(board.getCell(i,j).getContent().equals(cwd.getBoard().getCell(i,j).getContent())) { //poprawna komorka
							g2.setColor(Color.GREEN);
							g2.drawRect(20 + 20 * i, 20 + 20 * j, 20, 20);
							g2.setColor(Color.BLACK);
						}else { //bledna komorka
							g2.setColor(Color.RED);
							g2.drawRect(20 + 20 * i, 20 + 20 * j, 20, 20);
							g2.setColor(Color.BLACK);
						}
					} else { //puste pole
						g2.setColor(Color.LIGHT_GRAY);
						g2.fillRect(20 + 20 * i, 20 + 20 * j, 20, 20);
						g2.setColor(Color.BLACK);
					}
					if(!board.getCell(i,j).getContent().equals("#")){
						g2.setFont(new Font("Arial Narrow", Font.PLAIN, 12));
						g2.drawString(board.getCell(i, j).getContent(), 20+20*i+10, 20+20*j+15); //wprowadzone rozwiazanie
						g2.setFont(cwfont);
					}
				}
			}
			if(cwd.getType().equals(Crossword.CwType.SIMPLE)){
				for (int i = 1; i < cwd.getEntriesList().size(); i++) {
					g2.drawString(new Integer(i).toString() + ". "
							+ cwd.getEntriesList().get(i).getClue(), 450,
							20 + 10 * i);
				}
			}else{
				for (int i = 0; i < cwd.getEntriesList().size(); i++) {
					g2.drawString(new Integer(i+1).toString() + ". "
							+ cwd.getEntriesList().get(i).getClue(), 450,
							20 + 10 * i);
				}
			}
			if(cwd.getType().equals(Crossword.CwType.MULTIC)){
				Iterator<CwEntry> iter = cwd.getROEntryIter();
				
				int count = 0;
				while(iter.hasNext()){
					CwEntry cwe = iter.next();
					count++;
					
					if(cwe.getD() == CwEntry.Direction.VERT){
						g2.drawString(new Integer(count).toString(), 20+20*cwe.getX()+8, 20+20*cwe.getY()-3);
					}else {
						g2.drawString(new Integer(count).toString(), 20+20*cwe.getX()-10, 20+20*cwe.getY()+10);
					}
				}
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {
		requestFocus();
		
		if(cwd == null)
		{
			return;
		}
		
		setCurrentCell((e.getX()-20) / 20, (e.getY()-20) / 20);
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DELETE){
			if(board.getCell(currentX, currentY).getContent().equals("#")){
				return;
			}
			board.getCell(currentX, currentY).setContent("");

		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			if(currentY > 0){
				setCurrentCell(currentX, currentY-1);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(currentY < board.getHeight()-1){
				setCurrentCell(currentX, currentY+1);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(currentX > 0){
				setCurrentCell(currentX-1, currentY);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(currentX < board.getWidth()-1){
				setCurrentCell(currentX+1, currentY);
			}
		}
		repaint();
	}
	public void keyTyped(KeyEvent e){
		if(cwd == null){
			return;
		}
		
		char c = e.getKeyChar();
		if(e.getKeyChar() == '\b'){
			if(board.getCell(currentX, currentY).getContent().equals("#")){
				return;
			}
			board.getCell(currentX, currentY).setContent("");
		}else if(c != '#'){
			c = (""+c).toUpperCase().charAt(0);
			
			board.getCell(currentX, currentY).setContent("" + c);
		}
		repaint();
	}
}
