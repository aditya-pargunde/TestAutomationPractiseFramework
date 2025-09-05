package utilities;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentTestNGListener implements ITestListener {

	private static ExtentReports extent = ExtentManager.getReporter();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
	    test.get().log(Status.FAIL, result.getThrowable());

	    // Get WebDriver from BaseTest
	    WebDriver driver = BaseTest.getDriver();
	    if (driver != null) {
	        // Capture screenshot with unique timestamp
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
			// Add screenshot to report
			test.get().addScreenCaptureFromPath(screenshotPath);
	    }
	}


	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}