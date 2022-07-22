package googleusingby;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import apps.utils.CommonUtil;
import apps.utils.CommonUtilusingelement;
import apps.utils.PageBase;
import apps.utils.TestBase;
import googleusingxml.dataextractor;
import keyboardpress.Keyboard;

public class Googleusingxml extends TestBase{
	private CommonUtilusingelement commonUtil;
	private Keyboard keyboard;

	enum objectrepo{
		searchbox(dataextractor.getElement("LoginPage.searchbox")),
		entersearchbutton(dataextractor.getElement("LoginPage.entersearchbutton")),
		Settings(dataextractor.getElement("LoginPage.Settings")),
		listoflinks(dataextractor.getElements("LoginPage.listoflinks"));
		private WebElement webelement; private List<WebElement> listofwebelements;
		objectrepo(WebElement element) {
			// TODO Auto-generated constructor stub
			this.webelement = element;
		}
		objectrepo(List<WebElement> listofweb){
			this.listofwebelements = listofweb;
		}
	}

	public void enterandsearchname() throws AWTException {
		keyboard=new Keyboard();
//		CommonUtilusingelement.sendText(objectrepo.searchbox.webelement, "vivek");
		keyboard.type("Vivek");
		
//		CommonUtilusingelement.sendText(dataextractor.getElement("LoginPage.searchbox"),"vivek");
//		System.out.println(objectrepo.listoflinks.listofwebelements);
//		dataextractor.getElements("LoginPage.listoflinks").
//		forEach(n->
//		{
//			System.out.println(n.getText());
//		});
		if(CommonUtilusingelement.click(objectrepo.entersearchbutton.webelement))
			reporter.log(Status.PASS, "entered search button");
		
		else
			reporter.log(Status.FAIL, "Unable to enter options");
//	}

//	public Googleusingxml() {
//		commonUtil = new CommonUtilusingelement(driver);
//		//		this.driver=driver;
//	}

}
}




