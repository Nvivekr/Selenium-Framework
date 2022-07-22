package apps.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import apps.reporting.Reporter;


public class CommonUtil {
//	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private WebDriverWait wait;
	private WebDriver driver;
	public static Properties props = new Properties();
	private static String strCurrentTestcaseReportFilePath;
	private static int month;
	private static int day;
	private static int year;
	private static String strStartTime;
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-hh.mm.ss";
	private static String strCurrentTestcaseName;
//	private static String strTestcasesHTMLReports;
//	private static String strScreenshotSharedPath;
	
	public CommonUtil(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}
	
	TestBase testBase = new TestBase();
	Reporter reporter = testBase.reporter;
	private Actions builder;

	public static synchronized void loadProps() {
		
		try {
			//System.out.println(ConstUtil.APPSSFILE);
			props.load(new FileInputStream("C:\\Users\\rmishra28.EAD\\Desktop\\AppRemediationProject\\AppRemediationFramework\\src\\test\\resources\\config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public String zipFile(String fileToZip, boolean excludeContainingFolder) {
		String archivedFile = fileToZip + ".zip";
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(ConstUtil.APPSSFILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (props.getProperty("emailreportswitch").equals("1")) {
			ZipOutputStream zipOut;
			try {
				zipOut = new ZipOutputStream(new FileOutputStream(archivedFile));

				File srcFile = new File(fileToZip);
				if (excludeContainingFolder && srcFile.isDirectory()) {
					for (String fileName : srcFile.list()) {
						addToZip("", fileToZip + "/" + fileName, zipOut);
					}
				} else {
					addToZip("", fileToZip, zipOut);
				}
				zipOut.flush();
				zipOut.close();
				System.out.println("Successfully created " + archivedFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return archivedFile;
	}
	
	private static void addToZip(String path, String srcFile, ZipOutputStream zipOut) throws IOException {
		File file = new File(srcFile);
		String filePath = "".equals(path) ? file.getName() : path + "/" + file.getName();
		if (file.isDirectory()) {
			for (String fileName : file.list()) {
				addToZip(filePath, srcFile + "/" + fileName, zipOut);
			}
		} else {
			zipOut.putNextEntry(new ZipEntry(filePath));
			FileInputStream in = new FileInputStream(srcFile);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int len;
			while ((len = in.read(buffer)) != -1) {
				zipOut.write(buffer, 0, len);
			}
			in.close();
		}
	}
	public void sendEmail(String to, String cc, String attachmentSource) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(ConstUtil.APPSSFILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (props.getProperty("emailreportswitch").equals("1")) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			final String from = "socialmodule@gmail.com";
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, "@lt12345");
				}
			});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				if (to.contains(";")) {
					String[] toarr = to.split(";");
					for (int i = 0; i < to.split(";").length; i++)
						message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toarr[i]));
				} else
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				if (cc.contains(";")) {
					String[] ccarr = cc.split(";");
					for (int i = 0; i < cc.split(";").length; i++)
						message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccarr[i]));
				} else
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

				message.setSubject("LMR_AutomationReport");
				BodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setText(props.getProperty("mail.body"));

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();

				String filename = attachmentSource;
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("LMR_AutomationReport.zip");
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);

				Transport.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			System.out.println("Mail Sent to " + to + "," + cc + " successfully");
		} else {
			System.out.println(
					"Not sending any email as email report switch is disabled. To enable email report switch access config.properties and update emailreportswitch to 1");
		}
	}

		*//**
	 * 
	 * @param driver
	 * @param tablelocator      - Locator of the web table
	 * @param columnNumber      - Required Column
	 * @param startingRowNumber - the row from where the actual data present
	 *                          excluding the table headers
	 * @return
	 * 
	 * 		This method can be used to retrieve any particular column value from
	 *         a web table
	 * 
	 *         NOTE: Right click on o1st element the desired column. Copy the xpath
	 *         till "/tbody" and pass as tableLoactor. Row and column number starts
	 *         from index 1 instead of index 0.
	 * 
	 *//*
	public static synchronized List<String> getColumnDataAsString(WebDriver driver, String tableLocatorXpath,
			int columnNumber, int startingRowNumber) {

		List<WebElement> rows = driver.findElements(By.xpath(tableLocatorXpath + "/tr"));
//		System.out.println("CommonUtil.getColumnData()------no of rows :"+rows.size());
		List<String> columnData = new ArrayList<>();
		for (int i = startingRowNumber; i <= rows.size(); i++) {
			columnData.add(driver.findElement(By.xpath(tableLocatorXpath + "/tr[" + i + "]/td[" + columnNumber + "]"))
					.getText());
		}
		return columnData;
	}

	public static synchronized List<WebElement> getColumnDataAsWebElement(WebDriver driver, String tableLocatorXpath,
			int columnNumber, int startingRowNumber) {

		List<WebElement> rows = driver.findElements(By.xpath(tableLocatorXpath + "/tr"));
//		System.out.println("CommonUtil.getColumnData()------no of rows :"+rows.size());
		List<WebElement> columnData = new ArrayList<>();
		for (int i = startingRowNumber; i <= rows.size(); i++) {
			columnData.add(driver.findElement(By.xpath(tableLocatorXpath + "/tr[" + i + "]/td[" + columnNumber + "]")));
		}
		return columnData;
	}

	public static synchronized List<Boolean> getColumnDataAsBoolean(WebDriver driver, String tableLocatorXpath,
			int columnNumber, int startingRowNumber) {

		List<WebElement> rows = driver.findElements(By.xpath(tableLocatorXpath + "/tr"));
//		System.out.println("CommonUtil.getColumnData()------no of rows :"+rows.size());
		List<Boolean> columnData = new ArrayList<>();
		for (int i = startingRowNumber; i <= rows.size(); i++) {
			columnData.add(driver.findElement(By.xpath(tableLocatorXpath + "/tr[" + i + "]/td[" + columnNumber + "]"))
					.isSelected());
		}
		return columnData;
	}

	public static Calendar getCurrentTimeWithTimeZone(String timeZone) {
		return Calendar.getInstance(TimeZone.getTimeZone(timeZone));
	}

	

	public static synchronized void switchToFrameAndClickOnElement(WebDriver driver, By by) {
		for (int i = 0; i < driver.findElements(By.tagName("iframe")).size(); i++) {
			driver.switchTo().frame(i);
			waitforSec(2000);
			int size = driver.findElements(by).size();
			System.out.println("size is :" + size + " - for frame :" + i);
			if (size > 0) {
				System.out.println("Clicked on Element in switched frame");
				driver.findElement(by).click();
				waitforSec(2000);
			}
			driver.switchTo().defaultContent();
			waitforSec(2000);
		}
	}*/

	public static synchronized void waitforSec(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*@Deprecated
	public static synchronized String[][] getDatafromExcelSheet(ExcelUtil excelUtil, String sheetName) {
		String[][] strArray = new String[excelUtil.getRowCount(sheetName)][excelUtil.getColumnCount(sheetName)];
		for (int i = 0; i < excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < excelUtil.getColumnCount(sheetName); j++) {
				strArray[i][j] = excelUtil.getCellData(sheetName, j, i);
			}
		}
		return strArray;
	}

	public static synchronized boolean isAttributeAvailable(WebDriver driver, By by, String attribute) {
		if (driver.findElement(by).getAttribute(attribute) != null) {
			return true;
		} else
			return false;

	}

	

	*//**
	 * Can be used to check whether an entered string is a
	 *         valid date of dateformat
	 * @param dateFormat eg - "yyyy-MM-dd"
	 * @param date
	 * @return
	 *//*
	public static synchronized boolean isDateValid(String dateFormat, String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setLenient(false);
		try {
			simpleDateFormat.parse(date.trim());
		} catch (Exception pe) {
			return false;
		}
		return true;
	}

	*//**
	 * Can be used to generate Random String
	 * @return
	 *//*
	private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
	private static final SecureRandom RANDOM = new SecureRandom();

	public static synchronized String generateRandomString(int randomStringLength) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < randomStringLength; ++i) {
			sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return sb.toString();
	}
	
	*//**
	 * Can be used to generate Random number
	 * @return
	 *//*
	private static final String NUMBER = "0123456789";
	private static final SecureRandom RAND = new SecureRandom();

	public static synchronized String generateRandomNumber(int randomStringLength) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < randomStringLength; ++i) {
			sb.append(NUMBER.charAt(RAND.nextInt(NUMBER.length())));
		}
		return sb.toString();
	}
	
	*//**
	  * Can be used to generate Random number between a range
	 * @return integer
	 *//*	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
			

	
	public static synchronized void scrollIntoView(WebDriver driver, By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(locator));
	}
	*/
	
	public Boolean sendText(By by, String text) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if(driver.findElement(by).isDisplayed()) {
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(text);
				}
			else {
				 JavascriptExecutor jse = (JavascriptExecutor)driver;
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
				 jse.executeScript("arguments[0].value='"+text+"';", driver.findElement(by));
			}
			return true;
			}
		catch (Exception ex) {
	        if (ex.getMessage().contains("Element is not interactable")) {
	        	JavascriptExecutor jse = (JavascriptExecutor)driver;
	        	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
			    jse.executeScript("arguments[0].value='"+text+"';", driver.findElement(by));
				 return true;
	        }
	        else {
	        	ex.printStackTrace();
	        	return false;
	        }
	    }
	}
	
	
	public Boolean click(By by) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if(driver.findElement(by).isDisplayed()) {
				driver.findElement(by).click();
				}
			else {
				 JavascriptExecutor jse = (JavascriptExecutor)driver;
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
				 jse.executeScript("arguments[0].click()", driver.findElement(by));
			}
			return true;
			}
		catch (Exception ex) {
	        if (ex.getMessage().contains("Element is not displayed")) {
	        	JavascriptExecutor jse = (JavascriptExecutor)driver;
	        	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
				jse.executeScript("arguments[0].click()", driver.findElement(by));
				 return true;
	        }
	        else {
	        	ex.printStackTrace();
	        	return false;
	        }
	    }
	}
	
	public Boolean contextClick(By by) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			builder = new Actions(driver);
			builder.contextClick(driver.findElement(by)).build().perform();	
			return true;
			}
		catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	        }
	}
	
	public String getText(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if(driver.findElement(by).isDisplayed()) {
				return wait.until(ExpectedConditions.presenceOfElementLocated(by)).getText();
				}
			else {
				 JavascriptExecutor jse = (JavascriptExecutor)driver;
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
				 return (String) jse.executeScript("arguments[0].value", driver.findElement(by));
			}
			}
		catch (Exception ex) {
	        if (ex.getMessage().contains("Element is not displayed")) {
	        	JavascriptExecutor jse = (JavascriptExecutor)driver;
	        	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
	        	return (String) jse.executeScript("arguments[0].value", driver.findElement(by));
	        }
	        else {
	        	ex.printStackTrace();
	        	return null;
	        	}
	        }
		}
	
	public String getAttribute(By by, String attribute) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if(driver.findElement(by).isDisplayed()) {
				return wait.until(ExpectedConditions.presenceOfElementLocated(by)).getAttribute(attribute);
				}
			else {
				 JavascriptExecutor jse = (JavascriptExecutor)driver;
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
				 return (String) jse.executeScript("arguments[0].getAttribute('"+attribute+"')", driver.findElement(by));
			}
			}
		catch (Exception ex) {
	        if (ex.getMessage().contains("Element is not displayed")) {
	        	JavascriptExecutor jse = (JavascriptExecutor)driver;
	        	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
	        	return (String) jse.executeScript("arguments[0].getAttribute('"+attribute+"')",driver.findElement(by));
	        }
	        else {
	        	ex.printStackTrace();
	        	return null;
	        	}
	        }
		}
	
	public void switchToWindow(int windownum) {
		 Set<String> handles = driver.getWindowHandles();
		 Iterator<String> it = handles.iterator();
		 String parentWindowHandId = it.next();
		 switch (windownum) {
		case 1:
			driver.switchTo().window(parentWindowHandId);
			break;
		case 2:
			String childWindowHandId = it.next();
			driver.switchTo().window(childWindowHandId);
			break;
		default:
			System.out.println("Please enter 1 or 2, else extend the window handling");
			break;
		} 
	}
	
	public static String now() {

//		File ostestcaseshtmlreportsharedpath = new File(strTestcasesHTMLReports);
//		if (!ostestcaseshtmlreportsharedpath.exists()) {
//			ostestcaseshtmlreportsharedpath.mkdir();
//		}
//
//		File osscreenshotsharedpath = new File(strScreenshotSharedPath);
//		if (!osscreenshotsharedpath.exists()) {
//			osscreenshotsharedpath.mkdir();
//		}

		Calendar cal = Calendar.getInstance();
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		year = cal.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//		strCurrentTime = sdf.format(cal.getTime());
//		strScreenshot = (String) (strScreenshotSharedPath + "\\" + strCurrentTestcaseName);

		return sdf.format(cal.getTime());
	}
	
	public String regEx(String strMatcher, String strPattern) {
		Pattern pattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(strMatcher);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	    	return matcher.group();
	    } else {
	    	return "";
	    }
	}
	
	public void robot(String filepath,String filename) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_P);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_P);
		Thread.sleep(30000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		
		String text = filename;
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(2000);
		
		for(int i=0;i<6;i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(1000);
			}
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(1000);
		text = filepath;
		stringSelection = new StringSelection(text);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_SHIFT);
		for(int i=0;i<5;i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(1000);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(1000);
		}
		robot.keyRelease(KeyEvent.VK_SHIFT);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static String initializeCurrentTestExecutionReport() {

		try {
			strCurrentTestcaseReportFilePath = "C:\\Users\\mjain85\\OneDrive - DXC Production\\Documents\\AppRemediationFramework-master\\AppRemediationFramework-master_v0.2\\xyz.txt";
			File strReportFile = new File(strCurrentTestcaseReportFilePath);
			FileWriter aWriter;
			aWriter = new FileWriter(strReportFile, true);

			BufferedWriter bw = new BufferedWriter(aWriter);
			bw.write("<!DOCTYPE html>\n");
			bw.write("<html>\n");
			bw.write("<head>\n");
			bw.write("<style>\n");
			bw.write("#header {\n");
			bw.write("background-color:white;\n");
			bw.write("color:black;\n");
			bw.write("padding:5px;\n");
			bw.write("}\n");
			bw.write("</style>\n");
			bw.write("</head>\n");
			bw.write("<body>\n");
			bw.write("<div id='header'>\n");
			bw.write("<body>");
			bw.write("<div class=\"page_container\">");
			bw.write("<div class=\"head\">");
			bw.write("</div>");
			bw.write("<div class=\"content\">");
			bw.write("<table class=\"content_table\" cellpadding=\"0\" cellspacing=\"0\">");
			bw.write("<tr>");
			bw.write("<td valign=\"top\">");
			bw.write("<div class=\"right_content\">");
			bw.write("<div id=\"tabs_environment\">");
			bw.write("<u><b><font color=\"Blue\">Execution Report</font></b></u>");
			bw.write("<div id=\"tabs-set-1\"  class=\"f2\">");
			bw.write("<div style=\"margin-top: 10px;\">");
			bw.write("<table id=\"set_table\" width=\"100%\" class=\"f2\" cellpadding=\"\" cellspacing=\"10\" ><tr>");
			bw.write("<td><b>Exec. Date</b></td>");
			bw.write("<td><b>Exec. Start Time</b></td>");
			bw.write("<td><b>Test Case </b></td>");
			bw.write("<td><b>Machine Name</b></td>");
			bw.write("<td><b>Machine Login User Name</b></td>");
			bw.write("<td><b>OS</b></td>");
			bw.write("<td><b>Browser</b></td>");
			bw.write("<td><b>Env. & URL</b></td>");
			bw.write("</tr>");
			bw.write("<tr class=\"list_table_tr\">");
			bw.write("<td>" + month + "-" + day + "-" + year + "</td>");
			bw.write("<td>" + strStartTime + "</td>");
			bw.write("<td>" + strCurrentTestcaseName + "</td>");
			bw.write("<td>" + System.getenv("COMPUTERNAME") + "</td>");
			bw.write("<td>" + System.getProperty("user.name") + "</td>");
			bw.write("<td>" + System.getProperty("os.name") + "</td>");
			bw.write("<td>Firebox</td>");
			/*
			 * ENV = new Properties(); String env = CONFIG.getProperty("ENV");
			 * FileInputStream fs2 = new
			 * FileInputStream(System.getProperty("user.dir"
			 * )+"//src//com//hsn//config//"+env+".properties"); ENV.load(fs2);
			 * strcurrentEnv = CONFIG.getProperty("ENV").trim().toUpperCase();
			 * strcurrenEnvtUrl = ENV.getProperty("siteUrl").trim();
			 * 
			 * if(strcurrenEnvtUrl.contains("?")) { strcurrenEnvtUrl =
			 * ENV.getProperty("siteUrl").split("\\?")[0].toString().trim(); }
			 * 
			 * strCurrentEnvandUrl = strcurrentEnv + " & " + strcurrenEnvtUrl;
			 */
			bw.write("<td> QAT1</td>");
			bw.write("</tr>");
			bw.write(" <tr class=\"list_table_tr\">");
			bw.write(" <td></td>");
			bw.write(" <td></td>");
			bw.write(" <td></td>");
			bw.write("<td></td>");
			bw.write(" <td></td>");
			bw.write("<td></td>");
			bw.write("</tr>");
			bw.write("<tr class=\"list_table_tr\">");
			bw.write("<td><b>Test Step Name</b></td>");
			bw.write("<td><b>Test Step Description</b></td>");
			bw.write("<td><b>Test Data</b></td>");
			bw.write("<td><b>Result</b></td>");
			bw.write("<td><b>Result Description</b></td>");
			bw.write("<td><b>ScreenShot</b></td>");
			bw.write("</tr>");
			bw.flush();
			bw.close();
			aWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	
}
