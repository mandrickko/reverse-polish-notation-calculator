/*
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
*/

/**
 * Testings of using input source from external file instead of System.in
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 */
public class ProcessInputFromExternalFileTest {
/* disabled this testing in order not to mess up my console for other junit tests, and confuse people
 * 
	private Calculator calculator;

	@Before
	public void createCalculator() {
		Settings.load();
		calculator = new Calculator();
	}
	
	@Test
	// Test user input from external file "src/test/resources/userInput.txt"
	// We can use an external file instead of System.in if we need automated testing.
	public void ManualInput() throws FileNotFoundException {
				
		File inputFile = new File("src/test/resources/userInput.txt");
		Scanner scanner = new Scanner(inputFile);

		System.out.println("These are output from ProcessInputFromExternalFileTest");

		System.out.println("Calculated result:");
		calculator.start(scanner);
				
		BigDecimal expectedValue = new BigDecimal(13.5);
		expectedValue = expectedValue.setScale(Settings.scale, Settings.roundingMode);
		assertEquals(expectedValue, calculator.getResultStack().peek().setScale(Settings.scale, Settings.roundingMode));
	}
*/	
}