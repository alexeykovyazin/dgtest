package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Backup;
import TestPageLocator.DashboardPage.Database;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.BackupBD;

public class TestCloudBackupReceiver extends EnvContainer {
    private WebDriver _driver;

    public String _url,_standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _pagedatabase;
    private Backup _page;
    private Helper _ctx;

    @BeforeClass
    public void suiteSetUp()
    {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, Backup.class);
        _pagedatabase = PageFactory.initElements(_driver, Database.class);
        openUrl();
        Helper.waitSetup(_driver, 1000);
        _ctx.current(_page.NameBDText(BackupBD)).click();
        _ctx.current(_page.CloudBackupReceiverSettingsBtn(BackupBD)).click();
        _ctx.current(_page.CheckPeriodField).waitelementToBeClickable();
    }
    @AfterMethod
    public void tearDown() {
       // openUrl();
        Helper.interceptionJSonPage(_driver);
    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    // WHEN we set the wrong schedule in the backup settings THEN, the error "Cron expression or period must be set properly"
    @Test( enabled = true, priority = 1)
    public void testCheckPeriodEmptyField()  {

        //actions
        _ctx.current(_page.CheckPeriodField).setValue("").waitUpdate();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    // WHEN we leave the field empty "Watch Incoming Files" field THEN, the error "Monitored folder is empty - please specify it"
    @Test( enabled = true, priority = 2)
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

    // WHEN we leave the field empty "Filename Template" field THEN, the error "Template for monitored file names is empty"
    @Test( enabled = true, priority = 3)
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

    // WHEN we leave the field empty "Extension Packed Files" field THEN, the error "Extension for packed files is empty"
    @Test( enabled = true, priority = 4)
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

    // WHEN we leave the field empty "Decrypt Password" field THEN, the error "Decrypt password is empty"
    @Test( enabled = true, priority = 5)
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

    // WHEN we fill the "Alert File Count" field with an incorrect value THEN an error "is not an integer number"
    @Test( enabled = true, priority = 6)
    public void testCheckAlertFileCountIncorrectValue()  {
        // prepare
        InitTestCloudBackupReceiver();
        String value = "ere";
        //actions
        _ctx.current(_page.AlertFileCountField).setValue(value).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+value+"\" is not an integer number",
                "value must be a number");

    }

    // WHEN we fill the "Alert File Age" field with an incorrect value THEN an error "The server encountered an unexpected condition which prevented it from fulfilling the request"
    @Test( enabled = true, priority = 7)
    public void testCheckAlertFileAgeEmptyField()  {
        // prepare
        InitTestCloudBackupReceiver();

        //actions
        _ctx.current(_page.AlertFileAgeField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"The server encountered an unexpected condition which prevented it from fulfilling the request",
                "the field must not be empty");

    }

    // WHEN we fill in the settings Cloud Backup Receiver with the correct data THEN the backup is successfully created
    @Test( enabled = true, priority = 8)
    public void testAddCloudBackupReceiverCorrect()  {
        //actions
        _ctx.current(_page.EnabledCheckbox).click();
        InitTestCloudBackupReceiver();
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        Helper.waitSetup(_driver,10000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(BackupBD)).click();

        // verification
        Assert.assertEquals(_page.CloudBackupReceiverPanelOk(BackupBD).getText(),"OK" ,
                "Status cloud backup receiver not OK");
    }


    private void InitTestCloudBackupReceiver() {
        _ctx.current(_page.CheckPeriodField).setValue("10").
                current(_page.MonitorFolderField).setValue("${db.repparam_log_archive_directory}").
                current(_page.FilenameTemplateField).setValue("*.arch*").
                current(_page.ArchiveExtentionField).setValue(".replpacked").
                current(_page.DecryptPasswordField).setValue("123").
                current(_page.AlertFileCountField).setValue("33").
                current(_page.AlertFileAgeField).setValue("360");

    }
}
