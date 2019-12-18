package TestPage.TestDashboard;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.DashboardPage.Backup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.*;

public class TestBackup extends EnvContainer {
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
        addDatabase();
    }
    @AfterMethod
    public void tearDown() {
        openUrl();

    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
       // _ctx.current(_pagedatabase.DatabaseSettingsBtn).waitelementToBeClickable();
    }
    private void addDatabase(){
        //actions
        _ctx.current(_pagedatabase.DatabaseSettingsBtn).click();
        _ctx.current(_pagedatabase.DbNameField).waitelementToBeClickable();
        _ctx.waitSetup(_driver, 2000);
        _ctx.current(_pagedatabase.DbNameField).setValue(NameBackupBD).
                current(_pagedatabase.DbPathField).setValue(DB_Backup_FDB).
                current(_pagedatabase.DbSaveBtn).click().waitUpdate();
    }

    // WHEN we set the wrong schedule in the backup settings THEN, the error "Cron expression or period must be set properly"
    @Test( enabled = true, priority = 1)
    public void testCreateVBScheduleIncorrect()  {
        //actions
        _ctx.current(_page.NameBDText(NameBackupBD)).click();
        _ctx.current(_page.VerifiedBackupSettingsBtn).click();
        _ctx.current(_page.ScheduleFieldSeting).waitelementToBeClickable();
        _ctx.current(_page.ScheduleFieldSeting).setValue("").
            current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");


    }

    @Test( enabled = true, priority = 2)
    public void testCreateVBMaxNumbNegative ()  {
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.NameBDText(NameBackupBD)).click();
        _ctx.current(_page.VerifiedBackupSettingsBtn).click();
        _ctx.current(_page.MaxNumbFilesFieldSeting).waitelementToBeClickable();
        _ctx.current(_page.MaxNumbFilesFieldSeting).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),""+negativeNumb+" should be >= 0",
                "DB path or DB alias not be empty");


    }

    @Test( enabled = true, priority = 3)
    public void testCreateVBMaxNumbNotNumbers ()  {
        String notNumb = "dfdfdfdf";

        //actions
        _ctx.current(_page.NameBDText(NameBackupBD)).click();
        _ctx.current(_page.VerifiedBackupSettingsBtn).click();
        _ctx.current(_page.MaxNumbFilesFieldSeting).waitelementToBeClickable();
        _ctx.current(_page.MaxNumbFilesFieldSeting).setValue(notNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),""+notNumb+" is not an integer number",
                "DB path or DB alias not be empty");


    }

    @Test( enabled = true, priority = 4)
    public void testCreateVBCorrect ()  {
        String cron = "0/10 * * * * ?";

        //actions
        _ctx.current(_page.NameBDText(NameBackupBD)).click();
        _ctx.current(_page.VerifiedBackupSettingsBtn).click();
        _ctx.current(_page.EnabledCheckboxSeting).waitelementToBeClickable();
        _ctx.current(_page.EnabledCheckboxSeting).click().
            current(_page.ScheduleFieldSeting).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();

        Helper.waitSetup(_driver,10000);
        openUrl();
        // verification
        Assert.assertEquals(_page.VerifiedBakupPanelOk.getText(),"OK",
                "Status verified backup not OK");
        Assert.assertEquals(_page.VerifiedBakupPanelSchedule.getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");
        Assert.assertEquals(_page.VerifiedBakupPanelTotal.getText(),"Total backup files: 1 (max depth 5)",
                "cron schedule must be displayed");

    }
}
