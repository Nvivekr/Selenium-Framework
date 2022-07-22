package apps.utils;

import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

/**
 * <b>Description</b><br>
 * All user defined softasserts created in the framework should be stored here.
 */
public class SoftAssertion extends TestBase {
	
	
	public static void sAssert(String actual, String expected, String message) {
		if (expected.equals(actual))
			reporter.log(Status.PASS, message + " -Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + " -Failed");
	}

	public static void sAssert(int actual, int expected, String message) {
		if (expected == actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
	}

	public static void sAssert(boolean actual, boolean expected, String message) {
		if (expected==actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
	}

	public static void sAssert(double actual, double expected, String message) {
		if (expected == actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
	}

	public static void sAssert(float actual, float expected, String message) {
		if (expected == actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
	}

	public static void sAssertTrue(Boolean condition, String message) {
		if (condition)
			reporter.log(Status.PASS, message + "- Success");
		else
			reporter.log(message + "- Failed", driver);
		sAssert.assertTrue(condition, message + "-Failed");
	}

	public static void sAssertFalse(Boolean condition, String message) {
		if (condition)
			reporter.log(message + "- Failed .", driver);
		else
			reporter.log(Status.PASS, message + "- Success.");
		sAssert.assertFalse(condition, message + "-Success");
	}

	public static void sAssert(List<String> actual, List<String> expected, String message) {
		if (expected.equals(actual))
			reporter.log(Status.PASS, message + " -Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + " -Failed");
	}
			
	public static void sAssert(Map<String, String> actual, Map<String, String> expected, String message) {
		if(expected.equals(actual)) {
			reporter.log(Status.PASS, message + " -Success.");
		}else {
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
//			sAssert.assertEquals(actual, expected, message + " -Failed.");
			sAssert.assertEquals(actual, expected);
		}
	}

}
