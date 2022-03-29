// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.pxp.cc.util.CCUtils;

public class AddOrgNodePage extends CCMainPage {

	@FindBy(id = "addNodeTitle")
	private WebElement lablePageTitle;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement crossIcon;

	@FindBy(id = "addNodeCancel")
	private WebElement buttonCancelInAddNewNode;

	@FindBy(id = "addNodeSave")
	private WebElement buttonSaveAndAdd;

	@FindBy(id = "parent-label")
	private WebElement labelParentEntity;

	@FindBy(id = "addNodeClientDisp")
	private WebElement textboxParentEntityNameAtSystem;

	@FindBy(xpath = "//ng-select[@id='addNodeEnterpriseSelect'] | //ng-select[@id='addNodePracticeLabel']")
	private WebElement dropdownParentEntityName;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxParentEntityName;

	@FindBy(xpath = "//span[text()='Practice Name *']")
	private WebElement labelPracticeName;

	@FindBy(xpath = "//span[text()='System Name *']")
	private WebElement labelSystemName;

	@FindBy(xpath = "//span[text()='Enterprise Name *']")
	private WebElement labelEnterpriseName;

	@FindBy(id = "addNodeNameInput")
	private WebElement textboxName;

	@FindBy(xpath = "//span[text()='Solutions Display Name']")
	private WebElement labelSolutionDisplayName;

	@FindBy(id = "addNodeSolutionInput")
	private WebElement textboxSolutionDisplayName;

	@FindBy(xpath = "//span[text()='SAP ID']")
	private WebElement labelSapId;

	@FindBy(id = "addNodeSapInput")
	private WebElement textboxSapId;

	@FindBy(id = "timezone-label")
	private WebElement labelTimezone;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxTimezone;

	@FindBy(xpath = "//label[text()='Status * ']")
	private WebElement labelStatus;

	@FindBy(xpath = "//input[@id='addNodeActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='addNodeInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(xpath = "//ng-select[@placeholder='Search/Select']")
	private WebElement placeholderParentEntityDropdown;

	@FindBy(xpath = "//ng-select[@placeholder='Select']")
	private WebElement placeholderTimezoneDropdown;

	@FindBy(xpath = "//span[text()='Which solutions will this practice use?']")
	private WebElement labelSolution;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> checkboxSolution;

	@FindBy(xpath = "//div[text()=' Maximum characters reached ']")
	private WebElement errorMaximumCharacter;

	@FindBy(xpath = "//div[@class='text-danger ng-star-inserted']//div[@class='ng-star-inserted']")
	private WebElement errorNameRequired;

	@FindBy(xpath = "(//div[@class='text-danger ng-star-inserted'])[3]")
	private WebElement errorInvalidSap;

	@FindBy(xpath = "//div[contains(text(),'Parent Entity is required')]")
	private WebElement errorParentNameRequired;

	@FindBy(xpath = "//div[@aria-selected='true']/span")
	private WebElement selectedTimezoneValue;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> timezoneOptionList;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']")
	private List<WebElement> labelSolutionList;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']//preceding::div[@class='mat-checkbox-inner-container']")
	private List<WebElement> particularSolutionCheckbox;

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	List<String> actualSolutionValues = new ArrayList<String>();

	public final List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "PSS", "Pay", "Portal",
			"Virtual Visits");

	public AddOrgNodePage(WebDriver driver) {
		super(driver);
	}

	public void verifyActiveButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		if (radioButtonActive.isDisplayed() == true && radioButtonActive.isSelected() == true) {
			log("Active button is displayed on screen and selected");
		} else {
			log("Active button is not displayed on screen and selected");
		}
	}

	public Boolean verifyValueIsNotSelectedInParentEntity() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		String parentEntityName = textboxParentEntityName.getText().trim();
		if (parentEntityName.length() == 0) {
			log("Serach text is not present in parent entity textbox");
			return true;
		} else {
			log("Search text is present in parent entity textbox");
		}
		return false;

	}

	public void verifyDataIsNotFetchedIfParentEntityNotSelected(Boolean isParentEntityNameNotSelected) {
		if (isParentEntityNameNotSelected == true) {
			log("Verifying text is not present in sap id field");
			CCUtils.verifyAndWaitForElement(textboxSapId, driver);
			String sapId = textboxSapId.getText().trim();
			CCUtils.assertTrue(sapId.length() == 0, "Text is present in sap id field");
			log("Verifying text is not present in timezone field");
			CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
			dropdownTimezone.click();
			CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
			String timezone = textboxTimezone.getText().trim();
			CCUtils.assertTrue(timezone.length() == 0, "Text is present in timezone field");
		}
	}

	public GeneralSettingsPage clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		buttonSaveAndAdd.click();
		log("Clicked on Save&Add button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancelInAddNewNode, driver);
		javascriptClick(buttonCancelInAddNewNode);
		log("Clicked on Cancel button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		javascriptClick(crossIcon);
		log("Clicked on X icon");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void verifyErrorMessageForOrgNodeNameField(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered text in org name field is " + orgName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSolutionDisplayNameField(String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered text in solution display name field is " + solutionDisplayName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for solution display name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSapIdField(String sapId) {
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered text in sap id field is " + sapId);
		CCUtils.verifyAndWaitForElement(errorInvalidSap, driver);
		CCUtils.assertEquals(errorInvalidSap.getText().trim(), "SAP ID cannot contain more than 50 characters");
		log("Error Message displayed for sap id field is " + errorInvalidSap.getText().trim());
	}

	public void verifyOrgNameIsRequiredField(String labelText) {
		CCUtils.verifyAndWaitForElement(errorNameRequired, driver);
		if (labelText.trim().equals("System Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "System name is required");
			log("System name is required error message displayed on add new system popup");
		} else if (labelText.trim().equals("Enterprise Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Enterprise name is required");
			log("Enterprise name is required error message displayed on add new enterprise popup");
		} else if (labelText.trim().equals("Practice Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Practice name is required");
			log("Practice name is required error message displayed on add new practice popup");
		}
	}

	public void enterSpaceInRequiredField() {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter space in org name textbox");
		textboxName.sendKeys("  ");
		clickOnSaveAndAddButton();
	}

	public void verifyParentEntityNameIsRequiredField() {
		CCUtils.verifyAndWaitForElement(errorParentNameRequired, driver);
		CCUtils.assertEquals(errorParentNameRequired.getText().trim(), "Parent Entity is required");
		log("Parent Entity required error message displayed in add new enterprise popup");
	}

	public void verifyParentEntityDropdownisReadonlyField() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityNameAtSystem, driver);
		log("Verify parent entity dropdown is disabled on add system page");
		String state = textboxParentEntityNameAtSystem.getAttribute("readonly");
		CCUtils.assertNotNull(state, "The object return null value");
	}

	public void verifyTestDataFetchedFromSelectedParent(String sapId, String timezone, String Status) {
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxSapId, sapId));
		log("Verifying sap id is fetched from selected parent or not");
		CCUtils.assertEquals(textboxSapId.getAttribute("value").trim(), sapId.trim());
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("Click on timezone dropdown");
		dropdownTimezone.click();
		log("Verifying timezone is fetched from selected parent or not");
		CCUtils.assertEquals(selectedTimezoneValue.getText().trim(), timezone.trim());
		log("Verifying status is fetched from selected parent or not");
		if (Status.toString().trim().equals("active")) {
			CCUtils.assertTrue(radioButtonActive.isSelected(), "Active radio button is not selected");
		} else {
			CCUtils.assertTrue(radioButtonInactive.isSelected(), "Inactive radio button is not selected");
		}
	}

	public void verifyClientNameIsDisplayedOnParentEntityDropdown(String clientName) {
		log("Verifying client name is displayed on parent entity drop down");
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxParentEntityNameAtSystem, clientName));
		CCUtils.assertEquals(textboxParentEntityNameAtSystem.getAttribute("value").trim(), clientName);
	}

	public void createNewOrgNode(String orgName, String sapId, String timezone, String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered org name is " + orgName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered org sap id is " + sapId);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
		textboxTimezone.sendKeys(timezone, Keys.ENTER);
		log("Entered org timezone is " + timezone);
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered org solution display name is " + solutionDisplayName);
	}

	public void verifySolutionLabelIsDisplayed() {
		CCUtils.verifyAndWaitForElement(labelSolution, driver);
		CCUtils.assertEquals(labelSolution.getText().trim(), "Which solutions will this practice use?");
	}

	public void addSolutions() throws InterruptedException {
		log("Select all solutions");
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < checkboxSolution.size(); i++) {
			javascriptClick(checkboxSolution.get(i));
		}
	}

	public void verifyBoxTextForParentEntityField() {
		log("Verifying search/select placeholder is displayed for parent entity field");
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		CCUtils.assertEquals(dropdownParentEntityName.getAttribute("placeholder").toString(), "Search/Select");
	}

	public void verifyBoxTextForSapIdField() {
		log("Verifying input text placeholder is displayed for sap id field textbox");
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		CCUtils.assertEquals(textboxSapId.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForTimezoneField() {
		log("Verifying select placeholder is displayed for timezone field");
		CCUtils.verifyAndWaitForElement(placeholderTimezoneDropdown, driver);
		CCUtils.assertEquals(placeholderTimezoneDropdown.getAttribute("placeholder").toString(), "Select");
	}

	public void verifyBoxTextForSolutionDisplayNameField() {
		log("Verifying inputtText placeholder is displayed for solution display name field textbox");
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.clear();
		CCUtils.assertEquals(textboxSolutionDisplayName.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForNameField() {
		log("Verifying input text placeholder is displayed for name field textbox");
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").toString(), "Input Text");
	}

	public void selectInactiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
		javascriptClick(radioButtonInactive);
		log("Clicked on inactive radiobutton in popup");
	}

	public void selectActiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		javascriptClick(radioButtonActive);
		log("Clicked on active radiobutton in popup");
	}

	public void clickOnParentEntityNameDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		dropdownParentEntityName.click();
		log("Clicked on parent entity name dropdown in add new enterprise popup");
	}

	public void searchTextForParentEntityName(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		log("Enter text in parent entity textbox");
		textboxParentEntityName.sendKeys(searchText.trim());
		textboxParentEntityName.sendKeys(Keys.ENTER);
		log("Entered searched text is " + searchText.trim());
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public String getSystemNameLabelText() {
		return labelSystemName.getText().trim();
	}

	public String getEnterpriseNameLabelText() {
		return labelEnterpriseName.getText().trim();
	}

	public String getPracticeNameLabelText() {
		return labelPracticeName.getText().trim();
	}

	public void verifySapIdFieldAcceptBlankSpace(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter text in org name textbox");
		textboxName.sendKeys(orgName);
		log("Entered org name text is " + orgName.trim());
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		log("Enter space in org sap id textbox");
		textboxSapId.sendKeys(" ");
	}

	public void getSolutionValues() throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < labelSolutionList.size(); i++) {
			actualSolutionValues.add(labelSolutionList.get(i).getText().trim());
		}
		log("Solution available for user are :" + actualSolutionValues);
	}

	public void verifySolutionValues() throws InterruptedException {
		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void addNewSystemNode(String orgName, String sapId, String timezone, String solutionDisplayName,
			Boolean isActive) {
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}

	public void addNewNode(String parentEntityName, Boolean isActive, String orgName, String sapId, String timezone,
			String solutionDisplayName) {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(parentEntityName);
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}

	public void addNewPracticeNodeWithSolutions(String parentEntityName, Boolean isActive, String orgName, String sapId,
			String timezone, String solutionDisplayName) throws InterruptedException {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(parentEntityName);
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		addSolutions();
		clickOnSaveAndAddButton();
	}

	public GeneralSettingsPage createPracticeWithSolutions(String practiceName, String clientName,
			List<String> solutions, String sapId, String timezone, String solutionDisplayName)
			throws InterruptedException, AWTException {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(clientName);
		logStep("Creating Practice with name : " + practiceName + " with solutions " + solutions.toString());
		createNewOrgNode(practiceName, sapId, timezone, solutionDisplayName);
		selectFewSolutions(solutions);
		clickOnSaveAndAddButton();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void selectFewSolutions(List<String> providedSolutions) throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		for (String selectSolution : providedSolutions) {
			for (int i = 0; i < labelSolutionList.size(); i++) {
				CCUtils.verifyAndWaitForElement(labelSolutionList.get(i), driver);
				if ((labelSolutionList.get(i).getText().trim()).contains(selectSolution)) {
					CCUtils.verifyAndWaitForElement(particularSolutionCheckbox.get(i), driver);
					particularSolutionCheckbox.get(i).click();
					log("Solution name : " + selectSolution + " is selected.");
				}
			}
		}
	}

	public void verifyAddOrgNodePageTitle(String nodeName) {
		log("Verifying add org node page title");
		if (nodeName.equals("SYSTEM")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new system page title is not displayed on add new system popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New System");
		} else if (nodeName.equals("ENTERPRISE")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new enterprise page title is not displayed on add new enterprise popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New Enterprise");
		} else if (nodeName.equals("PRACTICE")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new practice page title is not displayed on add new practice popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New Practice");
		}
	}

	public void verifyParentEntityLableText() {
		CCUtils.verifyAndWaitForElement(labelParentEntity, driver);
		log("Verifying parent entity label text");
		CCUtils.assertTrue(labelParentEntity.isDisplayed(),
				"Parent entity label is not displayed on add new node popup");
		CCUtils.assertEquals(labelParentEntity.getText().trim(), "Parent Entity *");
	}

	public void verifyNodeNameLableText(String node) {
		log("Verifying org node name label text");
		if (node.equals("SYSTEM")) {
			CCUtils.verifyAndWaitForElement(labelSystemName, driver);
			CCUtils.assertTrue(labelSystemName.isDisplayed(),
					"System name label is not displayed on add new system popup");
			CCUtils.assertEquals(labelSystemName.getText().trim(), "System Name *");
		} else if (node.equals("ENTERPRISE")) {
			CCUtils.verifyAndWaitForElement(labelEnterpriseName, driver);
			CCUtils.assertTrue(labelEnterpriseName.isDisplayed(),
					"Enterprise name label is not displayed on add new enterprise popup");
			CCUtils.assertEquals(labelEnterpriseName.getText().trim(), "Enterprise Name *");
		} else if (node.equals("PRACTICE")) {
			CCUtils.verifyAndWaitForElement(labelPracticeName, driver);
			CCUtils.assertTrue(labelPracticeName.isDisplayed(),
					"Practice name label is not displayed on add new practice popup");
			CCUtils.assertEquals(labelPracticeName.getText().trim(), "Practice Name *");
		}
	}

	public void verifySolutionDisplayNameLableText() {
		CCUtils.verifyAndWaitForElement(labelSolutionDisplayName, driver);
		log("Verifying solution display name label text");
		CCUtils.assertTrue(labelSolutionDisplayName.isDisplayed(),
				"Solution display name label is not displayed on add new node popup");
		CCUtils.assertEquals(labelSolutionDisplayName.getText().trim(), "Solutions Display Name");
	}

	public void verifySapIdLableText() {
		CCUtils.verifyAndWaitForElement(labelSapId, driver);
		log("Verifying sap id label text");
		CCUtils.assertTrue(labelSapId.isDisplayed(), "Sap id label is not displayed on add new node popup");
		CCUtils.assertEquals(labelSapId.getText().trim(), "SAP ID");
	}

	public void verifyTimezoneLableText() {
		CCUtils.verifyAndWaitForElement(labelTimezone, driver);
		log("Verifying timezone label text");
		CCUtils.assertTrue(labelTimezone.isDisplayed(), "Timezone label is not displayed on add new node popup");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone");
	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatus, driver);
		CCUtils.assertTrue(labelStatus.isDisplayed(), "Status label is not displayed on add new node popup");
		log("Verifying status label text");
		CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
		log("Status label text is " + labelStatus.getText().trim());
		log("Verifying active label and radio button are displayed on add new node popup");
		CCUtils.assertEquals(labelActive.getText().trim(), "Active");
		CCUtils.assertTrue(radioButtonActive.isDisplayed(), "Active radiobutton is not present on add new node popup");
		log("Verifying inactive label and radio button are displayed on add new node popup");
		CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
		CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
				"Inactive radiobutton is not present on add new node popup");
	}

	public void verifyLabelPresentOnAddNewNodePopup(String nodeName) {
		verifyAddOrgNodePageTitle(nodeName);
		verifyParentEntityLableText();
		verifyNodeNameLableText(nodeName);
		verifySolutionDisplayNameLableText();
		verifySapIdLableText();
		verifyTimezoneLableText();
		verifyStatusLabelTextAndRadioButtons();
	}

	public void enterOrgName(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered org name is " + orgName);
	}

}
