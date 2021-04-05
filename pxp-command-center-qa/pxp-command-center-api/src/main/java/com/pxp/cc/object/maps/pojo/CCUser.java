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
	String invalidpassword;
	String loginattempts;
	String lockoutusername;
	String usernameAdmin;
	String passwordAdmin;
	String usernameExternal;
	String passwordExternal;
	String usernameInternal;
	String passwordInternal;
	String clientnode;
	String systemnode;
	String enterprisenode;
	String practicenode;
	String locationnode;
	String incorrectEmail;
	String externalUser;
	String name;
	String emailID;
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
		setUrl(getProperty("commandcenterurl"));
		setUsername(getProperty("email"));
		setPassword(getProperty("password"));
		setClientnode(getProperty("searchclient"));
		setSystemnode(getProperty("searchsystem"));
		setEnterprisenode(getProperty("searchenterprise"));
		setPracticenode(getProperty("searchpractice"));
		setLocationnode(getProperty("searchlocation"));
		setEmailID(getProperty("emailID"));
		setResetEmail(getProperty("resetEmail"));
		setClientName(getProperty("clientName"));
		setUnregisteredEmail(getProperty("unregisteredEmail"));
		setExternalUser(getProperty("externalUser"));
		setInvalidTokenUrl(getProperty("invalidTokenUrl"));
		setAdminEmail(getProperty("adminEmail"));
		setNavigationEmail(getProperty("navigationEmail"));
		setPasswordAdmin(getProperty("passwordadmin"));
	}

	public void setAdminCCWithWrongPassword() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setUsername(getProperty("email"));
		setInvalidpassword(getProperty("invalidpassword"));
		setLoginattempts(getProperty("loginattempts"));
	}

	public void setAdminCCWithWrongUsername() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setInvalidUsername(getProperty("invalidusername"));
		setPassword(getProperty("password"));
		setLoginattempts(getProperty("loginattempts"));
	}

	public void setCCAdminUser() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setUsernameAdmin(getProperty("usernameadmin"));
		setPasswordAdmin(getProperty("passwordadmin"));
	}

	public void setCCExternalUser() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setUsernameExternal(getProperty("usernameexternal"));
		setPasswordExternal(getProperty("passwordexternal"));
	}

	public void setCCInternalUser() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setUsernameInternal(getProperty("usernameinternal"));
		setPasswordInternal(getProperty("passwordinternal"));
	}

	public void setAdminCCWithLockoutUsername() throws Exception {
		setUrl(getProperty("commandcenterurl"));
		setLockoutusername(getProperty("lockoutusername"));
		setInvalidpassword(getProperty("invalidpassword"));
		setLoginattempts(getProperty("loginattempts"));
	}

}
