package exceptions;
/**
 * An 'umbrella' exception to signal occurrence of problem that would cause computation to fail.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	public InvalidInputException(String errMsg) {
		super(errMsg);
	}
}