// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class TabsNavigationOnGeneralSettingPage extends CCMainPage {

	public TabsNavigationOnGeneralSettingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "locationOnIcon")
	private WebElement locationTab;

	@FindBy(id = "manageAccountsIcon")
	private WebElement providersAndResourcesTab;

	@FindBy(id = "headerEventIcon")
	private WebElement appointmentTypeTab;

	@FindBy(id = "locationTile")
	private WebElement locationTile;

	@FindBy(id = "providerTile")
	private WebElement providersAndResourcesTile;

	@FindBy(id = "appointmentTile")
	private WebElement appointmentTypeTile;

	@FindBy(id = "generalLabel")
	private WebElement labelPageTitle;

	@FindBy(id = "appointmentLabel")
	private WebElement appointmentLabel;

	@FindBy(id = "headerSettingIcon")
	private WebElement genaralSettingIcon;

	public void verifyVisibilityOfTab() throws InterruptedException, AWTException {
		log("Verifying Location,Provider & Resources ,Appointment type Tabs are present");
		verifyLocationTab();
		verifyProvidersAndResourcesTab();
		verifyAppointmentTypeTab();
	}

	public void verifyLocationTab() {
		CCUtils.verifyAndWaitForElement(locationTab, driver);
		CCUtils.assertEquals(locationTab.isEnabled(), true);
		log("Location Tab is Enabled");
	}

	public void verifyProvidersAndResourcesTab() {
		CCUtils.verifyAndWaitForElement(providersAndResourcesTab, driver);
		CCUtils.assertEquals(providersAndResourcesTab.isEnabled(), true);
		log("ProvidersAndResources Tab is Enabled");
	}

	public void verifyAppointmentTypeTab() {
		CCUtils.verifyAndWaitForElement(appointmentTypeTab, driver);
		CCUtils.assertEquals(appointmentTypeTab.isEnabled(), true);
		log("AppointmentTypeTab Tab is Enabled");
	}

	public void verifyVisibilityOfTiles() throws InterruptedException, AWTException {
		log("Verifying Location,Provider & Resources ,Appointment type Tabs are present");
		verifyLocationTile();
		verifyProvidersAndResourcesTile();
		verifyAppointmentTypeTile();
	}

	public void verifyLocationTile() {
		CCUtils.verifyAndWaitForElement(locationTile, driver);
		CCUtils.assertEquals(locationTile.isEnabled(), true);
		log("Location tile is Enabled");
	}

	public void verifyProvidersAndResourcesTile() {
		CCUtils.verifyAndWaitForElement(providersAndResourcesTile, driver);
		CCUtils.assertEquals(providersAndResourcesTile.isEnabled(), true);
		log("ProvidersAndResources tile is Enabled");
	}

	public void verifyAppointmentTypeTile() {
		CCUtils.verifyAndWaitForElement(appointmentTypeTile, driver);
		CCUtils.assertEquals(appointmentTypeTile.isEnabled(), true);
		log("AppointmentTypeTab tile is Enabled");
	}

	public void verifyNavigationOfTab() throws InterruptedException, AWTException {
		log("Verifying Location,Provider & Resources ,Appointment type Tabs are present");
		verifyNavigationLocationTab();
		verifyNavigationProvidersAndResourcesTab();
		verifyNavigationAppointmentTypeTab();
	}

	public void verifyNavigationLocationTab() {
		CCUtils.verifyAndWaitForElement(locationTab, driver);
		locationTab.click();
		log("Location tab is Clickable");
		verifyLocationlabel();
		log("LocationLabel is displayed");
	}

	public void verifyNavigationProvidersAndResourcesTab() {
		CCUtils.verifyAndWaitForElement(providersAndResourcesTab, driver);
		providersAndResourcesTab.click();
		log("ProvidersAndResources tab is Clickable");
		verifyProvidersAndResourceslabel();
	}

	public void verifyNavigationAppointmentTypeTab() {
		CCUtils.verifyAndWaitForElement(appointmentTypeTab, driver);
		appointmentTypeTab.click();
		log("AppointmentTypeTab tab is Clickable");
		verifynAppointmentTypelabel();
	}

	public void verifyLocationlabel() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Locations");
		log("LocationLabel is displayed");
	}

	public void verifyProvidersAndResourceslabel() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Providers, Resources & Staff");
		log("ProvidersAndResources Label is displayed");
	}

	public void verifynAppointmentTypelabel() {
		CCUtils.verifyAndWaitForElement(appointmentLabel, driver);
		CCUtils.assertEquals(appointmentLabel.getText().trim(), "Appointment Types");
		log("nAppointmentType Label is displayed");
	}

	public void verifyNavigationOfTile() throws InterruptedException, AWTException {
		log("Verifying Location,Provider & Resources ,Appointment type Tabs are present");
		verifyNavigationLocationTile();
		verifyNavigationProvidersAndResourcesTile();
		verifyNavigationAppointmentTypeTile();
	}

	public void verifyNavigationLocationTile() {
		CCUtils.verifyAndWaitForElement(genaralSettingIcon, driver);
		genaralSettingIcon.click();
		CCUtils.verifyAndWaitForElement(locationTile, driver);
		locationTile.click();
		log("Location Tile is Clickable");
		verifyLocationlabel();
		log("LocationLabel is displayed");
	}

	public void verifyNavigationProvidersAndResourcesTile() {
		CCUtils.verifyAndWaitForElement(genaralSettingIcon, driver);
		genaralSettingIcon.click();
		CCUtils.verifyAndWaitForElement(providersAndResourcesTile, driver);
		providersAndResourcesTile.click();
		log("ProvidersAndResources Tile is Clickable");
		verifyProvidersAndResourceslabel();
	}

	public void verifyNavigationAppointmentTypeTile() {
		CCUtils.verifyAndWaitForElement(genaralSettingIcon, driver);
		genaralSettingIcon.click();
		CCUtils.verifyAndWaitForElement(appointmentTypeTile, driver);
		appointmentTypeTile.click();
		log("AppointmentTypeTab Tile is Clickable");
		verifynAppointmentTypelabel();
	}
}
