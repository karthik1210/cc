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
	public String usernameExternal;
	public String passwordExternal;
	public String usernameInternal;
	public String passwordInternal;
	public String externalUser;
	public String resetEmail;
	public String unregisteredEmail;
	public String invalidTokenUrl;
	public String mailinatorSpaceInPassword;
	public String mailinatorRepetitiveSequence;
	public String mailinatorOldPassword;
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
		setResetEmail(getProperty("reset.password.reset.email"));
		setMailinatorSpaceInPassword(getProperty("reset.password.space.in.password"));
		setMailinatorRepetitiveSequence(getProperty("reset.password.repetitive.sequence"));
		setMailinatorOldPassword(getProperty("reset.password.old.password"));
		setMailinatorInvalidToken(getProperty("reset.password.invalid.token"));
		setMailinatorCommonPassword(getProperty("reset.password.common.password"));
		setMailinatorMaximumCharacters(getProperty("reset.password.maximum.characters"));
		setUnregisteredEmail(getProperty("reset.password.unregistered.email"));
		setExternalUser(getProperty("reset.password.external.user"));
		setInvalidTokenUrl(getProperty("reset.password.invalid.token.url"));
		setInvalidUsername(getProperty("invalid.user.name"));
		setLockoutUsername(getProperty("lockout.user.name"));
		setInvalidPassword(getProperty("invalid.password"));
		setLoginAttempts(getProperty("login.attempts"));
		setUsernameExternal(getProperty("external.user.name"));
		setPasswordExternal(getProperty("external.password"));
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
