// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.awt.*;
import java.util.List;

public class PracticeLinksPage extends CCMainPage {

    public PracticeLinksPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'Practice Links')]")
    private WebElement practiceLinksLabel;

    @FindBy(xpath = "//b[contains(text(),'PRACTICE WEBSITE')]")
    private WebElement practiceWebsiteLabel;

    @FindBy(xpath = "//mat-icon[@class='mat-icon notranslate pr-2 material-icons mat-icon-no-color']")
    private WebElement practiceWebsiteLinkIcon;

    @FindBy(xpath = "//button[@class='mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary']/span[contains(text(),'EDIT')]")
    private WebElement practiceWebsiteEdit;

    @FindBy(xpath = "//b[contains(text(),'EXTERNAL URLS')]")
    private WebElement externalUrlLabel;

    @FindBy(xpath = "//button[@class = 'mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary ng-star-inserted']/span[contains(text(),'EDIT')]")
    private WebElement externalUrlEdit;

    @FindBy(xpath = "//button[@class = 'mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary']/span[contains(text(),'ADD')]")
    private WebElement externalUrlAdd;

    @FindBy(xpath = "//label[contains(text(),'No external URLs configured. Click EDIT to add.')]")
    private WebElement noExternalUrlConfigureLabel;

    @FindBy(xpath = "//form[@id='submit']/div/input")
    private WebElement practiceWebsiteTextBox;

    @FindBy(xpath = "//span[contains(text(),'CANCEL')]")
    private WebElement cancelButtonForPracticeWebsite;

    @FindBy(xpath = "//div/label[@class='content-header ng-star-inserted']")
    private WebElement practiceWebsiteDisplayArea;

    @FindBy(xpath = "//span[contains(text(),'SAVE')]")
    private WebElement saveButtonForPracticeWebsite;

    @FindBy(xpath = "//div[@class='col-1 text-right']/span/button/mat-icon[contains(text(),' keyboard_arrow_down ')]")
    private WebElement practiceWebsiteExpandArrow;

    @FindBy(xpath = "//div[@class='col-1 text-right']/span/button/mat-icon[contains(text(),' keyboard_arrow_up ')]")
    private WebElement practiceWebsiteCollapseArrow;

    @FindBy(xpath = "//span/input")
    private List<WebElement> listOfexternalUrlHyperLinkTexts;

    @FindBy(xpath = "//div[@class='leftColumn col-lg-11 col-md-11 ml-3']/input")
    private List<WebElement> listOfExternalUrls;

    @FindBy(xpath = "//button[@id='saveDetailsBtn']/span[contains(text(),'SAVE')]")
    private WebElement externalUrlSaveButton;

    @FindBy(xpath = "//button/span[contains(text(),'CANCEL')]")
    private WebElement externalUrlCancelButton;

    @FindBy(xpath = "//div[@class='col-6']/a[@target='_blank']")
    private List<WebElement> listOfExternalUrlsDisplayArea;

    @FindBy(xpath = "//span[contains(text(),'drag')]")
    private List<WebElement> listOfDragElements;

    @FindBy(xpath = "//button/span[contains(text(),'REARRANGE')]")
    private WebElement externalUrlRearrangeButton;

    @FindBy(xpath = "//mat-icon[contains(text(),'remove_circle')]")
    private List<WebElement> listOfDeleteExternalUrlIcon;

    @FindBy(xpath = "//mat-icon[contains(text(),'remove_circle')]")
    private WebElement deleteExternalUrlIcon;

    @FindBy(xpath = "//div[contains(text(),'Maximum characters reached')]")
    private WebElement maximumCharacterError;

    @FindBy(xpath = "//span[@class='content']/label")
    private List<WebElement> listOfDisplayText;

    @FindBy(xpath = "//div[contains(text(),'SOCIAL MEDIA')]")
    private WebElement socialMediaLabel;

    @FindBy(xpath = "//span[contains(text(),'Twitter')]")
    private WebElement twitterLabel;

    @FindBy(xpath = "//span[contains(text(),'Facebook')]")
    private WebElement facebookLabel;

    @FindBy(xpath = "//span[contains(text(),'Linkedin')]")
    private WebElement linkedinLabel;

    @FindBy(xpath = "//span[contains(text(),'YouTube')]")
    private WebElement youtubeLabel;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div")
    private WebElement twitterUrl;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div")
    private WebElement facebookUrl;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div")
    private WebElement linkedinUrl;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div")
    private WebElement youtubeUrl;

    @FindBy(xpath = "//button[@class = 'mat-focus-indicator secondary-button mat-stroked-button mat-button-base mat-primary ng-star-inserted']/span[contains(text(),'Edit')]")
    private WebElement socialMediaEdit;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div[@class='col-7 mr-0']")
    private WebElement twitterUrlEditTextBox;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div[@class='col-1 text-center pl-0']")
    private WebElement twitterToggleButton;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div[@class='col-7 mr-0']")
    private WebElement facebookUrlEditTextBox;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div[@class='col-1 text-center pl-0']")
    private WebElement facebookToggleButton;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div[@class='col-7 mr-0']")
    private WebElement linkedinUrlEditTextBox;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div[@class='col-1 text-center pl-0']")
    private WebElement linkedinToggleButton;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div[@class='col-7 mr-0']")
    private WebElement youtubeUrlEditTextBox;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div[@class='col-1 text-center pl-0']")
    private WebElement youtubeToggleButton;

    @FindBy(xpath = "//div[@class='ng-star-inserted']/span[contains(text(),'CANCEL')]")
    private WebElement socialMediaCancelButton;

    @FindBy(xpath = "//button[@class='savebutton mx-3' and contains(text(),'SAVE')]")
    private WebElement socialMediaSaveButton;

    @FindBy(xpath = "//div[contains(text(),'URL cannot be blank if enabled.')]")
    private WebElement socialMediaError;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div/input")
    private WebElement twitterEditBoxWhenToggleOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div/input")
    private WebElement facebookEditBoxWhenToggleOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div/input")
    private WebElement linkedinEditBoxWhenToggleOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div/input")
    private WebElement youtubeEditBoxWhenToggleOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div/input")
    private WebElement twitterTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div/input")
    private WebElement facebookTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div/input")
    private WebElement linkedinTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-untouched ng-pristine ng-valid ng-star-inserted']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div/input")
    private WebElement youtubeTextBoxWhenWebsiteAvailable;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'YouTube')]/parent::div/following-sibling::div/following-sibling::div/mat-slide-toggle/label/div")
    private WebElement youtubeToggleButtonWhenOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Linkedin')]/parent::div/following-sibling::div/following-sibling::div/mat-slide-toggle/label/div")
    private WebElement linkedinToggleButtonWhenOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Facebook')]/parent::div/following-sibling::div/following-sibling::div/mat-slide-toggle/label/div")
    private WebElement facebookToggleButtonWhenOn;

    @FindBy(xpath = "//div[@class='row socialContent px-0 ng-star-inserted ng-dirty ng-invalid ng-touched']/child::div/span[contains(text(),'Twitter')]/parent::div/following-sibling::div/following-sibling::div/mat-slide-toggle/label/div")
    private WebElement twitterToggleButtonWhenOn;

    @FindBy(xpath = "//div[@class='col-8']/a")
    private List<WebElement> listOfSocialMediaUrlsDisplay;

    @FindBy(xpath = "//div[@class='col-6']/label[contains(text(),'Hyperlink Text')]")
    private WebElement hyperlinkText;

    @FindBy(xpath = "//div[@class='col-6']/label[contains(text(),'Website URL')]")
    private WebElement websiteUrl;

    public void verifyHyperLinkText() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, hyperlinkText);
        CCUtils.verifyAndWaitForElement(hyperlinkText, driver);
        log("Verifying Hyperlink text title is available or not");
        CCUtils.assertEquals(hyperlinkText.getText().equalsIgnoreCase("Hyperlink Text"), true);
        log("Successfully hyperlink text title displayed");
    }

    public void verifyWebsiteUrl() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, websiteUrl);
        CCUtils.verifyAndWaitForElement(websiteUrl, driver);
        log("Verifying Website Url title is available or not");
        CCUtils.assertEquals(websiteUrl.getText().equalsIgnoreCase("Website URL"), true);
        log("Successfully website url title displayed");
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
                Thread.sleep(2000);
                javascriptClick(cancelButtonForPracticeWebsite);
            }
            else
                log("Practice website is not available to update");
        } else if (action.equalsIgnoreCase("Save")) {
            if (!practiceWebsiteDisplayArea.getText().isEmpty() || practiceWebsiteDisplayArea.getText().isEmpty()) {
                javascriptClick(practiceWebsiteEdit);
                log("Entering value for practice website");
                practiceWebsiteTextBox.clear();
                Thread.sleep(3000);
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

    public void verifyPracticeWebsiteExpandArrowIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteExpandArrow);
        CCUtils.verifyAndWaitForElement(practiceWebsiteExpandArrow, driver);
        log("verifying Practice website expand arrow availability");
        CCUtils.assertEquals(practiceWebsiteExpandArrow.isDisplayed(), true);
    }

    public void verifyPracticeWebsiteCollapseArrowIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, practiceWebsiteCollapseArrow);
        CCUtils.verifyAndWaitForElement(practiceWebsiteCollapseArrow, driver);
        log("verifying Practice website collapse arrow availability");
        CCUtils.assertEquals(practiceWebsiteCollapseArrow.isDisplayed(), true);
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

    public void verifyExternalUrlAddIsDisplayed() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlAdd);
        CCUtils.verifyAndWaitForElement(externalUrlAdd, driver);
        log("Verifying EXTERNAL URLS Add button");
        CCUtils.assertEquals(externalUrlAdd.isDisplayed(), true);
    }

    public void clickOnExternalUrlAddButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlAdd);
        CCUtils.verifyAndWaitForElement(externalUrlAdd, driver);
        log("Clicking on External Url Add button with + symbol");
        javascriptClick(externalUrlAdd);
        log("Clicked successfully on external url Add button");
    }

    public void enterExternalUrlHyperLinkText(String hyperLinkNameEN, String hyperLinkNameES) throws InterruptedException, AWTException {
        listOfexternalUrlHyperLinkTexts.get(listOfexternalUrlHyperLinkTexts.size() - 2).sendKeys(hyperLinkNameEN);
        listOfexternalUrlHyperLinkTexts.get(listOfexternalUrlHyperLinkTexts.size() - 1).sendKeys(hyperLinkNameES);
        log("HyperLink Text added successfully");
    }

    public void enterExternalUrl(String externalWebsite) throws InterruptedException, AWTException {
        listOfExternalUrls.get(listOfExternalUrls.size() - 1).sendKeys(externalWebsite);
        log("External url added successfully");
    }

    public void clickOnExternalUrlEditButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, externalUrlEdit);
        CCUtils.verifyAndWaitForElement(externalUrlEdit, driver);
        log("Clicking on Edit button in External Url");
        javascriptClick(externalUrlEdit);
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
                WebElement deleteIcon = driver.findElement(By.xpath("(//mat-icon[contains(text(),'remove_circle')])[" + j + "]"));
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
        WebElement from = driver.findElement(By.xpath("(//div[@class='cdk-drag row ng-star-inserted'])[" + currentPosition + "]"));
        WebElement to = driver.findElement(By.xpath("(//div[@class='cdk-drag row ng-star-inserted'])[" + targetPosition + "]"));
        CCUtils.verifyAndWaitForElement(from, driver);
        CCUtils.verifyAndWaitForElement(to, driver);
        actions.dragAndDrop(from, to).build().perform();
        Thread.sleep(5000);
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
                int j = i * 2 + 1;
                int k = j + 1;
                String displayText = listOfDisplayText.get(j).getText() + CCUtils.generateAlphaNumericString(3);
                javascriptClick(externalUrlEdit);
                WebElement elementEN = driver.findElement(By.xpath("(//span/input)[" + j + "]"));
                WebElement elementES = driver.findElement(By.xpath("(//span/input)[" + k + "]"));
                elementEN.clear();
                new Actions(driver).moveToElement(elementEN).pause(3000).click().build().perform();
                elementEN.sendKeys(displayText);
                elementES.clear();
                new Actions(driver).moveToElement(elementES).pause(3000).click().build().perform();
                elementES.sendKeys(displayText);
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

    public void verifySocialMediaLabel() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaLabel);
        CCUtils.verifyAndWaitForElement(socialMediaLabel, driver);
        log("Verifying social media label availability");
        CCUtils.assertTrue(socialMediaLabel.isDisplayed(),"Social media label is displayed successfully");
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

    public void verifySocialMediaEditButton() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaEdit);
        CCUtils.verifyAndWaitForElement(socialMediaEdit, driver);
        log("Verifying Social media edit button availability");
        CCUtils.assertTrue(socialMediaEdit.isDisplayed(),"Socail Media Edit button is displayed successfully");
    }

    public void clickOnSocialMediaEdit() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaEdit);
        CCUtils.verifyAndWaitForElement(socialMediaEdit, driver);
        log("clicking on edit button on social media section");
        javascriptClick(socialMediaEdit);
        log("successfully clicked on edit in social media section");
    }

    public void verifyToggleOff() throws InterruptedException, AWTException {
        log("Verifying Toggle button off");
        CCUtils.assertEquals(twitterToggleButton.isSelected(),false);
        log("Twitter toggle off");
        CCUtils.assertEquals(facebookToggleButton.isSelected(),false);
        log("Facebook toggle off");
        CCUtils.assertEquals(linkedinToggleButton.isSelected(),false);
        log("Linkedin toggle off");
        CCUtils.assertEquals(youtubeToggleButton.isSelected(),false);
        log("Youtube toggle off");
    }

    public void verifyEditTextBoxUrlEmpty() throws InterruptedException, AWTException {
        CCUtils.assertEquals(twitterUrlEditTextBox.getText().isEmpty(),true);
        log("Twitter edit text box empty");
        CCUtils.assertEquals(facebookUrlEditTextBox.getText().isEmpty(),true);
        log("Facebook edit text box empty");
        CCUtils.assertEquals(linkedinUrlEditTextBox.getText().isEmpty(),true);
        log("Linkedin edit text box empty");
        CCUtils.assertEquals(youtubeUrlEditTextBox.getText().isEmpty(),true);
        log("Youtube edit text box empty");
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
            WebElement twitter = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[1]"));
            if(twitter.getText().isEmpty())
                twitter.sendKeys(url);
        }
        else if(socialMediaName.equalsIgnoreCase("Facebook")){
            WebElement facebook = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[2]"));
            if(facebook.getText().isEmpty())
                facebook.sendKeys(url);
        }
        else if(socialMediaName.equalsIgnoreCase("Linkedin")){
            WebElement linkedin = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[3]"));
            if(linkedin.getText().isEmpty())
                linkedin.sendKeys(url);
        }
        else {
            WebElement youtube = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[4]"));
            if(youtube.getText().isEmpty())
                youtube.sendKeys(url);
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

    public void verifySocialMediaUrlEmpty() throws InterruptedException, AWTException {
        CCUtils.assertEquals(listOfSocialMediaUrlsDisplay.isEmpty(),true);
    }

    public void updateValueOnSocialMedia(String socialMediaName, String url) throws InterruptedException, AWTException {
        if(socialMediaName.equalsIgnoreCase("Twitter")){
            WebElement twitter = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[1]"));
                twitter.sendKeys("update");
        }
        else if(socialMediaName.equalsIgnoreCase("Facebook")){
            WebElement facebook = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[2]"));
                facebook.sendKeys("update");
        }
        else if(socialMediaName.equalsIgnoreCase("Linkedin")){
            WebElement linkedin = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[3]"));
                linkedin.sendKeys("update");
        }
        else {
            WebElement youtube = driver.findElement(By.xpath("(//div[@class='col-7 mr-0']/input)[4]"));
                youtube.sendKeys("update");
        }
    }

    public void verifySocialMediaError() throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, socialMediaError);
        CCUtils.verifyAndWaitForElement(socialMediaError, driver);
        log("verifying social media error");
        CCUtils.assertEquals(socialMediaError.getText().equalsIgnoreCase("URL cannot be blank if enabled."),true);
        log("Error message Verified successfully and displayed the message as "+socialMediaError.getText());
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

