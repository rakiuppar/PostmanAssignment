package com.postman.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.postman.qa.base.Base;
import com.postman.qa.pages.HomePage;
import com.postman.qa.pages.LoginPage;

public class PersonalWorkspaceTests extends Base {
	private static final String EXPECTED_PAGE_TITLE = "Postman Dashboard";
	private static final String EXPECTED_MESSAGE_WS_CREATED = "Personal workspace created";
	private static final String EXPECTED_MESSAGE_WS_UPDATED = "Workspace details updated";
	private static final String EXPECTED_MESSAGE_WS_DELETED = "Personal workspace deleted";
	private static final String OUT_INCORRECT_TITLE = "Title is not expected";
	private static final String OUT_INCORRECT_EXPECTED_MESSAGE = "Success Message is not as expected";
	private static final String OUT_UNSUCCESSFUL_CANCEL_OPERATION = "Cancel operation is unsuccessful";
	private static final String OUT_WORKSPACE_MISSING_DASHBOARD = "New Workspace created does not exist on the Dashboard";
	private static final String OUT_WORKSPACE_MISSING_SEARCHLIST = "New Workspace created does not exist in Search result";
	private static final String OUT_WORKSPACE_PRESENT_DASHBOARD = "Workspace still exist on the Dashboard";
	private static final String OUT_OUSIDE_WORKSPACE = "User is not inside the Workspace selected";
	private static final String OUT_BUTTON_ENABLED = "Button is enabled";

	LoginPage loginPage;
	HomePage homePage;

	public PersonalWorkspaceTests() {
		super();
	}

	@BeforeTest
	public void setup() {
		initialization();
		homePage = new HomePage();
		loginPage = new LoginPage();
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}

	// Log in to application
	@Test(priority = 0)
	public void loginToApplication() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		loginPage.login();
		loginPage.selectUser();
		softAssert.assertEquals(getTitle(), EXPECTED_PAGE_TITLE, OUT_INCORRECT_TITLE);
		softAssert.assertAll();
	}

	// Create WS from dash board and verify the new WS exists in the list
	@Test(priority = 1)
	public void createWorkspaceFromDashboard() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.clickOnCreateNewWS();
		homePage.enterWSDetails(prop.getProperty("wsName"), prop.getProperty("wsDescription"));
		homePage.clickOnCreateButton();
		softAssert.assertEquals(homePage.getSuccessMessageTitle(), EXPECTED_MESSAGE_WS_CREATED,
				OUT_INCORRECT_EXPECTED_MESSAGE);
		homePage.navigateToAllWS();
		softAssert.assertTrue(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_WORKSPACE_MISSING_DASHBOARD);
		softAssert.assertAll();
	}

	// Read and Update the WS created
	@Test(priority = 2)
	public void updateWorkspaceCreated() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnWSOptions(prop.getProperty("wsName"));
		homePage.clickRenameWS();
		homePage.enterWSDetails(prop.getProperty("updatedWSName"), prop.getProperty("updatedWSDescription"));
		homePage.clickSaveChanges();
		softAssert.assertEquals(homePage.getSuccessMessageTitle(), EXPECTED_MESSAGE_WS_UPDATED,
				OUT_INCORRECT_EXPECTED_MESSAGE);
		homePage.navigateToAllWS();
		softAssert.assertTrue(homePage.verifyWorkspaceInList(prop.getProperty("updatedWSName")),
				OUT_WORKSPACE_MISSING_DASHBOARD);
		softAssert.assertAll();
	}

	// Read and Delete the WS created
	@Test(priority = 3)
	public void deleteWorkspaceCreated() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnWSOptions(prop.getProperty("updatedWSName"));
		homePage.clickDeleteWS();
		homePage.clickDeleteConfirmButton();
		softAssert.assertEquals(homePage.getSuccessMessageTitle(), EXPECTED_MESSAGE_WS_DELETED,
				OUT_INCORRECT_EXPECTED_MESSAGE);
		homePage.navigateToAllWS();
		// Script will halt for few seconds since it will be trying to find a WS which
		// doesn't exists.
		softAssert.assertFalse(homePage.verifyWorkspaceInList(prop.getProperty("updatedWSName")),
				OUT_WORKSPACE_PRESENT_DASHBOARD);
		softAssert.assertAll();
	}

	// Create the Workspace from drop down Menu and verify the new WS exists in the
	// list
	@Test(priority = 4)
	public void createWorkspaceFromDropDown() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.clickOnWSDropDownMenu();
		homePage.clickOnCreateNewWSUnderDropDown();
		homePage.enterWSDetails(prop.getProperty("wsName"), prop.getProperty("wsDescription"));
		homePage.clickOnCreateButton();
		softAssert.assertEquals(homePage.getSuccessMessageTitle(), EXPECTED_MESSAGE_WS_CREATED,
				OUT_INCORRECT_EXPECTED_MESSAGE);
		homePage.navigateToAllWS();
		softAssert.assertTrue(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_WORKSPACE_MISSING_DASHBOARD);
		softAssert.assertAll();
	}

	// Search and select the Workspace from drop down search box
	@Test(priority = 5)
	public void searchWorkspaceFromDropDown() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.clickOnWSDropDownMenu();
		homePage.searchInWSDropDownMenu(prop.getProperty("wsName"));
		Assert.assertTrue(homePage.checkForWSInSearchResult(prop.getProperty("wsName")),
				OUT_WORKSPACE_MISSING_SEARCHLIST);
		homePage.clickOnWSInSearchResult(prop.getProperty("wsName"));
		softAssert.assertTrue(homePage.checkAPIButtonPresent(), OUT_OUSIDE_WORKSPACE);
		softAssert.assertAll();
	}

	// Create the Duplicate Workspace with same name
	@Test(priority = 6)
	public void createDuplicateWorkspace() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.createNewWSForDuplicateWS();
		homePage.enterWSDetails(prop.getProperty("wsName"), prop.getProperty("wsDescription"));
		homePage.clickOnCreateButton();
		homePage.navigateToAllWS();
		softAssert.assertTrue(homePage.checkDuplicateWSPresent(prop.getProperty("wsName")),
				OUT_WORKSPACE_MISSING_DASHBOARD);
		softAssert.assertAll();
	}

	// Read and and try to Delete the Default WS
	@Test(priority = 7)
	public void deleteDefaultWorkspaceCreated() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnDefaultWSOptions(prop.getProperty("defaultWSName"));
		homePage.clickDeleteWS();
		softAssert.assertTrue(homePage.isDeleteDisabled(), OUT_BUTTON_ENABLED);
		homePage.clickOnCancelButton();
		softAssert.assertAll();
	}

	// Cancel the Create Workspace action
	@Test(priority = 8)
	public void cancelWSCreation() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnCreateNewWS();
		homePage.enterWSDetails(prop.getProperty("wsName"), prop.getProperty("wsDescription"));
		homePage.clickOnCancelButton();
		// Script will halt for few seconds since it will be trying to find a WS which
		// doesn't exists.
		softAssert.assertFalse(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_UNSUCCESSFUL_CANCEL_OPERATION);
		homePage.clickOnCreateNewWS();
		homePage.enterWSDetails(prop.getProperty("wsName"), prop.getProperty("wsDescription"));
		homePage.clickOnCreateButton();
		softAssert.assertAll();
	}

	// Cancel the Update Workspace action
	@Test(priority = 9)
	public void cancelWSUpdation() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnWSOptions(prop.getProperty("wsName"));
		homePage.clickRenameWS();
		homePage.enterWSDetails(prop.getProperty("updatedWSName"), prop.getProperty("updatedWSDescription"));
		homePage.clickOnCancelButton();
		softAssert.assertTrue(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_UNSUCCESSFUL_CANCEL_OPERATION);
		softAssert.assertAll();
	}

	// Cancel the Delete Workspace action
	@Test(priority = 10)
	public void cancelWSDeletion() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		homePage.navigateToAllWS();
		homePage.clickOnWSOptions(prop.getProperty("wsName"));
		homePage.clickDeleteWS();
		homePage.clickOnCancelButton();
		softAssert.assertTrue(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_UNSUCCESSFUL_CANCEL_OPERATION);
		homePage.clickOnWSOptions(prop.getProperty("wsName"));
		homePage.clickDeleteWS();
		homePage.clickDeleteConfirmButton();
		homePage.navigateToAllWS();
		// Script will halt for few seconds since it will be trying to find a WS which
		// doesn't exists.
		softAssert.assertFalse(homePage.verifyWorkspaceInList(prop.getProperty("wsName")),
				OUT_WORKSPACE_PRESENT_DASHBOARD);
		softAssert.assertAll();
	}

}
