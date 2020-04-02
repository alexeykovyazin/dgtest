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

import static Constants.InitData.CloudTestDB;
import static Constants.InitData.TestDB;

public class TestTransactions extends EnvContainer {
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
        Helper.waitSetup(_driver, 1000);
        if (BrowserType.equals("chrome")) {
            _ctx.current(_page.TransactionsSettingsBtn(TestDB)).click();
        }
        if(BrowserType.equals("firefox")){
            _ctx.current(_page.TransactionsSettingsBtn(TestDB)).scrollToElement().click();
        }
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
        //Helper.waitSetup(_driver,1000);
    }

    private void openUrl() {
        _driver.navigate().to(_standarturl);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        Helper.waitSetup(_driver, 1000);
    }

    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we leave the field  \"Period\" empty THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckPeriodEmptyField()  {

        //actions
        _ctx.current(_page.CheckPeriodField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Max transactions\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledMaxTransactionsEmptyField()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.MaxTransactionsField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }


    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Alert when OST-OIT more than\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledAlterOSTOITMoreEmptyField()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.OSTOITField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Alert when Next-OAT more than\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledAlterNextOATMoreEmptyField()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.NextOATField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }


    private void InitTest() {
        _ctx.current(_page.CheckPeriodField).setValue("4").
                current(_page.MaxTransactionsField).setValue("280000000000000").
                current(_page.OSTOITField).setValue("30000").
                current(_page.NextOATField).setValue("250000");

    }


}
