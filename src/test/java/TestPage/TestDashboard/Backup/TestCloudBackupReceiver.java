package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.*;

public class TestCloudBackupReceiver extends EnvContainer {
    private WebDriver _driver;

    public String _url,_standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _pagedatabase;
    private GeneralLocators _page;
    private Helper _ctx;

    @BeforeClass
    public void classSetUp()
    {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, GeneralLocators.class);
        _pagedatabase = PageFactory.initElements(_driver, Database.class);
        openUrl();
        Helper.waitSetup(_driver, 1000);
        _ctx.current(_page.NameBDText(CloudTestDB)).click();
        _ctx.current(_page.CloudBackupReceiverSettingsBtn(CloudTestDB)).scrollToElement().click();
        _ctx.current(_page.CheckPeriodField).waitelementToBeClickable();
    }
//    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we set the wrong schedule in the backup settings THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckPeriodEmptyField()  {
        InitTestCloudBackupReceiver();
        //actions
        _ctx.current(_page.CheckPeriodField).setValue("").waitUpdate();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Watch Incoming Files\" empty THEN, the error \"Monitored folder is empty - please specify it\"")
    public void testCheckWatchIncomingFilesEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.MonitorFolderField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Monitored folder is empty - please specify it!",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Filename Template\" empty THEN, the error \"Template for monitored file names is empty\"")
    public void testCheckFilenameTemplateEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.FilenameTemplateField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Template for monitored file names is empty",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field  \"Extension Packed Files\" empty THEN, the error \"Extension for packed files is empty\"")
    public void testCheckExtensionPackedFilesEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.ArchiveExtentionField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Extension for packed files is empty",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we leave the field  \"Decrypt Password\" empty THEN, the error \"Decrypt password is empty\"")
    public void testCheckDecryptPasswordEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.DecryptPasswordField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Decrypt password is empty",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 6)
    @Description(value = "WHEN we fill the \"Alert File Count\" field with an incorrect value THEN an error \"is not an integer number\"")
    public void testCheckAlertFileCountIncorrectValue()  {
        // prepare
        InitTestCloudBackupReceiver();
        String value = "text";
        //actions
        _ctx.current(_page.AlertFileCountField).setValue(value).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        if (BrowserType.equals("chrome")) {
            Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"1"+value+"\" is not an integer number",
                    "value must be a number");
        }
        if(BrowserType.equals("firefox")){
            Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+value+"\" is not an integer number",
                    "value must be a number");
        }


    }

    @Test( enabled = true, priority = 7)
    @Description(value = "WHEN we fill the \"Alert File Age\" field with an incorrect value THEN an error \"The server encountered an unexpected condition which prevented it from fulfilling the request\"")
    public void testCheckAlertFileAgeEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.AlertFileAgeField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "the field must not be empty");

    }

//    @Test( enabled = true, priority = 8)
//    @Description(value = "WHEN we fill in the settings Cloud Backup Receiver with the correct data THEN the backup is successfully created")
//    public void testAddCloudBackupReceiverCorrect()  {
//        //actions
//        _ctx.current(_page.EnabledCheckbox).click();
//        InitTestCloudBackupReceiver();
//        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
//
//        Helper.waitSetup(_driver,30000);
//        openUrl();
//        _ctx.implicitlyWaitElement();
//        _ctx.current(_page.NameBDText(CloudTestDB)).click();
//
//        // verification
//        Assert.assertEquals(_page.CloudBackupReceiverPanelOk(CloudTestDB).getText(),"OK" ,
//                "Status cloud backup receiver not OK");
//        Assert.assertEquals(_page.CloudBackupReceiverPanelPeriod(CloudTestDB).getText(),"[period 30 sec]" ,
//                "cron schedule must be displayed");
//        Assert.assertEquals(_page.CloudBackupReceiverPanelLastSendFile(CloudTestDB).getText(),CloudReceiverFileReplpacked ,
//                "Name file must be displayed");
//    }


    private void InitTestCloudBackupReceiver() {
        _ctx.current(_page.CheckPeriodField).setValue("30").
                current(_page.MonitorFolderField).setValue("C:\\dgtest\\src\\test\\resurces\\WorkDB\\CloudReceiverTest").
                current(_page.UnpackDirectoryField).setValue("C:\\dgtest\\src\\test\\resurces\\WorkDB\\CloudReceiverTest").
                current(_page.FilenameTemplateField).setValue("*.arch*").
                current(_page.ArchiveExtentionField).setValue(".replpacked").
                current(_page.DecryptPasswordField).setValue("zipmasterkey").
                current(_page.AlertFileCountField).setValue("33").
                current(_page.AlertFileAgeField).setValue("36000");

    }
}
