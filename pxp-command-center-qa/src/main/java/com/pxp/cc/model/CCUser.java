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
	private String authBaseUrl;
	private String serviceBaseUrl;
	private String commandCenterUser;
	private String commandCenterPassword;
	private String queryFullClientName;
	private String queryPartialClientName;
	private String queryFullCommandCenterId;
    private String queryPartialCommandCenterId;
	private String queryFullSystemName;
	private String queryPartialSystemName;  
	private String queryFullEnterpriseName;
	private String queryPartialEnterpriseName;
	private String queryFullPracticeName;
	private String queryPartialPracticeName;    
	private String page;
	private String pageSize1; 
	private String pageSize2; 		
	private String pageSize3;
	private String pageSize4;
	private String pageSize5;
    private String type; 
    private String clientonetype;
    private String clienttwotype;
    private String clientthreetype;
    private String clientfourtype;
    private String clientfivetype;
    private String clientsixtype;
    private String clientseventype;
    private String clienteighttype;
    private String clientninetype;
    private String clienttentype;
    private String clienteleventype;
    private String clienttwelvetype;
    private String clientthirteentype;
    private String clientforteentype;
    private String clientfifteentype;
    private String nonexistingclient;
    private String nonexistingccid;
    private String nonexistingsystem;
    private String nonexistingenterprise;
    private String nonexistingpractice;

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
		setQueryFullClientName(getProperty("query.full.client.name"));
		setQueryPartialClientName(getProperty("query.partial.client.name"));
		setQueryFullCommandCenterId(getProperty("query.full.command.center.id"));
		setQueryPartialCommandCenterId(getProperty("query.partial.command.center.id"));
		setQueryFullSystemName(getProperty("query.full.system.name"));
		setQueryPartialSystemName(getProperty("query.partial.system.name"));
		setQueryFullEnterpriseName(getProperty("query.full.enterprise.name"));
		setQueryPartialEnterpriseName(getProperty("query.partial.enterprise.name"));
		setQueryFullPracticeName(getProperty("query.full.practice.name"));
		setQueryPartialPracticeName(getProperty("query.partial.practice.name"));
		setPage(getProperty("page"));
		setPageSize1(getProperty("pageSize1"));
		setPageSize2(getProperty("pageSize2"));
		setPageSize3(getProperty("pageSize3"));
		setPageSize4(getProperty("pageSize4"));
		setPageSize5(getProperty("pageSize5"));
		setClientonetype(getProperty("client.one.type"));
		setClienttwotype(getProperty("client.two.type"));
		setClientthreetype(getProperty("client.three.type"));
		setClientfourtype(getProperty("client.four.type"));
		setClientfivetype(getProperty("client.five.type"));
		setClientsixtype(getProperty("client.six.type"));
		setClientseventype(getProperty("client.seven.type"));
		setClienteighttype(getProperty("client.eight.type"));
		setClientninetype(getProperty("client.nine.type"));
		setClienttentype(getProperty("client.ten.type"));
		setClienteleventype(getProperty("client.eleven.type"));
		setClienttwelvetype(getProperty("client.twelve.type"));
		setClientthirteentype(getProperty("client.thirteen.type"));
		setClientforteentype(getProperty("client.forteen.type"));
		setClientfifteentype(getProperty("client.fifteen.type"));
		setNonexistingclient(getProperty("nonexisting.full.client.name"));
		setNonexistingccid(getProperty("nonexisting.full.command.center.id"));
		setNonexistingsystem(getProperty("nonexisting.full.system.name"));
		setNonexistingenterprise(getProperty("nonexisting.full.enterprise.name"));
		setNonexistingpractice(getProperty("nonexisting.full.practice.name"));
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
