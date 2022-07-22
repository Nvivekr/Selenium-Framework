package apps.googlepageobjects;



import org.openqa.selenium.WebDriver;

import googleusingby.Google;
import googleusingby.Google2;
import googleusingby.Googleusingxml;

public class GooglePageObjectManager {
	private Google google;
	private Google2 google2;
	private Googleusingxml gxml;

	public Google getLoginPage() {
		return (google == null) ? google = new Google() : google;
		//return (google == null) ? null: google;
	}
	public Google2 getGooglePage() {
		return (google2 == null) ? google2 = new Google2() : google2;
		//return (google == null) ? null: google;
	}
	public Googleusingxml getGoogleusingxml() {
		return (gxml == null) ? gxml = new Googleusingxml() : gxml;
		//return (google == null) ? null: google;
	}


}
