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

import static Constants.InitData.*;

public class TestIncrementalBackup extends EnvContainer {
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
        _ctx.current(_page.IncrementalBackupSettingsBtn(BackupBD)).click();
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

    // WHEN we leave the field empty "Max Duration"  THEN the error "The server encountered an unexpected condition which prevented it from fulfilling the request"
    @Test( enabled = true, priority = 1)
    public void testCreateIncrementalBackupMaxDurationEmpty()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MaxDurationField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"The server encountered an unexpected condition which prevented it from fulfilling the request",
                "Field not be empty");


    }
    // WHEN we leave the field empty "Min Free Space"  THEN the error " "" is not an integer number"
    @Test( enabled = true, priority = 2)
    public void testCreateIncrementalBackupMinFreeSpaceEmpty ()  {
        // prepare
        InitBackup();
        String value = "a>?$%^^";
        //actions
        _ctx.current(_page.MinFreeSpaceField).setValue(value).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+value+"\" is not an integer number",
                "Value is not an integer number");

    }

    // WHEN we leave the field empty "Path Folder"  THEN the error " "" should not be null or empty"
    @Test( enabled = true, priority = 3)
    public void testCreateIncrementalBackupPathFolderEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.DirectoryField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "The server encountered an unexpected condition which prevented it from fulfilling the request");
    }

    // WHEN we leave the field empty "Journal Folder"  THEN the error " "" should not be null or empty"
    @Test( enabled = true, priority = 4)
    public void testCreateIncrementalBackupJournalFolderEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.JournalNameField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "The server encountered an unexpected condition which prevented it from fulfilling the request");
    }

    // WHEN we leave the field empty "Perform Backup" field THEN the error "The server encountered an unexpected condition which prevented it from fulfilling the request"
    @Test( enabled = true, priority = 5)
    public void testCreateIncrementalBackupPerformBackupEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.PerformBackupDelayField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"The server encountered an unexpected condition which prevented it from fulfilling the request",
                "");


    }
    // WHEN we fill in the settings Incremental Backup with the correct data THEN the backup is successfully created
    @Test( enabled = true, priority = 6)
    public void testCreateIncrementalBackupCorrect ()  {
        // prepare
        InitBackup();
        String cron = "0/10 * * * * ?";

        //actions
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.ScheduleAdvencedField).click().
                current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,10000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(BackupBD)).click();
        // verification
        Assert.assertEquals(_page.IncrementalBackupPanel(BackupBD).getText(),"Advanced schedule",
                "Scheme backup not Advanced schedule");
        Assert.assertEquals(_page.IncrementalBackupPanelOk(BackupBD).getText(),"OK" ,
                "Status incremental backup not OK");

    }

    private void InitBackup() {
       // _ctx.current(_page.ScheduleSimpleBtn).click().
                _ctx.current(_page.MaxDurationField).setValue("86400").
                current(_page.MinFreeSpaceField).setValue("10737418240").
                current(_page.PerformBackupDelayField).setValue("30").
                current(_page.DirectoryField).setValue("${db.default-directory}/${job.id}").
                current(_page.JournalNameField).setValue("${db.default-directory}/${job.id}/nbackup_files_${db.id}.journal");

    }
}
