package TestPage.TestPerformance;

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

public class TestTransactionMonitoring extends EnvContainer {
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
        _ctx.current(generalpage.PerformanceMenuBtn).click();
        _ctx.current(_page.PerformancePageCheck).waitelementToBeClickable();
    }

    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }

    /**
     * Long running active transactions monitoring
     */
    @Test( enabled = true, priority = 1)
    @Description(value = "WHEN we leave the field \"Period\" empty THEN, the error \"Cron expression or period must be set properly\"")
    public void testCheckPeriodEmptyField()  {

        //actions
        _ctx.current(_page.ConfigureTransactionBtn(TestDB)).click();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.ScheduleField).setValue("");
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Output folder\" empty THEN, the error \"should not be null or empty\"")
    public void testCheckFieldOutputFolderEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.OutputFolderField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Show transactions older than (minutes)\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledShowTransactionsOlderEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.ShowTransactionsOlderField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we set a negative number in the \"Show transactions older than (minutes)\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledShowTransactionsOlderNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.ShowTransactionsOlderField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we leave the field  \"Send alert if oldest active transaction is older than (minutes)\" empty THEN, the error \"is not an integer number\"")
    public void testCheckSendAlertOldestActiveTransactionEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.SendAlertOldestActiveTransactionField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 6)
    @Description(value = "WHEN we set a negative number in the \"Send alert if oldest active transaction is older than (minutes)\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateSendAlertOldestActiveTransactionNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.SendAlertOldestActiveTransactionField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 7)
    @Description(value = "WHEN we leave the field  \"Show only NN oldest active transactions\" empty THEN, the error \"is not an integer number\"")
    public void testCheckShowOnlyNNOldestActiveTransactionsEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.ShowOnlyNNOldestActiveTransactionsField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 8)
    @Description(value = "WHEN we set a negative number in the \"Show only NN oldest active transactions\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateShowOnlyNNOldestActiveTransactionsNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.ShowOnlyNNOldestActiveTransactionsField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    private void InitTest() {
        _ctx.current(_page.ScheduleField).setValue("0 0/5 * ? * *").
                current(_page.OutputFolderField).setValue("${db.default-directory}/${job.id}/monstorage").
                current(_page.ShowTransactionsOlderField).setValue("60").
                current(_page.SendAlertOldestActiveTransactionField).setValue("60").
                current(_page.ShowOnlyNNOldestActiveTransactionsField).setValue("50");

    }


}
