// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
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

	@FindBy(xpath = "//mat-checkbox[@name='checkboxPurchase']")
	private WebElement checkboxTerminateSolutions;

	@FindBy(xpath = "//input[@aria-checked='true']//preceding::span[@class='form']")
	private List<WebElement> LabelSolutionsName;

	@FindBy(xpath = "//div[contains(text(),'Solutions Active within this [Client, System, Enterprise, Practice')]//following::div[@class='row']")
	private WebElement LabelSolutionsNameOfActiveImlementation;

	@FindBy(xpath = "//mat-checkbox[@name='checkboxNonPurchase']")
	private WebElement checkboxActivateTerminatedSolutions;

	@FindBy(xpath = "//span[contains(text(),' Solutions Available for Purchase ')]//following::div[@class='row purchase-list']")
	private WebElement verifyTerminatedSolutionsName;

	@FindBy(xpath = "//div[@class='row purchase-list']")
	private List<WebElement> listOfTerminatedSolutions;

	@FindBy(xpath = "//mat-checkbox[@name='checkboxPurchase']")
	private List<WebElement> checkboxListOfAllTerminateSolutions;

	@FindBy(xpath = "//mat-checkbox[@name='checkboxNonPurchase']")
	private List<WebElement> checkboxListOfAllActivatedSolutions;

	@FindBy(xpath = "//span[contains(text(),' Solutions Available for Purchase ')]//following::span[@class='form']")
	private List<WebElement> listOfSolutionsAvailableAfterTerminate;

	@FindBy(xpath = "//span[contains(text(),' Solutions Available for Purchase ')]//preceding::span[@class='form']")
	private List<WebElement> listOfActiveSolutions;

	List<String> actualSolutionValues = new ArrayList<String>();

	public void clickOnOrgPageEditButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonOrgEdit, driver);
		scrollAndWait(0, 500, 1000);
		buttonOrgEdit.click();
	}

	public String selectTerminateCheckboxForSingleProduct() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(checkboxTerminateSolutions, driver);
		CCUtils.scrollPageToEnd();
		String currentSolutionsName = LabelSolutionsNameOfActiveImlementation.getText();
		checkboxTerminateSolutions.click();
		String[] list = currentSolutionsName.split(" ");
		String solutionName = list[1];
		String solutionStatus = list[2];
		log("get the solutions name :" + solutionName + ", " + solutionStatus);
		return solutionName;
	}

	public String verifyTerminatedSolutionsIsAvailableForPurchase() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(verifyTerminatedSolutionsName, driver);
		scrollAndWait(0, 250, 8000);
		String terminateSolutionsName = verifyTerminatedSolutionsName.getText();
		String[] listTerminated = terminateSolutionsName.split(" ");
		String solutionNameTerminated = listTerminated[1];
		String[] listStatus = terminateSolutionsName.split("\n");
		String[] solutionStatus = listStatus[1].split(" ");
		String date = "Terminated(" + CCUtils.dateformatter() + ")";
		CCUtils.assertEquals(solutionStatus[1], date);
		return solutionNameTerminated;
	}

	public void clickOnOrgPageSaveButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonOrgSave, driver);
		scrollAndWait(0, 500, 200);
		buttonOrgSave.click();
	}

	public String selectActivateCheckboxForSingleProduct() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(checkboxActivateTerminatedSolutions, driver);
		CCUtils.scrollPageToEnd();
		log("Print the Activate solutions : " + checkboxActivateTerminatedSolutions);
		String currentSolutionsName = verifyTerminatedSolutionsName.getText();
		checkboxActivateTerminatedSolutions.click();
		String[] list = currentSolutionsName.split(" ");
		String solutionName = list[1];
		return solutionName;
	}

	public String verifyActivatedSolutionsIsAvailableForTerminate() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(LabelSolutionsNameOfActiveImlementation, driver);
		scrollAndWait(0, 250, 8000);
		String activateSolutionsName = LabelSolutionsNameOfActiveImlementation.getText();
		String[] listActivate = activateSolutionsName.split(" ");
		String solutionNameActivated = listActivate[1];
		return solutionNameActivated;
	}

	public void selectAllTerminateCheckboxOfProducts() throws InterruptedException {
		for (int i = 0; i < checkboxListOfAllTerminateSolutions.size(); i++) {
			scrollAndWait(0, 250, 8000);
			checkboxListOfAllTerminateSolutions.get(i).click();
		}

	}

	public void verifySolutionsNameForAvailableToPurchase() throws InterruptedException {
		for (int i = 0; i < listOfSolutionsAvailableAfterTerminate.size(); i++) {
			actualSolutionValues.add(listOfSolutionsAvailableAfterTerminate.get(i).getText());
		}
		List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "Pay", "Portal", "PSS",
				"Virtual Visits");

		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void verifyActiveSolutions() throws InterruptedException {
		for (int i = 0; i < listOfActiveSolutions.size(); i++) {
			actualSolutionValues.add(listOfActiveSolutions.get(i).getText());
		}

		List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "Pay", "Portal", "PSS",
				"Virtual Visits");

		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void selectAllActivateCheckboxOfProducts() throws InterruptedException {
		for (int i = 0; i < checkboxListOfAllActivatedSolutions.size(); i++) {
			scrollAndWait(0, 250, 8000);
			checkboxListOfAllActivatedSolutions.get(i).click();
		}
	}

	public void verifyActiveSolutionsNames() throws InterruptedException {
		for (int i = 0; i < LabelSolutionsName.size(); i++) {
			actualSolutionValues.add(LabelSolutionsName.get(i).getText());
		}

		List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "Pay", "Portal", "PSS",
				"Virtual Visits");

		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

}
