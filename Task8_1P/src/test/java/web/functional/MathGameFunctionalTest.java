package web.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Functional tests for the Math Game application.
 * These tests verify the application flow from login to completing all math questions.
 * 
 * Note: To run these tests, you need to:
 * 1. Have Chrome browser installed
 * 2. Download ChromeDriver that matches your Chrome version from https://chromedriver.chromium.org/downloads
 * 3. Set the path to the ChromeDriver executable in the setUp() method
 * 4. Have the application running on localhost:8080
 */
public class MathGameFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "http://localhost:8080";
    
    private boolean isServerRunning() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds timeout
            connection.connect();
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode == 200;
        } catch (Exception e) {
            System.out.println("Server is not running: " + e.getMessage());
            return false;
        }
    }
    
    @Before
    public void setUp() {
        // Check if server is running, skip tests if not
        boolean serverRunning = isServerRunning();
        Assume.assumeTrue("Server is not running. Please start the server before running these tests.", serverRunning);
        
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/farid/chromedriver");
        
        // Configure Chrome to run in headless mode (no UI)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10); // 10 seconds timeout
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Test the complete happy path flow:
     * 1. Navigate to the welcome page
     * 2. Click on the login link
     * 3. Login with valid credentials
     * 4. Answer Question 1 correctly
     * 5. Answer Question 2 correctly
     * 6. Answer Question 3 correctly
     * 7. Verify completion message
     */
    @Test
    public void testCompleteHappyPathFlow() {
        // Navigate to welcome page
        driver.get(BASE_URL);
        
        // Click on login link
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
        loginLink.click();
        
        // Verify we're on the login page by looking for the login form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@action='/login']")));
        
        // Login with valid credentials
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement dobField = driver.findElement(By.id("dob"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        usernameField.sendKeys("ahsan");
        passwordField.sendKeys("ahsan_pass");
        dobField.sendKeys("2000-01-01"); // Any date works as it's not validated
        loginButton.click();
        
        // Verify we're on Question 1 page
        wait.until(ExpectedConditions.titleContains("Q1"));
        
        // Answer Question 1 correctly
        WebElement number1Field = driver.findElement(By.id("number1"));
        WebElement number2Field = driver.findElement(By.id("number2"));
        WebElement resultField = driver.findElement(By.id("result"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("5");
        number2Field.sendKeys("3");
        resultField.sendKeys("8"); // 5 + 3 = 8
        submitButton.click();
        
        // Verify we're on Question 2 page
        wait.until(ExpectedConditions.titleContains("Q2"));
        
        // Answer Question 2 correctly
        number1Field = driver.findElement(By.id("number1"));
        number2Field = driver.findElement(By.id("number2"));
        resultField = driver.findElement(By.id("result"));
        submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("10");
        number2Field.sendKeys("4");
        resultField.sendKeys("6"); // 10 - 4 = 6
        submitButton.click();
        
        // Verify we're on Question 3 page
        wait.until(ExpectedConditions.titleContains("Q3"));
        
        // Answer Question 3 correctly
        number1Field = driver.findElement(By.id("number1"));
        number2Field = driver.findElement(By.id("number2"));
        resultField = driver.findElement(By.id("result"));
        submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("6");
        number2Field.sendKeys("7");
        resultField.sendKeys("42"); // 6 * 7 = 42
        submitButton.click();
        
        // Verify completion message
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Congratulations')]"))); 
        Assert.assertTrue(message.getText().contains("Congratulations"));
    }
    
    /**
     * Test invalid login credentials
     */
    @Test
    public void testInvalidLogin() {
        // Navigate to login page
        driver.get(BASE_URL + "/login");
        
        // Login with invalid credentials
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement dobField = driver.findElement(By.id("dob"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        usernameField.sendKeys("wrong");
        passwordField.sendKeys("wrong");
        dobField.sendKeys("2000-01-01");
        loginButton.click();
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Incorrect credentials')]"))); 
        Assert.assertTrue(errorMessage.getText().contains("Incorrect credentials"));
    }
    
    /**
     * Test wrong answer for Question 1
     */
    @Test
    public void testWrongAnswerQ1() {
        // Login first
        driver.get(BASE_URL + "/login");
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement dobField = driver.findElement(By.id("dob"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        usernameField.sendKeys("ahsan");
        passwordField.sendKeys("ahsan_pass");
        dobField.sendKeys("2000-01-01");
        loginButton.click();
        
        // Verify we're on Question 1 page
        wait.until(ExpectedConditions.titleContains("Q1"));
        
        // Answer Question 1 incorrectly
        WebElement number1Field = driver.findElement(By.id("number1"));
        WebElement number2Field = driver.findElement(By.id("number2"));
        WebElement resultField = driver.findElement(By.id("result"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("5");
        number2Field.sendKeys("3");
        resultField.sendKeys("9"); // Wrong answer: 5 + 3 = 8, not 9
        submitButton.click();
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Wrong answer')]"))); 
        Assert.assertTrue(errorMessage.getText().contains("Wrong answer"));
    }
    
    /**
     * Test invalid input for Question 2
     */
    @Test
    public void testInvalidInputQ2() {
        // Login and navigate to Q2
        driver.get(BASE_URL + "/login");
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement dobField = driver.findElement(By.id("dob"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        usernameField.sendKeys("ahsan");
        passwordField.sendKeys("ahsan_pass");
        dobField.sendKeys("2000-01-01");
        loginButton.click();
        
        // Answer Q1 correctly to get to Q2
        wait.until(ExpectedConditions.titleContains("Q1"));
        WebElement number1Field = driver.findElement(By.id("number1"));
        WebElement number2Field = driver.findElement(By.id("number2"));
        WebElement resultField = driver.findElement(By.id("result"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("5");
        number2Field.sendKeys("3");
        resultField.sendKeys("8");
        submitButton.click();
        
        // Verify we're on Question 2 page
        wait.until(ExpectedConditions.titleContains("Q2"));
        
        // Enter invalid input for Question 2
        number1Field = driver.findElement(By.id("number1"));
        number2Field = driver.findElement(By.id("number2"));
        resultField = driver.findElement(By.id("result"));
        submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("10");
        number2Field.sendKeys("4");
        resultField.sendKeys("abc"); // Invalid input
        submitButton.click();
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'valid number')]"))); 
        Assert.assertTrue(errorMessage.getText().contains("valid number"));
    }
    
    /**
     * Test empty input for Question 3
     */
    @Test
    public void testEmptyInputQ3() {
        // Login and navigate through Q1 and Q2 to get to Q3
        driver.get(BASE_URL + "/login");
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement dobField = driver.findElement(By.id("dob"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        usernameField.sendKeys("ahsan");
        passwordField.sendKeys("ahsan_pass");
        dobField.sendKeys("2000-01-01");
        loginButton.click();
        
        // Answer Q1 correctly
        wait.until(ExpectedConditions.titleContains("Q1"));
        WebElement number1Field = driver.findElement(By.id("number1"));
        WebElement number2Field = driver.findElement(By.id("number2"));
        WebElement resultField = driver.findElement(By.id("result"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("5");
        number2Field.sendKeys("3");
        resultField.sendKeys("8");
        submitButton.click();
        
        // Answer Q2 correctly
        wait.until(ExpectedConditions.titleContains("Q2"));
        number1Field = driver.findElement(By.id("number1"));
        number2Field = driver.findElement(By.id("number2"));
        resultField = driver.findElement(By.id("result"));
        submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        number1Field.sendKeys("10");
        number2Field.sendKeys("4");
        resultField.sendKeys("6");
        submitButton.click();
        
        // Verify we're on Question 3 page
        wait.until(ExpectedConditions.titleContains("Q3"));
        
        // Submit with empty inputs
        number1Field = driver.findElement(By.id("number1"));
        number2Field = driver.findElement(By.id("number2"));
        resultField = driver.findElement(By.id("result"));
        submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Leave fields empty
        submitButton.click();
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'valid numbers')]"))); 
        Assert.assertTrue(errorMessage.getText().contains("valid numbers"));
    }
}
