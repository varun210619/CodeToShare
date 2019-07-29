package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

public class BookingSecondPageSearch {
	
	private static WebElement element = null;
	 
	/*Description: Element property - checkbox filter budget
	 */
	public static WebElement ChkFilterBudget1(WebDriver driver){
		element = driver.findElement(By.cssSelector("a[data-id='pri-4']"));
	    return element;
    }
	
	/*Description: Element property - checkbox2 filter budget
	 */
	public static WebElement ChkFilterBudget2(WebDriver driver){
		element = driver.findElement(By.cssSelector("a[data-id='pri-3']"));
		return element;
    }
	
	/*Description: Element property - list of searched accomodations
	 */
	public static WebElement lstSearchAccomodation(WebDriver driver){
		element = driver.findElement(By.xpath("//div[@id='hotellist_inner']/div[contains(@class,'sr_item_default')]"));
	    return element;
    }
	
	/*Description: Element property - price of searched accomodation
	 */
	public static WebElement lblPriceOfAccommodation(WebDriver driver, int iCount){
		element = driver.findElement(By.xpath("//div[@id='hotellist_inner']/div[contains(@class,'sr_item')]["+iCount+"]//div[contains(@class,'bui-price-display__value')]"));
		return element;
    }
	
	/*Description: Element property - price of searched accomodation
	 */
	public static WebElement chkReviewScore(WebDriver driver, String reviewScore){
		element = driver.findElement(By.xpath("//a[@data-id='review_score-"+reviewScore+"0']"));
	    return element;
    }
	
	/*Description: Element property - review score of searched accomodation
	 */
	public static WebElement lblReviewScore(WebDriver driver){
		element = driver.findElement(By.xpath("//div[@id='hotellist_inner']/div[contains(@class,'sr_item_default')][1]//div[contains(@aria-label,'Scored')]"));
	    return element;
    }
	
	/*Description: Method to wait for list of searched accomodation
	 */
	public static void WaitLoadingSearchList(WebDriver driver) throws InterruptedException{
		//fetching count of wait elements
		int countOfBookingLoading=driver.findElements(By.xpath("//div[@class='sr-usp-overlay sr-usp-overlay--wide']")).size();
		
		//waiting for max 10 seconds for list to be uploaded
		for (int iTime=0;iTime<10;iTime++)
		{
			if(countOfBookingLoading>=1)
			{
				Thread.sleep(iTime*1000);
				countOfBookingLoading=driver.findElements(By.xpath("//div[@class='sr-usp-overlay sr-usp-overlay--wide']")).size();
			}
			else
			{
				break;
			}
		}
	}
	
	/*Description: Method to select Filter Budget and return range
	 */
	public static int[] SelectFilterBudget(WebDriver driver) throws InterruptedException 
	{
		//checking filter checkbox
		ChkFilterBudget1(driver).click();
		WaitLoadingSearchList(driver);		
		//fetching range of filter
		String minValue=ChkFilterBudget2(driver).getAttribute("data-value");
		String maxValue=ChkFilterBudget1(driver).getAttribute("data-value");
		int intMinValue=Integer.parseInt(minValue);
		int intMaxValue=Integer.parseInt(maxValue);
		Reporter.log("Filter budget min value:"+intMinValue);
		Reporter.log("Filter budget max value:"+intMaxValue);
		return new int[] {intMinValue, intMaxValue};
	}
	
	/*Description: Method to verify price is within range of filter
	 */
	public static void VerifyPriceForFirstThreeAcomodation(WebDriver driver, int minRange, int maxRange)
	{
		//fetching number of searched accommodations
		int countofSearchedValues=driver.findElements(By.xpath("//div[@id='hotellist_inner']/div[contains(@class,'sr_item_default')]")).size();
		
		String price;
		int intPrice,NoPriceAvailable,intCheckAccomodations;
		
		intCheckAccomodations=3;
		
		//if number of searched accommodations is less than 3 - then verifying for only listed accommodation
		if (countofSearchedValues<3)
		{
			intCheckAccomodations=countofSearchedValues;
		}
		
		//looping for accommodations
		for (int iCount=1;iCount<=intCheckAccomodations;iCount++)
		{
			//checking if price is available for listed accomodation
			NoPriceAvailable=driver.findElements(By.xpath("//div[@id='hotellist_inner']/div[contains(@class,'sr_item_default')]["+iCount+"]//span[@class='sold_out_property']")).size();
			if (NoPriceAvailable>=1)
			{
				Reporter.log("Price is not available for: " + iCount + " accomodation");
				continue;
			}
		 
			price=lblPriceOfAccommodation(driver, iCount).getText();
			price = price.replaceAll("\\D+","");
			
			intPrice=Integer.parseInt(price);
	
			if (intPrice>maxRange || intPrice<minRange)
			{
				Reporter.log("Failed.Price:"+price+" is out of range("+minRange+","+maxRange+") for accommodation : " + iCount);
				Assert.assertTrue(false,"Failed.Price is out of range for accommodation : " + iCount);
			}
			else
			{
				Reporter.log("Passed.Price:"+price+" is within range("+minRange+","+maxRange+") for accommodation : " + iCount);
				Assert.assertTrue(true,"Passed.Price is within range for accommodation : " + iCount);
			}
		 }
	}
	
	/*Description: Method to select review score filter
	 */
	public static void SelectReviewScoreFilter(WebDriver driver, String reviewScore) throws InterruptedException
	{
		//checking review score checkbox
		if (!chkReviewScore(driver, reviewScore).isSelected())
		{
			JavascriptExecutor js = (JavascriptExecutor) driver; 
			js.executeScript("arguments[0].click()",chkReviewScore(driver, reviewScore));
		}
		Reporter.log("Review Score:"+reviewScore+" selected");
		//waiting for list to be loaded
		WaitLoadingSearchList(driver);	
	}
	
	/*Description: Method to verify review score
	 */
	public static void VerifyReviewScore(WebDriver driver, String reviewScore)
	{
		String ratingFetch;
		//fetching review score
		ratingFetch=lblReviewScore(driver).getText();
		 
		Double dblRatingFetch=Double.parseDouble(ratingFetch);
		Double dblRatingEntered=Double.parseDouble(reviewScore);
		//verifying the review score is above required score or not
		if (dblRatingFetch<dblRatingEntered)
		{
			Reporter.log("Failed. Review Rating:"+ dblRatingFetch + " less than entered rating:"+dblRatingEntered);
			Assert.assertTrue(false,"Failed. Review Rating:"+ dblRatingFetch + " less than entered rating:"+dblRatingEntered);
		}
		else
		{
			Reporter.log("Passed. Review Rating:"+ dblRatingFetch + " more than entered rating:"+dblRatingEntered);
			Assert.assertTrue(true,"Passed. Review Rating:"+ dblRatingFetch + " more than entered rating:"+dblRatingEntered);
		}
	}
}
