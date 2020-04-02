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

import static Constants.InitData.BackupBD;

public class TestSqlPing extends EnvContainer {
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
        _ctx.current(_page.SqlPingSettingsBtn(BackupBD)).scrollToElement().click();
        //Assert.assertTrue(_ctx.tryFindWebElement(_page.ScheduleField),"Element Schedule Field not found");
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
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
    @Description(value = "WHEN we set the wrong schedule in the Sql ping settings THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckScheduleIncorrect()  {

        //actions
        _ctx.current(_page.ScheduleField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "the set schedule is not correct");


    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Sql Query\" empty THEN, the error \"should not be null or empty\"")
    public void testCheckSqlQueryEmptyField ()  {

        //actions
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-FRI").
                current(_page.SqlQueryField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "the entered value must be a number");


    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we fill in the settings Sql Ping with the correct data THEN the sql ping is successfully created")
    public void testCreateSqlPingCorrect ()  {
        // prepare
        String cron = "0/30 * * * * ?";
        InitTestValidateDb();
        //actions
        _ctx.current(_page.EnabledCheckbox).waitelementToBeClickable();
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,40000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(BackupBD)).click();
        Helper.waitSetup(_driver,1000);

        // verification
        Assert.assertEquals(_page.SqlPingPanelOk(BackupBD).getText(),"OK",
                "Status sql ping not OK");



    }

    private void InitTestValidateDb() {
        _ctx.current(_page.ScheduleField).setValue("0 0/5 * ? * *").
                current(_page.SqlQueryField).setValue("select count(*) from RDB$TYPES R1, RDB$TYPES R2, RDB$TYPES R3, rdb$relations R4 where R1.rdb$type_name like 'WIN%' and R2.rdb$type_name like 'DOS%' and R3.rdb$type_name like 'ISO%' and R4.rdb$relation_id < 23");

    }
}
