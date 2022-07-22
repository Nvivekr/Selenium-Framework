package apps.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	static ExtentReports extentreport;

	public static synchronized ExtentReports getInstance() {
		return extentreport;
	}
	/**
	 Creates the instance for extent reports and returns the extentreport instance
	 @return extentreports instance
	*/

	public static synchronized ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTheme(Theme.DARK);
		String s[] = fileName.split("/");
		htmlReporter.config().setDocumentTitle("LMR Automation Report - " + s[s.length - 1]);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(s[s.length - 1]);

		extentreport = new ExtentReports();
		extentreport.attachReporter(htmlReporter);

		return extentreport;
	}

}
