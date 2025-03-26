package sit707_week2;

import java.io.*;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * @author Ahsan Habib
 */
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void officeworks_registration_page(String url) {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "/Users/farid/Downloads/chromedriver-mac-x64/chromedriver");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		/*
		 * How to identify a HTML input field -
		 * Step 1: Inspect the webpage, 
		 * Step 2: locate the input field, 
		 * Step 3: Find out how to identify it, by id/name/...
		 */
		
		// Find first input field which is firstname
		WebElement firstName = driver.findElement(By.id("firstname"));
		System.out.println("Found element: " + firstName);
		// Send first name
		firstName.sendKeys("Farid");
	
		
		/*
		 * Find following input fields and populate with values
		 */
		// Write code
		
		// Send last name
		WebElement lastName = driver.findElement(By.id("lastname"));
		System.out.println("Found element: " + lastName);
		lastName.sendKeys("Vazirnia");
		
		// Send phone number
		WebElement phoneNumber = driver.findElement(By.id("phoneNumber"));
		System.out.println("Found element: " + phoneNumber);
		phoneNumber.sendKeys("0412345678");
		
		// Send email
		WebElement email = driver.findElement(By.id("email"));
		System.out.println("Found element: " + email);
		email.sendKeys("faridvazirnia@gmail.com");

		// Send password
		WebElement password = driver.findElement(By.id("password"));
		System.out.println("Found element: " + password);
		password.sendKeys("fariiID@@99");
		
		WebElement confirmPassword = driver.findElement(By.id("confirmPassword"));
		System.out.println("Found element: " + confirmPassword);
		confirmPassword.sendKeys("fariiID@@999");
		
		
		/*
		 * Identify button 'Create account' and click to submit using Selenium API.
		 */
		// Write code
		WebElement button = driver.findElement(By.cssSelector("button[type=submit]"));
		System.out.println("Found element: " + button);
		button.click();
		
		/*
		 * Take screenshot using selenium API.
		 */
		try {
			File File = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(File, new File("officeworks.jpeg"));
		} catch (Exception e) {
			System.out.println("Error happpend while taking screenshot");
		}
		
		
		// Sleep a while
		sleep(2);
		
		// close chrome driver
		driver.close();	
	}
	
	public static void goodguys_registration_page(String url) {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "/Users/farid/Downloads/chromedriver-mac-x64/chromedriver");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		/*
		 * How to identify a HTML input field -
		 * Step 1: Inspect the webpage, 
		 * Step 2: locate the input field, 
		 * Step 3: Find out how to identify it, by id/name/...
		 */
		
		// Find first input field which is firstname
		WebElement firstName = driver.findElement(By.id("regAddForm_firstName"));
		System.out.println("Found element: " + firstName);
		// Send first name
		firstName.sendKeys("Farid");
	
		
		/*
		 * Find following input fields and populate with values
		 */
		// Write code
		
		// Send last name
		WebElement lastName = driver.findElement(By.id("regAddForm_lastName"));
		System.out.println("Found element: " + lastName);
		lastName.sendKeys("Vazirnia");
		
		// Send phone number
		WebElement address = driver.findElement(By.id("regAddForm_qasaddress"));
		System.out.println("Found element: " + address);
		address.sendKeys("70 Elgar Road, BURWOOD  VIC 3125");
		
		// Send phone number
		WebElement phoneNumber = driver.findElement(By.id("regAddForm_mobileNum"));
		System.out.println("Found element: " + phoneNumber);
		phoneNumber.sendKeys("0412345678");
		
		// Send email
		WebElement email = driver.findElement(By.id("regAddForm_email1"));
		System.out.println("Found element: " + email);
		email.sendKeys("faridvazirnia@gmail.com");
		
		

		// Send password
		WebElement password = driver.findElement(By.id("regAddForm_password"));
		System.out.println("Found element: " + password);
		password.sendKeys("fariiID@@99");
		
		WebElement confirmPassword = driver.findElement(By.id("regAddForm_passwordNew"));
		System.out.println("Found element: " + confirmPassword);
		confirmPassword.sendKeys("fariiID@@999");
		
		
		/*
		 * Identify the sign up form and submit it 
		 */
		// Write code
		WebElement form = driver.findElement(By.name("Register"));
		System.out.println("Found element: " + form);
		form.submit();
		
		/*
		 * Take screenshot using selenium API.
		 */
		try {
			File File = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(File, new File("goodguys.jpeg"));
		} catch (Exception e) {
			System.out.println("Error happpend while taking screenshot");
		}
		
		
		// Sleep a while
		sleep(2);
		
		// close chrome driver
		driver.close();	
	}
}
