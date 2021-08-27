package com.pxp.cc.objectmaps;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.pxp.cc.util.CCUtils;

public class EditOrgNodePage extends CCMainPage {
	
	public EditOrgNodePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "treeExpandBtn")
	private WebElement clientCarretIcon;
	
	@FindBy(xpath = "//mat-icon[contains(text(),'keyboard_arrow_up')]")
	private WebElement systemCarretIcon;
	
	@FindBy(xpath = "//mat-icon[contains(text(),'keyboard_arrow_up')]")
	private WebElement enterpriseCarretIcon;

	@FindBy(xpath = "//mat-tree-node[@aria-level='2']")
	private WebElement clickSystemNode;
	
	@FindBy(xpath = "//mat-tree-node[@aria-level='3']")
	private WebElement clickEnterpriseNode;
	
	@FindBy(xpath = "//mat-tree-node[@aria-level='4']")
	private WebElement clickPracticeNode;
	
	@FindBy(id = "orgEditBtn")
	private WebElement editOrgButton;

	@FindBy(id = "orgSapInput")
	private WebElement textBoxOrgEditSapId;

	@FindBy(id = "orgSolutionInput")
	private WebElement textBoxSolutionDisplayName;

	@FindBy(id = "timezoneSelect")
	private WebElement dropDownTimezone;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textBoxTimezone;

	@FindBy(id = "orgInactiveRadio")
	private WebElement orgInactiveRadioButton;

	@FindBy(id = "orgActiveRadio")
	private WebElement orgActiveRadioButton;

	@FindBy(id = "orgSaveBtn")
	private WebElement orgSaveBtn;

	@FindBy(id = "orgCancelBtn")
	private WebElement orgCancelBtn;

	@FindBy(id = "a2ff2005c281-0")
	private WebElement orgTimezoneSelectOption;

	@FindBy(id = "OrgSapError")
	private WebElement errorMessageSapIdMaxLength;
	
	@FindBy(xpath = "//*[@id=\"cdk-overlay-0\"]/snack-bar-container/app-snack-icon/div/span")
	private WebElement errorMessageInactiveOrgNode;

	@FindBy(id = "orgSolutionError")
	private WebElement errorMessageSolutionDisplayNameMaxLength;

//	@FindBy(id = "headerLogoutLink")
//	private WebElement userLogout;

	public void clickOnClientCarretIcon() {
		wait.until(ExpectedConditions.visibilityOf(clientCarretIcon));
		clientCarretIcon.click();
	}
	
	public void clickOnSystemCarretIcon() {
		wait.until(ExpectedConditions.visibilityOf(systemCarretIcon));
		systemCarretIcon.click();
	}

	public void clickOnSystemNode() {
		CCUtils.verifyAndWaitForElement(clickSystemNode, driver);
		log("Clicked on system node");
		clickSystemNode.click();
	}
	
	public void clickOnEnterpriseCarretIcon() {
		wait.until(ExpectedConditions.visibilityOf(enterpriseCarretIcon));
		enterpriseCarretIcon.click();
	}
	
	public void clickOnEnterpriseNode() {
		wait.until(ExpectedConditions.visibilityOf(clickEnterpriseNode));
		clickEnterpriseNode.click();
	}
	
	public void clickOnPracticeNode() {
		wait.until(ExpectedConditions.visibilityOf(clickPracticeNode));
		clickPracticeNode.click();
	}

	public void clickEditButtonOfOrg() {
		CCUtils.verifyAndWaitForElement(editOrgButton, driver);
		log("Clicked on edit button");
		editOrgButton.click();
	}

//	public void clickLogout() throws InterruptedException {
//		CCUtils.verifyAndWaitForElement(userLogout, driver);
//		javascriptClick(userLogout);
//		log("Clicked on logout");
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	}

	public void clickActiveRadioButtonOfOrgNode() {
		CCUtils.verifyAndWaitForElement(orgActiveRadioButton, driver);
		log("Clicked on active radio button");
		orgActiveRadioButton.click();
	}

	public void clickInactiveRadioButtonOfOrgNode() {
		CCUtils.verifyAndWaitForElement(orgInactiveRadioButton, driver);
		log("Clicked on Inactive radio button");
		orgInactiveRadioButton.click();
	}

	public void clickCancelButtonOfOrg() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(orgCancelBtn, driver);
		log("Clicked on cancel button");
		scrollAndWait(0, 500, 200);
		orgCancelBtn.click();
	}

	public void clickSaveButtonOfOrg() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(orgSaveBtn, driver);
		log("Clicked on save button");
		scrollAndWait(0, 500, 200);
		orgSaveBtn.click();
	}

	public void navigateToGeneralSettingsPage() throws InterruptedException {
		WebElement searchBox = driver.findElement(By.id("directorySearchInput"));
		CCUtils.verifyAndWaitForElement(searchBox, driver);
		searchBox.click();
		Thread.sleep(5000);
		searchBox.sendKeys("CLIENTRUCHI");
		WebElement searchButton = driver.findElement(By.id("directoryToggleIcon"));
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		Thread.sleep(2000);
		searchButton.click();
		Thread.sleep(5000);
		WebElement row = driver.findElement(By.xpath("//td[@id='name-row']"));
		CCUtils.verifyAndWaitForElement(row, driver);
		Thread.sleep(5000);
		row.click();

	}

	public void editOrgOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {

		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys(sapId);
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(solutionsDisplayName);
		scrollAndWait(0, 500, 200);
		dropDownTimezone.click();
//		textBoxTimezone.sendKeys(timezone);
//		CCUtils.verifyAndWaitForElement(timezone, driver);
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);

	}

	public void overrideEditOrgOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {

		textBoxOrgEditSapId.sendKeys(sapId);
		textBoxSolutionDisplayName.sendKeys(solutionsDisplayName);
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);

	}

	public void nullEditOnGeneralSettingPage(String orgTimezoneSelectOption) throws InterruptedException {

		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys(" ");
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(" ");
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(orgTimezoneSelectOption);
		textBoxTimezone.sendKeys(Keys.ENTER);
	}

	public void maximumLengthEditOrgOnGeneralSettingPage(String timezone) throws InterruptedException {

		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys("VC848684425643610242937645@$$q$&(**&*^bcgdBN77BJGS2");
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS2");
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);

	}


	public void selectTimezoneList() {

//		Select dropdown = new Select(dropDownTimezone);
//		List<WebElement> options = new ArrayList<String>();
//		dropdown.selectByValue("a46856b54dac-1");
//		dropdown.selectByValue("a46856b54dac-2");
//		dropdown.selectByValue("a46856b54dac-3");
//		dropdown.selectByValue("a46856b54dac-4");
//		dropdown.selectByValue("a46856b54dac-5");
//		dropdown.selectByValue("a46856b54dac-6");
//		dropdown.selectByValue("a46856b54dac-7");
//		dropdown.selectByValue("a46856b54dac-8");
//		dropdown.selectByValue("a46856b54dac-9");
//		dropdown.selectByValue("a46856b54dac-10");
//		dropdown.selectByValue("a46856b54dac-11");
//		dropdown.selectByValue("a46856b54dac-12");
//		dropdown.selectByValue("a46856b54dac-13");
//		dropdown.selectByValue("a46856b54dac-14");
//		dropdown.selectByValue("a46856b54dac-10");
//		dropdown.selectByValue("a46856b54dac-10");
//		dropdown.selectByValue("a46856b54dac-10");

	}

	public String sapIdMaxLengthValidationMessageOnEditOrg() {
		String errorSapIdLength = errorMessageSapIdMaxLength.getText();
		return errorSapIdLength;
	}

	public String solutionDisplayNameMaxLengthValidationMessageOnEditOrg() {
		String errorSolutionDisplayNameLength = errorMessageSolutionDisplayNameMaxLength.getText();
		return errorSolutionDisplayNameLength;
	}
	
	public String errorMessageOnEditOrgForInactiveClient() {
		String InactiveClient = errorMessageInactiveOrgNode.getText();
		return InactiveClient;
	}
	

}
