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
	public String clientonetype;
	public String clienttwotype;
	public String clientthreetype;
	public String clientfourtype;
	public String clientfivetype;
	public String clientsixtype;
	public String clientseventype;
	public String clienteighttype;
	public String clientninetype;
	public String clienttentype;
	public String clienteleventype;
	public String clienttwelvetype;
	public String clientthirteentype;
	public String clientforteentype;
	public String clientfifteentype;
	public String nonexistingclient;
	public String nonexistingccid;
	public String nonexistingsystem;
	public String nonexistingenterprise;
	public String nonexistingpractice;
	private String active;
	private String clientId;
	private String clientName;
	private String clientSapNumber;
	private String testClient;
	private String displayName;
	private String timezoneId;
	private String clientType;
	private Integer practiceCount;
	private String invalidClientName;
	private String invalidClientSapNumber;
	private String existingClientSapNumber;

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
		setClientonetype(getProperty("api.client.one.type"));
		setClienttwotype(getProperty("api.client.two.type"));
		setClientthreetype(getProperty("api.client.three.type"));
		setClientfourtype(getProperty("api.client.four.type"));
		setClientfivetype(getProperty("api.client.five.type"));
		setClientsixtype(getProperty("api.client.six.type"));
		setClientseventype(getProperty("api.client.seven.type"));
		setClienteighttype(getProperty("api.client.eight.type"));
		setClientninetype(getProperty("api.client.nine.type"));
		setClienttentype(getProperty("api.client.ten.type"));
		setClienteleventype(getProperty("api.client.eleven.type"));
		setClienttwelvetype(getProperty("api.client.twelve.type"));
		setClientthirteentype(getProperty("api.client.thirteen.type"));
		setClientforteentype(getProperty("api.client.forteen.type"));
		setClientfifteentype(getProperty("api.client.fifteen.type"));
		setNonexistingclient(getProperty("api.nonexisting.full.client.name"));
		setNonexistingccid(getProperty("api.nonexisting.full.command.center.id"));
		setNonexistingsystem(getProperty("api.nonexisting.full.system.name"));
		setNonexistingenterprise(getProperty("api.nonexisting.full.enterprise.name"));
		setNonexistingpractice(getProperty("api.nonexisting.full.practice.name"));
		setPracticeCount(Integer.parseInt(getProperty("api.client.practice.count")));
		setCreateClientApiTestData();
		setInvalidTestDataForClientNameAndSap();
		setExistingClientSapNumber(getProperty("api.existing.client.sapnumber"));
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

	public void setCreateClientApiTestData() throws Exception {
		setActive(getProperty("api.client.active"));
		setClientId(getProperty("api.client.id"));
		setClientName(getProperty("api.client.name"));
		setClientSapNumber(getProperty("api.client.sapnumber"));
		setTestClient(getProperty("api.client.testclient"));
		setClientType(getProperty("api.client.type"));
		setDisplayName(getProperty("api.client.displayname"));
		setTimezoneId(getProperty("api.client.timezoneid"));
	}

	public void setInvalidTestDataForClientNameAndSap() throws Exception {
		setInvalidClientName(getProperty("api.invalid.client.name"));
		setInvalidClientSapNumber(getProperty("api.invalid.client.sapnumber"));
	}
}
