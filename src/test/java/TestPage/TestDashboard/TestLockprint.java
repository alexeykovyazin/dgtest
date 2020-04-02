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

public class TestLockprint extends EnvContainer {
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
        _ctx.current(_page.LockprintSettingsBtn(TestDB)).scrollToElement().click();
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
        Helper.waitSetup(_driver, 2000);
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
        _ctx.current(_page.CheckPeriodField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();
//        Helper.waitSetup(_driver,4000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Deadlock Scans threshold\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledDeadlockScansThresholdEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.DeadlockScansThresholdField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we set a negative number in the \"Deadlock Scans threshold\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledDeadlockScansThresholdNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.DeadlockScansThresholdField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field  \"Deadlock threshold\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledDeadlockThresholdEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.DeadlockThresholdField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 5)
    @Description(value = "WHEN we set a negative number in the \"Deadlock threshold\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledDeadlockThresholdNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.DeadlockThresholdField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 6)
    @Description(value = "WHEN we leave the field  \"Mutex Wait threshold\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledMutexWaitThresholdEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.MutexWaitThresholdField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 7)
    @Description(value = "WHEN we set a negative number in the \"Mutex Wait threshold\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledMutexWaitThresholdNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.MutexWaitThresholdField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 8)
    @Description(value = "WHEN we leave the field  \"Hash Slots is less than\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledHashSlotsEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.HashSlotsField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 9)
    @Description(value = "WHEN we set a negative number in the \"Hash Slots is less than\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledHashSlotsNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.HashSlotsField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 10)
    @Description(value = "WHEN we leave the field  \"Min length is more than\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledMinLengthEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.MinLengthField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 11)
    @Description(value = "WHEN we set a negative number in the \"Min length is more than\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledMinLengthNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.MinLengthField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 12)
    @Description(value = "WHEN we leave the field  \"Average length is more than\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledAverageLengthEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.AverageLengthField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 13)
    @Description(value = "WHEN we set a negative number in the \"Average length is more than\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledAverageLengthNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.AverageLengthField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 14)
    @Description(value = "WHEN we leave the field  \"Owners limit\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledOwnersLimitEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.OwnersLimitField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 15)
    @Description(value = "WHEN we set a negative number in the \"Owners limit\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledOwnersLimitNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.OwnersLimitField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 16)
    @Description(value = "WHEN we leave the field  \"Free owners limit\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledFreeOwnersLimitEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.FreeOwnersLimitField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 17)
    @Description(value = "WHEN we set a negative number in the \"Free owners limit\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledFreeOwnersLimitNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.FreeOwnersLimitField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 18)
    @Description(value = "WHEN we leave the field  \"Lock table size\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledLockTableSizeEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.LockTableSizeField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 19)
    @Description(value = "WHEN we set a negative number in the \"Lock table size\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledLockTableSizeNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.LockTableSizeField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    @Test( enabled = true, priority = 20)
    @Description(value = "WHEN we leave the field  \"Warn if page buffers in database header more than [NNN]\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFiledBuffersMaxEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.BuffersMaxField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 21)
    @Description(value = "WHEN we set a negative number in the \"Warn if page buffers in database header more than [NNN]\" field THEN the error \" \"\" should be >= 0\"")
    public void testCreateFiledBuffersMaxNumbNegativeValue ()  {
        // prepare
        String negativeNumb = "-8";

        //actions
        _ctx.current(_page.BuffersMaxField).setValue(negativeNumb).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\""+negativeNumb+"\" should be >= 0",
                "the entered number must be greater than 0");

    }

    private void InitTest() {
        _ctx.current(_page.CheckPeriodField).setValue("4").
                current(_page.DeadlockScansThresholdField).setValue("1234").
                current(_page.DeadlockThresholdField).setValue("0").
                current(_page.MutexWaitThresholdField).setValue("18").
                current(_page.HashSlotsField).setValue("2047").
                current(_page.MinLengthField).setValue("18").
                current(_page.AverageLengthField).setValue("6").
                current(_page.OwnersLimitField).setValue("700").
                current(_page.FreeOwnersLimitField).setValue("1000").
                current(_page.LockTableSizeField).setValue("40000000").
                current(_page.BuffersMaxField).setValue("10000000");

    }


}
