/* @author Rakesh Mustoor
 * 
 */
package com.postman.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base 
{
	public static WebDriver driver;
	public static Properties prop;
	
	public Base()
	{
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/postman/qa/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void initialization()
	{
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "BrowserDrivers\\chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "BrowserDrivers\\geckodriver.exe");	
			driver = new FirefoxDriver(); 
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
	}
	
	public static void customWaitForElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	public static String getTitle()
	{
		return driver.getTitle();
	}
	

}


