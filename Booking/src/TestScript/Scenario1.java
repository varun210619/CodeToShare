package TestScript;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageObjects.*;
import Utility.*;

@Listeners(Utility.Listener.class)
public class Scenario1 {
	
	WebDriver driver;
	
	//Method to be executed before test
	@BeforeTest
	public void LaunchApp()
	{
		DriverInit driverInit=new DriverInit();
		driver=driverInit.getDriver();
		//maximizing browser
		driver.manage().window().maximize();
		
		//providing maximum timout for every step 
		driver.manage().timeouts().implicitlyWait(Constants.implicitWait, TimeUnit.SECONDS);
	}
	
	//Test to verify whether accommodation is selected or not
	@Test
    public void test1(){

		DOMConfigurator.configure("log4j.xml");
		
		Log.startTestCase("Sceanrio 1");
		
		//navigating to the url
		driver.get(Constants.URL);
		Log.info("Navigating to URL");
		
		//verifying accommodation selected or not 
		Boolean bAccomodationCheck;
		bAccomodationCheck=BookingFirstPage.CheckAccomoodationSelected(driver);
		Assert.assertTrue(bAccomodationCheck,"Accomodation selected or not verified");
		Reporter.log("Accomodation selected verified");
	}
	
	//Test to verify first 3 accommodations is within range or not
	@Test
    public void test2() throws InterruptedException, ParseException {
		
		//Entering city name
		Log.info("Entering city name");
		BookingFirstPage.EnterCityName(driver, Constants.city);
				
		//Entering random checkin and checkout dates and fetching number of nights
		Log.info("Entering random dates for checkin and check out");
		int intNumberOfNights=BookingFirstPage.EnterRandomCheckInOut(driver);
				
		//Entering number of adults, children and rooms
		Log.info("Entering adults");
		BookingFirstPage.EnterAdultChildrenRoom(driver, "adults", Constants.numberOfAdults);
		Log.info("Entering children");
		BookingFirstPage.EnterAdultChildrenRoom(driver, "children", Constants.numberOfChildren);
		Log.info("Entering rooms");
		BookingFirstPage.EnterAdultChildrenRoom(driver, "rooms", Constants.numberOfRooms);
				
		//Entering age of child
		Log.info("Entering age");
		BookingFirstPage.EnterAge(driver);
				
		//Entering travel for work checkbox option
		Log.info("Selecting travel for work checkbox");
		BookingFirstPage.EnteTravelForWorkCheck(driver, Constants.travelForWork);
				
		//clicking on search button
		Log.info("Searching accommodation");
		BookingFirstPage.ClickSearch(driver);
				
		//selecting filter budget and fetching min, max range for filter
		Log.info("Selecting filter budget");
		int[] intRange=BookingSecondPageSearch.SelectFilterBudget(driver);
				
		//Calculating minimum and maximum range of filter --- Need to check whether price displayed depends upon number of rooms or not
		//int intMinValue=intRange[0]*intNumberOfNights*Constants.numberOfRooms;
		//int intMaxValue=intRange[1]*intNumberOfNights*Constants.numberOfRooms;
		int intMinValue=intRange[0]*intNumberOfNights;
		int intMaxValue=intRange[1]*intNumberOfNights;
				
		
		//verifying price for first three accommodations is within filter range
		Log.info("Verifying Price for 3 accommodations");
		BookingSecondPageSearch.VerifyPriceForFirstThreeAcomodation(driver, intMinValue, intMaxValue);
	}
	
	//Test to verify review score is above filter for first searched accommodation
	@Test
    public void test3() throws InterruptedException, ParseException {
		//Selecting review score filter
		String ReviewScore=Constants.reviewScore;
		Log.info("Selecting review score filter and verifying same for first accommodation");
		BookingSecondPageSearch.SelectReviewScoreFilter(driver,ReviewScore);
		
		//Verifying review rating for first searched property if above review score ot not
		BookingSecondPageSearch.VerifyReviewScore(driver,ReviewScore);
		
	}
	
	@AfterTest
	public void EndOfTest()
	{
		Log.endTestCase("Sceanrio 1");
		
		//quitting driver
		driver.quit();
	}
}
