package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Backup;
import TestPageLocator.DashboardPage.Database;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.BackupBD;

public class TestRestoreDb extends EnvContainer {
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
        _ctx.current(_page.RestoreDbSettingsBtn(BackupBD)).click();
       // _ctx.current(_page.ScheduleField).waitelementToBeClickable();
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

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we set the wrong schedule in the backup settings THEN, the error \"Cron expression or period must be set properly\"")
    public void testCreateRestoreDbScheduleIncorrect()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.ScheduleField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");


    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field empty \"Get backup from folder\"  THEN the error \" \"\" should not be null or empty\"")
    public void testCreateRestoreDbBackupFolderEmptyField ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.DirectoryField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field empty \"Template for backup file name\"  THEN the error \" \"\" should not be null or empty\"")
    public void testCreateRestoreDbPathTemplateBackupFolderEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.NamePatternField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field empty \"Take backup not older than, hours\"  THEN the error \"The server encountered an unexpected condition which prevented it from fulfilling the request\"")
    public void testCreateRestoreTimeTakeBackupEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MaxFileageTorestoreHField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"The server encountered an unexpected condition which prevented it from fulfilling the request",
                "");
    }

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we leave the field empty \"Restore to directory\"  THEN the error \" \"\" should not be null or empty\"")
    public void testCreateRestoreDbPathRestoreFolderEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.RestoreDirField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    @Test( enabled = true, priority = 6)
    @Description(value = "WHEN we leave the field empty \"Restore with file name\"  THEN the error \" \"\" should not be null or empty\"")
    public void testCreateRestoreDbRestoreFileNameEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.RestoreDbNameField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    @Test( enabled = true, priority = 7)
    @Description(value = "WHEN we leave the field empty \"Append suffix to file name\"  THEN the error \" \"\" should not be null or empty\"")
    public void testCreateRestoreDbSuffixFileNameEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.AppendSuffixFileNameField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    @Test( enabled = true, priority = 8)
    @Description(value = "WHEN we leave the field empty \"Limit restore process time to (minutes)\"  THEN the error \"The server encountered an unexpected condition which prevented it from fulfilling the request\"")
    public void testCreateRestoreDbLimitProcessTimeEmptyField()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.LimitRestoreProcTimeField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"The server encountered an unexpected condition which prevented it from fulfilling the request",
                "");
    }

    @Test( enabled = true, priority = 9)
    @Description(value = "WHEN we leave the field empty \"Check available space before restore. Minimum value (bytes)\"  THEN the error \" \"\" is not an integer number\"")
    public void testCreateRestoreDbMinFreeSpaceEmptyField ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MinFreeSpaceField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "");

    }

    @Test( enabled = true, priority = 10)
    @Description(value = "WHEN we fill in the settings Restore Db with the correct data THEN the RestoreDb is successfully created")
    public void testCreateRestoreDbCorrect ()  {
        // prepare
        InitBackup();
        String cron = "0/10 * * * * ?";

        //actions
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,10000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(BackupBD)).click();
        // verification
        Assert.assertEquals(_page.RestoreDbPanelOk(BackupBD).getText(),"OK",
                "Status verified backup not OK");
        Assert.assertEquals(_page.RestoreDbSchedule(BackupBD).getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");

    }

    private void InitBackup() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.DirectoryField).setValue("C:\\dgtest\\src\\test\\resurces\\WorkDB").
                current(_page.NamePatternField).setValue("backup_{0,date,yyyyMMdd_HH-mm}").
                current(_page.MaxFileageTorestoreHField).setValue("24").
                current(_page.RestoreDirField).setValue("${backup-directory}").
                current(_page.RestoreDbNameField).setValue("${db.id}_{0,date,yyyyMMdd_HH-mm}_testrestore.fdb").
                current(_page.AppendSuffixFileNameField).setValue("{0,date,yyyyMMdd_HH-mm}.old").
                current(_page.LimitRestoreProcTimeField).setValue("720").
                current(_page.MinFreeSpaceField).setValue("10000000");

    }
}
