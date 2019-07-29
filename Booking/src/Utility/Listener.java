package Utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
	
	//ITestListener will execute before starting of Test set 
	public void onStart(ITestContext arg0) {
		//Reporter.log("About to begin executing Test " + arg0.getName(), true);
	}

	//ITestListener will execute once the Test set is finished
	public void onFinish(ITestContext arg0) {
		//Reporter.log("Completed executing test " + arg0.getName(), true);
	}

	//ITestListener will execute only when the test is pass test
	public void onTestSuccess(ITestResult arg0) {
		//TBD - screenshot or any log
	}

	//ITestListener will execute only on the event of fail test
	public void onTestFailure(ITestResult result) {
		//TBD - screenshot or any log
	}
	
	//ITestListener will execute before the main test start 
	public void onTestStart(ITestResult arg0) {
		//TBD-no implementation yet
	}

	//ITestListener will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult arg0) {
		//TBD-no implementation yet
	}

	//ITestListener will execute only if test failed within success ercentage
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		//TBD-no implementation yet
	}

}
