package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        // Set the path for ChromeDriver
        System.setProperty(
            "webdriver.chrome.driver",
            "C:\\chromedriver\\chromedriver-win64\\chromedriver.exe"
        );

        // Create WebDriver instance
        WebDriver driver = new ChromeDriver();
        System.out.println(driver);

        // Open Google website
        driver.get("https://www.google.com");

        // Pause execution for 5 seconds (5000 milliseconds)
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        // Close the browser
        driver.close();
    }
}

