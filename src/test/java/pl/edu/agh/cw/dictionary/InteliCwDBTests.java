package pl.edu.agh.cw.dictionary;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class InteliCwDBTests {
	@Test
	public void getRandom_databaseIsEmpty_nullReturned(){
		InteliCwDB db = new InteliCwDB("cwdbtest.txt");
		Entry e = db.getRandom();
		
		assertNull(e);
	}

	@Test
	public void getRandom_noEntryWithLengthFound_nullReturned(){
		InteliCwDB db = new InteliCwDB("cwdb.txt");
		Entry e = db.getRandom(20);
		
		assertNull(e);
	}
}
