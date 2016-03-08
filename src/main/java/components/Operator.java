package components;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exceptions.InsufficientParametersException;
import exceptions.InvalidArithmeticException;
import operations.AddOperation;
import operations.ClearOperation;
import operations.DivideOperation;
import operations.MultiplyOperation;
import operations.Operation;
import operations.SqrtOperation;
import operations.SubtractOperation;

/**
 * This enum Operator is where we define allowable operators as well as their associated functions required for computation. 
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public enum Operator {
	ADD("+") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new AddOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}
	},
	SUBSTRACT("-") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new SubtractOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}	
	},
	MULTIPLY("*") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new MultiplyOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}	
	},
	DIVIDE("/") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new DivideOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}	
	},	
	SQRT("sqrt") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new SqrtOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}	
	},
	CLEAR("clear") {
		public void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, HashMap<String, Integer> operatorHashMap) 
				throws InsufficientParametersException, InvalidArithmeticException {
			Operation operation = new ClearOperation();
			operation.apply(resultStack, journalStack, operatorHashMap);
		}	
	};
	
	private final String operatorText;
	
	private Operator(String operatorText) {
		this.operatorText = operatorText;
	}
	
	public abstract void process(CalculatorStack<BigDecimal> resultStack, CalculatorStack<String> journalStack, 
			HashMap<String, Integer> operatorHashMap) throws InsufficientParametersException, InvalidArithmeticException;

	private static final Map<String, Operator> map;
	
	static {
		map = new HashMap<>();
		for (Operator operator : Operator.values()) {
			map.put(operator.operatorText, operator);
		}
	}
	
	public static Operator find(String operatorText) {
		return map.get(operatorText);
	}
}