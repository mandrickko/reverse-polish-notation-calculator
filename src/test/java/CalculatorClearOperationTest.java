import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import exceptions.InvalidInputException;
import settings.Settings;

/** 
 * Testings for 'clear' operations.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 *
 */
public class CalculatorClearOperationTest {
	private Calculator calculator;
	
	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}

	@Test
	public void testProcessClearInSeparateLines() throws InvalidInputException {
		String value1 = "12";
		String value2 = "2";		
		String value3 = "clear";

		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
		calculator.processUserInput(value3);

		assertEquals(null, calculator.getResultStack().peek());
		assertEquals(null, calculator.getJournalStack().peek());
		assertEquals(0, calculator.getOperatorHashMap().size());
	}	
	
	@Test
	public void testMultiplicationClearAndSubtractionCombinations() throws InvalidInputException {
		String value1 = "1 2 3 4 5";
		String value2 = "*";		
		String value3 = "clear 3 4 -";

		calculator.processUserInput(value1);
		assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(calculator.getJournalStack().toArray()));			
		
		calculator.processUserInput(value2);
		assertEquals("[1, 2, 3, 20]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[1, 2, 3, 4, 5, *]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("*"));

		calculator.processUserInput(value3);
		assertEquals("[-1]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[3, 4, -]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(null, calculator.getOperatorHashMap().get("*"));
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("-"));
	}	
		
	@Test
	public void testSubtractionAndClearInSeparateLines() throws InvalidInputException {
		String value1 = "5 2 -";
		String value2 = "3 -";		
		String value3 = "clear";
		String operator = "-";

		calculator.processUserInput(value1);
		assertEquals("[3]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2, -]", Arrays.toString(calculator.getJournalStack().toArray()));			
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get(operator));
		
		calculator.processUserInput(value2);
		assertEquals("[0]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2, -, 3, -]", Arrays.toString(calculator.getJournalStack().toArray()));			
		assertEquals(new Integer(2), calculator.getOperatorHashMap().get(operator));
		
		calculator.processUserInput(value3);
		assertEquals("[]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[]", Arrays.toString(calculator.getJournalStack().toArray()));			
		assertEquals(null, calculator.getOperatorHashMap().get(operator));
	}	
}
