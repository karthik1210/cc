// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.object.maps.pojo;

import java.util.Properties;

import lombok.Data;

@Data
public class CCUser {

	public Properties properties;

	public CCUser(Properties property) {
		properties = property;
	}

	String url;
	String username;
	String password;
	String invalidUsername;
	String invalidPassword;
	String loginAttempts;
	String lockoutUsername;
	String usernameAdmin;
	String passwordAdmin;
	String usernameExternal;
	String passwordExternal;
	String usernameInternal;
	String passwordInternal;
	String clientNode;
	String systemNode;
	String enterpriseNode;
	String practiceNode;
	String locationNode;
	String incorrectEmail;
	String externalUser;
	String name;
	String emailId;
	String resetEmail;
	String clientName;
	String unregisteredEmail;
	String invalidTokenUrl;
	String adminEmail;
	String navigationEmail;

	public String getProperty(String prop) throws Exception {
		if (properties.getProperty(prop) == null)
			throw new Exception("Property " + prop + " not found in the property file.");
		return properties.getProperty(prop);
	}

	public void setCCuser() throws Exception {
		setUrl(getProperty("base.url"));
		setUsername(getProperty("user.name"));
		setPassword(getProperty("user.password"));
		setClientNode(getProperty("search.client"));
		setSystemNode(getProperty("search.system"));
		setEnterpriseNode(getProperty("search.enterprise"));
		setPracticeNode(getProperty("search.practice"));
		setLocationNode(getProperty("search.location"));
		setEmailId(getProperty("reset.password.email"));
		setResetEmail(getProperty("reset.password.reset.email"));
		setClientName(getProperty("reset.password.client.name"));
		setUnregisteredEmail(getProperty("reset.password.unregistered.email"));
		setExternalUser(getProperty("reset.password.external.user"));
		setInvalidTokenUrl(getProperty("invalid.token.url"));
		setAdminEmail(getProperty("left.navigation.admin.email"));
		setNavigationEmail(getProperty("left.navigation.email"));
		setPasswordAdmin(getProperty("admin.password"));
	}

	public void setAdminCCWithWrongPassword() throws Exception {
		setUrl(getProperty("base.url"));
		setUsername(getProperty("user.name"));
		setInvalidPassword(getProperty("invalid.password"));
		setLoginAttempts(getProperty("login.attempts"));
	}

	public void setAdminCCWithWrongUsername() throws Exception {
		setUrl(getProperty("base.url"));
		setInvalidUsername(getProperty("invalid.user.name"));
		setPassword(getProperty("user.password"));
		setLoginAttempts(getProperty("login.attempts"));
	}

	public void setCCAdminUser() throws Exception {
		setUrl(getProperty("base.url"));
		setUsernameAdmin(getProperty("admin.username"));
		setPasswordAdmin(getProperty("admin.password"));
	}

	public void setCCExternalUser() throws Exception {
		setUrl(getProperty("base.url"));
		setUsernameExternal(getProperty("external.user.name"));
		setPasswordExternal(getProperty("external.password"));
	}

	public void setCCInternalUser() throws Exception {
		setUrl(getProperty("base.url"));
		setUsernameInternal(getProperty("internal.user.name"));
		setPasswordInternal(getProperty("internal.password"));
	}

	public void setAdminCCWithLockoutUsername() throws Exception {
		setUrl(getProperty("base.url"));
		setLockoutUsername(getProperty("lockout.user.name"));
		setInvalidPassword(getProperty("invalid.password"));
		setLoginAttempts(getProperty("login.attempts"));
	}

}
