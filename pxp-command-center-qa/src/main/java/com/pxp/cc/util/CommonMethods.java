// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.objectmaps.CCMainPage;

public class CommonMethods extends CCMainPage {

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(id = "snackCloseBtn")
	private WebElement buttonCloseToaster;

	@FindBy(xpath = "//div[@class='flex']/span//following::a")
	private WebElement linkClientName;

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public String getToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		return toasterMessage.getText().trim();

	}

	public void closeToasterMessage() {
		CCUtils.verifyAndWaitForElement(buttonCloseToaster, driver);
		log("Click on cross icon");
		buttonCloseToaster.click();
	}

	public void clickToasterlink() {
		CCUtils.verifyAndWaitForElement(linkClientName, driver);
		linkClientName.click();
	}
}
