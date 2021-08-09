// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class CCMainPage extends BasePageObject {

	public CCMainPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public CCMainPage(WebDriver driver, String url) {
		super(driver);
		if (url != null) {
			String sanitizedUrl = url.trim();
			driver.get(sanitizedUrl);
		}
		driver.manage().window().maximize();
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PageFactory.initElements(driver, this);
	}

	public void highlightElement(WebElement element) {
		jse.executeScript("arguments[0].setAttribute('style','border: solid 2px green');", element);
	}

	public String elementToString(WebElement element) {
		return "Element (id: " + element.getAttribute("id") + ", tag: " + element.getTagName() + ")";
	}

	public String fetchWebElementColor(WebElement element) {
		String value = element.getCssValue("color");
		return value;
	}

}
