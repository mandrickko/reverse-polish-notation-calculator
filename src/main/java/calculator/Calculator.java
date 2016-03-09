package calculator;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import components.CalculatorStack;
import components.Operator;
import exceptions.InsufficientParametersException;
import exceptions.InvalidArithmeticException;
import exceptions.InvalidInputException;
import settings.InputSource;
import settings.OutputFormatter;
import settings.Settings;

/**
 * This Calculator program is a command-line based RPN (reverse polish notation) calculator.
 * The operations supported are: +, -, *, /, sqrt (ie. square root), undo (ie. previous operation), clear (ie. clear the result operation stack) 
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class Calculator {
	
	/**
	 * userInputLine - current line of user input.
	 * ressultStack - the resultant values after computations.
	 * journalStack - for keeping track of every operand and operators entered by user. This is used for handling 'undo'.
	 * operatorHashMap - for keeping track of the number of encounters for each of the operator. 
	 * 					 This is used to identify the position of the operator when insufficient parameter problem occurs.
	 * scanner - the input device to accept user input. It is set to 'System.in' (ie. user terminal) as default for this calculator. 
	 * decimalFormat - for controlling the number of decimal places of the stack output to be displayed.  
	 */
	private String userInputLine;
	private CalculatorStack<BigDecimal> resultStack;
	private CalculatorStack<String> journalStack;
	private HashMap<String, Integer> operatorHashMap;
	
	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		calculator.start(InputSource.scanner);

	}
	
	public Calculator() {
		resultStack = new CalculatorStack<>();
		journalStack = new CalculatorStack<>();
		operatorHashMap = new HashMap<>();
		
	    	InputSource.setInputSource();
		OutputFormatter.setOutputFormatter();	
	}
	
	public CalculatorStack<BigDecimal> getResultStack() {
		return resultStack;
	}
	
	public CalculatorStack<String> getJournalStack() {
		return journalStack;
	}
	
	public HashMap<String, Integer> getOperatorHashMap() {
		return operatorHashMap;
	}
	
	public String getUserInputLine() {
		return userInputLine;
	}

	/**
	 * Start the calculator and repeatedly accept input from user one line at a time. Each input line will computed in reverse polish computation mode.
	 * If desired, we start the calculator using a scanner that accept input from an external file or other source instead of System.in.
	 */
	public void start(Scanner scanner) {
		while (scanner.hasNextLine()) {
			userInputLine = scanner.nextLine();
			
			if ("".equals(userInputLine))
				continue;
				
			if ("quit".equals(userInputLine))
				break;
			
			try {			
				processUserInput(userInputLine);
			} catch (InvalidInputException iie) {
				System.out.println(iie.getMessage());
			} finally {
				// uncomment the statement below if need to check content of the interim journal stacks
				// printAllJournalStackValues();
				printAllResultStackValues();
			}			
		}
	}
	
	/**
	 * Process an user input line.
	 * 
	 * @param inputLine User input line.
	 * @throws InvalidInputException This exception is thrown when it encounters error in user input.
	 */
	public void processUserInput(String inputLine) throws InvalidInputException {
		
		// -1 to represent an non-existent position. We assume that we have no bad token when we start
		int positionOfErrToken = -1;
		
		for (String token : inputLine.split("\\s+")) {
			if ("undo".equals(token)) {
				processUndo();
			} else {
				try {					
					processCalculation(token);
				} catch (NumberFormatException nfe) {
					
					positionOfErrToken = inputLine.indexOf(token) + 1; 
					String errMsg = String.format(Settings.invalidTokenErrMsg, "token", token, positionOfErrToken);
					throw new InvalidInputException(errMsg);
				
				} catch (InsufficientParametersException ipe) {	
				
					int offset = 0;
					int counter = operatorHashMap.get(token);
					for (int i=0; i < counter; i++) {
						offset = inputLine.indexOf(token, offset);
						positionOfErrToken = ++offset;	
					}					
					String errMsg = String.format(Settings.insufficientParametersErrMsg, "operator", token, positionOfErrToken);
					throw new InvalidInputException(errMsg);
				
				} catch (InvalidArithmeticException dbze) {
					throw new InvalidInputException(dbze.getMessage());
				}
			}
		}
	}
	
	/**
	 * Print the resultant stack values.
	 */
	public void printAllResultStackValues() {
		
		// uncomment the below statement if needed to compare values stored in the stack with the values displayed. The statement below prints values stored in stack
		// resultStack.printAllStackValues();		
		
		Object[] resultArray = (Object[]) resultStack.toArray();
		
		StringBuilder stringBuilder = new StringBuilder("stack: ");
		for (Object resultEntry: resultArray) {
			String number = OutputFormatter.decimalFormat.format(resultEntry);
			stringBuilder.append(number).append(" ").trimToSize();
		}
			
		String resultLine = stringBuilder.toString();
		System.out.println(resultLine);
	}
	
	/**
	 * For dumping content of the journal stack. This is for debugging purpose.
	 */
	public void printAllJournalStackValues() {
		journalStack.printAllStackValues();
	}
	
	/**
	 * Apply 'undo' to the previous operation.
	 * 
	 * @throws InvalidInputException This exception is thrown when it encounters error in user input.
	 */
	private void processUndo() throws InvalidInputException {
		journalStack.pop();
		rebuildResultStack();
	}
	
	/**
	 * Rebuild the resultant stack based on entries in journal stack.
	 * 
	 * @throws InvalidInputException This exception is thrown when it encounters error in user input.
	 */
	private void rebuildResultStack() throws InvalidInputException {
		
		Object[] journalArray = (Object[]) journalStack.toArray();
		
		resultStack.clear();
		journalStack.clear();
		operatorHashMap.clear();
		
		StringBuilder stringBuilder = new StringBuilder("");
		for (Object journalEntry: journalArray)
			stringBuilder.append((String)journalEntry).append(" ").trimToSize();

		String newInputLine = stringBuilder.toString();
	
		processUserInput(newInputLine);
	}

	/**
	 * Process each token in the user input string. The token could be a number or an operator. If the token is an operator, this calculator 
	 * would perform the necessary computation and update the resultant stack. If it is a number, the number will be pushed into the resultant stack
	 * awaiting for futher actions. Throws exceptions if there are number format problem, insufficient parameter problem, or arithmetic problem (such 
	 * as 'divided-by-zero' problem).
	 * 
	 * @param token A token in the user input string.
	 * @throws NumberFormatException This exception is thrown if the token is neither a number nor an operator
	 * @throws InsufficientParametersException This exception is thrown when there are not enough number of stack values for completing the computation.
	 * @throws InvalidArithmeticException This exception is thrown when arithmetic failure occurs. e.g. Divide by zero.
	 */
	private void processCalculation(String token) throws NumberFormatException, InsufficientParametersException, InvalidArithmeticException {				
		journalStack.push(token);
		Operator operator = Operator.find(token);
		if (operator != null) {
			incrementOperatorHashMap(token);
			operator.process(resultStack, journalStack, operatorHashMap);
		} else {
			// if token is not a numeric value equivalent, Double.parseDouble(token) would throw a Java exception NumberFormatException.
			Double.parseDouble(token);
			
			BigDecimal number = new BigDecimal(token);
			resultStack.push(number);
		}	
	}

	/**
	 * For keeping track of the number of times an operator has been encountered. The number will be used to identify the position of the operator
	 * in the user input string when insufficient parameter error or invalid token error occurs.
	 * 
	 * @param token
	 */
	private void incrementOperatorHashMap(String token) {
		Integer counter = operatorHashMap.get(token);
		if (counter == null)
			operatorHashMap.put(token, 1);
		else {
			int number = counter.intValue();
			operatorHashMap.put(token, ++number);
		}	
	}
}
