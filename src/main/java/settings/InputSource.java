package settings;
import java.util.Scanner;

public class InputSource {
	public static InputSource inputSource;
	
	private InputSource() {
	    if ((Settings.inputSource == null) || ("System.in".equals(Settings.inputSource))) {
			// System.out.println("Enter User Input or type 'quit' to quit: ");	
			
			// sorry, I am being paranoid. This should not happen, but just in case...
			if (Settings.inputSource == null) {
				Settings.inputSource = "System.in";
				Settings.scanner = new Scanner(System.in);
			}
	    }		
	}
	
	public static void setInputSource() {
		inputSource = new InputSource();
	}
}
