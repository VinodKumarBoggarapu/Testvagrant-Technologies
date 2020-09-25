package BrowserLaunch;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


public class WebDriverInStance {

    WebDriver driver;
    Properties configproperties;
    DesiredCapabilities capabilities;
    private static Logger log = LogManager.getLogger(Logger.class.getName());
    public Wait<WebDriver> wait;

    public WebDriverInStance(WebDriver driver) {
        this.driver=driver;
    }


    private Properties loadConfigProperties(String configFileName) {

        return configproperties = loadProperties(System.getProperty("user.dir") + "/Resources/" + configFileName);

    }

    private Properties loadProperties(String propfile) {

        Properties prop = null;
        InputStream input = null;
        try {
            log.info("Reading of config file started");
            prop = new Properties();
            input = new FileInputStream(propfile);
            prop.load(new InputStreamReader(input, "UTF-8"));

        } catch (IOException e) {
            log.error("Reading the config file failed because of IOException." + propfile);
        }

        return prop;

    }


    public void LoadConfigurationsProperties(String configFileName) {

        loadConfigProperties(configFileName);

        Config.setBrowser(configproperties.getProperty("browser"));
        Config.setAuthenticationURL(configproperties.getProperty("authenticationURL"));

    }

    public WebDriver launchBrowser() throws InterruptedException, IOException {
        String browser = Config.getBrowser();
        System.out.println("Print browser " + browser.toString());
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.merge(getFireFoxDriverCapabilites());
                driver = new FirefoxDriver(ffOptions);
                driver.get(Config.getAuthenticationURL());
                driver.manage().window().maximize();
                break;
            case "CHROME":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.merge(getChromeDriverCapabilites());
                driver = new ChromeDriver(chromeOptions);
                driver.get(Config.getAuthenticationURL());
                log.info("The driver version is " + Config.getBrowser());
                Thread.sleep(3000);
                driver.manage().window().setSize(new Dimension(1410, 1100));
                driver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
                break;
            case "CHROMEHEADLESS":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("window-size=1200x600");
                driver = new ChromeDriver(options);
                driver.get(Config.getAuthenticationURL());
                log.info("The driver version is " + Config.getBrowser());
                Thread.sleep(3000);
                driver.manage().window().setSize(new Dimension(1410, 1100));
                driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
                break;
            default:
                log.info("Browser not supported");
                driver.quit();
        }
        log.info("");
        return driver;

    }

    public DesiredCapabilities getChromeDriverCapabilites() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("AUTOMATION NAME", "LandmarkShops Web Automation");
        capabilities.setJavascriptEnabled(true);
        log.info("Launching: " + capabilities.getCapability("AUTOMATION NAME"));
        log.info("The platform is " + capabilities.getPlatform());
        log.info("Running tests on Browser: " + capabilities.getBrowserName());
        log.info(capabilities.getVersion());
        return capabilities;
    }

    public DesiredCapabilities getFireFoxDriverCapabilites() {

        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver");

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("AUTOMATION NAME", "LandmarkShops Web Automation");
        capabilities.setCapability("marionette", true);
        capabilities.setJavascriptEnabled(true);
        log.info("Launching: " + capabilities.getCapability("AUTOMATION NAME"));
        log.info("The platform is " + capabilities.getPlatform());
        log.info("Running tests on Browser: " + capabilities.getBrowserName());
        log.info(capabilities.getVersion());
        log.info(capabilities.getCapability("profile"));
        return capabilities;
    }

    public void quitDriver() {
        driver.quit();
    }

    public void visibilityOf(WebElement element, long withTimeoutDuration) {
        wait = new FluentWait<WebDriver>(driver).withTimeout(withTimeoutDuration, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOf(element));
    }


    }