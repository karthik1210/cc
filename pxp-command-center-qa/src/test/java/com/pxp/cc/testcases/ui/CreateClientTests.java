// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;

public class CreateClientTests extends CommandCenterGenericTest {

	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;

	@Test(enabled = true, groups = { "AcceptanceTests", "SmokeTest" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyClientPopup() throws Exception {
		log("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		createClientPage.createNewClient(ccuser.getNewClientName(), ccuser.getNewClientSap(),
				ccuser.getClientTimezone());
		String actualMessage = createClientPage.clickConfirmPopup();
		if (actualMessage.contains("Success")) {
			log("Client created sucessfully, toaster message is " + actualMessage);
		} else {
			log("Client not created, error toaster message is " + actualMessage);
		}

	}

	@Test(enabled = true, groups = { "AcceptanceTests", "SmokeTest" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyUniqueClientNameAndSap() throws Exception {

		log("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		createClientPage.verifyClientNameAndSapUnique(ccuser.getDuplicateClientName(), ccuser.getDuplicateClientSap());
	}

	@Test(enabled = true, groups = { "AcceptanceTests", "SmokeTest" }, retryAnalyzer = RetryAnalyzer.class)
	public void createInactiveTestClient() throws Exception {
		log("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		createClientPage.createNewClient(ccuser.getNewTestClientName(), ccuser.getNewTestClientSap(),
				ccuser.getClientTimezone());
		createClientPage.selectInactiveRadiobutton();
		createClientPage.selectTestCheckbox();
		createClientPage.clickConfirmPopup();
		String actualMessage = createClientPage.clickConfirmPopup();
		if (actualMessage.contains("Success")) {
			log("Client created sucessfully, toaster message is " + actualMessage);
		} else {
			log("Client not created, error toaster message is " + actualMessage);
		}
	}

}
