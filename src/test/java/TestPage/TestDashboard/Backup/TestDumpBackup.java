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

public class TestDumpBackup extends EnvContainer {
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
        _ctx.current(_page.NameBDText(TestDB)).click();
        _ctx.current(_page.DumpBackupSettingsBtn(TestDB)).scrollToElement().doubleClick();

        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
       // Assert.assertTrue(_ctx.tryFindWebElement(_page.ScheduleField),"Element Schedule Field not found");
        _ctx.implicitlyWaitElement(10);
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
    @Description(value = "WHEN we set the wrong schedule in the backup settings THEN, the error \"Cron expression or period must be set properly\"")
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

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Min Free Space\"  empty THEN the error \" \"\" is not an integer number\"")
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

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Path Folder\"  empty THEN the error \" \"\" should not be null or empty\"")
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

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field  \"maximum number Files\" empty THEN the error \" \"\" is not an integer number\"")
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

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we fill in the settings Dump Backup with the correct data THEN the backup is successfully created")
    public void testCreateDumpBackupCorrect ()  {
        // prepare
        InitBackup();
        String cron = "0/30 * * * * ?";

        //actions
        _ctx.current(_page.EnabledCheckbox).click().
            current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,30000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(TestDB)).click();

        // verification
        Assert.assertEquals(_page.DumpBackupPanelOk(TestDB).getText(),"OK",
                "Status verified backup not OK");
        Assert.assertEquals(_page.DumpBackupPanelSchedule(TestDB).getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");

    }

    private void InitBackup() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.MinFreeSpaceField).setValue("10000000").
                current(_page.MaxNumbFilesField).setValue("1").
                current(_page.DirectoryField).setValue("${db.default-directory}/${job.id}");

    }
}
