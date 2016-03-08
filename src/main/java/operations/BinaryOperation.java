package operations;
import java.math.BigDecimal;
import java.util.HashMap;

import components.CalculatorStack;
import exceptions.InsufficientParametersException;
import exceptions.InvalidArithmeticException;

/**
 * A abstract parent class for all mathematical operations that require two values in computation.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public abstract class BinaryOperation implements Operation {
	@Override
	public void apply(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
			throws InsufficientParametersException, InvalidArithmeticException {	
		
		BigDecimal value2 = resultStack.peek();
		if (value2 != null) 
			resultStack.pop();
		else
			throw new InsufficientParametersException();
		
		BigDecimal value1 = resultStack.peek();
		
		if (value1 != null) 
			resultStack.replaceTop(calculate(value1, value2));
		else {
			// we need to push back to the stack the previously popped value since we don't have sufficient parameters to finish up the binary operation
			resultStack.push(value2);
			throw new InsufficientParametersException();
		}
	}
	
	protected abstract BigDecimal calculate(BigDecimal value1, BigDecimal value2) throws InvalidArithmeticException;
}
