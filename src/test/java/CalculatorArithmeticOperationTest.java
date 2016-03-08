import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import exceptions.InvalidInputException;
import settings.Settings;

/**
 * Testings for various arithmetic operations.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 *
 */
public class CalculatorArithmeticOperationTest {
	private Calculator calculator;
	
	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}

	@Test
	/*
	 * There are 3 internal memories to verify - 
	 * 1. The Result Stack. This contains the calculated results. 
	 * 2. The Journal Stack. This contains the user input. This serves as a journal for undo.
	 * 3. The Operator HashMap. This contains the number of occurrence of the operators encountered. 
	 * 	  This is used for locating the problematic operator when insufficient parameters error occurs.
	 */
	public void testPushingTwoValuesToStackAndVerifyUsingPushPop() throws InvalidInputException {
		String value1 = "5";
		String value2 = "2";		
		String value = "5 2";
		
		calculator.processUserInput(value);
				
		assertEquals(new BigDecimal(value2), calculator.getResultStack().peek());
		assertEquals(value2, calculator.getJournalStack().peek());		
		
		calculator.getResultStack().pop();
		calculator.getJournalStack().pop();

		assertEquals(new BigDecimal(value1), calculator.getResultStack().peek());
		assertEquals(value1, calculator.getJournalStack().peek());		
		assertEquals(true, calculator.getOperatorHashMap().isEmpty());				
	}
	
	@Test
	public void testPushingTwoValuesInOneLineToStack() throws InvalidInputException {
		String value = "5 2";
		
		calculator.processUserInput(value);

		assertEquals("[5, 2]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2]", Arrays.toString(calculator.getJournalStack().toArray()));	
		assertEquals(true, calculator.getOperatorHashMap().isEmpty());				
	}
	
	@Test
	public void testPushingTwoValuesInSeparateLinesToStack() throws InvalidInputException {
		String value1 = "5";
		String value2 = "2";		
		
		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
				
		assertEquals("[5, 2]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2]", Arrays.toString(calculator.getJournalStack().toArray()));	
		assertEquals(true, calculator.getOperatorHashMap().isEmpty());						
	}
	
	@Test
	public void testAdditionInSeparateLines() throws InvalidInputException {
		String value1 = "5";
		String value2 = "2";		
		String value3 = "+";

		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
		calculator.processUserInput(value3);

		assertEquals("[7]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2, +]", Arrays.toString(calculator.getJournalStack().toArray()));			
				
		Integer expectedCounter = new Integer(1);
		assertEquals(expectedCounter, calculator.getOperatorHashMap().get(value3));
	}		
	
	@Test
	public void testSubtractionInOneLine() throws InvalidInputException {
		String value1 = "5 2 -";
		String value2 = "-";
		
		calculator.processUserInput(value1);
	
		assertEquals("[3]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[5, 2, -]", Arrays.toString(calculator.getJournalStack().toArray()));	
		
		Integer expectedCounter = new Integer(1);
		assertEquals(expectedCounter, calculator.getOperatorHashMap().get(value2));
	}	
					
	@Test
	public void testMultiplicationInSeparateLinesAndVerifyUsingPushPop() throws InvalidInputException {
		String value1 = "12";
		String value2 = "2";		
		String value3 = "*";

		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
		calculator.processUserInput(value3);

		BigDecimal expectedValue = new BigDecimal(24);

		assertEquals(expectedValue, calculator.getResultStack().peek());
		
		assertEquals(value3, calculator.getJournalStack().peek());		
		calculator.getJournalStack().pop();
		assertEquals(value2, calculator.getJournalStack().peek());		
		calculator.getJournalStack().pop();
		assertEquals(value1, calculator.getJournalStack().peek());
		
		Integer expectedCounter = new Integer(1);
		assertEquals(expectedCounter, calculator.getOperatorHashMap().get(value3));

	}	

	@Test
	public void testDivisionInOneLine() throws InvalidInputException {
		String value1 = "12 2 /";

		calculator.processUserInput(value1);
		assertEquals("[6]", Arrays.toString(calculator.getResultStack().toArray()));
		assertEquals("[12, 2, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("/"));
	}	
			
	@Test
	public void testDivisionMultiplicationCombination() throws InvalidInputException {
		String value1 = "7 12 2 /";
		String value2 = "*";
		String value3 = "4 /";
		
		calculator.processUserInput(value1);
		assertEquals("[7, 6]", Arrays.toString(calculator.getResultStack().toArray()));
		
		calculator.processUserInput(value2);
		assertEquals("[42]", Arrays.toString(calculator.getResultStack().toArray()));

		calculator.processUserInput(value3);
		assertEquals("[10.5]", Arrays.toString(calculator.getResultStack().toArray()));
		
		assertEquals("[7, 12, 2, /, *, 4, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(2), calculator.getOperatorHashMap().get("/"));
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("*"));	
	}	
			
	@Test
	public void testMultipleMultiplication() throws InvalidInputException {
		String value1 = "1 2 3 4 5";
		String value2 = "* * * *";
		
		calculator.processUserInput(value1);
		assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(calculator.getResultStack().toArray()));
		
		calculator.processUserInput(value2);
		assertEquals("[120]", Arrays.toString(calculator.getResultStack().toArray()));
		
		assertEquals("[1, 2, 3, 4, 5, *, *, *, *]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(4), calculator.getOperatorHashMap().get("*"));	
	}	

	@Test
	public void testDivisionMultiplicationWithNegativeValues() throws InvalidInputException {
		String value1 = "-7 -2 -4 /";
		String value2 = "*";
		String value3 = "-0.5 /";
		
		calculator.processUserInput(value1);
		assertEquals("[-7, 0.5]", Arrays.toString(calculator.getResultStack().toArray()));
		
		calculator.processUserInput(value2);
		assertEquals("[-3.5]", Arrays.toString(calculator.getResultStack().toArray()));

		calculator.processUserInput(value3);
		assertEquals("[7]", Arrays.toString(calculator.getResultStack().toArray()));
		
		assertEquals("[-7, -2, -4, /, *, -0.5, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(2), calculator.getOperatorHashMap().get("/"));
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("*"));	
	}			
		
	@Test
	public void testProcessSqrtInSeparateLines() throws InvalidInputException {
		String value1 = "2";
		String value2 = "sqrt";		
		
		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
		
		BigDecimal inputValue = new BigDecimal(value1);
		BigDecimal expectedValue = BigDecimal.valueOf(Math.sqrt(inputValue.doubleValue()));
		assertEquals(expectedValue, calculator.getResultStack().peek());
		
		assertEquals(value2, calculator.getJournalStack().peek());		
		calculator.getJournalStack().pop();
		assertEquals(value1, calculator.getJournalStack().peek());
		
		Integer expectedCounter = new Integer(1);
		assertEquals(expectedCounter, calculator.getOperatorHashMap().get(value2));
	}
	
	@Test
	public void testSimpleNonTerminatingDecimalExpansionCase1() throws InvalidInputException {
		String value1 = "1 -3";
		String value2 = "/";
		
		calculator.processUserInput(value1);
		assertEquals("[1, -3]", Arrays.toString(calculator.getResultStack().toArray()));
		
		calculator.processUserInput(value2);
	
		BigDecimal num1 = new BigDecimal(1);
		BigDecimal num2 = new BigDecimal(-3);
		BigDecimal expectedValue = num1.divide(num2, Settings.mathContext);
		assertEquals(expectedValue, calculator.getResultStack().peek());
	
		assertEquals("[1, -3, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("/"));
	}		
			
	@Test
	public void testSimpleNonTerminatingDecimalExpansionCase2() throws InvalidInputException {
		String value1 = "-2 -3";
		String value2 = "/";
		
		calculator.processUserInput(value1);
		assertEquals("[-2, -3]", Arrays.toString(calculator.getResultStack().toArray()));
		
		calculator.processUserInput(value2);
	
		BigDecimal num1 = new BigDecimal(-2);
		BigDecimal num2 = new BigDecimal(-3);
		BigDecimal expectedValue = num1.divide(num2, Settings.mathContext);
		assertEquals(expectedValue, calculator.getResultStack().peek());
	
		assertEquals("[-2, -3, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("/"));
	}		
	
	@Test
	public void testComplexNonTerminatingDecimalExpansion() throws InvalidInputException {
		String value1 = "-7 -2 -3 /";
		String value2 = "*";
		String value3 = "-0.5 /";
		
		calculator.processUserInput(value1);
		BigDecimal num1 = new BigDecimal(-2);
		BigDecimal num2 = new BigDecimal(-3);
		BigDecimal expectedValue = num1.divide(num2, Settings.mathContext);
		assertEquals(expectedValue, calculator.getResultStack().peek());
		
		calculator.processUserInput(value2);
		num1 = new BigDecimal(-7);
		num2 = expectedValue;
		expectedValue = num1.multiply(num2);
		assertEquals(expectedValue, calculator.getResultStack().peek());
		
		calculator.processUserInput(value3);
		num1 = expectedValue;
		num2 = new BigDecimal(-0.5);
		expectedValue = num1.divide(num2, Settings.mathContext);
		assertEquals(expectedValue, calculator.getResultStack().peek());
				
		assertEquals("[-7, -2, -3, /, *, -0.5, /]", Arrays.toString(calculator.getJournalStack().toArray()));		
		assertEquals(new Integer(2), calculator.getOperatorHashMap().get("/"));
		assertEquals(new Integer(1), calculator.getOperatorHashMap().get("*"));	
	}

	@Test
	public void testMultiplyWithTenDecimalNumber() throws InvalidInputException {		
		String value1 = "0.1666666666";
		String value2 = "99 *";
		
		calculator.processUserInput(value1);
		calculator.processUserInput(value2);

		BigDecimal num1 = new BigDecimal(value1);
		BigDecimal num2 = new BigDecimal(99);
		BigDecimal expectedValue = num1.multiply(num2).setScale(Settings.scale, Settings.roundingMode);
		assertEquals(expectedValue, calculator.getResultStack().peek().setScale(Settings.scale, Settings.roundingMode));
	}
	
	@Test
	public void testMultiplyDivideLongDecimalNumbers() throws InvalidInputException {
		String value1 = "0.77777777777777777777";
		String value2 = "0.22222222222222222222";
		String value3 = "0.33333333333333333333";
		String value4 = "/ *";

		calculator.processUserInput(value1);
		calculator.processUserInput(value2);
		calculator.processUserInput(value3);
		calculator.processUserInput(value4);

		BigDecimal num1 = new BigDecimal(value1);
		BigDecimal num2 = new BigDecimal(value2);
		BigDecimal num3 = new BigDecimal(value3);

		BigDecimal expectedValue = num2.divide(num3, Settings.mathContext).multiply(num1).setScale(Settings.scale, Settings.roundingMode);
		assertEquals(expectedValue, calculator.getResultStack().peek().setScale(Settings.scale, Settings.roundingMode));
	}
}

