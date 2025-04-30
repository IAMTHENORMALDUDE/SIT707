package web.service;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginServiceTest {
	
	private void sleep(long sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    // Update these paths as needed for your environment
    private static final String CHROME_DRIVER_PATH = "/Users/farid/chromedriver";
    private static final String LOGIN_HTML_PATH = "file:///Users/farid/Downloads/71P/pages/login.html";

    private WebDriver setupDriverAndNavigate() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.navigate().to(LOGIN_HTML_PATH);
        sleep(2);
        return driver;
    }

    private void fillLoginForm(WebDriver driver, String username, String password, String dob) {
        WebElement ele = driver.findElement(By.id("username"));
        ele.clear();
        ele.sendKeys(username);

        ele = driver.findElement(By.id("passwd"));
        ele.clear();
        ele.sendKeys(password);

        ele = driver.findElement(By.id("dob"));
        ele.clear();
        ele.sendKeys(dob);

        ele = driver.findElement(By.cssSelector("[type=submit]"));
        ele.submit();
        sleep(2);
    }

    private void assertTitle(WebDriver driver, String expectedTitle) {
        String title = driver.getTitle();
        System.out.println("Title: " + title);
        Assert.assertEquals(expectedTitle, title);
        driver.close();
    }

    @Test
    public void testLoginSuccess() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "ahsan", "ahsan_pass", "01-01-1990");
        assertTitle(driver, "success");
    }

    @Test
    public void testInvalidUsername() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "wronguser", "ahsan_pass", "1990-01-01");
        assertTitle(driver, "fail");
    }

    @Test
    public void testInvalidPassword() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "ahsan", "wrongpass", "1990-01-01");
        assertTitle(driver, "fail");
    }

    @Test
    public void testInvalidDob() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "ahsan", "ahsan_pass", "2000-12-31");
        assertTitle(driver, "fail");
    }

    @Test
    public void testAllFieldsInvalid() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "user", "pass", "2000-01-01");
        assertTitle(driver, "fail");
    }

    @Test
    public void testEmptyFields() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "", "", "");
        assertTitle(driver, "fail");
    }

    @Test
    public void testWrongDateFormat() {
        WebDriver driver = setupDriverAndNavigate();
        fillLoginForm(driver, "ahsan", "ahsan_pass", "01-01-1990");
        assertTitle(driver, "success");
    }
}
