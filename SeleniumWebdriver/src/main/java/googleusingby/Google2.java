package googleusingby;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import apps.utils.CommonUtil;
import apps.utils.PageBase;

public class Google2 extends PageBase{
	
//	public Google2(WebDriver driver) {
//		this.driver=driver;
//		PageFactory.initElements(driver, this);	}
	private CommonUtil commonUtil;

	public Google2() {
		commonUtil = new CommonUtil(driver);
//		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@title='Search']") public WebElement searchbox;
	@FindBy(xpath="(//input[@name='btnK'])[1]") WebElement entersearchbutton;
	
	
	public void sendkeysinsearchbox(String name) {
		wait.until(ExpectedConditions.visibilityOf(searchbox)).sendKeys(name);
		
		
//		commonUtil.sendText(searchbox, name);
		System.out.println("completed enterning name");
	}
	
//	public void enterbuttonsearch() {		//for clicking userdefined method has been created
//		if(commonUtil.click(entersearchbutton))
//			 reporter.log(Status.PASS, "entered search button");
//		 else
//			 reporter.log(Status.FAIL, "Unable to enter options");
//		}

}
