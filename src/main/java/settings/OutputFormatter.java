package settings;
import java.text.DecimalFormat;

public class OutputFormatter {
	public static DecimalFormat decimalFormat;
	public static OutputFormatter outputFormatter;
	
	private OutputFormatter() {
		Settings.load();
		decimalFormat = new DecimalFormat(Settings.decimalFormatEditMask);
		decimalFormat.setRoundingMode(Settings.roundingMode);	
	}
	
	public static void setOutputFormatter() {
		outputFormatter = new OutputFormatter();
	}
}
