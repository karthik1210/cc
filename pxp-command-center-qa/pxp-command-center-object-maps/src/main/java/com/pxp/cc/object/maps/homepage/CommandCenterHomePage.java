// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.object.maps.homepage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	@FindBy(how = How.XPATH, using = "//div[@class='alert-drawer-main ng-tns-c147-21 ng-trigger ng-trigger-slideInOut']//div[1]//div[1]//div[1]//div[3]//span[2]//label[1]")
	private WebElement alertsNotifictaionStatus;

	@FindBy(how = How.XPATH, using = "//div[@class='cdk-overlay-container']//a")
	private List<WebElement> ssoLinkList;

	@FindBy(how = How.ID, using = "mat142")
	private WebElement ccHamburgerMenu;

	@FindBy(how = How.XPATH, using = "//*[@id=\"d101\"]/mat-sidenav-container/mat-sidenav/div/mat-nav-list")
	private WebElement organizationTree;

	@FindAll({
			@FindBy(how = How.XPATH, using = "//*[@id=\"d101\"]/mat-sidenav-container/mat-sidenav/div/mat-nav-list/a") })
	private List<WebElement> menuItem;

	@FindBy(how = How.XPATH, using = "OrganizationTree")
	private WebElement organizationTreeText;

	@FindBy(how = How.XPATH, using = "//a[@ng-reflect-router-link='/dashboard/users']//div[@class='mat-list-item-content']")
	private WebElement commandCenterUsers;

	@FindBy(how = How.XPATH, using = "//a[@ng-reflect-router-link='/dashboard/reports']//div[@class='mat-list-item-content']")
	private WebElement reporting;

	@FindBy(how = How.XPATH, using = "//*[@id='d101']/mat-sidenav-container/mat-sidenav/div/mat-nav-list/a[4]/div/div[2]/p")
	private WebElement developerTools;

	List<String> actualSsoLinks = new ArrayList<String>();

	public final List<String> expectedInternalSsoLinks = Arrays.asList("Appointments Dashboard",
			"DCF Admin Tool - Super User", "Form Builder (Forms)", "Practice Portal", "PSS Admin",
			"Virtual Visits Admin Tool");
	public final List<String> expectedExternalSsoLinks = Arrays.asList("Appointments Dashboard", "Form Builder (Forms)",
			"Pay Reports", "Practice Portal", "PSS Admin");

	public CommandCenterHomePage(WebDriver driver) {
		super(driver);
	}

	public void clickHamburger() {
		highlightElement(ccHamburgerMenu);
		wait.until(ExpectedConditions.visibilityOf(ccHamburgerMenu));
		ccHamburgerMenu.click();
	}

	public void clickCCMenuItems() {
		for (WebElement webElement : menuItem) {
			wait.until(ExpectedConditions.visibilityOf(webElement));
			webElement.click();
			log(driver.getCurrentUrl());
			log("Title logger :" + webElement.getText());
			String getElementText = webElement.findElement(By.className("mat-list-text")).getText();
			if (getElementText.equals("Organization Tree")) {
				gotoOrganizationTree();
				CCUtils.assertEquals(getElementText, ("Organization Tree"));
			} else if (getElementText.equals("Command Center Users")) {
				gotoCommandCenterUsers();
				CCUtils.assertEquals(getElementText, ("Command Center Users"));
			} else if (getElementText.equals("Reporting")) {
				gotoReporting();
				CCUtils.assertEquals(getElementText, ("Reporting"));
			} else if (getElementText.equals("Developer Tools")) {
				gotoDeveloperTools();
				CCUtils.assertEquals(getElementText, ("Developer Tools"));
			}
		}
	}

	public void gotoOrganizationTree() {
		javascriptClick(organizationTree);
		log("OrganizationTree Tab clicked.....");
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(organizationTree));
	}

	public void gotoCommandCenterUsers() {
		javascriptClick(commandCenterUsers);
		log("Command Center Users Tab clicked.....");
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(commandCenterUsers));
	}

	public void gotoReporting() {
		javascriptClick(reporting);
		log("reporting Tab clicked.....");
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(reporting));
	}

	public void gotoDeveloperTools() {
		javascriptClick(developerTools);
		log("developerTools Tab clicked.....");
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(developerTools));
	}

	public void clickSSO() {
		highlightElement(ssoLinks);
		javascriptClick(ssoLinks);
		log("SSOlinks clicked.....");
	}

	public void clickAlerts() {
		highlightElement(alerts);
		javascriptClick(alerts);
		log("Alerts clicked.....");
	}

	public void clickUserIcon() {
		highlightElement(userIcon);
		wait.until(ExpectedConditions.visibilityOf(userIcon));
		javascriptClick(userIcon);
		log("User icon clicked.....");
	}

	public void clickLogout() throws InterruptedException {
		highlightElement(userLogout);
		wait.until(ExpectedConditions.visibilityOf(userLogout));
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
		CCUtils.assertTrue(alertsLabel.isDisplayed(), "Alerts is displayed");
		CCUtils.assertTrue(alertsClose.isDisplayed(), "Alert close button is displayed");
		CCUtils.assertTrue(alertsNotifictaionStatus.isDisplayed(), "Alerts notification status is displayed");
	}

}
