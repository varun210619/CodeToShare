package TestScript;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Utility.Constants;

public class DriverInit {
	private WebDriver driver;
	
	public WebDriver getDriver(){
		//chrome exe path
		String exePath=Constants.driverPath;
				
		//setting property for chrome driver
		System.setProperty("webdriver.chrome.driver", exePath);
			
		//including chrome settings for loading chrome extensions
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
				
		//driver object
		driver = new ChromeDriver(options);
		return driver;
	}

}
