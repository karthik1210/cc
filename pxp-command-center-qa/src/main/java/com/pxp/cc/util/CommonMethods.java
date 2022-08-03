// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.objectmaps.CCMainPage;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class CommonMethods extends CCMainPage {

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(id = "usernameValidationMsg")
	private WebElement loginUserName;

	@FindBy(id = "snackCloseBtn")
	private WebElement buttonCloseToaster;

	@FindBy(xpath = "//div[@class='flex']/span//following::a")
	private WebElement linkClientName;

	@FindBy(id = "FirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "LastNameInput")
	private WebElement textboxLastName;

	@FindBy(xpath = "//input[@id='addEmailInput'] | //input[@id='EmailInput']")
	private WebElement textboxEmail;

	@FindBy(id = "loginUsernameInput")
	private WebElement textboxUserName;

	@FindBy(xpath = "//input[@id='addMobileInput'] | //input[@id='mobileInput'] | //input[@id='MobileNumberInput']")
	private WebElement textboxMobileNumber;

	@FindBy(id = "maxFirstNameError")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(id = "maxLastNameError")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(id = "maxUsernameError")
	private WebElement errorMaximumCharacterUsername;

	@FindBy(id = "maxEmailError")
	private WebElement errorMaximumCharacterEmail;

	@FindBy(id = "firstNameError")
	private WebElement errorFirstNameRequired;

	@FindBy(id = "lastNameError")
	private WebElement errorLastNameRequired;

	@FindBy(id = "usernameError")
	private WebElement errorUsernameRequired;

	@FindBy(id = "emailError")
	private WebElement errorEmailRequired;

	@FindBy(xpath = "//div[normalize-space()='Username already in use']")
	private WebElement errorUniqueUsername;

	@FindBy(xpath = "//div[normalize-space()='Email already in use']")
	private WebElement errorUniqueEmail;

	@FindBy(xpath = "//div[normalize-space()='Mobile number already in use']")
	private WebElement errorUniqueMobileNumber;

	@FindBy(id = "validMobileNumberError")
	private WebElement errorValidMobileNumber;

	@FindBy(id = "validEmailError")
	private WebElement errorValidEmail;

	@FindBy(id = "validFirstNameError")
	private WebElement errorValidFirstName;

	@FindBy(id = "validLastNameError")
	private WebElement errorValidLastName;

	@FindBy(id = "validUsernameError")
	private WebElement errorValidUsername;

	@FindBy(xpath = "//span[text()='First Name *']")
	private WebElement labelFirstName;

	@FindBy(xpath = "//span[text()='Last Name *']")
	private WebElement labelLastName;

	@FindBy(xpath = "//span[text()='Username *']")
	private WebElement labelUsername;

	@FindBy(xpath = "//span[text()='Email *']")
	private WebElement labelEmail;

	@FindBy(xpath = "//span[text()='Mobile Number']")
	private WebElement labelMobileNumber;

	@FindBy(xpath = "//span[text()='Status *']")
	private WebElement labelStatus;

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//input[@id='createUserActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(id = "FirstNameInput")
	private WebElement labelFirstNameValue;

	@FindBy(id = "LastNameInput")
	private WebElement labelLastNameValue;

	@FindBy(id = "EmailInput")
	private WebElement labelEmailValue;

	@FindBy(id = "MobileNumberInput")
	private WebElement labelMobileNumberValue;

	@FindBy(xpath = "//div[@class='icon suffix-icon']/span")
	private WebElement iconUsernameValidationError;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//div[@role='combobox']//following::span[@class='ng-arrow-wrapper'][last()-1]")
	private WebElement dropdownClientType;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> listOfOptions;

	@FindBy(xpath = "//ng-dropdown-panel[@role='listbox']//following::div[@role='option']//span")
	private List<WebElement> listOfClientTypeOptions;

	List<String> actualtimezoneValues = new ArrayList<String>();
	List<String> actualClientTypeValues = new ArrayList<String>();

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public String getToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		return toasterMessage.getText().trim();

	}

	public void closeToasterMessage() {
		CCUtils.verifyAndWaitForElement(buttonCloseToaster, driver);
		log("Click on cross icon");
		buttonCloseToaster.click();
	}

	public void clickToasterlink() {
		linkClientName.click();
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(input);
		log("Entered input for Firstname : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.clear();
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(input);
		log("Entered input for Last name : " + input);
	}

	public void enterUserName(String input) {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.clear();
		textboxUserName.sendKeys(input);
		log("Entered input for username : " + input);
	}

	public void enterEmail(String input) {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.clear();
		textboxEmail.sendKeys(input);
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.click();
		log("Entered input for email : " + input);
	}

	public void enterMobileNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.clear();
		textboxMobileNumber.sendKeys(input);
		log("Entered input for mobile number : " + input);
	}

	public void focusOnInactiveRadioButton() {
		CCUtils.focusOnElement(radioButtonInactive, driver);
	}

	public String getMaximumCharacterErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		return errorMaximumCharacterFirstName.getText().trim();
	}

	public String getMaximumCharacterErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterLastName, driver);
		return errorMaximumCharacterLastName.getText().trim();
	}

	public String getMaximumCharacterErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterUsername, driver);
		return errorMaximumCharacterUsername.getText().trim();
	}

	public String getMaximumCharacterErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterEmail, driver);
		return errorMaximumCharacterEmail.getText().trim();
	}

	public String getRequiredErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorFirstNameRequired, driver);
		return errorFirstNameRequired.getText().trim();
	}

	public String getRequiredErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorLastNameRequired, driver);
		return errorLastNameRequired.getText().trim();
	}

	public String getRequiredErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorUsernameRequired, driver);
		return errorUsernameRequired.getText().trim();
	}

	public String getRequiredErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorEmailRequired, driver);
		return errorEmailRequired.getText().trim();
	}

	public String getValidErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorValidFirstName, driver);
		return errorValidFirstName.getText().trim();
	}

	public String getValidErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorValidLastName, driver);
		return errorValidLastName.getText().trim();
	}

	public String getValidErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorValidUsername, driver);
		return errorValidUsername.getText().trim();
	}

	public String getValidErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorValidEmail, driver);
		return errorValidEmail.getText().trim();
	}

	public String getValidErrorForMobileNumberField() {
		CCUtils.verifyAndWaitForElement(errorValidMobileNumber, driver);
		return errorValidMobileNumber.getText().trim();
	}

	public String getDuplicateErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorUniqueUsername, driver);
		return errorUniqueUsername.getText().trim();
	}

	public String getDuplicateErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorUniqueEmail, driver);
		return errorUniqueEmail.getText().trim();
	}

	public String getDuplicateErrorForMobileNumberField() {
		CCUtils.verifyAndWaitForElement(errorUniqueMobileNumber, driver);
		return errorUniqueMobileNumber.getText().trim();
	}

	public void clearFirstNameField() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		CCUtils.clearTextboxField(textboxFirstName);
		log("deleted input in first name field");
	}

	public void clearLastNameField() {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		CCUtils.clearTextboxField(textboxLastName);
		log("deleted input in last name field");
	}

	public void clearUsernameField() {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		CCUtils.clearTextboxField(textboxUserName);
		log("deleted input in username field");
	}

	public void clearEmailField() {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		CCUtils.clearTextboxField(textboxEmail);
		log("deleted input in email field");
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		log("Verifying first name label text");
		CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name *");
		log("First name label text is " + labelFirstName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		log("Verifying input text placeholder is displayed for first name textbox field");
		CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		log("Verifying last name label text");
		CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name *");
		log("Last name label text is " + labelLastName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		log("Verifying input text placeholder is displayed for last name textbox field");
		CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		log("Verifying username label text");
		CCUtils.assertEquals(labelUsername.getText().trim(), "Username *");
		log("Username label text is " + labelUsername.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		log("Verifying input text placeholder is displayed for username textbox field ");
		CCUtils.assertEquals(textboxUserName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for username textbox field is " + textboxUserName.getAttribute("placeholder").trim());
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		log("Verifying email label text");
		CCUtils.assertEquals(labelEmail.getText().trim(), "Email *");
		log("Email label text is " + labelEmail.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		log("Verifying input text placeholder is displayed for email textbox field ");
		CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
		log("Verifying mobile number label text");
		CCUtils.assertEquals(labelMobileNumber.getText().trim(), "Mobile Number");
		log("Mobile number label text is " + labelMobileNumber.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		log("Verifying input text placeholder is displayed for mobile number textbox field ");
		CCUtils.assertEquals(textboxMobileNumber.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for mobile number textbox field is " + textboxMobileNumber.getAttribute("placeholder").trim());

	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatus, driver);
		log("Verifying status label text");
		CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
		log("Status label text is " + labelStatus.getText().trim());
		log("Verifying active label and radio button are displayed on add new user page");
		CCUtils.assertEquals(labelActive.getText().trim(), "Active");
		CCUtils.assertTrue(radioButtonActive.isDisplayed(), "Active radiobutton is not present on add new user page");
		log("Verifying inactive label and radio button are displayed on add new user page");
		CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
		CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
				"Inactive radiobutton is not present on add new user page");
	}

	public void verifyActiveRadioButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		CCUtils.assertTrue(radioButtonActive.isSelected(),
				"Active button is not displayed on add new user page and selected");
	}

	public String getFirstNameValue() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		return textboxFirstName.getAttribute("value").trim();
	}

	public String getLastNameValue() {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		return textboxLastName.getAttribute("value").trim();
	}

	public String getEmailValue() {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		return textboxEmail.getAttribute("value").trim();
	}

	public String getUserNameValue() {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		return textboxUserName.getAttribute("value").trim();
	}

	public String getMobileNumberValue() {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		return textboxMobileNumber.getAttribute("value").replace("-", "").trim();
	}

	public void selectInactiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
		javascriptClick(radioButtonInactive);
		log("Clicked on inactive radiobutton in popup");
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(loginUserName, driver);
		return loginUserName.getText().trim();
	}

	public String getFirstName() {
		CCUtils.verifyAndWaitForElement(labelFirstNameValue, driver);
		return labelFirstNameValue.getText().trim();
	}

	public String getLastName() {
		CCUtils.verifyAndWaitForElement(labelLastNameValue, driver);
		return labelLastNameValue.getText().trim();
	}

	public String getEmailAddress() {
		CCUtils.verifyAndWaitForElement(labelEmailValue, driver);
		return labelEmailValue.getText().trim();
	}

	public String getMobileNumber() {
		CCUtils.verifyAndWaitForElement(labelMobileNumberValue, driver);
		return labelMobileNumberValue.getText().trim();
	}

	public String getTooltipTextForUsernameField() {
		CCUtils.verifyAndWaitForElement(iconUsernameValidationError, driver);
		log("Hovering the mouse over the username field error icon");
		CCUtils.mouseHoverOverTheElement(driver, iconUsernameValidationError);
		return iconUsernameValidationError.getAttribute("title").trim();
	}

	public final List<String> expectedTimezoneValues = Arrays.asList("Select", "International Date Line West",
			"Midway Island, Samoa", "Hawaii Standard Time", "Alaska Standard Time", "Pacific Standard Time",
			"Tijuana, Baja California", "Arizona Standard Time", "Chihuahua, La Paz, Mazatlan",
			"Mountain Standard Time", "Central America", "Central Standard Time", "Guadalajara, Mexico City, Monterrey",
			"Saskatchewan", "Bagota, Lima, Quito, Rio Branco", "Eastern Standard Time", "Indiana Standard Time",
			"Caracas", "Atlantic Standard Time", "La Paz", "Manaus", "Santiago", "Newfoundland",
			"Buenos Aires, Georgetown", "Greenland", "Montevideo", "Mid-Atlantic", "Azores", "Cape Verde Is.",
			"Casablanca,Monrovia,Reykjavik", "Greenwich Mean Time", "Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna",
			"Belgrade, Bratislava, Budapest, Ljubljana, Prague", "Brussels, Copenhagen, Madrid, Paris",
			"Sarajevo, Skopje, Warsaw, Zagreb", "West Central Africa", "Amman", "Athens, Bucharest, Istanbul", "Beirut",
			"Cairo", "Harare, Pretoria", "Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", "Jerusalem", "Minsk",
			"Baghdad", "Kuwait, Riyadh", "Moscow, St. Petersburg, Volgograd", "Nairobi", "Tehran", "Abu Dhabi, Muscat",
			"Baku", "Yerevan", "Kabul", "Ekaterinburg", "Islamabad, Karachi, Tashkent",
			"Chennai, Kolkata, Mumbai, New Delhi", "Kathmandu", "Almaty, Novosibirsk", "Astana, Dhaka",
			"Yangon (Rangoon)", "Bangkok, Hanoi, Jakarta", "Krasnoyarsk", "Hong Kong Standard Time",
			"Irkutsk, Ulaan Bataar", "Kuala Lumpur, Singapore", "Perth", "Taipei", "Osaka, Sapporo, Tokyo", "Seoul",
			"Yakutsk", "Adelaide", "Darwin", "Brisbane", "Canberra, Melbourne, Sydney", "Guam, Port Moresby", "Hobart",
			"Vladivostok", "Magadan, Solomon Is., New Caledonia", "Auckland, Wellington", "Nuku'alofa");

	public void getTimezoneDropdownValue() {
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		for (int i = 0; i < listOfOptions.size(); i++) {
			actualtimezoneValues.add(listOfOptions.get(i).getText().trim());
		}
		log("Timezone values present for user are :" + actualtimezoneValues);
		dropdownTimezone.click();
	}

	public void verifyTimezoneDropdownValues() throws InterruptedException {
		CCUtils.assertEquals(actualtimezoneValues, expectedTimezoneValues);
	}

	public final List<String> expectedClientTypes = Arrays.asList("Direct Client", "Dedicated IT", "GBS", "Itelagen",
			"KM Strategic", "MdCentric", "Medicalistics", "MTS/MMIC", "OSIS", "SharedMed", "TSI", "Virmedice",
			"Health Prime International", "Newport Medical Group", "New Stork Healthdata Management");

	public void getClientTypeDropdownValue() {
		CCUtils.verifyForElementToBeClickable(dropdownClientType, driver);
		dropdownClientType.click();
		for (int i = 0; i < listOfClientTypeOptions.size(); i++) {
			actualClientTypeValues.add(listOfClientTypeOptions.get(i).getText().trim());
		}
		log("Client type values present for user are :" + actualClientTypeValues);
		dropdownClientType.click();
	}

	public void verifyClientTypeDropdownValues(String baseUrl) throws InterruptedException {
		 List<String> expectedClientTypes = new ArrayList<String>();
		String url = baseUrl + ApiPath.CLIENT_TYPES_DL;
		Response fetchClientType = given().when().get(url).then().extract().response();
		JSONArray jsonArray = new JSONArray(fetchClientType.getBody().asString());
		JSONObject jsonObject;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			expectedClientTypes.add(jsonObject.getString("name"));
			log("print json object"+jsonObject);
		}
		log("print the value of list : "+expectedClientTypes);
		log("print the value of list : "+actualClientTypeValues);
		CCUtils.assertEquals(actualClientTypeValues, expectedClientTypes);
	}

	public void verifyUsernameGeneratedUsingBeginningOfEmail(String email) {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);
		String[] strList = email.split("@");
		String username = strList[0];
		log("Verifying username is generated using beginning of email address");
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.click();
		CCUtils.assertEquals(textboxUserName.getAttribute("value").trim(), username);
		log("Usename text is " + textboxUserName.getAttribute("value").trim());
	}

	public void focusOnUserNameField() {
		CCUtils.focusOnElement(textboxUserName, driver);
	}

	public void focusOnEmailField() {
		CCUtils.focusOnElement(textboxEmail, driver);
	}

}
