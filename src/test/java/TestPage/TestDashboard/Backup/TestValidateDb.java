package TestPage.TestDashboard.Backup;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.*;

public class TestValidateDb extends EnvContainer {
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
        Helper.waitSetup(_driver, 1000);
        _ctx.current(_page.NameBDText(CloudTestDB)).click();
        Helper.waitSetup(_driver, 1000);
        _ctx.current(_page.ValidateDbSettingsBtn(CloudTestDB)).scrollToElement().click();
        _ctx.current(_page.ScheduleField).waitelementToBeClickable();
        Helper.waitSetup(_driver, 2000);
    }
//    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we set the wrong schedule in the validate bd settings THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckScheduleIncorrectField()  {

        //actions
        _ctx.current(_page.ScheduleField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "the set schedule is not correct");


    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Shutdown Timeout\" empty THEN, the error \"is not an integer number\"")
    public void testCheckShutdownTimeoutEmptyField ()  {
        // prepare
        InitTestValidateDb();
        //actions
        _ctx.current(_page.ShutdownTimeoutField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "the entered value must be a number");


    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we fill in the settings Validate BD with the correct data THEN the validate bd is successfully created")
    public void testCreateValidateBDCorrect ()  {
        // prepare
        String cron = "0/30 * * * * ?";
        InitTestValidateDb();
        //actions
        _ctx.current(_page.EnabledCheckbox).waitelementToBeClickable();
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.ScheduleField).setValue(cron).
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,30000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(CloudTestDB)).click();
        Helper.waitSetup(_driver,1000);

        // verification
        Assert.assertEquals(_page.ValidateDbPanelSchedule(CloudTestDB).getText(),"[scheduled "+cron+"]" ,
                "cron schedule must be displayed");
        Assert.assertEquals(_page.ValidateDbPanelOk(CloudTestDB).getText(),"OK",
                "Status verified backup not OK");



    }

    private void InitTestValidateDb() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-FRI").
                current(_page.ShutdownTimeoutField).setValue("10");

    }
}
