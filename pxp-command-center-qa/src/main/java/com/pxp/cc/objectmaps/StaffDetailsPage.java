//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class StaffDetailsPage extends CCMainPage {

	public CommonMethods commonMethods;
	public StaffDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='slider round']")
	private WebElement practice;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement entepriseArrow;

	@FindBy(xpath = "//span[@class='slider round']")
	private WebElement toggleButton;

	@FindBy(xpath = "//mat-icon[text()='arrow_back ']")
	private WebElement backIcon;

	@FindBy(xpath = "(//span[contains(text(),'Staff')])[2]")
	private WebElement labelStaff;

	@FindBy(xpath = "//div[contains(text(),'Staff Info')]")
	private WebElement labelStaffInfoSection;

	@FindBy(xpath = "//div[contains(text(),'Command Center ID')]")
	private WebElement labelCommandCenterId;

	@FindBy(xpath = "//div[contains(text(),'Staff ID ')]")
	private WebElement labelStaffId;

	@FindBy(xpath = "//div[contains(text(),'Task Groups')]")
	private WebElement taskGroups;

	@FindBy(xpath = "//div[contains(text(),' REMOVE IMAGE ')]")
	private WebElement labelRemoveImage;
	
	@FindBy(xpath="//button[contains(text(),'+ ADD')]")
	private WebElement labelAddButton;
	
	@FindBy(xpath="//div[contains(text(),'No data to display')]")
	private WebElement labelNoData;

	public void verifyStaffInfoSectionLabel() {
		CCUtils.verifyAndWaitForElement(labelStaffInfoSection, driver);
		log("Verifying Staff Info Label");
		CCUtils.assertEquals(labelStaffInfoSection.getText().trim(), "Staff Info");
	}

	public void verifyStaffCommandCenterIdLabel() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterId, driver);
		log("Verifying Command Center Id Label");
		CCUtils.assertEquals(labelCommandCenterId.getText().trim(), "Command Center ID");
	}

	public void verifyTaskGroups() {
		CCUtils.verifyAndWaitForElement(taskGroups, driver);
		log("Verifying Task Group Label");
		CCUtils.assertEquals(taskGroups.getText().trim(), "Task Groups");

	}

	public void verifyRemoveImageLabel() {
		CCUtils.verifyAndWaitForElement(labelRemoveImage, driver);
		log("Verifying Remove Label");
		System.out.println(labelRemoveImage.getText());
		CCUtils.assertEquals(labelRemoveImage.isDisplayed(), true);
	}

	public void verifyStaffIdLabel() {
		CCUtils.verifyAndWaitForElement(labelStaffId, driver);
		log("Verifying label Staff Id Label");
	    CCUtils.assertEquals(labelStaffId.isDisplayed(),true);
	 }
	
	public void verifyAddButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelAddButton, driver);
		log("Verifying label Add button is displayed");
		CCUtils.assertEquals(labelAddButton.isDisplayed(),true);
	}
	
	public void verifyNoDataDisplay() {
		CCUtils.verifyAndWaitForElement(labelNoData, driver);
		log("Verifying label No data is displayed");
		CCUtils.assertEquals(labelNoData.isDisplayed(),true);
	}

	public void verifyStaffInfoSection() throws InterruptedException, AWTException {
		log("Verifying Staff Info Section");
		verifyStaffInfoSectionLabel();
		verifyStaffCommandCenterIdLabel();
		verifyTaskGroups();
		verifyRemoveImageLabel();
		verifyStaffIdLabel();
		verifyAddButtonLabel();
		verifyNoDataDisplay();
	}

	public void clickToEntepriseArrow() {
		CCUtils.verifyAndWaitForElement(entepriseArrow, driver);
		entepriseArrow.click();
		log("clicked to enteprise Arrow");
	}

	public void clickOnToggleButton() {
		CCUtils.verifyAndWaitForElement(toggleButton, driver);
		toggleButton.click();
		log("clicked to toggle button");
	}

	public void clickToBackButton() throws AWTException {
		CCUtils.scrollPageToStart();
		CCUtils.verifyElementToBeClickable(backIcon, driver);
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked to back button in staff details page");
	}

}
