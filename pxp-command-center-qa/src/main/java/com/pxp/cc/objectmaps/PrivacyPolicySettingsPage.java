// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PrivacyPolicySettingsPage extends CCMainPage {

    public PrivacyPolicySettingsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='cardHeader pl-3' and contains(text(),'Privacy Policies')]")
    private WebElement privacyPoliciesLabel;

    @FindBy(xpath = "//div[@class='col-2 text-right']/button/following-sibling::span")
    private WebElement privacyPolicyExpandButton;

    @FindBy(xpath = "//div[@class='col-2 text-right']/button")
    private WebElement privacyPolicyAddButton;

    @FindBy(xpath = "//span[contains(text(),'No Privacy Policies.')]")
    private WebElement noPrivacyPoliciesText;




}
