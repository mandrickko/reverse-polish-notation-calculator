package operations;
import java.math.BigDecimal;

/**
 * Subtract one number from the other number.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class SubtractOperation extends BinaryOperation {

	@Override
	protected BigDecimal calculate(BigDecimal value1, BigDecimal value2) {
		return value1.subtract(value2);
	}
}
