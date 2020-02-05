package TestPage.TestDashboard;

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

import static Constants.InitData.TestDB;

public class TestIndexStatistics extends EnvContainer {
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
        _ctx.current(_page.NameBDText(TestDB)).click();
        _ctx.current(_page.IndexStatisticsSettingsBtn(TestDB)).scrollToElement().click();
        _ctx.current(_page.ScheduleField).waitelementToBeClickable();
    }

    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we leave the field  \"Period\" empty THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckFieldPeriodEmptyValue()  {

        //actions
        _ctx.current(_page.ScheduleField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"DB size to switch, bytes\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFieldStatisticsArchiveDepthEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.DBSizeToSwitchField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }


    private void InitTest() {
        _ctx.current(_page.ScheduleField).setValue("0 30 20 ? * MON-FRI").
                current(_page.DBSizeToSwitchField).setValue("4000000000");

    }


}
