package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.ProviderDetailsPage;
import com.pxp.cc.objectmaps.ProviderResourceAndStaffSearchPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddProviderImageTests extends CommandCenterGenericTest{

	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	public ProviderResourceAndStaffSearchPage providerResourceStaffSearchPage;
	public ProviderDetailsPage providerDetailsPage;
	String clientName, systemName , enterpriseName , agPracticeName, providerName;
	public AddOrgNodePage addOrgNodePage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		/*
		 * createClientPage = new CreateClientPage(driver);
		 * createClientPage.clickCreateClient(); String clientName =
		 * testData.getProperty("new.client.name")
		 * .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)); String
		 * clientSapNumber = testData.getProperty("new.client.sap")
		 * .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		 * createClientPage.createNewClient(clientName, clientSapNumber,
		 * testData.getProperty("new.client.type"),
		 * testData.getProperty("new.client.timezone"));
		 * createClientPage.clickConfirmPopup(); String actualMessage =
		 * commonMethods.getToasterMessage(); logStep("Verify success toaster message");
		 * CCUtils.assertEquals(actualMessage, "Success! " + clientName +
		 * " has been successfully created");
		 */
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchAndSelectClientNameInClientTable("Aa_qa_shippuden");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerResourceStaffSearchPage = new ProviderResourceAndStaffSearchPage(driver);
		providerDetailsPage = new ProviderDetailsPage(driver);
	}

	public String createSystem() throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnAddSystem();
		String sysName = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.enterNewSystemDetails(sysName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(sysName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return sysName;
	}

	public String createAgnosticPractice(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return practiceName;
	}
	
	public String createProviderWithinAgPractice() throws Exception {
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		String firstName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		addNewProviderPage.enterFirstName(firstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
		return firstName;
	}
	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify provider image is successfully uploaded")
	public void testAddProviderImage() throws Exception {
		/*
		 * systemName = createSystem(); agPracticeName =
		 * createAgnosticPractice(systemName); providerName =
		 * createProviderWithinAgPractice();
		 */
		
		providerResourceStaffSearchPage.enterProviderInSearchTextbox("Naruto");
		providerResourceStaffSearchPage.clickToSearchButton();
		List<String> listOfProvider = new ArrayList<String>();
		listOfProvider.add("Naruto");
		providerResourceStaffSearchPage.verifySearchProviderFound("Naruto");
		providerDetailsPage.uploadProviderImage();
	}
}
