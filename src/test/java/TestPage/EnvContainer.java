package TestPage;

import Helpers.Helper;

import TestPageLocator.Common.GeneralLocators;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.logging.Level;

public class EnvContainer
{
    public static String URL,Pass, Login;
    public static WebDriver Driver;
    private Helper _helper;
    public static GeneralLocators pagecommon;


    @BeforeSuite
    @Parameters({ "browsername", "pathdriver", "url", "username", "password" })
    public void suiteSetUp(String browserType, String pathDriver, String url, String username, String password) {

        if (System.getProperty("url") != null) {
            url = System.getProperty("url");
        }
        Pass = password;
        Login = username;
        Driver = getDriverByName(browserType, pathDriver);
        _helper = new Helper(Driver);
        URL = url;
        // Init Env
        Driver.navigate().to(url);
        Driver.manage().window().setSize(new Dimension(1920, 1080));

        pagecommon = PageFactory.initElements(Driver, GeneralLocators.class);


    }

    @AfterSuite
    public void suiteTearDown() {
        // int testStatus = result.getStatus();
        /*
         * if (ITestResult.FAILURE == result.getStatus()) {
         * captureScreenshot(result.getName() + browserType); }
         */
        Driver.quit();
    }

    @AfterMethod
    public void testTearDown( Method method) {
        try {
            Helper.interceptionJSonPage(Driver);
        }catch (Exception ex){

        }

    }

    @AfterTest
    public void testTearDownTests() {

    }

    /**
     * Init Driver
     */

    public static WebDriver getDriverByName(String browserType, String pathDriver) {
        WebDriver driver = null;

        if (browserType.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", pathDriver);
            System.setProperty("webdriver.chrome.verboseLogging", "false");
            System.setProperty("webdriver.chrome.args", "--disable-logging");
            System.setProperty("webdriver.chrome.silentOutput", "true");
            java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF); //it works

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--log-level=OFF");
            options.addArguments("--silent");

            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            driver = new ChromeDriver(options);
            return driver;
        } else {
            throw new RuntimeException("driver not found");
            // return null;
        }

    }


}