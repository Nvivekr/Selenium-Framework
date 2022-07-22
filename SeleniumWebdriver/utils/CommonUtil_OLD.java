package apps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
/*import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

public class CommonUtil_OLD {
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private Reporter reporter;
	public static Properties props = new Properties();

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
		
}
