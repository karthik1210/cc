//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddOrRemoveResourcesForAppointmentTypeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response addOrRemoveResourcesForAppointmentTypeApiResponse;
	List<ResourceDetails> resourceList;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid resource id in ResourceId field and verify response body")
	public void testResponseBodyForInvalidResourceId() throws IOException {
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("non.existing.resource.id")), true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "ERROR", "Invalid ResourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid practice id in practiceId field and verify response body")
	public void testResponseBodyForInvalidPracticeId() throws IOException {
		resourceList = Arrays.asList(
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true),
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.two")), false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.one")));
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "ERROR", "Invalid ResourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid appointment type id in appointmentTypeId field and verify response body")
	public void testResponseBodyForInvalidAppointmentTypeId() throws IOException {
		resourceList = Arrays.asList(
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true),
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.two")), false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.one")));
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "ERROR", "Invalid ResourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add/Remove single resource id for appointment type and verify response body")
	public void testAddOrRemoveResourcesFunctionalityUsingSingleResourcId() throws IOException {
		logStep("Add valid resource id for appointment type");
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.one")));
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "SUCCESS", null);

		logStep("Remove valid resource id for appointment type");
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "SUCCESS", null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add/Remove multiple resource id for appointment type and verify response body")
	public void testAddOrRemoveResourcesFunctionalityUsingMultipleResourcId() throws IOException {
		logStep("Add valid resource ids for appointment type");
		resourceList = Arrays.asList(
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true),
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.two")), true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.one")));
		resourceIds.add(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "SUCCESS", null);

		logStep("Remove valid resource ids for appointment type");
		resourceList = Arrays.asList(
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), false),
				new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.two")), false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);
		validation.verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(
				addOrRemoveResourcesForAppointmentTypeApiResponse, resourceIds, "SUCCESS", null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank list in payload and verify response body")
	public void testErrorMessageForBlankPayload() throws IOException {
		logStep("Add valid resource ids for appointment type");
		resourceList = Arrays.asList();
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(addOrRemoveResourcesForAppointmentTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST call instead of PUT and verify response body")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo
				.invalidMethodCallForAddOrRemoveResourcesForAppoinmentType(
						Integer.parseInt(testData.getProperty("valid.practice.id")),
						Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(
				addOrRemoveResourcesForAppointmentTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("valid.resource.id.one")), true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo
				.addOrRemoveResourcesForAppoinmentTypeUsingInvalidUrl(
						Integer.parseInt(testData.getProperty("valid.practice.id")),
						Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addOrRemoveResourcesForAppointmentTypeApiResponse);
	}

}
