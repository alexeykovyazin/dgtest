package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Backup;
import TestPageLocator.DashboardPage.Database;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
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
    public void suiteSetUp()
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
    public void testCreateVBScheduleIncorrect()  {
//
//        //actions
//        _ctx.current(_page.ScheduleField).setValue("").
//            current(_page.DbSaveBtn).click().waitUpdate();
////        Helper.waitSetup(_driver,4000);
//        // verification
//        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
//                "Cron expression installed incorrectly");


    }



    private void InitBackup() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.MaxNumbFilesField).setValue("5");

    }
}
