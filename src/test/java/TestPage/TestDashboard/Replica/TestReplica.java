package TestPage.TestDashboard.Replica;

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

public class TestReplica extends EnvContainer {
    private WebDriver _driver;

    public String _url,_standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _pagedatabase;
    private Backup _page;
    private Helper _ctx;

    @BeforeClass
    public void classSetUp()
    {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, Backup.class);
        _pagedatabase = PageFactory.initElements(_driver, Database.class);
        openUrl();
        Helper.waitSetup(_driver, 1000);
        _ctx.current(_page.NameBDText(BackupBD)).click();
        _ctx.current(_page.CloudBackupSettingsBtn(BackupBD)).click();
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
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    // WHEN we leave the field empty "Period" field THEN, the error "Cron expression or period must be set properly"
    @Test( enabled = true, priority = 1)
    public void testCheckPeriodEmptyField()  {

        //actions
        _ctx.current(_page.CheckPeriodField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    // WHEN we leave the field empty "Monitor Folder" field THEN, the error "Monitored folder is empty - please specify it"
    @Test( enabled = true, priority = 2)
    public void testCheckMonitorFolderEmptyField()  {
        // prepare
        InitTestCloudBackup();

        //actions
        _ctx.current(_page.MonitorFolderField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Monitored folder is empty - please specify it",
                "the field must not be empty");

    }

    // WHEN we leave the field empty "Filename Template" field THEN, the error "should not be null or empty"
    @Test( enabled = true, priority = 3)
    public void testCheckFilenameTemplateEmptyField()  {
        // prepare
        InitTestCloudBackup();

        //actions
        _ctx.current(_page.FilenameTemplateField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "the field must not be empty");

    }

    // WHEN we leave the field empty "Filed Connection Ftp" field THEN, the error "is not an integer number"
    @Test( enabled = true, priority = 4)
    public void testCheckFiledConnectionFtpEmptyField()  {
        // prepare
        InitTestCloudBackup();

        //actions
        _ctx.current(_page.FiledConnectionFtpField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    // WHEN we leave the field empty "Keep N Source Files" field THEN, the error "is not an integer number"
    @Test( enabled = true, priority = 5)
    public void testCheckKeepNSourceFilesFieldEmptyField()  {
        // prepare
        InitTestCloudBackup();

        //actions
        _ctx.current(_page.KeepNsourceFilesField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    // WHEN we leave the field empty "Ftp Server" field THEN, the error "FTP Server parameter is empty"
    @Test( enabled = true, priority = 6)
    public void testCheckFtpServerEmptyField()  {
        // prepare
        _ctx.current(_page.ConfigureFtpBtn).click();

        //_ctx.current(_page.UploadFtpCheckbox).click();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.UploadFtpCheckbox).waitelementToBeClickable();
        _ctx.hoverAndClick(_page.UploadFtpCheckbox);
        Helper.waitSetup(_driver,1000);
        //actions

        _ctx.current(_page.FtpServerField).setValue("").
            current(_page.FtpSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDangerFtp.getText(),"FTP Server parameter is empty",
                "the field must not be empty");

    }

    // WHEN we leave the field empty "Ftp Port" field THEN, the error "FTP Port is not valid"
    @Test( enabled = true, priority = 7)
    public void testCheckFtpPortEmptyField()  {
        //prepare
        InitTestFtp();

        //actions
        _ctx.current(_page.FtpPortField).setValue("").
                current(_page.FtpSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDangerFtp.getText(),"FTP Port is not valid",
                "the field must not be empty");

    }

    // WHEN we fill in the settings FTP with the correct data THEN the FTP is successfully created
    //TODO:: ADD Check
    @Test( enabled = true, priority = 8)
    public void testAddFtpCorrect()  {
        //prepare
        _ctx.current(_page.FtpServerField).setValue("www.myserver.com").
                current(_page.FtpPortField).setValue("21").
                current(_page.FtpUserField).setValue("UserName").
                current(_page.FtpSaveBtn).click().waitUpdate();

        // verification
       // Assert.assertEquals(_page.BackupAllertDanger.getText(),"FTP Server parameter is empty",
                //"-------");

    }

    // WHEN we fill in the settings Cloud Backup with the correct data THEN the backup is successfully created
    //TODO:: ADD Check
    @Test( enabled = true, priority = 9)
    public void testAddCloudBackupCorrect()  {
        // prepare
        InitTestCloudBackup();
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.CheckPeriodField).setValue("10").
                current(_page.MonitorFolderField).setValue("${db.repparam_log_archive_directory}").
                current(_page.FilenameTemplateField).setValue("*.arch*").
                current(_page.FiledConnectionFtpField).setValue("3").
                current(_page.KeepNsourceFilesField).setValue("33");
        //actions
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification


    }


    private void InitTestCloudBackup() {
        _ctx.current(_page.CheckPeriodField).setValue("10").
                current(_page.MonitorFolderField).setValue("${db.repparam_log_archive_directory}").
                current(_page.FilenameTemplateField).setValue("*.arch*").
                current(_page.FiledConnectionFtpField).setValue("3").
                current(_page.KeepNsourceFilesField).setValue("33");

    }

    private void InitTestFtp() {
        _ctx.current(_page.FtpServerField).setValue("www.myserver.com").
                current(_page.FtpPortField).setValue("21");

    }
}
