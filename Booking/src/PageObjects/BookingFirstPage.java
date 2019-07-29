package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import Modules.RandomNumbers;
import Utility.Log;

public class BookingFirstPage {
	
	private static WebElement element = null;
	 
	/*Description: Element property - Accomodations tab
	 */
	public static WebElement tab_Accomodations(WebDriver driver){
		element = driver.findElement(By.cssSelector(".xpb__link.selected[data-ga-track*='|accommodation|']"));
	    return element;
    }
	
	/*Description : Element property - Destination city name input text box
	 */
	public static WebElement InputDestination(WebDriver driver){
		element = driver.findElement(By.id("ss"));
	    return element;
    }
	
	/*Description : Element property - CheckinDate
	 */
	public static WebElement Checkin(WebDriver driver){
		element = driver.findElement(By.cssSelector(".xp__dates__checkin"));
	    return element;
    }
	
	/*Description : Element property - calender date
	 */
	public static WebElement CalenderDate(WebDriver driver, String date){
		element=driver.findElement(By.cssSelector("td[data-date='"+date+"']"));
	    return element;
    }
	
	/*Description : Element property - guest input section
	 */
	public static WebElement GuestInputSection(WebDriver driver){
		element = driver.findElement(By.id("xp__guests__inputs-container"));
	    return element;
    }
	
	/*Description : Element property - guest input 
	 */
	public static WebElement GuestInput(WebDriver driver){
		element = driver.findElement(By.id("xp__guests__toggle"));
	    return element;
    }
	
	/*Description : Element property - number of adult, children, rooms label 
	 */
	public static WebElement LabelGuestNumber(WebDriver driver, String requiredField){
		element = driver.findElement(By.xpath("//div[contains(@class,'" + requiredField +"')]//span[@class='bui-stepper__display']"));
		return element;
    }
	
	/*Description : Element property - button add for increasing number  
	 */
	public static WebElement ButtonAddNumberGuestSection(WebDriver driver, String requiredField){
		element = driver.findElement(By.xpath("//div[contains(@class,'"+ requiredField +"')]//button[contains(@class,'add')]"));
	    return element;
    }
	
	/*Description : Element property - button subtract for increasing number  
	 */
	public static WebElement ButtonSubtractNumberGuestSection(WebDriver driver, String requiredField){
		element = driver.findElement(By.xpath("//div[contains(@class,'"+ requiredField +"')]//button[contains(@class,'subtract')]"));
		return element;
    }
	
	/*Description : Element property - Select Age in guest section 
	 */
	public static WebElement DrpDwnAge(WebDriver driver){
		element = driver.findElement(By.name("age"));
	    return element;
    }
	
	/*Description : Element property - Select Age in guest section 
	 */
	public static WebElement ChkTravelForWork(WebDriver driver){
		element = driver.findElement(By.id("sb_travel_purpose_checkbox"));
	    return element;
    }
	
	/*Description : Element property - Search button 
	 */
	public static WebElement ButtonSearch(WebDriver driver){
		element = driver.findElement(By.className("sb-searchbox__button"));
	    return element;
    }	 
	
	/*Description: Method to check whether accommodation tab is selected or not
	 */
	public static boolean CheckAccomoodationSelected(WebDriver driver){
		
		String strAccomodationSelectedCheck;
		Boolean bAccomodationCheck;
		Log.info("Verifying accommodation is selected or not");
		strAccomodationSelectedCheck=BookingFirstPage.tab_Accomodations(driver).getAttribute("class");
		bAccomodationCheck=strAccomodationSelectedCheck.contains("selected");
		return bAccomodationCheck;
	}
	
	/*Description: Method to enter city name
	 */
	public static void EnterCityName(WebDriver driver, String city){
		
		InputDestination(driver).click();
		InputDestination(driver).sendKeys(city);
		Reporter.log("City name entered:"+city);
	}
	
	/*Description: Method to enter random checkin and checkout dates and returning number of nights
	 */
	public static int EnterRandomCheckInOut(WebDriver driver){
		
		//Fetch random dates for checkin and checkout
		//randomly taking day for next 5 days for checking in
		int intRandomCheckin=RandomNumbers.getRandomNumberInRange(0, 5);
		//randomly taking day for next 12-19 day for checking out
		int intRandomCheckout=RandomNumbers.getRandomNumberInRange(12, 19);
		//calculating number of Nights
		int intNumberOfNights;
		intNumberOfNights=intRandomCheckout-intRandomCheckin; 
				
		//fetching check in, out dates
		String strCheckinDate=RandomNumbers.getRandomDate(intRandomCheckin);
		String strCheckoutDate=RandomNumbers.getRandomDate(intRandomCheckout);
		Reporter.log("Checkin Date:"+strCheckinDate);
		Reporter.log("Check out Date:"+strCheckoutDate);
		
		//Entering dates
		Checkin(driver).click();
		CalenderDate(driver, strCheckinDate).click();
		CalenderDate(driver, strCheckoutDate).click();
		Reporter.log("Number of Nights:"+intNumberOfNights);
		return intNumberOfNights;
	}
	
	/*Description: Method to enter guest details
	 */
	public static void EnterAdultChildrenRoom(WebDriver driver, String requiredField, int numberOfField)
	{
		//exiting function if guest data is incorrect
		if (numberOfField<0)
		{
			Reporter.log("Failed.Invalid number of Fields:"+numberOfField + " for " + requiredField);
			Assert.assertTrue(false,"Failed.Invalid number of Fields:"+numberOfField + " for " + requiredField);
			return;
		}
		
		//exiting function if adults or rooms is less than 1
		if ((requiredField.equals("adults") || requiredField.equals("rooms")) && numberOfField < 1)
		{
			Reporter.log("FailedInvalid number of Fields:"+numberOfField + " for " + requiredField);
			Assert.assertTrue(false, "Failed.Invalid number of Fields:"+numberOfField + " for " + requiredField);
			return;
		}
		
		//clicking guest detail element
		if (!(GuestInputSection(driver)).isDisplayed())
		{
			GuestInput(driver).click();
		}
		
		//fetching number/value of field-adults/children/rooms
		String noInUI=LabelGuestNumber(driver,requiredField).getText();
		int intNoInUI=Integer.parseInt(noInUI);
		
		//clicking on add/subtract 
		if (numberOfField>intNoInUI)
		{
			//clicking on + button in adult section till required number is reached
			while (numberOfField != intNoInUI)
			{	
				//clicking on add button
				ButtonAddNumberGuestSection(driver, requiredField).click();
				
				//fetching the number/value for required field
				noInUI=LabelGuestNumber(driver,requiredField).getText();
				intNoInUI=Integer.parseInt(noInUI);
			}
		}
		else
		{
			//clicking on - minus in adult section till required number is reached
			while (numberOfField != intNoInUI)
			{	
				//clicking on subtract button
				ButtonSubtractNumberGuestSection(driver, requiredField).click();
				
				//fetching the number/value for required field
				noInUI=LabelGuestNumber(driver,requiredField).getText();
				intNoInUI=Integer.parseInt(noInUI);
			}
		}
		Reporter.log("Entered number of " + requiredField + " : "+ noInUI);
	}
	
	/*Description: Method to enter age of children
	 */
	public static void EnterAge(WebDriver driver)
	{
		//fetching random number/age from range 0-17
		int Age=RandomNumbers.getRandomNumberInRange(0,17);
		
		//selecting age from age-dropdown
		Select drpAge = new Select(DrpDwnAge(driver));
		drpAge.selectByValue(String.valueOf(Age));
		Reporter.log("Age of child :"+ Age);
	}
	
	/*Description: Method to click for checkbox - travel for work
	 */
	public static void EnteTravelForWorkCheck(WebDriver driver,String TravellingForWork)
	{
		Reporter.log("Travel for work:"+TravellingForWork);
		//checking of checkbox  for travel for work is selected or not
		Boolean bTravelCheck=ChkTravelForWork(driver).isSelected();
		
		//selecting checkbox if travel for work is yes
		if (TravellingForWork.equalsIgnoreCase("yes"))
		{
			if (!bTravelCheck)
			{
				JavascriptExecutor js = (JavascriptExecutor) driver; 
				js.executeScript("arguments[0].click()",ChkTravelForWork(driver));
			}
		}
		//deselecting checkbox if travel for work is not yes
		else
		{
			if (bTravelCheck)
			{
				JavascriptExecutor js = (JavascriptExecutor) driver; 
				js.executeScript("arguments[0].click()",ChkTravelForWork(driver));
			}
		}
		bTravelCheck=ChkTravelForWork(driver).isSelected();
		Reporter.log("Travel For Work Selected(true/false):"+bTravelCheck);
	}
	
	/*Description: Method to click for search button
	 */
	public static void ClickSearch(WebDriver driver)
	{
		ButtonSearch(driver).click();
		Reporter.log("Searching Accommodation");
	}
}
