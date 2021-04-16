package com.pxp.cc.ui.testcases;

import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.pxp.cc.object.maps.homepage.CommandCenterHomePage;
import com.pxp.cc.object.maps.loginpage.CommandCenterLoginPage;

public class CommandCenterGenericTest extends BaseTestNGWebDriver {

	public static Properties property = new Properties();

	@BeforeClass(alwaysRun = true)
	public void setUpTestData() throws Exception {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		InputStream url = ClassLoader.getSystemResourceAsStream("data-driven/" + propertyFileNameString);
		property.load(url);
	}

	public CommandCenterHomePage loginToCCPortal(WebDriver driver, String url, String username, String password) throws InterruptedException {
		log("SUBSTEP : Login to CC portal. url = " + url + " and email =" + username + " and password=" + password);
		CommandCenterLoginPage commandcenterloginpage = new CommandCenterLoginPage(driver, url);
		CommandCenterHomePage commandcenterhomepage = commandcenterloginpage.login(username, password);
		Thread.sleep(2000);
		return commandcenterhomepage;
	}
}
