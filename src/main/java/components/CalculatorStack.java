package components;
import java.util.Arrays;
import java.util.Stack;

/**
 * Generic stack to be used as internal memories for resultant stack and journal stack of the calculator.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class CalculatorStack<T> {
	
	private Stack<T> values = new Stack<>();
	
	public void printAllStackValues() {
		System.out.println(Arrays.toString(values.toArray()));
	}
	
	public T peek() {
		if (values.size() == 0)
			return null;
		
		return values.peek();
	}

	public void push(T value) {
		values.push(value);
	}

	public void replaceTop(T value) {
		pop();
		values.push(value);
	}

	public void pop() {
		if (values.size() > 0)
			values.pop();
	}
	
	public void clear() {
		values.clear();
	}

	public Object[] toArray() {
		Object[] stringArray = (Object[]) values.toArray();
		return stringArray;
	}
}
