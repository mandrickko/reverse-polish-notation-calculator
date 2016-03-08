package operations;
import java.math.BigDecimal;

import exceptions.InvalidArithmeticException;
import settings.Settings;

/**
 * Divide one number by the other number.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class DivideOperation extends BinaryOperation {

	@Override
	protected BigDecimal calculate(BigDecimal value1, BigDecimal value2) throws InvalidArithmeticException {		
		if (value2.compareTo(BigDecimal.ZERO) == 0)  {							
			String errMsg = String.format(Settings.divideByZeroErrMsg, value1); 
			throw new InvalidArithmeticException(errMsg);
		}		
		return value1.divide(value2, Settings.mathContext);
	}
}