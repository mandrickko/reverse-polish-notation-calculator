package settings;
import java.util.Scanner;

public class InputSource {
	public static Scanner scanner;
	
	private InputSource() {
		// intentionally left blanked
	}
	
	public static void setInputSource() {
		
		Settings.load();

		if (Settings.inputSource == null) {
			Settings.inputSource = "System.in";
			Settings.scanner = new Scanner(System.in);
		}		
		scanner = Settings.scanner;
	}
}
