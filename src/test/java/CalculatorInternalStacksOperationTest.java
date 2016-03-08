import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import components.CalculatorStack;
import settings.Settings;

/**
 * Testings for operations for internal memories of the calculator.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 */
public class CalculatorInternalStacksOperationTest {
	private CalculatorStack<BigDecimal> resultStack;
	private CalculatorStack<String> journalStack;

	@Before
	public void createInternalStacks() {
		Settings.load();
		resultStack = new CalculatorStack<>();
		journalStack = new CalculatorStack<>();
	}
	
	// start testing of resultStack operations...
	@Test
	public void testResultStackInitialValue() {
		assertEquals(null, resultStack.peek());
	}
	
	@Test
	public void testPushingOneValueOntoResultResultStack() {
		BigDecimal value = new BigDecimal(16);
		resultStack.push(value);
		assertEquals(value, resultStack.peek());
	}
	
	@Test 
	public void testPushingMultipleValuesOntoResultStack() {
		resultStack.push(new BigDecimal(1));
		resultStack.push(new BigDecimal(2));
		resultStack.push(new BigDecimal(3));
		resultStack.push(new BigDecimal(4));
		BigDecimal value = new BigDecimal(5);
		resultStack.push(value);		
		assertEquals(value, resultStack.peek());
		assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(resultStack.toArray()));
	}
	
	@Test
	public void testReplacingTopOfResultStack() {
		resultStack.push(new BigDecimal(22));
		BigDecimal value = new BigDecimal(74);
		resultStack.replaceTop(value);
		assertEquals(value, resultStack.peek());
	}
	
	@Test
	public void testPoppingValuesFromResultStack() {
		BigDecimal value = new BigDecimal(25);
		resultStack.push(value);
		resultStack.push(new BigDecimal(35));
		resultStack.push(new BigDecimal(45));
		resultStack.pop();
		resultStack.pop();
		assertEquals(value, resultStack.peek());
	}
	
	@Test
	public void testPoppingEmptyResultStack() {
		resultStack.pop();
		assertEquals(null, resultStack.peek());
	}	
	
	@Test
	public void testClearingResultStack() {
		resultStack.clear();
		assertEquals(null, resultStack.peek());
	}
	
	// start testing of journalStack operations...
	@Test
	public void testJournalStackInitialValue() {
		assertEquals(null, journalStack.peek());
	}
	
	@Test
	public void testPushingOneValueOntoJournalStack() {
		String value = "15";
		journalStack.push(value);
		assertEquals(value, journalStack.peek());
	}
	
	@Test 
	public void testPushingMultipleValuesOntoJournalStack() {
		journalStack.push("1");
		journalStack.push("2");
		journalStack.push("-");
		journalStack.push("24");
		String value = "*";
		journalStack.push(value);		
		assertEquals(value, journalStack.peek());
	}
	
	@Test
	public void testReplacingTopOfJournalStack() {
		journalStack.push("22");
		String value = "/";
		journalStack.replaceTop(value);
		assertEquals(value, journalStack.peek());
	}
	
	@Test
	public void testPoppingValuesFromJournalStack() {
		String value = "sqrt";
		journalStack.push(value);
		journalStack.push("35");
		journalStack.push("*");
		journalStack.pop();
		journalStack.pop();
		assertEquals("sqrt", journalStack.peek());
	}
	
	@Test
	public void testPoppingEmptyJournalStack() {
		journalStack.pop();
		assertEquals(null, journalStack.peek());
	}	
	
	@Test
	public void testClearingJournalStack() {
		journalStack.clear();
		assertEquals(null, journalStack.peek());
	}	
}
