package operations;
/**
 * An interface for all classes that perform arithmetic operation.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
import java.math.BigDecimal;
import java.util.HashMap;

import components.CalculatorStack;
import exceptions.InsufficientParametersException;
import exceptions.InvalidArithmeticException;

public interface Operation {
	void apply(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
			throws InsufficientParametersException, InvalidArithmeticException;
}
