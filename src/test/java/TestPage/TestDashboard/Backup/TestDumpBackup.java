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

public class TestDumpBackup extends EnvContainer {
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
        _ctx.current(_page.NameBDText(ReplicaBD)).click();
        _ctx.current(_page.DumpBackupSettingsBtn(ReplicaBD)).click();
        _ctx.current(_page.ScheduleField).waitelementToBeClickable();

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
    public void testCreateDumpBackupScheduleIncorrect()  {
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
    // WHEN we leave the field empty "Min Free Space"  THEN the error " "" is not an integer number"
    @Test( enabled = true, priority = 2)
    public void testCreateDumpBackupMinFreeSpaceEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MinFreeSpaceField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "");

    }

    // WHEN we leave the field empty "Path Folder"  THEN the error " "" should not be null or empty"
    @Test( enabled = true, priority = 3)
    public void testCreateDumpBackupPathFolderEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.DirectoryField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "");
    }

    // WHEN we leave the field empty "maximum number Files" field THEN the error " "" is not an integer number"
    @Test( enabled = true, priority = 4)
    public void testCreateDumpBackupMaxNumbFilesEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MaxNumbFilesField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "");


    }
    // WHEN we fill in the settings Dump Backup with the correct data THEN the backup is successfully created
    @Test( enabled = true, priority = 5)
    public void testCreateDumpBackupCorrect ()  {
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
        _ctx.current(_page.NameBDText(ReplicaBD)).click();

        // verification
        Assert.assertEquals(_page.DumpBackupPanelOk(ReplicaBD).getText(),"OK",
                "Status verified backup not OK");
        Assert.assertEquals(_page.DumpBackupPanelSchedule(ReplicaBD).getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");

    }

    private void InitBackup() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.MinFreeSpaceField).setValue("10000000").
                current(_page.MaxNumbFilesField).setValue("1").
                current(_page.DirectoryField).setValue("${db.default-directory}/${job.id}");

    }
}
