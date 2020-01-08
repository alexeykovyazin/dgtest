package TestPage;

import Helpers.Helper;

import TestPageLocator.DashboardPage.Dashboard;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import TestPageLocator.RegistrationLocators;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.logging.Level;

import static Constants.InitData.*;

public class EnvContainer
{
    public static String URL,Pass, Login;
    public static WebDriver Driver;
    private Helper _helper;
    public static GeneralLocators generalpage;
    public static RegistrationLocators registerpage;
    public static Dashboard dashboardpage;
    private Database _pagedatabase;


    @BeforeSuite
    @Parameters({ "browsername", "pathdriver", "url", "username", "password" })
    public void suiteSetUp(String browserType, String pathDriver, String url, String username, String password) throws IOException, InterruptedException {

        if (System.getProperty("url") != null) {
            url = System.getProperty("url");
        }
        Pass = password;
        Login = username;
        Driver = getDriverByName(browserType, pathDriver);
        _helper = new Helper(Driver);
        URL = url;
        // Init Env


        generalpage = PageFactory.initElements(Driver, GeneralLocators.class);
        registerpage = PageFactory.initElements(Driver, RegistrationLocators.class);
        dashboardpage = PageFactory.initElements(Driver, Dashboard.class);
        _pagedatabase = PageFactory.initElements(Driver, Database.class);

        InitData();
        Driver.navigate().to(url);
        Driver.manage().window().setSize(new Dimension(1920, 1080));
        Helper.waitSetup(Driver,3000);

        Registration();
        AddServer();
        AddDatabase();

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
     * Main Func
     */

    private void Registration(){
        if(_helper.current(registerpage.EnterpriseCheckbox).waitelementToBeClickable()== false){
            _helper.current(generalpage.RegistrationMenuBtn).click();
        }

        _helper.current(registerpage.EnterpriseCheckbox).click().
                current(registerpage.EmailField).setValue("autotest@ib-aid.com").
                current(registerpage.PasswordField).setValue("2019QAZ2019QAZ").
                current(registerpage.ActiveButton).click();
        Helper.waitSetup(Driver,2000);
        Assert.assertFalse(_helper.isdisplayedElement(registerpage.AllertRegistr), "Alert not displayed");
    }

    private void AddServer(){
        _helper.current(generalpage.DashboardMenuBtn).click().
                current(dashboardpage.AddFirebirdButton).waitelementToBeClickable();
        _helper.current(dashboardpage.AddFirebirdButton).click();
        Helper.waitSetup(Driver,3000);
        _helper.current(dashboardpage.SaveButton).waitelementToBeClickable();
        _helper.current(dashboardpage.SaveButton).click();
        Helper.waitSetup(Driver,3000);
    }

    private void DirectoryCopy() throws IOException {
        File srcFolder = new File("C:\\dgtest\\src\\test\\resurces\\config");
        File destFolder = new File("C:\\HQBirdData\\config");

        //проверка наличия директории источника
        if (!srcFolder.exists()) {

            System.err.println("Такой директории не существует!");

        } else {

            _helper.copyDirectory(srcFolder, destFolder);

        }
    }

    private void InitData() throws IOException, InterruptedException {

        //_helper.implicitlyWaitElement();
        Runtime.getRuntime().exec("powershell.exe  Start-Process C:\\dgtest\\src\\test\\resurces\\BatFiles\\stopservice.bat -Verb runAs").waitFor();
        Helper.waitSetup(Driver, 60000);

        _helper.deleteFolder(new File("C:\\HQBirdData\\config\\agent\\servers\\hqbirdsrv"));
        _helper.deleteFolder(new File("C:\\HQBirdData\\output\\logs\\agent\\servers\\hqbirdsrv"));
        _helper.deleteFolder(new File("C:\\HQBirdData\\config\\installid.bin"));
        _helper.deleteFolder(new File("C:\\HQBirdData\\config\\unlock"));

       // DirectoryCopy();
        Runtime.getRuntime().exec("powershell.exe  Start-Process C:\\dgtest\\src\\test\\resurces\\BatFiles\\restartservice.bat -Verb runAs").waitFor();
        _helper.implicitlyWaitElement();
        Helper.waitSetup(Driver, 5000);

    }

    private void AddDatabase(){
        //actions
        _helper.current(_pagedatabase.DatabaseSettingsBtn).click();
        _helper.current(_pagedatabase.DbNameField).waitelementToBeClickable();
        _helper.current(_pagedatabase.DbNameField).setValue(BackupBD).
                current(_pagedatabase.DbPathField).setValue(Backup_DB_Path).
                current(_pagedatabase.DbSaveBtn).click().waitUpdate();
        Assert.assertTrue(_helper.isdisplayedElement(_pagedatabase.NameBD(BackupBD)), "database is not successfully add");


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