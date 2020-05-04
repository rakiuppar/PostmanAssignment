/* @author Rakesh Mustoor
 * 
 */
package com.postman.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.postman.qa.base.Base;

public class LoginPage extends Base {

	@FindBy(id = "username")
	WebElement userName;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "sign-in-btn")
	WebElement signInButton;

	@FindBy(xpath = "//a[contains(text(),'Switch accounts')]")
	WebElement switchAccountsButton;

	@FindBy(xpath = "//*[contains(text(),'rakiuppar1@')] ")
	WebElement selectAccount;

	public void loadUrl(String url) {
		driver.get(prop.getProperty("url"));
	}

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	// Enter credentials and login to app
	public void login() {
		userName.sendKeys(prop.getProperty("userName"));
		password.sendKeys(prop.getProperty("password"));
		signInButton.click();
	}

	// Select the proper user to continue
	public void selectUser() {
		customWaitForElement(switchAccountsButton);
		switchAccountsButton.click();
		customWaitForElement(selectAccount);
		selectAccount.click();
	}

	// Close the Browser
	public void closeBrowser() {
		driver.quit();

	}

}
