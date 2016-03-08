import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import exceptions.InvalidInputException;
import settings.Settings;

/**
 * testings for 'undo' operations.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 */
public class CalculatorUndoOperationTest {
	private Calculator calculator;
	
	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}

	@Test
	public void testMultipleUndoInOneInputLine() throws InvalidInputException {
		String inputLine = "5 4 3 2 undo undo * 5 * undo";
		calculator.processUserInput(inputLine);
		assertEquals("[20, 5]", Arrays.toString(calculator.getResultStack().toArray()));
	}	

	@Test
	public void testMultipleUndoInMultipleInputLine() throws InvalidInputException {
		String inputLine = "5 4 3 2";
		calculator.processUserInput(inputLine);
		//calculator.showAllJournalStackValues();
		//calculator.showAllResultStackValues();
		
		inputLine =  "undo undo *";
		calculator.processUserInput(inputLine);
		
		inputLine = "5 *";
		calculator.processUserInput(inputLine);
		
		inputLine = "undo";
		calculator.processUserInput(inputLine);
		
		assertEquals("[20, 5]", Arrays.toString(calculator.getResultStack().toArray()));
	}	
}
