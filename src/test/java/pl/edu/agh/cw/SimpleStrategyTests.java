package pl.edu.agh.cw;

import org.junit.Test;

import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

public class SimpleStrategyTests {

	@Test(expected=FailedToGenerateCrosswordException.class)
	public void generateSimpleStrategyCrossword_emptyDatabase_FailedToGenerateCrosswordExceptionThrown() throws FailedToGenerateCrosswordException {
		InteliCwDB db = new InteliCwDB("cwdbtest.txt");
		Crossword cw = new Crossword(db,1,1,1);

		cw.generate(new SimpleStrategy());

	}
	
	@Test(expected=FailedToGenerateCrosswordException.class)
	public void generateSimpleStrategyCrossword_1x1Size_FailedToGenerateCrosswordExceptionThrown() throws FailedToGenerateCrosswordException {
		InteliCwDB db = new InteliCwDB("cwdb.txt");
		Crossword cw = new Crossword(db,1,1,1);

		cw.generate(new SimpleStrategy());

	}
}
