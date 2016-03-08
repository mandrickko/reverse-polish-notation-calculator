import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import settings.Settings;

/**
 * Testings to verify the initial states of the working memories of the calculator.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 */
public class NewCalculatorTest {
	private Calculator calculator;
	
	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}
	
	@Test
	public void testCalculatorResultStackInitialValue() {
		assertEquals(null, calculator.getResultStack().peek());
	}
	
	@Test
	public void testCalculatorJournalStackInitialValue() {
		assertEquals(null, calculator.getJournalStack().peek());
	}
	
	@Test
	public void testCalculatorOperatorHashMapInitialValue() {
		assertEquals(true, calculator.getOperatorHashMap().isEmpty());
	}	
}
