package sit707_week2;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class BunningsLoginTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/amir/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1024, 768));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Helper method to perform login
    private void login(String email, String password) {
        driver.get("https://www.bunnings.com.au/login");
        // Wait for page to load (simple sleep, replace with WebDriverWait for production)
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // Locate input fields (update locators if needed)
        WebElement emailField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement signInButton = driver.findElement(By.id("login-submit"));

        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);

        signInButton.click();

        // Wait for response
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    public void testLogin_EmptyFields() {
        login("", "");
        // Expect error message or no redirect
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_InvalidEmail_ValidPassword() {
        login("invalid@email.com", "ValidPassword123!");
        // Expect error message or no redirect
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_ValidEmail_InvalidPassword() {
        login("testuser@email.com", "wrongpassword");
        // Expect error message or no redirect
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_ValidEmail_ValidPassword() {
        // Replace with real credentials if available, otherwise this will fail as expected
        login("s222470713@deakin.edu.au", "9PLnsAb#Q5$muBc");
        // Expect redirect to account page or dashboard
        Assert.assertFalse(driver.getCurrentUrl().contains("login"));
    }
}
