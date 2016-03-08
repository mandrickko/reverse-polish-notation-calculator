package operations;
import java.math.BigDecimal;
import java.util.HashMap;

import components.CalculatorStack;
import exceptions.InsufficientParametersException;
import exceptions.InvalidArithmeticException;

/**
 * A abstract parent class for all mathematical operations that require only one value in computation.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public abstract class UnaryOperation implements Operation {
	@Override
	public void apply(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
					throws InsufficientParametersException, InvalidArithmeticException {
		BigDecimal value1 = resultStack.peek();
		
		if (value1 != null) 
			resultStack.replaceTop(calculate(value1));
		else
			throw new InsufficientParametersException();
	}
	protected abstract BigDecimal calculate(BigDecimal value1) throws InvalidArithmeticException;
}
