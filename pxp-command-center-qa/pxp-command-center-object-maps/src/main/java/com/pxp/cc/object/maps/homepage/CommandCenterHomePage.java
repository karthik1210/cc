// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.object.maps.homepage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.pxp.cc.object.maps.ccmainpage.CCMainPage;
import com.pxp.cc.object.maps.utilities.CCUtils;

public class CommandCenterHomePage extends CCMainPage {

	@FindBy(how = How.XPATH, using = "//mat-icon[@class='mat-icon notranslate mat-badge mat-badge-warn material-icons mat-icon-no-color mat-badge-overlap mat-badge-above mat-badge-after mat-badge-medium']")
	private WebElement alerts;

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']//span[@class='mat-button-wrapper']")
	private WebElement userFullName;

	@FindBy(how = How.XPATH, using = "//mat-icon[normalize-space()='link']")
	private WebElement ssoLinks;

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']")
	private WebElement userIcon;

	@FindBy(how = How.XPATH, using = "//h1[@class='.page-title']")
	private WebElement pxpCommandCenterLabel;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Profile Settings']")
	private WebElement profileSetting;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Help']")
	private WebElement userHelp;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Log out']")
	private WebElement userLogout;

	@FindBy(how = How.XPATH, using = "//span[@matbadgeoverlap='false']")
	private WebElement alertsLabel;

	@FindBy(how = How.XPATH, using = "//mat-icon[normalize-space()='cancel']")
	private WebElement alertsClose;

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger alert-view-button ng-tns-c128-0 mat-icon-button mat-button-base']//span[@class='mat-button-wrapper']")
	private WebElement alertsViewButton;

	@FindBy(how = How.XPATH, using = "//div[@class='alert-drawer-main ng-tns-c147-0 ng-trigger ng-trigger-slideInOut']//div[1]//div[1]//div[1]//div[3]//span[2]//label[1]")
	private WebElement alertsNotifictaionStatus;


	@FindBy(how = How.XPATH, using = "//div[@class='cdk-overlay-container']//a")
	private List<WebElement> ssoLinkList;

	List<String> actualSsoLinks = new ArrayList<String>();

	public final List<String> expectedInternalSsoLinks = Arrays.asList("Appointments Dashboard",
			"DCF Admin Tool - Super User", "Form Builder (Forms)", "Practice Portal", "PSS Admin",
			"Virtual Visits Admin Tool");
	public final List<String> expectedExternalSsoLinks = Arrays.asList("Appointments Dashboard", "Form Builder (Forms)",
			"Pay Reports", "Practice Portal", "PSS Admin");

	public CommandCenterHomePage(WebDriver driver) {
		super(driver);
	}

	public void clickSSO() {
		CCUtils.verifyAndWaitForElement(ssoLinks, driver);
		javascriptClick(ssoLinks);
		log("SSOlinks clicked.....");
	}

	public void clickAlerts() {
		CCUtils.verifyAndWaitForElement(alerts, driver);
		javascriptClick(alerts);
		log("Alerts clicked.....");
	}

	public void clickUserIcon() {
		CCUtils.verifyAndWaitForElement(userIcon, driver);
		javascriptClick(userIcon);
		log("User icon clicked.....");
	}

	public void clickLogout() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		javascriptClick(userLogout);
		log("Clicked on logout");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void verifyTopPanelElements() {
		log("SUBSTEP : Verifying Command Center Top Panel Elements");
		CCUtils.assertTrue(pxpCommandCenterLabel.getText().equals("PxP Command Center"), "PXP Home icon is displayed");
		CCUtils.assertTrue(ssoLinks.isDisplayed(), "SSO icon is displayed");
		CCUtils.assertTrue(alerts.isDisplayed(), "SSO icon is displayed");
		CCUtils.assertTrue(userFullName.isDisplayed(), "User icon is displayed");
	}

	public WebDriver verifyUserIconContent() {
		CCUtils.verifyAndWaitForElement(profileSetting, driver);
		CCUtils.verifyAndWaitForElement(userHelp, driver);
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		return driver;
	}

	public void getSsoLinks() {
		log("number of sso links present for user is " + ssoLinkList.size());

		for (int i = 0; i < ssoLinkList.size(); i++) {
			actualSsoLinks.add(ssoLinkList.get(i).getText());
		}
		log("SSO links present for user are :" + actualSsoLinks);
	}

	public void verifySSOLinksForInternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void verifySSOLinksForExternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void verifyAlertsContent() {
		log("Verifing contents for alert");
		CCUtils.assertTrue(alertsLabel.isEnabled(), "Alerts is displayed");
		CCUtils.assertTrue(alertsClose.isDisplayed(), "Alert close button is displayed");
		CCUtils.assertTrue(alertsNotifictaionStatus.isDisplayed(), "Alerts notification status is displayed");
	}

}
