package BrowserLaunch;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TestHarness {

    public static WebDriver driver;
    public WebDriverInStance webDriverInstance;
    public Wait<WebDriver> wait;


    @BeforeClass
    public void launchBrowser() throws Exception, IOException {
        webDriverInstance = new WebDriverInStance(driver);
        webDriverInstance.LoadConfigurationsProperties("vargrantconfigration.properties");
        driver = webDriverInstance.launchBrowser();

    }

    @AfterClass
    public void quitbrowser() {
        webDriverInstance.quitDriver();
    }

    public void visibilityOf(WebElement element, long withTimeoutDuration) {
        wait = new FluentWait<WebDriver>(driver).withTimeout(withTimeoutDuration, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void staticWait() throws InterruptedException {
        Thread.sleep(7000);
    }


}



