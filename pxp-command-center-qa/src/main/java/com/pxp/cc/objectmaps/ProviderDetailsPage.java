// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import com.pxp.cc.util.CCUtils;

public class ProviderDetailsPage extends CCMainPage {

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadProviderImage;

	@FindBy(xpath = "//div//span[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImage;

	@FindBy(xpath = "//div[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImageInactive;

	@FindBy(xpath = "//p[contains(text(),'Invalid file type')]")
	private WebElement invalidFileType;

	@FindBy(xpath = "//span[text()='Provider Details']")
	private WebElement providerDetailsTitle;

	@FindBy(xpath = "//img[@alt='prs-image']")
	private WebElement blankImage;

	public ProviderDetailsPage(WebDriver driver) {
		super(driver);
	}

	public String invalidImagePath = "\\src\\test\\resources\\data-driven\\images\\223267.jpg";

	public void verifyImageIsAlreadyUploaded() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Image is already uploaded");
	}

	public void verifyProviderDetailsTitle() {
		CCUtils.verifyAndWaitForElement(providerDetailsTitle, driver);
		log("verified Provider details page title is present");
	}

	public void uploadProviderImage() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(blankImage, driver);
		String imageSrcValue = blankImage.getAttribute("src");
		log("print the value of src attribute of image tag : " + imageSrcValue);
		javascriptClick(uploadProviderImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadProviderImage.sendKeys(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
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

	public void removeProviderImage() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible if image is already uploaded");
		removeImage.click();
		log("clicked to remove image button");
		CCUtils.verifyAndWaitForElement(removeImageInactive, driver);
		log("verified that remove image button got disabled");
	}

	public void verifyInvalidFileTypeErrorMessage() {
		CCUtils.verifyAndWaitForElement(invalidFileType, driver);
		log("Verified invalid file type error message is dispalyed");
	}

	public void uploadProviderInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(uploadProviderImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadProviderImage.sendKeys(projectpath + invalidImagePath);
		log("Verifying provider image uploaded");
	}

}
