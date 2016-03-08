package operations;
import java.math.BigDecimal;

/**
 * Multiply two numbers.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class MultiplyOperation extends BinaryOperation {

	@Override
	protected BigDecimal calculate(BigDecimal value1, BigDecimal value2) {
		return value1.multiply(value2);
	}
}

