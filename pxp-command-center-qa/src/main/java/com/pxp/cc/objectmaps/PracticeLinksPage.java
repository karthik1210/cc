// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.google.sitebricks.client.Web;
import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PracticeLinksPage extends CCMainPage {

    public PracticeLinksPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[normalize-space(text())='Practice Links']")
    private WebElement practiceLinksLabel;

    @FindBy(xpath = "//b[normalize-space(text())='PRACTICE WEBSITE']")
    private WebElement practiceWebsiteLabel;

    @FindBy(xpath = "//div[normalize-space(text())='Practice Links']/../following-sibling::div/../following-sibling::div/app-practice-website/div/div/div/mat-icon[text()='link']")
    private WebElement practiceWebsiteLinkIcon;

    @FindBy(xpath = "//b[normalize-space(text())='PRACTICE WEBSITE']/../following-sibling::div/following-sibling::div/span/button/span[normalize-space(text())='EDIT']")
    private WebElement practiceWebsiteEdit;

    @FindBy(xpath = "//b[normalize-space(text())='EXTERNAL URLS']")
    private WebElement externalUrlLabel;

    @FindBy(xpath = "//b[normalize-space(text())='EXTERNAL URLS']/../following-sibling::div/div/button/span[text()=' EDIT ']")
    private WebElement externalUrlEdit;

    @FindBy(xpath = "//b[normalize-space(text())='EXTERNAL URLS']/../following-sibling::div/following::div/div/following::span[text()=' ADD ']")
    private WebElement externalUrlAdd;

    @FindBy(xpath = "//label[normalize-space(text())='No external URLs configured. Click EDIT to add.']")
    private WebElement noExternalUrlConfigureLabel;

    @FindBy(xpath = "//input[@aria-label='Practice Website']")
    private WebElement practiceWebsiteTextBox;

    @FindBy(xpath = "//span[normalize-space(text())='CANCEL']")
    private WebElement cancelButtonForPracticeWebsite;

    @FindBy(xpath = "//b[normalize-space(text())='PRACTICE WEBSITE']/../following-sibling::div/span[@class='content-header ng-star-inserted']")
    private WebElement practiceWebsiteDisplayArea;

    @FindBy(xpath = "//span[normalize-space(text())='SAVE']")
    private WebElement saveButtonForPracticeWebsite;

    @FindBy(xpath = "//div[normalize-space(text())='Practice Links']/../following-sibling::div/span/button/mat-icon[text()=' keyboard_arrow_down '] | //div[normalize-space(text())='Practice Links']/../following-sibling::div/button/mat-icon[text()=' keyboard_arrow_down ']")
    private WebElement practiceWebsiteExpandArrow;

    @FindBy(xpath = "//div[normalize-space(text())='Practice Links']/../following-sibling::div/span/button/mat-icon[text()=' keyboard_arrow_up '] | //div[normalize-space(text())='Practice Links']/../following-sibling::div/button/mat-icon[text()=' keyboard_arrow_up ']")
    private WebElement practiceWebsiteCollapseArrow;

    @FindBy(xpath = "//span/input")
    private List<WebElement> listOfexternalUrlHyperLinkTexts;

    @FindBy(xpath = "//input[@aria-label='en']")
    private List<WebElement> englishHyperLinkTextBox;

    @FindBy(xpath = "//input[@aria-label='es']")
    private List<WebElement> spanishHyperLinkTextBox;

    @FindBy(xpath = "//input[@aria-label='Website Url']")
    private WebElement websiteExternalUrl;

    @FindBy(xpath = "//div[@class='leftColumn col-lg-11 col-md-11 ml-3']/input")
    private List<WebElement> listOfExternalUrls;

    @FindBy(xpath = "//span[normalize-space(text())='SAVE']")
    private WebElement externalUrlSaveButton;

    @FindBy(xpath = "//span[normalize-space(text())='CANCEL']")
    private WebElement externalUrlCancelButton;

    @FindBy(xpath = "//a[@class='link']")
    private List<WebElement> listOfExternalUrlsDisplayArea;

    @FindBy(xpath = "//span[normalize-space(text())='drag_indicator']")
    private List<WebElement> listOfDragElements;

    @FindBy(xpath = "//span[normalize-space(text())='REARRANGE']")
    private WebElement externalUrlRearrangeButton;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='remove_circle']")
    private List<WebElement> listOfDeleteExternalUrlIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='remove_circle']")
    private WebElement deleteExternalUrlIcon;

    @FindBy(xpath = "//div[normalize-space(text())='Maximum characters reached']")
    private WebElement maximumCharacterError;

    @FindBy(xpath = "//span[@class='content']/label")
    private List<WebElement> listOfDisplayText;

    @FindBy(xpath = "//div[normalize-space(text())='SOCIAL MEDIA']")
    private WebElement socialMediaLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Twitter']")
    private WebElement twitterLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Facebook']")
    private WebElement facebookLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Linkedin']")
    private WebElement linkedinLabel;

    @FindBy(xpath = "//span[normalize-space(text())='YouTube']")
    private WebElement youtubeLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Twitter']/../following-sibling::div")
    private WebElement twitterUrl;

    @FindBy(xpath = "//span[normalize-space(text())='Facebook']/../following-sibling::div")
    private WebElement facebookUrl;

    @FindBy(xpath = "//span[normalize-space(text())='Linkedin']/../following-sibling::div")
    private WebElement linkedinUrl;

    @FindBy(xpath = "//span[normalize-space(text())='YouTube']/../following-sibling::div")
    private WebElement youtubeUrl;

    @FindBy(xpath = "//div[normalize-space(text())='SOCIAL MEDIA']/../following-sibling::div/button/span[text()=' Edit ']")
    private WebElement socialMediaEdit;

    @FindBy(xpath = "//input[@aria-label='Twitter']")
    private WebElement twitterUrlEditTextBox;

    @FindBy(xpath = "//input[@aria-label='Twitter']/../following-sibling::div/mat-slide-toggle/label/div/input/following-sibling::div")
    private WebElement twitterToggleButton;

    @FindBy(xpath = "//input[@aria-label='Facebook']")
    private WebElement facebookUrlEditTextBox;

    @FindBy(xpath = "//input[@aria-label='Facebook']/../following-sibling::div/mat-slide-toggle/label/div/input/following-sibling::div")
    private WebElement facebookToggleButton;

    @FindBy(xpath = "//input[@aria-label='Linkedin']")
    private WebElement linkedinUrlEditTextBox;

    @FindBy(xpath = "//input[@aria-label='Linkedin']/../following-sibling::div/mat-slide-toggle/label/div/input/following-sibling::div")
    private WebElement linkedinToggleButton;

    @FindBy(xpath = "//input[@aria-label='YouTube']")
    private WebElement youtubeUrlEditTextBox;

    @FindBy(xpath = "//input[@aria-label='YouTube']/../following-sibling::div/mat-slide-toggle/label/div/input/following-sibling::div")
    private WebElement youtubeToggleButton;

    @FindBy(xpath = "//span[normalize-space(text())='CANCEL']")
    private WebElement socialMediaCancelButton;

    @FindBy(xpath = "//button[normalize-space(text())='SAVE']")
    private WebElement socialMediaSaveButton;

    @FindBy(xpath = "//div[normalize-space(text())='URL cannot be blank if enabled.']")
    private WebElement socialMediaError;

    @FindBy(xpath = "//input[@aria-label='Twitter']")
    private WebElement twitterEditBoxWhenToggleOn;

    @FindBy(xpath = "//input[@aria-label='Facebook']")
    private WebElement facebookEditBoxWhenToggleOn;

    @FindBy(xpath = "//input[@aria-label='Linkedin']")
    private WebElement linkedinEditBoxWhenToggleOn;

    @FindBy(xpath = "//input[@aria-label='YouTube']")
    private WebElement youtubeEditBoxWhenToggleOn;

    @FindBy(xpath = "//input[@aria-label='Twitter']")
    private WebElement twitterTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//input[@aria-label='Facebook']")
    private WebElement facebookTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//input[@aria-label='Linkedin']")
    private WebElement linkedinTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//input[@aria-label='YouTube']")
    private WebElement youtubeTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//input[@aria-label='YouTube']/../following-sibling::div/mat-slide-toggle/label/div/input[@type='checkbox']")
    private WebElement youtubeToggleButtonWhenOn;

    @FindBy(xpath = "//input[@aria-label='Linkedin']/../following-sibling::div/mat-slide-toggle/label/div/input[@type='checkbox']")
    private WebElement linkedinToggleButtonWhenOn;

    @FindBy(xpath = "//input[@aria-label='Facebook']/../following-sibling::div/mat-slide-toggle/label/div/input[@type='checkbox']")
    private WebElement facebookToggleButtonWhenOn;

    @FindBy(xpath = "//input[@aria-label='Twitter']/../following-sibling::div/mat-slide-toggle/label/div/input[@type='checkbox']")
    private WebElement twitterToggleButtonWhenOn;

    @FindBy(xpath = "//div[@class='col-8']/a")
    private List<WebElement> listOfSocialMediaUrlsDisplay;

    @FindBy(xpath = "//span[normalize-space(text())='Twitter']/../following-sibling::div/a[@class='link ng-star-inserted']")
    private WebElement twitterUrlDisplay;

    @FindBy(xpath = "//span[normalize-space(text())='Facebook']/../following-sibling::div/a[@class='link ng-star-inserted']")
    private WebElement facebookUrlDisplay;

    @FindBy(xpath = "//span[normalize-space(text())='Linkedin']/../following-sibling::div/a[@class='link ng-star-inserted']")
    private WebElement linkedinUrlDisplay;

    @FindBy(xpath = "//span[normalize-space(text())='YouTube']/../following-sibling::div/a[@class='link ng-star-inserted']")
    private WebElement youTubeUrlDisplay;

    @FindBy(xpath = "//label[normalize-space(text())='Hyperlink Text']")
    private WebElement hyperlinkText;

    @FindBy(xpath = "//label[normalize-space(text())='Website URL']")
    private WebElement websiteUrl;

    public HashMap<String, Boolean> verifyPracticeLinksElements() throws Exception {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        logStep("PXPP-20133 TC001 Verify that user can navigate to Practice Links section in Command Center Application");
        result.put("verifyPracticeLinksLabel", verifyPracticeLinksLabelIsDisplayed());
        logStep("PXPP-20286 TC002 Verify that user can collapse and expand Practice Links section in Command Center Application");
        result.put("verifyingPracticeLinkExpandArrow",verifyPracticeWebsiteExpandArrowIsDisplayed());
        clickOnPracticeWebsiteExpandButton();
        result.put("verifyingPracticeLinkCollapseArrow",verifyPracticeWebsiteCollapseArrowIsDisplayed());
        clickOnPracticeWebsiteCollapseButton();
        result.put("verifyingPracticeLinkExpandArrow",verifyPracticeWebsiteExpandArrowIsDisplayed());
        logStep("PXPP-20331 TC007 Verify that whether user is able to see the EXTERNAL URLS in Practice Links section at practice level");
        clickOnPracticeWebsiteExpandButton();
        result.put("verifyExternalUrl",verifyExternalUrlIsDisplayed());
        result.put("verifyEditOnExternalUrl",verifyExternalUrlEditIsDisplayed());
        logStep("PXPP-20344 TC008 Verify the External URLS by default 1st time in Practice Links section");
        result.put("verifyNoExternalUrl",verifyNoExternalUrlConfigureLabelIsDisplayed());
        clickOnExternalUrlEditButton();
        result.put("verifyAddOnExternalUrl",verifyExternalUrlAddIsDisplayed());
        clickOnExternalUrlAddButton();
        result.put("verifyHyperLinkText",verifyHyperLinkText());
        result.put("verifyWebsiteUrl",verifyWebsiteUrl());
        clickOnExternalUrlCancelButton();
        logStep("PXPP-20482 TC021 Verify the Social Media by default 1st time in Practice Links section");
        result.put("verifySocialMediaLabel",verifySocialMediaLabel());
        result.put("verifyEditOnSocialMedia",verifySocialMediaEditButton());
        result.put("verifyAllSocialMediaTitle",verifyAllSocialMediaTitle());
        clickOnSocialMediaEdit();
        result.put("verifyToggleOff",verifyToggleOff());
        result.put("verifyEditTextBoxUrlEmpty",verifyEditTextBoxUrlEmpty());
        logStep("PXPP-20485 TC024 Verify the Toggle buttons for social media in Practice Links section");
        result.put("verifyToggleOff",verifyToggleOff());
        result.put("verifyEditTextBoxUrlEmpty",verifyEditTextBoxUrlEmpty());
        clickOnSocialMediaCancel();
        result.put("verifySocialMediaUrlEmpty",verifySocialMediaUrlEmpty());
        logStep("PXPP-20489 TC028 Verify the Edit and Save functionality for updating existing social media with Toggle on/off in Practice Links section");
        result.put("verifyEditOnSocialMedia",verifySocialMediaEditButton());
        clickOnSocialMediaEdit();
        toggleSwitchClick("Facebook");
        result.put("verifySocialMediaError",verifySocialMediaError());
        clickOnSocialMediaCancel();
        return result;
    }



    public boolean verifyHyperLinkText() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, hyperlinkText);
            CCUtils.verifyAndWaitForElement(hyperlinkText, driver);
            log("Verifying Hyperlink text title is available or not");
            CCUtils.assertEquals(hyperlinkText.getText().equalsIgnoreCase("Hyperlink Text"), true);
            log("Successfully hyperlink text title displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyWebsiteUrl() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, websiteUrl);
            CCUtils.verifyAndWaitForElement(websiteUrl, driver);
            log("Verifying Website Url title is available or not");
            CCUtils.assertEquals(websiteUrl.getText().equalsIgnoreCase("Website URL"), true);
            log("Successfully website url title displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnExternalUrlRearrangeButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlRearrangeButton);
        CCUtils.verifyAndWaitForElement(externalUrlRearrangeButton, driver);
        log("clicking on external url Rearrange button");
        javascriptClick(externalUrlRearrangeButton);
        log("Clicked on external url Rearrange button");
    }

    public void verifyMaximumCharacterError() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, maximumCharacterError);
        CCUtils.verifyAndWaitForElement(maximumCharacterError, driver);
        log("Verifying error message once user enters more than 100 characters on text or url");
        CCUtils.assertEquals(maximumCharacterError.getText().equalsIgnoreCase("Maximum characters reached"), true);
        log("Character length should not exceed 100, Error!!!");
    }

    public void clickOnPracticeWebsiteExpandButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteExpandArrow);
        CCUtils.verifyAndWaitForElement(practiceWebsiteExpandArrow, driver);
        log("clicking on practice website Expand arrow");
        javascriptClick(practiceWebsiteExpandArrow);
        log("Clicked on Practice Website Expand arrow");
    }

    public void clickOnPracticeWebsiteCollapseButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteCollapseArrow);
        CCUtils.verifyAndWaitForElement(practiceWebsiteCollapseArrow, driver);
        log("clicking on practice website collapse arrow");
        javascriptClick(practiceWebsiteCollapseArrow);
        log("Clicked on Practice Website collapse arrow");
    }

    public void clickOnPracticeWebsiteSaveButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, saveButtonForPracticeWebsite);
        CCUtils.verifyAndWaitForElement(saveButtonForPracticeWebsite, driver);
        log("clicking on practice website save button");
        javascriptClick(saveButtonForPracticeWebsite);
        log("Clicked on Practice Website save button");
    }

    public void verifyPracticeWebsiteDisplayArea(String practiceWebsite) throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteDisplayArea);
        CCUtils.verifyAndWaitForElement(practiceWebsiteDisplayArea, driver);
        log("verifying Practice Website is displaying on not");
        CCUtils.assertEquals(practiceWebsiteDisplayArea.isDisplayed(), true);
        if (!practiceWebsiteDisplayArea.getText().equalsIgnoreCase(practiceWebsite))
            log("practice website not added due to" + practiceWebsite + " and " + practiceWebsiteDisplayArea.getText() + " are not same");
        else
            log("practice website " + practiceWebsite + " added successfully");
    }

    public void clickOnPracticeWebsiteCancelButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, cancelButtonForPracticeWebsite);
        CCUtils.verifyAndWaitForElement(cancelButtonForPracticeWebsite, driver);
        log("clicking on practice website cancel button");
        javascriptClick(cancelButtonForPracticeWebsite);
        log("Clicked on Practice Website cancel button");
    }

    public void enterPracticeWebsite(String practiceWebsite) throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteTextBox);
        CCUtils.verifyAndWaitForElement(practiceWebsiteTextBox, driver);
        if (practiceWebsiteTextBox.getText().isEmpty()) {
            log("Entering value for practice website");
            practiceWebsiteTextBox.clear();
            practiceWebsiteTextBox.sendKeys(practiceWebsite);
            log("Entered practice website as" + practiceWebsite);
        } else
            log("Already practice website added, cannot add more than one");
    }

    public void updateWithActionForPracticeWebsite(String practiceWebsite, String action) throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteDisplayArea);
        CCUtils.verifyAndWaitForElement(practiceWebsiteDisplayArea, driver);
        if (action.equalsIgnoreCase("cancel")) {
            if (!practiceWebsiteDisplayArea.getText().isEmpty()) {
                javascriptClick(practiceWebsiteEdit);
                CCUtils.verifyAndWaitForElement(cancelButtonForPracticeWebsite, driver);
                javascriptClick(cancelButtonForPracticeWebsite);
            }
            else
                log("Practice website is not available to update");
        } else if (action.equalsIgnoreCase("Save")) {
            if (!practiceWebsiteDisplayArea.getText().isEmpty()) {
                javascriptClick(practiceWebsiteEdit);
                log("Entering value for practice website");
                practiceWebsiteTextBox.clear();
                CCUtils.verifyAndWaitForElement(practiceWebsiteTextBox, driver);
                practiceWebsiteTextBox.sendKeys(practiceWebsite);
                log("Entered practice website as" + practiceWebsite);
                javascriptClick(saveButtonForPracticeWebsite);
            } else
                log("Practice website is not available to update");
        }
    }

    public void checkPracticeWebsiteIsEmptyOrNot() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteDisplayArea);
        CCUtils.verifyAndWaitForElement(practiceWebsiteDisplayArea, driver);
        log("check whether practice website is empty or already added");
        if (practiceWebsiteDisplayArea.getText().isEmpty())
            log("Practice website is empty and can be proceed to add practice website");
        else {
            CCUtils.assertTrue(!practiceWebsiteDisplayArea.getText().isEmpty(), "Already practice website added, cannot add more than one");
        }
    }

    public void clickOnPracticeWebsiteEditButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteEdit);
        CCUtils.verifyAndWaitForElement(practiceWebsiteEdit, driver);
        log("clicking on practice website Edit button");
        javascriptClick(practiceWebsiteEdit);
        log("Clicked on Practice Website Edit button");
    }

    public boolean verifyPracticeWebsiteExpandArrowIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, practiceWebsiteExpandArrow);
            CCUtils.verifyAndWaitForElement(practiceWebsiteExpandArrow, driver);
            log("verifying Practice website expand arrow availability");
            CCUtils.assertEquals(practiceWebsiteExpandArrow.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyPracticeWebsiteCollapseArrowIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, practiceWebsiteCollapseArrow);
            CCUtils.verifyAndWaitForElement(practiceWebsiteCollapseArrow, driver);
            log("verifying Practice website collapse arrow availability");
            CCUtils.assertEquals(practiceWebsiteCollapseArrow.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void verifyPracticeWebsiteLabelIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteLabel);
        CCUtils.verifyAndWaitForElement(practiceWebsiteLabel, driver);
        log("verifying Practice Website label availability");
        CCUtils.assertEquals(practiceWebsiteLabel.isDisplayed(), true);
        CCUtils.assertEquals(practiceWebsiteLabel.getText().trim(), "PRACTICE WEBSITE");
    }

    public void verifyPracticeWebsiteLinkIconIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteLinkIcon);
        CCUtils.verifyAndWaitForElement(practiceWebsiteLinkIcon, driver);
        log("verifying Practice website link icon availability");
        CCUtils.assertEquals(practiceWebsiteLinkIcon.isDisplayed(), true);
    }

    public void verifyPracticeWebsiteEditIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteEdit);
        CCUtils.verifyAndWaitForElement(practiceWebsiteEdit, driver);
        log("verifying Practice website Edit button availability");
        CCUtils.assertEquals(practiceWebsiteEdit.isDisplayed(), true);
    }

    public boolean verifyPracticeLinksLabelIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, practiceLinksLabel);
            CCUtils.verifyAndWaitForElement(practiceLinksLabel, driver);
            log("verifying Practice Links label availability");
            CCUtils.assertEquals(practiceLinksLabel.isDisplayed(), true);
            CCUtils.assertEquals(practiceLinksLabel.getText().trim(), "Practice Links");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyExternalUrlIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, externalUrlLabel);
            CCUtils.verifyAndWaitForElement(externalUrlLabel, driver);
            log("Verifying EXTERNAL URLS text");
            CCUtils.assertEquals(externalUrlLabel.isDisplayed(), true);
            CCUtils.assertEquals(externalUrlLabel.getText().trim(), "EXTERNAL URLS");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyExternalUrlEditIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, externalUrlEdit);
            CCUtils.verifyAndWaitForElement(externalUrlEdit, driver);
            log("Verifying EXTERNAL URLS EDIT button");
            CCUtils.assertEquals(externalUrlEdit.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyNoExternalUrlConfigureLabelIsDisplayed() {
        try {
            CCUtils.scrollPageViaElement(driver, noExternalUrlConfigureLabel);
            CCUtils.verifyAndWaitForElement(noExternalUrlConfigureLabel, driver);
            log("Verifying No EXTERNAL URLS CONFIGURE LABEL DISPLAYED");
            CCUtils.assertEquals(noExternalUrlConfigureLabel.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyExternalUrlAddIsDisplayed() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, externalUrlAdd);
            CCUtils.verifyAndWaitForElement(externalUrlAdd, driver);
            log("Verifying EXTERNAL URLS Add button");
            CCUtils.assertEquals(externalUrlAdd.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnExternalUrlAddButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlAdd);
        CCUtils.verifyAndWaitForElement(externalUrlAdd, driver);
        log("Clicking on External Url Add button with + symbol");
        new Actions(driver).moveToElement(externalUrlAdd).click().build().perform();
        log("Clicked successfully on external url Add button");
    }

    public void enterExternalUrlHyperLinkText(String hyperLinkNameEN, String hyperLinkNameES) throws InterruptedException, AWTException {
        englishHyperLinkTextBox.get(englishHyperLinkTextBox.size() -1).sendKeys(hyperLinkNameEN);
        spanishHyperLinkTextBox.get(spanishHyperLinkTextBox.size() - 1).sendKeys(hyperLinkNameES);
        log("HyperLink Text added successfully");
    }

    public void enterExternalUrl(String externalWebsite) throws InterruptedException, AWTException {
        listOfExternalUrls.get(listOfExternalUrls.size() - 1).clear();
        listOfExternalUrls.get(listOfExternalUrls.size() - 1).sendKeys(externalWebsite);
        log("External url added successfully");
    }

    public void clickOnExternalUrlEditButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlEdit);
        CCUtils.verifyAndWaitForElement(externalUrlEdit, driver);
        log("Clicking on Edit button in External Url");
        new Actions(driver).moveToElement(externalUrlEdit).click().build().perform();
        log("Successfully clicked on External Url Edit button");
    }

    public void clickOnExternalUrlSaveButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlSaveButton);
        CCUtils.verifyAndWaitForElement(externalUrlSaveButton, driver);
        log("Clicking on save button in External Url");
        javascriptClick(externalUrlSaveButton);
        log("Successfully clicked on External Url save button");
    }

    public void clickOnExternalUrlCancelButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlCancelButton);
        CCUtils.verifyAndWaitForElement(externalUrlCancelButton, driver);
        log("Clicking on Cancel button in External Url");
        javascriptClick(externalUrlCancelButton);
        log("Successfully clicked on External Url Cancel button");
    }

    public void verifyExternalUrlDisplay(List<String> externalWebsites) throws InterruptedException, AWTException {
        int flag = 1;
        for (int j = 0; j < externalWebsites.size(); j++) {
            for (int i = 0; i < listOfExternalUrlsDisplayArea.size(); i++) {
                if (listOfExternalUrlsDisplayArea.get(i).getText().equalsIgnoreCase(externalWebsites.get(j))) {
                    flag = 0;
                    break;
                }
                if (!listOfExternalUrlsDisplayArea.get(i).getText().equalsIgnoreCase(externalWebsites.get(j))) {
                    flag = 1;
                }
            }
            if (flag == 0)
                log("Given External Website " + externalWebsites.get(j) + " is available in External Url Section");
            if (flag == 1)
                log("Given External Website " + externalWebsites.get(j) + " is not added in External url section");
        }
        log("Total external url present is " + listOfExternalUrlsDisplayArea.size());
    }

    public void printListOfExternalUrls() throws InterruptedException, AWTException {
        for (int i = 0; i < listOfExternalUrlsDisplayArea.size(); i++)
            System.out.println(listOfExternalUrlsDisplayArea.get(i).getText());
    }

    public void deleteExternalUrl(String externalWebsite) throws InterruptedException, AWTException {
        for (int i = 0; i < listOfExternalUrlsDisplayArea.size(); i++) {
            if (listOfExternalUrlsDisplayArea.get(i).getText().equalsIgnoreCase(externalWebsite)) {
                int j = i + 1;
                javascriptClick(externalUrlEdit);
                Thread.sleep(3000);
                WebElement deleteIcon = driver.findElement(By.xpath("(//mat-icon[text()='remove_circle'])[" + j + "]"));
                Actions actions = new Actions(driver);
                actions.moveToElement(deleteIcon).pause(3000).build().perform();
                actions.click(deleteIcon).build().perform();
                Thread.sleep(5000);
                log("soft deleted on mentioned External url " + externalWebsite + " successfully!!");
                break;
            }
        }
    }

    public void dragAndDropExternalUrl(int currentPosition, int targetPosition) throws InterruptedException, AWTException {
        Actions actions = new Actions(driver);
        WebElement from = driver.findElement(By.xpath("(//span[text()='drag_indicator'])[" + currentPosition + "]"));
        WebElement to = driver.findElement(By.xpath("(//span[text()='drag_indicator'])[" + targetPosition + "]"));
        CCUtils.verifyAndWaitForElement(from, driver);
        CCUtils.verifyAndWaitForElement(to, driver);
        actions.clickAndHold(from).build().perform();
        actions.moveToElement(to).pause(3000).build().perform();
        actions.release(to).build().perform();
        Thread.sleep(5000);
    }

    public void verifyExternalUrlDeleteIconIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, deleteExternalUrlIcon);
        CCUtils.verifyAndWaitForElement(deleteExternalUrlIcon, driver);
        log("Verifying EXTERNAL URLS Delete Icon button");
        CCUtils.assertEquals(deleteExternalUrlIcon.isDisplayed(), true);
    }

    public void enterValueOnExistingValueOnDisplayText(String value) throws InterruptedException, AWTException {
        for (int i = 0; i < listOfExternalUrlsDisplayArea.size(); i++)
            if (listOfExternalUrlsDisplayArea.get(i).getText().equalsIgnoreCase(value)) {
                int k = i + 1;
                javascriptClick(externalUrlEdit);
                String displayTextEn = englishHyperLinkTextBox.get(i).getAttribute("value") + CCUtils.generateAlphaNumericString(3);
                String displayTextEs = spanishHyperLinkTextBox.get(i).getAttribute("value") + CCUtils.generateAlphaNumericString(3);
                WebElement elementEN = driver.findElement(By.xpath("(//input[@aria-label='en'])[" + k + "]"));
                WebElement elementES = driver.findElement(By.xpath("(//input[@aria-label='es'])[" + k + "]"));
                elementEN.clear();
                new Actions(driver).moveToElement(elementEN).pause(3000).click().build().perform();
                elementEN.sendKeys(displayTextEn);
                elementES.clear();
                new Actions(driver).moveToElement(elementES).pause(3000).click().build().perform();
                elementES.sendKeys(displayTextEs);
                break;
            }
    }

    public void enterValueOnExistingValueOnExternalUrl(String value) throws InterruptedException, AWTException {
        for (int i = 0; i < listOfDisplayText.size(); i++)
            if (listOfDisplayText.get(i).getText().equalsIgnoreCase(value)) {
                int j = i / 2;
                String externalUrl = listOfExternalUrlsDisplayArea.get(j).getText() + "/updated";
                javascriptClick(externalUrlEdit);
                listOfExternalUrls.get(j).clear();
                listOfExternalUrls.get(j).sendKeys(externalUrl);
                break;
            }
    }

    public void printListOfDisplayText() throws InterruptedException, AWTException {
        for (int i = 0; i < listOfDisplayText.size(); i++)
            System.out.println(listOfDisplayText.get(i).getText());
    }

    public boolean verifySocialMediaLabel() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, socialMediaLabel);
            CCUtils.verifyAndWaitForElement(socialMediaLabel, driver);
            log("Verifying social media label availability");
            CCUtils.assertTrue(socialMediaLabel.isDisplayed(), "Social media label is displayed successfully");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyAllSocialMediaTitle() throws Exception {
        try {
            CCUtils.scrollPageViaElement(driver, twitterLabel);
            CCUtils.verifyAndWaitForElement(twitterLabel, driver);
            log("Verifying twitter label availability");
            CCUtils.assertTrue(twitterLabel.isDisplayed(), "Twitter label is displayed successfully");
            CCUtils.scrollPageViaElement(driver, facebookLabel);
            CCUtils.verifyAndWaitForElement(facebookLabel, driver);
            log("Verifying facebook label availability");
            CCUtils.assertTrue(facebookLabel.isDisplayed(), "Facebook label is displayed successfully");
            CCUtils.scrollPageViaElement(driver, linkedinLabel);
            CCUtils.verifyAndWaitForElement(linkedinLabel, driver);
            log("Verifying Linkedin label availability");
            CCUtils.assertTrue(linkedinLabel.isDisplayed(), "Linkedin label is displayed successfully");
            CCUtils.scrollPageViaElement(driver, youtubeLabel);
            CCUtils.verifyAndWaitForElement(youtubeLabel, driver);
            log("Verifying youtube label availability");
            CCUtils.assertTrue(youtubeLabel.isDisplayed(), "Youtube label is displayed successfully");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void verifyTwitterLabel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, twitterLabel);
        CCUtils.verifyAndWaitForElement(twitterLabel, driver);
        log("Verifying twitter label availability");
        CCUtils.assertTrue(twitterLabel.isDisplayed(),"Twitter label is displayed successfully");
    }

    public void verifyFacebookLabel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, facebookLabel);
        CCUtils.verifyAndWaitForElement(facebookLabel, driver);
        log("Verifying facebook label availability");
        CCUtils.assertTrue(facebookLabel.isDisplayed(),"Facebook label is displayed successfully");
    }

    public void verifyLinkedinLabel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, linkedinLabel);
        CCUtils.verifyAndWaitForElement(linkedinLabel, driver);
        log("Verifying Linkedin label availability");
        CCUtils.assertTrue(linkedinLabel.isDisplayed(),"Linkedin label is displayed successfully");
    }

    public void verifyYoutubeLabel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, youtubeLabel);
        CCUtils.verifyAndWaitForElement(youtubeLabel, driver);
        log("Verifying youtube label availability");
        CCUtils.assertTrue(youtubeLabel.isDisplayed(),"Youtube label is displayed successfully");
    }

    public boolean verifySocialMediaEditButton() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, socialMediaEdit);
            CCUtils.verifyAndWaitForElement(socialMediaEdit, driver);
            log("Verifying Social media edit button availability");
            CCUtils.assertTrue(socialMediaEdit.isDisplayed(), "Socail Media Edit button is displayed successfully");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnSocialMediaEdit() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaEdit);
        CCUtils.verifyAndWaitForElement(socialMediaEdit, driver);
        log("clicking on edit button on social media section");
        javascriptClick(socialMediaEdit);
        log("successfully clicked on edit in social media section");
    }

    public boolean verifyToggleOff() throws InterruptedException, AWTException {
        try {
            log("Verifying Toggle button off");
            CCUtils.assertEquals(twitterToggleButton.isSelected(), false);
            log("Twitter toggle off");
            CCUtils.assertEquals(facebookToggleButton.isSelected(), false);
            log("Facebook toggle off");
            CCUtils.assertEquals(linkedinToggleButton.isSelected(), false);
            log("Linkedin toggle off");
            CCUtils.assertEquals(youtubeToggleButton.isSelected(), false);
            log("Youtube toggle off");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyEditTextBoxUrlEmpty() throws InterruptedException, AWTException {
        try {
            CCUtils.assertEquals(twitterUrlEditTextBox.getAttribute("value").isEmpty(), true);
            log("Twitter edit text box empty");
            CCUtils.assertEquals(facebookUrlEditTextBox.getAttribute("value").isEmpty(), true);
            log("Facebook edit text box empty");
            CCUtils.assertEquals(linkedinUrlEditTextBox.getAttribute("value").isEmpty(), true);
            log("Linkedin edit text box empty");
            CCUtils.assertEquals(youtubeUrlEditTextBox.getAttribute("value").isEmpty(), true);
            log("Youtube edit text box empty");
            return true;
        }catch (Exception exception) {
            return false;
        }
    }

    public void toggleAllSocial() {
        twitterToggleButton.click();
        facebookToggleButton.click();
        linkedinToggleButton.click();
        youtubeToggleButton.click();
    }

    public void toggleSwitchClick(String socialMediaName) throws InterruptedException, AWTException {
        if(socialMediaName.equalsIgnoreCase("Twitter")){
            twitterToggleButton.click();
            log(socialMediaName +" toggle on");
        }
        else if(socialMediaName.equalsIgnoreCase("Facebook")){
            facebookToggleButton.click();
            log(socialMediaName +" toggle on");
        }
        else if(socialMediaName.equalsIgnoreCase("Linkedin")){
            linkedinToggleButton.click();
            log(socialMediaName +" toggle on");
        }
        else {
            youtubeToggleButton.click();
            log(socialMediaName + " toggle on");
        }
    }

    public void enterValueOnSocialMedia(String socialMediaName, String url) throws InterruptedException, AWTException {
        if(socialMediaName.equalsIgnoreCase("Twitter")){
            CCUtils.verifyAndWaitForElement(twitterUrlEditTextBox, driver);
            if(twitterUrlEditTextBox.getAttribute("value").isEmpty())
                twitterUrlEditTextBox.sendKeys(url);
        }
        else if(socialMediaName.equalsIgnoreCase("Facebook")){
            CCUtils.verifyAndWaitForElement(facebookUrlEditTextBox, driver);
            if(facebookUrlEditTextBox.getAttribute("value").isEmpty())
                facebookUrlEditTextBox.sendKeys(url);
        }
        else if(socialMediaName.equalsIgnoreCase("Linkedin")){
            CCUtils.verifyAndWaitForElement(linkedinUrlEditTextBox, driver);
            if(linkedinUrlEditTextBox.getAttribute("value").isEmpty())
                linkedinUrlEditTextBox.sendKeys(url);
        }
        else if(socialMediaName.equalsIgnoreCase("YouTube")){
            CCUtils.verifyAndWaitForElement(youtubeUrlEditTextBox, driver);
            if(youtubeUrlEditTextBox.getAttribute("value").isEmpty())
                youtubeUrlEditTextBox.sendKeys(url);
        }
    }

    public void clickOnSocialMediaCancel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaCancelButton);
        CCUtils.verifyAndWaitForElement(socialMediaCancelButton, driver);
        log("clicking on cancel button on social media section");
        javascriptClick(socialMediaCancelButton);
        log("successfully clicked on cancel in social media section");
    }

    public void clickOnSocialMediaSave() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaSaveButton);
        CCUtils.verifyAndWaitForElement(socialMediaSaveButton, driver);
        log("clicking on save button on social media section");
        javascriptClick(socialMediaSaveButton);
        log("successfully clicked on save in social media section");
    }

    public void printSocialMediaUrl() throws InterruptedException, AWTException {
        for(int i=0; i<listOfSocialMediaUrlsDisplay.size();i++)
            System.out.println(listOfSocialMediaUrlsDisplay.get(i).getText());
    }

    public boolean verifySocialMediaUrlEmpty() throws InterruptedException, AWTException {
        try {
            CCUtils.assertEquals(listOfSocialMediaUrlsDisplay.isEmpty(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void updateValueOnSocialMedia(String socialMediaName, String url) throws InterruptedException, AWTException {
        if (socialMediaName.equalsIgnoreCase("Twitter")) {
            CCUtils.verifyAndWaitForElement(twitterUrlEditTextBox, driver);
            twitterUrlEditTextBox.sendKeys("update");
        } else if (socialMediaName.equalsIgnoreCase("Facebook")) {
            CCUtils.verifyAndWaitForElement(facebookUrlEditTextBox, driver);
            facebookUrlEditTextBox.sendKeys("update");
        } else if (socialMediaName.equalsIgnoreCase("Linkedin")) {
            CCUtils.verifyAndWaitForElement(linkedinUrlEditTextBox, driver);
            linkedinUrlEditTextBox.sendKeys("update");
        } else if(socialMediaName.equalsIgnoreCase("YouTube")) {
            CCUtils.verifyAndWaitForElement(youtubeUrlEditTextBox, driver);
            youtubeUrlEditTextBox.sendKeys("update");
        }
    }

    public boolean verifySocialMediaError() throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, socialMediaError);
            CCUtils.verifyAndWaitForElement(socialMediaError, driver);
            log("verifying social media error");
            CCUtils.assertEquals(socialMediaError.getText().equalsIgnoreCase("URL cannot be blank if enabled."), true);
            log("Error message Verified successfully and displayed the message as " + socialMediaError.getText());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void verifyRearrangeButtonOnExternalUrl() throws InterruptedException, AWTException {
        if(listOfExternalUrlsDisplayArea.size() > 1) {
            CCUtils.assertEquals(externalUrlRearrangeButton.isDisplayed(), true);
            log("Rearrange button is available as per excepted");
        }
        else {
            log("Rearrange button not available as per excepted");
        }
    }
}

