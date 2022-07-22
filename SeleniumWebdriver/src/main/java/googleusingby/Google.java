package googleusingby;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import apps.utils.CommonUtil;
import apps.utils.PageBase;

public class Google extends PageBase{
//	private CommonUtil commonUtil;
//
//	public Google() {
//		commonUtil = new CommonUtil(driver);
//	}
	
	private By searchbox = By.xpath("//input[@title='Search']");
	private By entersearchbutton = By.xpath("//div[@class='lJ9FBc']/..//input[@name='btnK']");
	
	public void sendkeysinsearchbox(String name) {
		commonUtil.sendText(searchbox, name);
	}
	
	public void enterbuttonsearch() {		//for clicking userdefined method has been created
		if(commonUtil.click(entersearchbutton))
			 reporter.log(Status.PASS, "entered search button");
		 else
			 reporter.log(Status.FAIL, "Unable to enter options");
		}

}
