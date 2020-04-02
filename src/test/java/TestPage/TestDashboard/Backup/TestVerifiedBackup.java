package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import static Constants.InitData.*;

public class TestVerifiedBackup extends EnvContainer {
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
        _ctx.current(_page.VerifiedBackupSettingsBtn(BackupBD)).scrollToElement().click();
        //_ctx.current(_page.ScheduleField).waitelementToBeClickable();
        //Assert.assertTrue(_ctx.tryFindWebElement(_page.ScheduleField),"Element Schedule Field not found");
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
        //InitVerifiedBackup();


    }
//    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//
//    }
    private void openUrl() {
        _driver.navigate().to(_standarturl);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        Helper.waitSetup(_driver, 1000);
    }


    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we set the wrong schedule in the backup settings THEN, the error \"Cron expression or period must be set properly\"")
    public void testCreateVerifiedBackupScheduleIncorrect()  {
        _ctx.waitSetup(_driver,1000);
        //actions
        _ctx.current(_page.ScheduleField).setValue("").
            current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "the set schedule is not correct");


    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we set a negative number in the \"maximum number of backups\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateVerifiedBackupMaxNumbNegative ()  {
        // prepare
        String negativeNumb = "-8";
        InitVerifiedBackup();
        //actions
        _ctx.current(_page.MaxNumbFilesField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");


    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we set other symbols in the \"maximum number of backups\" field THEN the error \" \"\" is not an integer number\"")
    public void testCreateVerifiedBackupMaxNumbNotNumbers ()  {
        // prepare
        String notNumb = "dfdfdfdf";
        InitVerifiedBackup();
        //actions
        _ctx.current(_page.MaxNumbFilesField).setValue(notNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+notNumb+"\" is not an integer number",
                "the entered value must be a number");


    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we fill in the settings Verified Backup with the correct data THEN the backup is successfully created")
    public void testCreateVerifiedBackupCorrect ()  {
        // prepare
        String cron = "0/30 * * * * ?";
        InitVerifiedBackup();
        //actions
        _ctx.current(_page.EnabledCheckbox).waitelementToBeClickable();
        _ctx.current(_page.EnabledCheckbox).click().
            current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,30000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(BackupBD)).click();
        Helper.waitSetup(_driver,1000);
        // verification
        Assert.assertEquals(_page.VerifiedBackupPanelOk(BackupBD).getText(),"OK",
                "Status verified backup not OK");
        Assert.assertEquals(_page.VerifiedBackupPanelSchedule(BackupBD).getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");
        Assert.assertEquals(_page.VerifiedBackupPanelTotal(BackupBD).getText(),"Total backup files: 1 (max depth 5)",
                "cron schedule must be displayed");

    }

    private void InitVerifiedBackup() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.MaxNumbFilesField).setValue("5");

    }
}
