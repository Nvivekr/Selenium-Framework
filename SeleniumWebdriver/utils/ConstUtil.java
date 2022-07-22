package apps.utils;

import apps.enums.DriverType;

public final class ConstUtil {
	public static String RESFOLDER = System.getProperty("user.dir") + "\\src\\test\\resources\\";

	public static final String APPSSFILE = RESFOLDER + "config.properties";
	/*public static final String EMAIL_TO = "rmishra28@dxc.com";
	public static final String EMAIL_CC = "rmishra28@dxc.com";*/

	public static final String CASURL = "https://ssotst.xchanging.com/portal/UI/Login";
	public static final String DFVURL = "https://ssotst.xchanging.com/portal/UI/Login";
	public static final String XDHURL = "https://10.16.26.63/XDHAdmin/";
	public static final String XAGURL = "https://xagtst.xchanging.com/XAGOnline/default.aspx";
	public static final String IMRURL = "https://repositorytsb.xchanging.com/web/";
	public static final String CASAURL = "https://casatsc.xchanging.com/servlet/CBServlet?CBinterface=caLPC&CBOperation=start&ssoSessionId=0&portalUpdateFlag=N&portalUpdateTime=0";
	public static final String GSTURL = "https://ewaybillgst.gov.in/login.aspx";
	
	public static final DriverType CASDRIVER = DriverType.CHROME;
	public static final DriverType DFVDRIVER = DriverType.CHROME;
	public static final DriverType XDHDRIVER = DriverType.CHROME;
	public static final DriverType XAGDRIVER = DriverType.CHROME;
	public static final DriverType IMRDRIVER = DriverType.INTERNETEXPLORER;
	public static final DriverType CASADRIVER = DriverType.INTERNETEXPLORER;
	public static final DriverType GSTDRIVER = DriverType.INTERNETEXPLORER;
	
}