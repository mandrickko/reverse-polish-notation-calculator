package operations;
import java.math.BigDecimal;

import exceptions.InvalidArithmeticException;
import settings.Settings;

/**
 * Calculate square root of a number
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class SqrtOperation extends UnaryOperation {

	@Override
	protected BigDecimal calculate(BigDecimal value1) throws InvalidArithmeticException {
		if (value1.compareTo(BigDecimal.ZERO) < 0)  {
			String errMsg = String.format(Settings.squarerootOnNegativeErrMsg, value1); 
			throw new InvalidArithmeticException(errMsg);
		}		
		return BigDecimal.valueOf(Math.sqrt(value1.doubleValue()));
	}
}