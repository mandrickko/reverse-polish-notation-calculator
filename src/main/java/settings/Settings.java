package settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.Scanner;

/**
 * Set up of default error messages, input source and calculator behavior setting variables. 
 * Override the default setting variables if new settings are found in the external 'setting.properties' file.
 * 
 * @author Mandrick Ko
 * @version 1.0 
 * @since 2016-02-23
 * 
 */
public class Settings {	

/*	left these old code here so that we will have idea of the expected values from /src/main/resources/setting.properties
 *  and know how to set the values if setting.properties are not already there.
 *  
	// error messages
	public static String divideByZeroErrMsg = "'%s 0 /': 'divided-by-zero' error";
	public static String squarerootOnNegativeErrMsg = "'%s sqrt': 'complex-number arithmetic' (ie. square root on negative value) not supported";
	public static String insufficientParametersErrMsg = "%s %s (position: %d): insufficient parameters";
	public static String invalidTokenErrMsg = "%s %s (position: %d): invalid token";

	// calculator behavior setup. These behavior can be overridden with values from external properties file if needed
	public static String decimal_format_edit_mask = "#.##########";
	public static RoundingMode roundingMode = RoundingMode.DOWN;
	public static MathContext mathContext = MathContext.DECIMAL64;
	public static int scale = 10;
*/	
	
	// error messages
	public static String divideByZeroErrMsg;
	public static String squarerootOnNegativeErrMsg;
	public static String insufficientParametersErrMsg;
	public static String invalidTokenErrMsg;

	// calculator behaviors
	public static String decimalFormatEditMask;
	public static RoundingMode roundingMode;
	public static MathContext mathContext;
	public static int scale;	
	
	// input source. Default is 'System.in' but can also be a path to an external file
	public static String inputSource = "System.in";
	public static Scanner scanner = new Scanner(System.in);
	
	public static void load() {
		try {
			 Properties appSettings = new Properties();
		     FileInputStream fis = new FileInputStream("src/main/resources/setting.properties");
		     appSettings.load(fis); 

		     // update setting variables. Update error messages.
		     String externalSetting = (String) appSettings.getProperty("divideByZeroErrMsg");
		     if (externalSetting != null)
		    	 divideByZeroErrMsg = externalSetting;
		     
		     externalSetting = (String) appSettings.getProperty("squarerootOnNegativeErrMsg");
		     if (externalSetting != null)
		    	 squarerootOnNegativeErrMsg = externalSetting;
		     
		     externalSetting = (String) appSettings.getProperty("insufficientParametersErrMsg");
		     if (externalSetting != null)
		    	 insufficientParametersErrMsg = externalSetting;
		     
		     externalSetting = (String) appSettings.getProperty("invalidTokenErrMsg");
		     if (externalSetting != null)
		    	 invalidTokenErrMsg = externalSetting;
		     
		     
		     // set up input source
		     externalSetting = (String) appSettings.getProperty("inputSource");
		     if ((externalSetting != null) && (!"System.in".equals(externalSetting))) {
		    	 inputSource = externalSetting;
		    	 File inputFile = new File(inputSource);
		    	 scanner = new Scanner(inputFile);
		     }
		     
		     
		     // update setting variables. Update calculator behavior settings.
		     externalSetting = (String) appSettings.getProperty("decimalFormatEditMask");
		     if (externalSetting != null)
		    	 decimalFormatEditMask = externalSetting;
		     
		     externalSetting = (String) appSettings.getProperty("scale");
		     if (externalSetting != null)
		    	 scale = Integer.parseInt(externalSetting);
		     
		     externalSetting = (String) appSettings.getProperty("roundingMode");
		     if (externalSetting != null) {
		    	 switch (externalSetting) {
		    	 	case("CEILING"):
		    	 		roundingMode = RoundingMode.CEILING;
		    	 		break;
		    	 	case("DOWN"):
		    	 		roundingMode = RoundingMode.DOWN;
		    	 		break;
		    	 	case("FLOOR"):
		    	 		roundingMode = RoundingMode.FLOOR;
		    	 		break;
		    	 	case("HALF_DOWN"):
		    	 		roundingMode = RoundingMode.HALF_DOWN;
		    	 		break;
		    	 	case("HALF_EVEN"):
		    	 		roundingMode = RoundingMode.HALF_EVEN;
		    	 		break;
		    	 	case("HALF_UP"):
		    	 		roundingMode = RoundingMode.HALF_UP;
		    	 		break;
		    	 	case("UNNECESSARY"):
		    	 		roundingMode = RoundingMode.UNNECESSARY;
		    	 		break;
		    	 	case("UP"):
		    	 		roundingMode = RoundingMode.UP;
		    	 		break;
		    	 	default: 
		    	 		roundingMode = RoundingMode.DOWN;
		    	 		break;
		    	 }
		     }
		    	 
		     //mathContext="DECIMAL64";
		     externalSetting = (String) appSettings.getProperty("mathContext");
		     if (externalSetting != null) {
		    	 switch (externalSetting) {
		    	 	case("DECIMAL128"):
		    	 		mathContext = MathContext.DECIMAL128;
		    	 		break;
		    	 	case("DECIMAL32"):
		    	 		mathContext = MathContext.DECIMAL32;
		    	 		break;
		    	 	case("DECIMAL64"):
		    	 		mathContext = MathContext.DECIMAL64;
		    	 		break;
		    	 	case("UNLIMITED"):
		    	 		mathContext = MathContext.UNLIMITED;
		    	 		break;
		    	 	default: 
		    	 		mathContext = MathContext.DECIMAL64;
		    	 		break;
		    	 }
		     }				     
   
		     fis.close();
		     
		} catch(IOException e)
	    {
	        System.out.println("Could not load settings file.");
	        System.out.println(e.getMessage());
	    }
	}
}