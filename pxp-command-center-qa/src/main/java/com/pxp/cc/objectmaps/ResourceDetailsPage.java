// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;

public class ResourceDetailsPage extends CCMainPage {

	public ResourceDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "searchOrgInput")
	private WebElement searchButton;

	@FindBy(xpath = "//img[@alt='prs-image']")
	private WebElement blankImage;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadResourceImage;

	@FindBy(xpath = "//div/span[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImage;

	@FindBy(xpath = "//div[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImageInactive;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']//td[1]")
	private WebElement listOfResourceFirstName;

	@FindBy(xpath = "//span[text()='Resource Details']")
	private WebElement resourceDetailsTitle;

	public String invalidImagePath = "\\src\\test\\resources\\data-driven\\images\\223267.jpg";

	@FindBy(xpath = "//p[contains(text(),'Invalid file type')]")
	private WebElement invalidFileType;

	@FindBy(xpath = "//span[normalize-space(text())='Success! Successfully removed image']")
	private WebElement toasterMessage;

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.click();
		log("clicked to search button");
	}

	public void verifyResourceDetailsTitle() {
		CCUtils.verifyAndWaitForElement(resourceDetailsTitle, driver);
		log("verified Resource details page title is present");
	}

	public void verifySearchResourceFound(String listOfResource) {
		log("Entered in a search resource loop");
		CCUtils.scrollPageViaElement(driver, listOfResourceFirstName);
		log("scroll page");
		log("get the text of list : " + listOfResourceFirstName.getText().toString());
		log("verified presence of list resource");
		CCUtils.verifyAndWaitForElement(listOfResourceFirstName, driver);
		if ((listOfResourceFirstName.getText().toString().trim()).equalsIgnoreCase(listOfResource.trim())) {
			listOfResourceFirstName.click();
		}
	}

	public void uploadResourceImage() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElementPresence(blankImage, driver);
		String imageSrcValue = blankImage.getAttribute("src");
		log("print the value of src attribute of image tag : " + imageSrcValue);
		javascriptClick(uploadResourceImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadResourceImage.sendKeys(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		Assert.assertFalse(
				blankImage.getAttribute("src")
						.equals("https://qa-command-center.dev.pxp-mf-cc.nextgenaws.net/assets/img/Default_pfp.jpg"),
				"verifying src image value");
		log("verifying image src attribute value is changed");
		log("print the value of src attribute value : " + blankImage.getAttribute("src"));
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible after image is uploaded");
		log("Verified that Image is uploaded");
	}

	public void removeResourceImage() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible if image is already uploaded");
		removeImage.click();
		log("clicked to remove image button");
		CCUtils.verifyAndWaitForElement(removeImageInactive, driver);
		log("verified that remove image button got disabled");
	}

	public void uploadResourceInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(uploadResourceImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadResourceImage.sendKeys(projectpath + invalidImagePath);
		log("Verifying Resource image uploaded");
	}

	public void verifyInvalidFileTypeErrorMessage() {
		CCUtils.verifyAndWaitForElement(invalidFileType, driver);
		log("Verified invalid file type error message is displayed");
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}
}
