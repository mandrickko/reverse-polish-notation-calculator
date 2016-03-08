import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import exceptions.InvalidInputException;
import settings.Settings;

/**
 * Testings for the different exception handling.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class CalculatorExceptionHandlingTest {
	private Calculator calculator;
	
	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}
		
	@Test
	public void testInsufficientParametersExceptionInOneLine() {
		String inputLine = "1 2 3 * 5 + * * 6 5";
		
		try {
			calculator.processUserInput(inputLine);
		} catch (InvalidInputException iie) {
			
			String expectedErrToken = "*";
			String expectedErrMsg = String.format(Settings.insufficientParametersErrMsg, "operator", expectedErrToken, 15);
			assertEquals(expectedErrMsg, iie.getMessage());
			
			assertEquals("[11]", Arrays.toString(calculator.getResultStack().toArray()));
			
			Integer expectedCounter = new Integer(3);
			assertEquals(expectedCounter, calculator.getOperatorHashMap().get(expectedErrToken));
		}
	}
	
	@Test
	public void testInsufficientParametersExceptionInSeparateLines() {
		String value1 = "1 2 3 * 5 + *";
		String value2 = "* 6 5";
		
		try {
			calculator.processUserInput(value1);
			calculator.processUserInput(value2);
		} catch (InvalidInputException iie) {
			
			String expectedErrToken = "*";
			String expectedErrMsg = String.format(Settings.insufficientParametersErrMsg, "operator", expectedErrToken, 1);
			assertEquals(expectedErrMsg, iie.getMessage());
	
			assertEquals("[11]", Arrays.toString(calculator.getResultStack().toArray()));
			
			Integer expectedCounter = new Integer(3);
			assertEquals(expectedCounter, calculator.getOperatorHashMap().get(expectedErrToken));
		}
	}
	
	@Test
	public void testNumberFormatExceptionInSeparateLines() {
		String value1 = "1 200Z 3 * N + *";
		String value2 = "* Y 5";
		
		try {
			calculator.processUserInput(value1);
			calculator.processUserInput(value2);
		} catch (InvalidInputException iie) {
			
			String expectedErrMsg = String.format(Settings.invalidTokenErrMsg, "token", "200Z", 3);
			assertEquals(expectedErrMsg, iie.getMessage());
	
			BigDecimal expectedValue = new BigDecimal(1);
			assertEquals(expectedValue, calculator.getResultStack().peek());
			
			assertEquals("[1]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}
	
	@Test
	public void testInvalidArithmeticExceptionDividendDivisorBothZeroesInOneLine() {
		String value1 = "5 0 0 / +";
		
		try {
			calculator.processUserInput(value1);
		} catch (InvalidInputException iie) {
			String expectedErrMsg = String.format(Settings.divideByZeroErrMsg, BigDecimal.ZERO); 
			assertEquals(expectedErrMsg, iie.getMessage());
			assertEquals("[5, 0]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}	
	
	@Test
	public void testInvalidArithmeticExceptionDividendDivisorBothZeroesInMultipleLines() {
		String value1 = "0 0";
		String value2 = "/";
		
		try {
			calculator.processUserInput(value1);
			calculator.processUserInput(value2);
		} catch (InvalidInputException iie) {
			String expectedErrMsg = String.format(Settings.divideByZeroErrMsg, BigDecimal.ZERO); 
			assertEquals(expectedErrMsg, iie.getMessage());
			assertEquals("[0]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}		
	
	@Test
	public void testInvalidArithmeticExceptionDivisorZeroInOneLine() {
		String value1 = "5 7 0 / +";
		
		try {
			calculator.processUserInput(value1);
		} catch (InvalidInputException iie) {
			String expectedErrMsg = String.format(Settings.divideByZeroErrMsg, new BigDecimal(7)); 
			assertEquals(expectedErrMsg, iie.getMessage());
			assertEquals("[5, 7]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}	
	
	@Test
	public void testInvalidArithmeticExceptionDivisorZeroInMultipleLines() {
		String value1 = "1 0";
		String value2 = "/";
		
		try {
			calculator.processUserInput(value1);
			calculator.processUserInput(value2);
		} catch (InvalidInputException iie) {
			String expectedErrMsg = String.format(Settings.divideByZeroErrMsg, new BigDecimal(1)); 			
			assertEquals(expectedErrMsg, iie.getMessage());
			assertEquals("[1]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}	
	
	@Test
	public void testInvalidArithmeticExceptionSquareRootOnNegativeNumber() {
		String value1 = "-2";
		String value2 = "sqrt";
		
		try {
			calculator.processUserInput(value1);
			calculator.processUserInput(value2);
		} catch (InvalidInputException iie) {
			String expectedErrMsg = String.format(Settings.squarerootOnNegativeErrMsg, new BigDecimal(-2)); 
			assertEquals(expectedErrMsg, iie.getMessage());
			assertEquals("[-2]", Arrays.toString(calculator.getResultStack().toArray()));
		}
	}
}
