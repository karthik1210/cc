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
	public String authBaseUrl;
	public String serviceBaseUrl;
	public String commandCenterUser;
	public String commandCenterPassword;
	public String queryFullClientName;
	public String queryPartialClientName;
	public String queryFullCommandCenterId;
	public String queryPartialCommandCenterId;
	public String queryFullSystemName;
	public String queryPartialSystemName;
	public String queryFullEnterpriseName;
	public String queryPartialEnterpriseName;
	public String queryFullPracticeName;
	public String queryPartialPracticeName;
	public String page;
	public String pageSizeOne;
	public String pageSizeTwo;
	public String pageSizeThree;
	public String pageSizeFour;
	public String pageSizeFive;
	public String type;
	public String clientOneType;
	public String clientTwoType;
	public String clientThreeType;
	public String clientFourType;
	public String clientFiveType;
	public String clientSixType;
	public String clientSevenType;
	public String clientEightType;
	public String clientNineType;
	public String clientTenType;
	public String clientElevenType;
	public String clientTwelveType;
	public String clientThirteenType;
	public String clientForteenType;
	public String clientFifteenType;
	public String nonexistingclient;
	public String nonexistingccid;
	public String nonexistingsystem;
	public String nonexistingenterprise;
	public String nonexistingpractice;
	public String selectClientAsFav;


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
		setAuthBaseUrl(getProperty("auth.baseurl"));
		setServiceBaseUrl(getProperty("service.baseurl"));
		setCommandCenterUser(getProperty("commandcenter.user"));
		setCommandCenterPassword(getProperty("commandcenter.password"));
		setQueryFullClientName(getProperty("api.query.full.client.name"));
		setQueryPartialClientName(getProperty("api.query.partial.client.name"));
		setQueryFullCommandCenterId(getProperty("api.query.full.command.center.id"));
		setQueryPartialCommandCenterId(getProperty("api.query.partial.command.center.id"));
		setQueryFullSystemName(getProperty("api.query.full.system.name"));
		setQueryPartialSystemName(getProperty("api.query.partial.system.name"));
		setQueryFullEnterpriseName(getProperty("api.query.full.enterprise.name"));
		setQueryPartialEnterpriseName(getProperty("api.query.partial.enterprise.name"));
		setQueryFullPracticeName(getProperty("api.query.full.practice.name"));
		setQueryPartialPracticeName(getProperty("api.query.partial.practice.name"));
		setPage(getProperty("api.page"));
		setPageSizeOne(getProperty("api.pageSize1"));
		setPageSizeTwo(getProperty("api.pageSize2"));
		setPageSizeThree(getProperty("api.pageSize3"));
		setPageSizeFour(getProperty("api.pageSize4"));
		setPageSizeFive(getProperty("api.pageSize5"));
		setClientOneType(getProperty("api.client.one.type"));
		setClientTwoType(getProperty("api.client.two.type"));
		setClientThreeType(getProperty("api.client.three.type"));
		setClientFourType(getProperty("api.client.four.type"));
		setClientFiveType(getProperty("api.client.five.type"));
		setClientSixType(getProperty("api.client.six.type"));
		setClientSevenType(getProperty("api.client.seven.type"));
		setClientEightType(getProperty("api.client.eight.type"));
		setClientNineType(getProperty("api.client.nine.type"));
		setClientTenType(getProperty("api.client.ten.type"));
		setClientElevenType(getProperty("api.client.eleven.type"));
		setClientTwelveType(getProperty("api.client.twelve.type"));
		setClientThirteenType(getProperty("api.client.thirteen.type"));
		setClientForteenType(getProperty("api.client.forteen.type"));
		setClientFifteenType(getProperty("api.client.fifteen.type"));
		setNonexistingclient(getProperty("api.nonexisting.full.client.name"));
		setNonexistingccid(getProperty("api.nonexisting.full.command.center.id"));
		setNonexistingsystem(getProperty("api.nonexisting.full.system.name"));
		setNonexistingenterprise(getProperty("api.nonexisting.full.enterprise.name"));
		setNonexistingpractice(getProperty("api.nonexisting.full.practice.name"));
		setSelectClientAsFav(getProperty("api.fav.clientid"));
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
