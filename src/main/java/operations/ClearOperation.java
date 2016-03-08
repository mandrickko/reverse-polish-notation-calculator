package operations;
import java.math.BigDecimal;
import java.util.HashMap;

import components.CalculatorStack;

/**
 * Clear all the internal stacks and map of the calculator.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class ClearOperation implements Operation {
	@Override
	public void apply(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) {
		resultStack.clear();
		journalStack.clear();
		operatorHashMap.clear();
	}
}
