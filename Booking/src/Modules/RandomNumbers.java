package Modules;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumbers {
	
	
	/*Method Description : Method returns random number in the range provided as input
	 */
	public static int getRandomNumberInRange(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min,max);
	}
	
	/*Method Description : Method returns random date
	 */
	public static String getRandomDate(int minDay)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(currentDate);

	    //manipulate date
	    c.add(Calendar.DATE, minDay);
	    Date currentDatePlusOne = c.getTime();
	    return dateFormat.format(currentDatePlusOne);
	}

}
