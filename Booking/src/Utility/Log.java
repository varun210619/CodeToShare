package Utility;

import org.apache.log4j.Logger;
 
 public class Log {
	 
	 // Initialize Log4j logs
	 private static Logger Log = Logger.getLogger(Log.class.getName());//
 
	 /*Description : Method for logging test case name
	  */
	 public static void startTestCase(String sTestCaseName){
		 
		 Log.info("****************************************************************************************");
		 Log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		 Log.info("****************************************************************************************");
	 }
 
	 /*Description : Method for logging end of test case
	  */
	 public static void endTestCase(String sTestCaseName){
		 Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
		 Log.info("X");
		 Log.info("X");
	 }
	 
	 /*Description : Method for logging info
	  */
	 public static void info(String message) {
		 Log.info(message);
	 }
	 
	 /*Description : Method for logging warning message
	  */
	 public static void warn(String message) {
		 Log.warn(message);
	 }
 
	 /*Description : Method for logging error message
	  */
	 public static void error(String message) {
		 Log.error(message);
	 }
 
	 /*Description : Method for logging debug message
	  */
	 public static void debug(String message) {
		 Log.debug(message);
	 }
}
