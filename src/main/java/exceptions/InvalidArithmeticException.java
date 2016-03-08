package exceptions;
/**
 * An exception to signal occurrence of computation problem such as divde-by-zero.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */@SuppressWarnings("serial")
public class InvalidArithmeticException extends Exception {
	public InvalidArithmeticException(String errMsg) {
		super(errMsg);
	}
}