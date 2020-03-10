package TestPage;

import Helpers.Helper;

import TestPageLocator.DashboardPage.Dashboard;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import TestPageLocator.RegistrationLocators;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static Constants.InitData.*;

public class EnvContainer
{
    public static String URL, Pathhqbirddata, BrowserType,pathDriver,serverPath,StandartDBPath,ServerBinaryPath;
    public static WebDriver Driver;
    private Helper _helper;
    public static GeneralLocators generalpage;
    public static RegistrationLocators registerpage;
    public static Dashboard dashboardpage;
    private Database _pagedatabase;


    @BeforeSuite
    @Parameters({ "browsername", "url","pathhqbirddata","serverInstallationPath"})
    public void suiteSetUp(String browserType, String url, String pathhqbirddata, String serverInstallationPath) throws IOException, InterruptedException {
        pathDriver = "null";
        if (System.getProperty("url") != null) {
            url = System.getProperty("url");
        }
        BrowserType = browserType;
        Pathhqbirddata = pathhqbirddata;
        serverPath = serverInstallationPath;
        BrowserType();
        ServerPath();
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

        // Registration enterprise version
        Registration();
        //Add default server
        AddServer();
        StopRefreshPage();
        // Create DB Backup, Replica, Master
        AddDatabaseInit();

    }

    @AfterSuite
    public void suiteTearDown() {
        Driver.quit();

    }

    @AfterMethod
    public void methodTearDown( Method method, ITestResult result) {

        if (! result.isSuccess()) {
            makeScreenshot(result.getName());
        }
        try {
            interceptionJSonPage(Driver);
        }catch (Exception ex){

        }
    }


    @Attachment(value = "{0}", type = "image/png")
    public byte[] makeScreenshot(String nametest) {
        return ((TakesScreenshot) Driver).getScreenshotAs(OutputType.BYTES);
    }
    /**
     * Main Func
     */

    private void BrowserType(){
        if (BrowserType.equals("chrome")) {
            pathDriver = "src/test/resurces/Driver/chromedriver.exe";
        }
        if(BrowserType.equals("firefox")){
            pathDriver = "src/test/resurces/Driver/geckodriver.exe";
        }
    }

    private void ServerPath(){
        if (serverPath.contains("irebird25")) {
            StandartDBPath = "C:\\dgtest\\src\\test\\resurces\\StandartDB\\Firebird25";
            ServerBinaryPath = serverPath + "bin";
        }
        if(serverPath.contains("irebird30")){
            StandartDBPath = "C:\\dgtest\\src\\test\\resurces\\StandartDB\\Firebird30";
            ServerBinaryPath = serverPath;
        }
    }
    private void Registration(){
        Helper.log("----------------------");
        Helper.log("Start registration");
        Helper.log("Wait page loading 10 sec");
        _helper.implicitlyWaitElement(10);
        if(_helper.current(registerpage.EnterpriseCheckbox).waitelementToBeClickable()== false){
            _helper.current(generalpage.RegistrationMenuBtn).click();
        }

        _helper.current(registerpage.EnterpriseCheckbox).click().
                current(registerpage.EmailField).setValue("autotest@ib-aid.com").
                current(registerpage.PasswordField).setValue("2019QAZ2019QAZ").
                current(registerpage.ActiveButton).click();
        Helper.log("Click button registration and wait 5 sec");
        Helper.waitSetup(Driver,5000);
        //Assert.assertFalse(_helper.isdisplayedElement(registerpage.AllertRegistr), "Alert not displayed");
    }

    private void AddServer(){
        Helper.log("----------------------");
        Helper.log("Start add default server");
        _helper.current(generalpage.DashboardMenuBtn).click().
                current(generalpage.AddFirebirdButton).waitelementToBeClickable();
        _helper.current(generalpage.AddFirebirdButton).click().
                current(generalpage.ServerInstallFolderButton).setValue(serverPath).
                current(generalpage.ServerBinaryFolderButton).setValue(ServerBinaryPath);
        Helper.log("Wait 3sec");
        Helper.waitSetup(Driver,3000);
        _helper.current(generalpage.SaveServerButton).waitelementToBeClickable();
        _helper.current(generalpage.SaveServerButton).click();
        Helper.log("Finish add default server");
        Helper.log("Wait 5sec");
        Helper.waitSetup(Driver,3000);
    }


    // Func for initialization data: Stop server, delete and copy files, start service
    private void InitData() throws IOException, InterruptedException {

        // stop service dg and wait 30s
        Helper.log("----------------------");
        Helper.log("start stopservice.bat");
        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"stopservice.bat -Verb runAs");
        Helper.log("Wait 30 sec");
        Helper.waitSetup(Driver, 30000);

        // delete some folders and files in the config
        _helper.deleteFolderOrFile(new File(Pathhqbirddata + "config\\agent\\servers\\hqbirdsrv"));
        //_helper.deleteFolderOrFile(new File(Pathhqbirddata + "output\\logs\\agent\\servers\\hqbirdsrv"));
        _helper.deleteFolderOrFile(new File(Pathhqbirddata + "output\\"));
        _helper.deleteFolderOrFile(new File(Pathhqbirddata + "config\\installid.bin"));
        _helper.deleteFolderOrFile(new File(Pathhqbirddata + "config\\unlock"));

        // we delete some spent backups and copy the reference backups to this folder
        _helper.deleteFolderOrFile(new File("C:\\dgtest\\src\\test\\resurces\\WorkDB"));
        _helper.deleteFolderOrFile(new File("C:\\dgtest\\src\\test\\resurces\\Ftp\\WorkCloudDB"));
        _helper.deleteFolderOrFile(new File("C:\\dgtest\\src\\test\\resurces\\Ftp\\WorkCloudReceiverDB"));

        // we delete folders allure :resuult and reports
        _helper.deleteFolderOrFile(new File("C:\\dgtest\\src\\test\\reports\\allure-results"));
        _helper.deleteFolderOrFile(new File("C:\\dgtest\\src\\test\\reports\\allure-reports"));

        DirectoryCopy(StandartDBPath, "C:\\dgtest\\src\\test\\resurces\\WorkDB");
        DirectoryCopy("C:\\dgtest\\src\\test\\resurces\\config", Pathhqbirddata + "config");

        // start service dg and wait 5s
        Helper.log("start startservice.bat");
        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"startservice.bat -Verb runAs");
        _helper.implicitlyWaitElement();
        Helper.log("Wait 5 sec");
        Helper.waitSetup(Driver, 5000);


    }
    private void AddDatabaseInit(){
       AddDatabase(BackupBD,Backup_DB_Path);
       AddDatabase(CloudTestDB,Cloud_DB_Path);
       AddDatabase(TestDB,Test_DB_Path);
       AddDatabase(MasterAsyncBD,Master_Async_DB_Path);
       AddDatabase(MasterSyncBD,Master_Sync_DB_Path);
       AddDatabase(ReplicaSyncBD,Replica_Sync_DB_Path);
    }

    private void StopRefreshPage(){
        openUrl();
        Helper.log("----------------------");
        Helper.log("start test StopRefreshPage");
        Helper.log("Wait page loading 10 sec");
        _helper.implicitlyWaitElement(10);
        Helper.log("Wait 3 sec");
        Helper.waitSetup(Driver, 3000);
        _helper.current(generalpage.PanelPageRefreshBtn).waitelementToBeClickable();
        _helper.current(generalpage.PanelPageRefreshBtn).click().
                current(generalpage.StopRefreshBtn).click().waitUpdate();
        Helper.log("finish test StopRefreshPage");
        Helper.log("----------------------");
    }
    private void AddDatabase(String dbName, String dbPath){
        //actions
        Helper.log("----------------------");
        Helper.log("start test add database - "+dbName+"");
        Helper.waitSetup(Driver, 1000);
        _helper.current(_pagedatabase.DatabaseSettingsBtn).click();
        Helper.waitSetup(Driver, 1000);
        _helper.current(_pagedatabase.DbNameField).waitelementToBeClickable();
        _helper.current(_pagedatabase.DbNameField).setValue(dbName).
                current(_pagedatabase.DbPathField).setValue(dbPath).
                current(_pagedatabase.DbSaveBtn).doubleClick().waitUpdate();
        Assert.assertTrue(_helper.isdisplayedElement(_pagedatabase.NameBD(dbName)), "database "+dbName+" is not successfully add");
        Helper.log("finish test add database - "+dbName+"");


    }
    private void openUrl() {
        Driver.navigate().to(URL);
        interceptionJSonPage(Driver);
    }
    private void DirectoryCopy(String copyFolder, String replacement) throws IOException {
        File srcFolder = new File(copyFolder);
        File destFolder = new File(replacement);

        //checking for a source directory
        if (!srcFolder.exists()) {

            System.err.println("This directory "+srcFolder+" does not exist!");

        } else {

            _helper.copyDirectory(srcFolder, destFolder);

        }
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
        }
        if (browserType.equals("firefox")) {

            System.setProperty("webdriver.gecko.driver", pathDriver);
//            LoggingPreferences loggingPrefs = new LoggingPreferences();
//            loggingPrefs.enable(LogType.BROWSER, Level.ALL);
//            loggingPrefs.enable(LogType.CLIENT, Level.ALL);
//            loggingPrefs.enable(LogType.DRIVER, Level.ALL);
//            loggingPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//            loggingPrefs.enable(LogType.PROFILER, Level.ALL);
//            loggingPrefs.enable(LogType.SERVER, Level.ALL);
            FirefoxOptions options = new FirefoxOptions();
//            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//            desiredCapabilities.setCapability("marionette", true);
//            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPrefs);


//            options.merge(desiredCapabilities);

            //options.setLogLevel(FirefoxDriverLogLevel.TRACE);

            driver = new FirefoxDriver(options);
            return driver;
        }
        else {
            throw new RuntimeException("driver not found");
            // return null;
        }

    }
    /**
     * Intercept function JS ERROR
     */
    public static String interceptionJSonPage(WebDriver webDriver) {
        if (BrowserType == "chrome") {
            Set<String> errorStrings = new HashSet<>();
            errorStrings.add("SyntaxError");
            errorStrings.add("EvalError");
            errorStrings.add("ReferenceError");
            errorStrings.add("RangeError");
            errorStrings.add("TypeError");
            errorStrings.add("URIError");
            //errorStrings.add("404");

            LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
            if (logEntries != null) {
                for (LogEntry logEntry : logEntries) {
                    for (String errorString : errorStrings) {
                        if (logEntry.getMessage().contains(errorString)) {
                            Assert.fail(new Date(logEntry.getTimestamp()) + " " + logEntry.getLevel() + " " + logEntry.getMessage());
                        }
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }


}