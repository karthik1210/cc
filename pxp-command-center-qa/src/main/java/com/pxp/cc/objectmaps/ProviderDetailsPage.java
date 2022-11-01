package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class ProviderDetailsPage extends CCMainPage{

	@FindBy(id = "editPrsImage")
	private WebElement uploadProviderImage;

	@FindBy(xpath = "//div[@class='active ng-star-inserted']")
	private WebElement removeImage;
	
	@FindBy(xpath = "//div[@class='inactive ng-star-inserted']")
	private WebElement removeImageInactive;
	
	public ProviderDetailsPage(WebDriver driver) {
		super(driver);
	}
	
	public void verifyImageIsAlreadyUploaded() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Image is already uploaded");
	}
	
	public void uploadProviderImage() throws InterruptedException, AWTException {
		//CCUtils.verifyAndWaitForElement(removeImageInactive, driver);
		CCUtils.verifyAndWaitForElement(uploadProviderImage, driver);
		javascriptClick(uploadProviderImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadProviderImage.sendKeys(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		log("Verified that Image is uploaded");
	}
	
}
