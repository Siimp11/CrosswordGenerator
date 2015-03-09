package pl.edu.agh.cw.board;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTests {

	@Test
	public void createPattern_emptyBoard_returnEmptyString(){
		Board b = new Board(0,0);
		String s = b.createPattern(0, 0, 0, 0);
		
		assertTrue(s.equals(""));
	}

}
