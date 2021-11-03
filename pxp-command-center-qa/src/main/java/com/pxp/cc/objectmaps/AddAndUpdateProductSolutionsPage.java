// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class AddAndUpdateProductSolutionsPage extends CCMainPage {

	public AddAndUpdateProductSolutionsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "orgEditBtn")
	private WebElement buttonOrgEdit;

	@FindBy(id = "orgSaveBtn")
	private WebElement buttonOrgSave;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[2]/div[1]/div[1]/div[2]//span[2]/mat-checkbox[1]")
	private WebElement terminateCheckboxSolutions;

	@FindBy(xpath = "//input[@aria-checked='true']//preceding::span[@class='form']")
	private List<WebElement> terminateCheckboxSolutionsName;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[4]/div[1]/div[1]")
	private WebElement checkboxSolutionsTerminatedActivate;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[4]/div[1]/div[1]/div[2]/div[2]/span[2]/mat-checkbox[1]")
	private WebElement checkboxActivateSolution;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[2]/div[1]/div[1]")
	private WebElement solutionActiveFromAvailable;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[2]")
	private List<WebElement> TotalListOfSolutions;

	@FindBy(xpath = "//app-purchased-solutions/div/div[2]/div[1]/div")
	private WebElement singleListOfSolutionRow;

	@FindBy(xpath = "//app-purchased-solutions[1]/div[1]/div[4]/div[1]/div[1]")
	private WebElement singleRowOfActivateSolution;

	@FindBy(xpath = "//mat-checkbox[@name='checkboxPurchase']")
	private List<WebElement> allTerminateCheckboxSolutions;

	@FindBy(xpath = "//mat-checkbox[@name='checkboxNonPurchase']")
	private List<WebElement> allActivatedCheckboxSolutions;

	@FindBy(xpath = "//span[contains(text(),' Solutions Available for Purchase ')]//following::span[@class='form']")
	private List<WebElement> listOfSolutionsAvailableAfterTerminate;

	public void clickOnOrgPageEditButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonOrgEdit, driver);
		scrollAndWait(0, 500, 1000);
		buttonOrgEdit.click();
	}

	public String selectTerminateCheckboxForSingleProduct() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(terminateCheckboxSolutions, driver);
		scrollAndWait(0, 250, 8000);
		log("Print the solutions : " + terminateCheckboxSolutions);
		String currentSolutionsName = singleListOfSolutionRow.getText();
		log("singleListOfSolutionRow : " + currentSolutionsName);
		terminateCheckboxSolutions.click();
		String[] list = currentSolutionsName.split(" ");
		String solutionName = list[1];
		log("get the solutions name :" + solutionName);
		return solutionName;
	}

	public String verifyTerminatedSolutionsIsAvailableForPurchase() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxSolutionsTerminatedActivate, driver);
		scrollAndWait(0, 250, 8000);
		String terminateSolutionsName = checkboxSolutionsTerminatedActivate.getText();
		log("singleListOfSolutionRow Terminated :" + terminateSolutionsName);
		String[] listTerminated = terminateSolutionsName.split(" ");
		String solutionNameTerminated = listTerminated[1];
		log(" get the solutions Terminated name :" + solutionNameTerminated);
		return solutionNameTerminated;
	}

	public void clickOnOrgPageSaveButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonOrgSave, driver);
		scrollAndWait(0, 500, 200);
		buttonOrgSave.click();
	}

	public String selectActivateCheckboxForSingleProducts() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxActivateSolution, driver);
		scrollAndWait(0, 250, 8000);
		log("Print the Activate solutions : " + checkboxActivateSolution);
		String currentSolutionsName = singleRowOfActivateSolution.getText();
		log("single Solution Row Activate: " + currentSolutionsName);
		checkboxActivateSolution.click();
		String[] list = currentSolutionsName.split(" ");
		String solutionName = list[1];
		log("get the solutions name Activated :" + solutionName);
		return solutionName;
	}

	public String verifyActivatedSolutionsIsAvailableForTerminate() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(solutionActiveFromAvailable, driver);
		scrollAndWait(0, 250, 8000);
		String activateSolutionsName = solutionActiveFromAvailable.getText();
		log("singleListOfSolutionRow Available :" + activateSolutionsName);
		String[] listActivate = activateSolutionsName.split(" ");
		String solutionNameActivated = listActivate[1];
		log(" get the solutions Available name :" + solutionNameActivated);
		return solutionNameActivated;
	}

	public void selectAllTerminateCheckboxOfProducts() throws InterruptedException {
		for (int i = 0; i < allTerminateCheckboxSolutions.size(); i++) {
			scrollAndWait(0, 250, 8000);
			allTerminateCheckboxSolutions.get(i).click();
		}

		List<String> actualSolutionValues = new ArrayList<String>();
		for (int i = 0; i < terminateCheckboxSolutionsName.size(); i++) {
			actualSolutionValues.add(terminateCheckboxSolutionsName.get(i).getText());
		}

		for (int i = 0; i < listOfSolutionsAvailableAfterTerminate.size(); i++) {
			listOfSolutionsAvailableAfterTerminate.get(i).getText();
			log("Solution available for user are :" + actualSolutionValues);
		}

		List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "Pay", "Portal", "PSS",
				"Virtual Visits");

		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void selectAllActivateCheckboxOfProducts() throws InterruptedException {
		for (int i = 0; i < allActivatedCheckboxSolutions.size(); i++) {
			scrollAndWait(0, 250, 8000);
			allActivatedCheckboxSolutions.get(i).click();
		}

		List<String> actualSolutionValues = new ArrayList<String>();
		for (int i = 0; i < terminateCheckboxSolutionsName.size(); i++) {
			actualSolutionValues.add(terminateCheckboxSolutionsName.get(i).getText());
		}

		for (int i = 0; i < allTerminateCheckboxSolutions.size(); i++) {
			allTerminateCheckboxSolutions.get(i).getText();
			log("Solution available for user are :" + actualSolutionValues);
		}

		List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "Pay", "Portal", "PSS",
				"Virtual Visits");

		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

}
