// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.model;

import com.medfusion.common.utils.PropertyFileLoader;

import lombok.Data;

@Data
public class CCUser {

	private PropertyFileLoader properties;
	public String url;
	public String username;
	public String password;
	public String invalidUsername;
	public String invalidPassword;
	public String loginAttempts;
	public String lockoutUsername;
	public String usernameAdmin;
	public String passwordAdmin;
	public String usernameExternal;
	public String passwordExternal;
	public String usernameInternal;
	public String passwordInternal;
	public String incorrectEmail;
	public String externalUser;
	public String name;
	public String emailId;
	public String resetEmail;
	public String clientName;
	public String unregisteredEmail;
	public String invalidTokenUrl;
	public String adminEmail;
	public String navigationEmail;
	public String withUsername;
	public String mailinatorSpaceInPassword;
	public String mailinatorRepetitiveSequence;
	public String mailinatorOldPassword;
	public String mailinatorExpiredLink;
	public String mailinatorInvalidToken;
	public String mailinatorCommonPassword;
	public String mailinatorMaximumCharacters;
	public String newClientName;
	public String newClientSap;
	public String clientTimezone;
	public String duplicateClientName;
	public String duplicateClientSap;
	public String newTestClientName;
	public String newTestClientSap;

	public CCUser(PropertyFileLoader property) {
		properties = property;
	}

	public String getProperty(String prop) throws Exception {
		if (properties.getProperty(prop) == null)
			throw new Exception("Property " + prop + " not found in the property file.");
		return properties.getProperty(prop);
	}

	public void init() throws Exception {
		setUrl(getProperty("base.url"));
		setUsername(getProperty("user.name"));
		setPassword(getProperty("user.password"));
		setPasswordAdmin(getProperty("admin.password"));
		setResetEmail(getProperty("reset.password.resetEmail"));
		setMailinatorSpaceInPassword(getProperty("reset.password.SpaceInPassword"));
		setMailinatorRepetitiveSequence(getProperty("reset.password.RepetitiveSequence"));
		setMailinatorOldPassword(getProperty("reset.password.OldPassword"));
		setMailinatorCommonPassword(getProperty("reset.password.CommonPassword"));
		setMailinatorMaximumCharacters(getProperty("reset.password.MaximumCharacters"));
		setUnregisteredEmail(getProperty("reset.password.unregisteredEmail"));
		setExternalUser(getProperty("reset.password.externalUser"));
		setInvalidTokenUrl(getProperty("reset.password.invalidTokenUrl"));
	}

	public void setCCExternalUser() throws Exception {
		setUsernameExternal(getProperty("external.user.name"));
		setPasswordExternal(getProperty("external.password"));
	}

	public void setCCInternalUser() throws Exception {
		setUsernameInternal(getProperty("internal.user.name"));
		setPasswordInternal(getProperty("internal.password"));
	}

	public void setNewClient() throws Exception {
		setNewClientName(getProperty("new.client.name"));
		setNewClientSap(getProperty("new.client.sap"));
		setClientTimezone(getProperty("new.client.timezone"));
	}

	public void setDuplicateClientClient() throws Exception {
		setDuplicateClientName(getProperty("duplicate.client.name"));
		setDuplicateClientSap(getProperty("duplicate.client.sap"));
	}

	public void setTestClient() throws Exception {
		setNewTestClientName(getProperty("new.test.client.name"));
		setNewTestClientSap(getProperty("new.test.client.sap"));
		setClientTimezone(getProperty("new.client.timezone"));
	}

}
