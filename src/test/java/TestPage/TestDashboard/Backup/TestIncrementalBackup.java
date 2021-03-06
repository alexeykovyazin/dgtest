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

public class TestIncrementalBackup extends EnvContainer {
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
        _ctx.current(_page.NameBDText(BackupBD)).click();
        _ctx.current(_page.IncrementalBackupSettingsBtn(BackupBD)).scrollToElement().click();
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
        Helper.waitSetup(_driver, 2000);
    }
//    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//    }
    private void openUrl() {
        _driver.navigate().to(_standarturl);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        Helper.waitSetup(_driver, 1000);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we leave the field  \"Max Duration\"  empty THEN the error \"The server encountered an unexpected condition which prevented it from fulfilling the request\"")
    public void testCreateIncrementalBackupMaxDurationEmpty()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.MaxDurationField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "Field not be empty");


    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Min Free Space\"  empty THEN the error \" \"\" is not an integer number\"")
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

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Path Folder\"  empty THEN the error \" \"\" should not be null or empty\"")
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

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field  \"Journal Folder\"  empty THEN the error \" \"\" should not be null or empty\"")
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

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we leave the field  \"Perform Backup\" empty THEN the error \"The server encountered an unexpected condition which prevented it from fulfilling the request\"")
    public void testCreateIncrementalBackupPerformBackupEmpty ()  {
        // prepare
        InitBackup();

        //actions
        _ctx.current(_page.PerformBackupDelayField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "");


    }

    @Test( enabled = true, priority = 6)
    @Description(value = "WHEN we fill in the settings Incremental Backup with the correct data THEN the backup is successfully created")
    public void testCreateIncrementalBackupCorrect ()  {
        // prepare
        InitBackup();
        String cron = "0/30 * * * * ?";

        //actions
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.ScheduleAdvencedField).click().
                current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,30000);
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
