/* @author Rakesh Mustoor
 * 
 */
package com.postman.qa.pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.postman.qa.base.Base;

public class HomePage extends Base {
	public static int randomNumber;

	@FindBy(xpath = "//a[@class='pm-header__logo']")
	WebElement workspacesIcon;

	@FindBy(xpath = "//span[@class='pm-ws-switcher__ws-text']")
	WebElement workspacesDropDown;

	@FindBy(xpath = "//a[@href='/workspaces/create']")
	WebElement createNewWorkspaceInDropDownMenu;

	@FindBy(xpath = "//a[@class='pm-link pm-link--manage-workspace']")
	WebElement allWorkspacesLink;

	@FindBy(xpath = "//input[@placeholder=\"Search for a workspace\"]")
	WebElement workspaceSearchBox;

	// This must be used as Custom xpath- have to split based on actual value
	@FindBy(xpath = "//a[contains(text(),'aaa')]")
	WebElement searchResult;

	@FindBy(xpath = "//button[contains(text(),'Create a new workspace')]")
	WebElement createNewWorkSpace;

	@FindBy(id = "ws-name")
	WebElement workspaceNameTextBox;

	@FindBy(xpath = "//textarea[@class=\"pm-form-control pm-form-control-undefined\"]")
	WebElement workspaceSummaryTextArea;

	@FindBy(xpath = "//button[contains(text(),'Create Workspace')]")
	WebElement createWorkspace;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	WebElement cancelButton;

	// Success Message
	@FindBy(xpath = "//*[@class='pm-h4 pm-toast-title']")
	WebElement successMessage;

	@FindBy(xpath = "//span[@class=\"pm-ws-switcher__ws-name\"]")
	WebElement switchWorkspaceLink;

	// This must be used as Custom xpath- have to split based on actual value
	@FindBy(xpath = "//a[@title='qqq']")
	WebElement workspaceList;

	// This must be used as Custom xpath- have to split based on actual value
	@FindBy(xpath = "//a[@title='qw']/../../following::button[2]")
	WebElement optionsMenu;

	@FindBy(xpath = "//div[contains(text(),'View Details')]")
	WebElement viewDetails;

	@FindBy(xpath = "//h3[@class='pm-h3 workspace-details__title']")
	WebElement viewDetailsWSTitle;

	@FindBy(xpath = "//div[@class='workspace-details__meta-container']//span[1]")
	WebElement viewDetailsUserInfo;

	@FindBy(xpath = "//div[contains(text(),'Rename')]")
	WebElement renameOption;

	@FindBy(xpath = "//button[contains(text(),'Save Changes')]")
	WebElement saveChangesButton;

	@FindBy(xpath = "//div[contains(text(),'Edit Description')]")
	WebElement editDescription;

	@FindBy(xpath = "//div[contains(text(),'Manage Members')]")
	WebElement manageMembers;

	@FindBy(xpath = "//div[contains(text(),'Delete')]")
	WebElement deleteOption;

	@FindBy(xpath = "//button[contains(text(),'Delete')]")
	WebElement deleteButton;

	@FindBy(xpath = "//button[contains(text(),'Create an API')]")
	WebElement createAPIButton;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	// -------- Methods for the home page element --------
	// Clicks on Create workspace by adding unique random number
	public void clickOnCreateNewWS() {
		customWaitForElement(createNewWorkSpace);
		createNewWorkSpace.click();
		generateRandomNumber();
	}

	// Clicks on Create workspace
	public void createNewWSForDuplicateWS() {
		customWaitForElement(createNewWorkSpace);
		createNewWorkSpace.click();
	}

	// Input workspace details
	public void enterWSDetails(String wsName, String wsDescription) {
		customWaitForElement(workspaceNameTextBox);
		workspaceNameTextBox.clear();
		workspaceNameTextBox.sendKeys(wsName + randomNumber);
		workspaceSummaryTextArea.clear();
		workspaceSummaryTextArea.sendKeys(wsDescription + randomNumber);
	}

	// Clicks on Create Workspace button
	public void clickOnCreateButton() {
		customWaitForElement(createWorkspace);
		createWorkspace.click();
	}

	// Clicks on Cancel button
	public void clickOnCancelButton() {
		customWaitForElement(cancelButton);
		cancelButton.click();
	}

	// get success message title
	public String getSuccessMessageTitle() {
		customWaitForElement(successMessage);
		return successMessage.getText();
	}

	// Navigate to All Workspaces
	public void navigateToAllWS() throws InterruptedException {
		Thread.sleep(2000);
		customWaitForElement(workspacesIcon);
		workspacesIcon.click();
	}

	// Verifies Workspace displayed under the dashboard
	public boolean verifyWorkspaceInList(String wsName) throws InterruptedException {
		String xpath = "//a[@title='" + wsName + randomNumber + "']";
		return verifyElementPresent(xpath);
	}

	// Navigate to Workspace options. This also handles Stale Element Exception
	public void clickOnWSOptions(String ws) throws InterruptedException {
		String xpath = "//a[@title='" + ws + randomNumber + "']/../../following::button[2]";
		checkAndClickOnElement(xpath);
	}

	// CLick on Default Workspace option
	public void clickOnDefaultWSOptions(String ws) throws InterruptedException {
		String xpath = "//a[@title='" + ws + "']/../../following::button[2]";
		checkAndClickOnElement(xpath);
	}

	// click on Rename WS option
	public void clickRenameWS() {
		customWaitForElement(renameOption);
		renameOption.click();
	}

	// click on save changes button
	public void clickSaveChanges() {
		customWaitForElement(saveChangesButton);
		saveChangesButton.click();
	}

	// click on Delete WS option
	public void clickDeleteWS() {
		customWaitForElement(deleteOption);
		deleteOption.click();
	}

	// click on Delete confirm button
	public void clickDeleteConfirmButton() {
		customWaitForElement(deleteButton);
		deleteButton.click();
	}

	// click on cancel button on any window
	public void clickCancelButton() {
		customWaitForElement(cancelButton);
		cancelButton.click();
	}

	// Click on workspaces drop down
	public void clickOnWSDropDownMenu() {
		customWaitForElement(workspacesDropDown);
		workspacesDropDown.click();
	}

	// Click on Create workspace
	public void clickOnCreateNewWSUnderDropDown() {
		customWaitForElement(createNewWorkspaceInDropDownMenu);
		createNewWorkspaceInDropDownMenu.click();
		generateRandomNumber();
	}

	// Search the input under workspaces drop down
	public void searchInWSDropDownMenu(String wsName) throws InterruptedException {
		customWaitForElement(workspaceSearchBox);
		workspaceSearchBox.clear();
		workspaceSearchBox.sendKeys(wsName + randomNumber);
		Thread.sleep(1000);
	}

	// Verify result for WS searched
	public boolean checkForWSInSearchResult(String wsName) {
		String xpath = "//a[contains(text(),'" + wsName + randomNumber + "')]";
		return verifyElementPresent(xpath);
	}

	// Selected the WS from the search resultS
	public void clickOnWSInSearchResult(String wsName) {
		String xpath = "//a[contains(text(),'" + wsName + randomNumber + "')]";
		driver.findElement(By.xpath(xpath)).click();
	}

	// Checks for the API button present
	public boolean checkAPIButtonPresent() {
		return verifyElementPresent(createAPIButton);
	}

	// Checks for the WS name is duplicate or not
	public boolean checkDuplicateWSPresent(String wsName) {
		String xpath = "//a[@title='" + wsName + randomNumber + "']";
		return duplicateElementCheck(xpath);
	}

	// Checks for the dlete button disabled
	public boolean isDeleteDisabled() {
		return !isElementEnabled(deleteButton);
	}

	// Get Delete tool tip
	public String deleteToolTip() {
		return deleteButton.getAttribute("title");
	}

	// ------- General methods -------
	// Generates a random int value
	public static void generateRandomNumber() {
		Random random = new Random();
		randomNumber = random.nextInt(999);
	}

	// Verifies element exists by taking custom xpath and returns boolean
	public boolean verifyElementPresent(String xpath) {
		try {
			if (driver.findElement(By.xpath(xpath)) != null)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	// Verifies element exists by taking Web element as input and returns boolean
	public boolean verifyElementPresent(WebElement element) {
		try {
			if (element != null)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	// Checks and returns true if duplicate elements exists
	public boolean duplicateElementCheck(String xpath) {
		try {
			List<WebElement> eles = driver.findElements(By.xpath(xpath));
			int count = eles.size();
			if (count > 1)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	// Find element and click once stale element exception is gone
	public void checkAndClickOnElement(String xpath) throws InterruptedException {
		int counter = 0;
		while (counter == 0) {
			Thread.sleep(2000);
			try {
				if (driver.findElement(By.xpath(xpath)).isDisplayed()
						&& driver.findElement(By.xpath(xpath)).isEnabled())
					counter++;
			} catch (Exception e) {
				driver.navigate().refresh();
			}
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(2000);
	}

	// Check element is enabled or disabled
	public boolean isElementEnabled(WebElement element) {
		if (element.isEnabled())
			return true;
		return false;
	}

}
