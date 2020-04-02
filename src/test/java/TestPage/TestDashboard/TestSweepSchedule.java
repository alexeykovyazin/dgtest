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

public class TestSweepSchedule extends EnvContainer {
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
        _ctx.current(_page.SweepScheduleSettingsBtn(TestDB)).scrollToElement().click();
        //Assert.assertTrue(_ctx.tryFindWebElement(_page.ScheduleField),"Element Schedule Field not found");
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
    }

    private void openUrl() {
        _driver.navigate().to(_standarturl);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        Helper.waitSetup(_driver, 1000);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we leave the field empty \"Period\" field THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckPeriodEmptyField()  {

        //actions
        _ctx.current(_page.ScheduleField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field empty \"Disconnect processes older than\" field THEN, the error \"is not an integer number\"")
    public void testCheckFiledDeadlockScansThresholdEmptyValue()  {
        // prepare
        InitTest();
        Helper.waitSetup(_driver,1000);
        //actions
        _ctx.current(_page.DisconnectProcessesField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we set a negative number in the \"Disconnect processes older than\" field THEN the error \" \"\" should be >= 1\"")
    public void testCreateFiledDeadlockScansThresholdNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.DisconnectProcessesField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 1",
                "the entered number must be greater than 0");

    }



    private void InitTest() {
        _ctx.current(_page.ScheduleField).setValue("0 0 23 ? * MON-SUN").
                current(_page.DisconnectProcessesField).setValue("30");

    }


}
