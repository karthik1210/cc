// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;

public class ExternalURLsPage extends CCMainPage {

    public ExternalURLsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'Practice Links')]")
    private WebElement practiceLinksLabel;

    @FindBy(xpath = "//b[contains(text(),'EXTERNAL URLS')]")
    private WebElement externalUrlLabel;

    @FindBy(xpath = "//button[@class = 'mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary ng-star-inserted']/span[contains(text(),'EDIT')]")
    private WebElement externalUrlEdit;

    @FindBy(xpath = "//button[@class = 'mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary']/span[contains(text(),'ADD')]")
    private WebElement externalUrlAdd;

    @FindBy(xpath = "//label[contains(text(),'No external URLs configured. Click EDIT to add.')]")
    private WebElement noExternalUrlConfigureLabel;

    public void verifyPracticeLinksLabelIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceLinksLabel);
        CCUtils.verifyAndWaitForElement(practiceLinksLabel, driver);
        log("verifying Practice Links label availability");
        CCUtils.assertEquals(practiceLinksLabel.isDisplayed(), true);
        CCUtils.assertEquals(practiceLinksLabel.getText().trim(), "Practice Links");
    }

    public void verifyExternalUrlIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlLabel);
        CCUtils.verifyAndWaitForElement(externalUrlLabel, driver);
        log("Verifying EXTERNAL URLS text");
        CCUtils.assertEquals(externalUrlLabel.isDisplayed(), true);
        CCUtils.assertEquals(externalUrlLabel.getText().trim(), "EXTERNAL URLS");
    }

    public void verifyExternalUrlEditIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlEdit);
        CCUtils.verifyAndWaitForElement(externalUrlEdit, driver);
        log("Verifying EXTERNAL URLS EDIT button");
        CCUtils.assertEquals(externalUrlEdit.isDisplayed(), true);
    }

    public void verifyNoExternalUrlConfigureLabelIsDisplayed() {
        CCUtils.scrollPageViaElement(driver, noExternalUrlConfigureLabel);
        CCUtils.verifyAndWaitForElement(noExternalUrlConfigureLabel, driver);
        log("Verifying No EXTERNAL URLS CONFIGURE LABEL DISPLAYED");
        CCUtils.assertEquals(noExternalUrlConfigureLabel.isDisplayed(), true);
    }

    public void verifyExternalUrlAddIsDisplayed() throws  InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlEdit);
        CCUtils.verifyAndWaitForElement(externalUrlEdit, driver);
        log("Verifying EXTERNAL URLS EDIT button");
        CCUtils.assertEquals(externalUrlEdit.isDisplayed(), true);
        externalUrlEdit.click();
        CCUtils.verifyAndWaitForElement(externalUrlAdd, driver);
        log("Verifying EXTERNAL URLS Add button");
        CCUtils.assertEquals(externalUrlAdd.isDisplayed(), true);
    }
}

