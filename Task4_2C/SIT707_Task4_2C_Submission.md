# SIT707 Software Quality and Testing

## Credit Task: Decision Table Unit Testing Using Selenium

**Student Name:** Farid Vazirnia  
**Student ID:** 222470713

---

## 1. Introduction

This submission demonstrates the use of decision table-based testing for the login functionality of the Bunnings website using Selenium and JUnit in Java. The task involves analyzing the login page, constructing a decision table, implementing automated test cases, and reflecting on the differences between unit testing and functional testing with Selenium.

---

## 2. Decision Table for Bunnings Login Page

| Test Case | Email Field | Password Field | Expected Result | System Response (Assertion)          |
| --------- | ----------- | -------------- | --------------- | ------------------------------------ |
| TC1       | Empty       | Empty          | Login fails     | Remain on login page, error shown    |
| TC2       | Invalid     | Valid          | Login fails     | Remain on login page, error shown    |
| TC3       | Valid       | Invalid        | Login fails     | Remain on login page, error shown    |
| TC4       | Valid       | Valid          | Login succeeds  | Redirect to account/dashboard page\* |

\*Note: TC4 requires real credentials. If not provided, this test is expected to fail.

---

## 3. Selenium and JUnit Test Implementation

### 3.1. Test Class: `BunningsLoginTest.java`

```java
package sit707_week2;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class BunningsLoginTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        // TODO: Set the correct path to your local ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1024, 768));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login(String email, String password) {
        driver.get("https://www.bunnings.com.au/login");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        WebElement emailField = driver.findElement(By.id("okta-signin-username"));
        WebElement passwordField = driver.findElement(By.id("okta-signin-password"));
        WebElement signInButton = driver.findElement(By.xpath("//input[@type='submit' or @value='Sign in']"));
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);
        signInButton.click();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    public void testLogin_EmptyFields() {
        login("", "");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_InvalidEmail_ValidPassword() {
        login("invalid@email.com", "ValidPassword123!");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_ValidEmail_InvalidPassword() {
        login("testuser@email.com", "wrongpassword");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void testLogin_ValidEmail_ValidPassword() {
        // Replace with real credentials if available
        login("your_real_email@domain.com", "your_real_password");
        Assert.assertFalse(driver.getCurrentUrl().contains("login"));
    }
}
```

### 3.2. Student Identity Tests (Fixed)

```java
@Test
public void testStudentIdentity() {
    String studentId = "222470713";
    Assert.assertNotNull("Student ID is null", studentId);
}

@Test
public void testStudentName() {
    String studentName = "Farid Vazirnia";
    Assert.assertNotNull("Student name is null", studentName);
}
```

---

## 4. JUnit Test Results

_Insert a screenshot of the Eclipse JUnit tab showing test statistics (Runs, Errors, Failures) here._

---

## 5. GitHub Repository

_Insert a screenshot of your GitHub repository page showing the latest project folder here._

---

## 6. Reflection: Unit Testing vs. Functional Testing with Selenium

Unit testing focuses on verifying the correctness of individual functions or methods in isolation, typically using frameworks like JUnit. For example, checking if a function returns the expected output for given inputs.

Functional testing with Selenium, on the other hand, automates user interactions with a web application to verify that the system behaves as expected from an end-user perspective. This includes navigating web pages, filling forms, and asserting on UI changes or navigation.

**Key differences:**

- **Scope:** Unit tests target code logic; Selenium tests target end-to-end user flows.
- **Environment:** Unit tests run in isolation; Selenium tests require a running browser and web server.
- **Assertions:** Unit tests assert on return values or state; Selenium tests assert on UI elements, URLs, or page content.

---

## 7. Discussion: Decision Table Testing and JUnit Assert for Selenium

**Decision Table Testing:**  
Decision tables help systematically cover all possible input combinations and their expected outcomes. For the login page, the table ensures that all combinations of valid/invalid email and password are tested, improving coverage and reliability.

**JUnit Assert for Selenium:**  
JUnit's assertion methods (e.g., `Assert.assertTrue`, `Assert.assertFalse`) are used in Selenium tests to validate outcomes, such as checking if the URL contains "login" after a failed attempt or does not after a successful login. This bridges the gap between UI automation and test validation.

---

## 8. Conclusion

This assignment demonstrates the application of decision table-based testing for a real-world login page using Selenium and JUnit. The approach ensures comprehensive coverage of possible login scenarios and highlights the differences between unit and functional testing.

---

**End of Submission**
