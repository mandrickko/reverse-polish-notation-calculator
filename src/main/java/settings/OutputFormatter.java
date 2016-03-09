package settings;
import java.text.DecimalFormat;

public class OutputFormatter {
	public static DecimalFormat decimalFormat;
	
	private OutputFormatter() {
		// intentionally left blanked
	}
	
	public static void setOutputFormatter() {
		Settings.load();
		decimalFormat = new DecimalFormat(Settings.decimalFormatEditMask);
		decimalFormat.setRoundingMode(Settings.roundingMode);	
	}
}
