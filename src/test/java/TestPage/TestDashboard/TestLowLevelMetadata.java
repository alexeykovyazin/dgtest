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

public class TestLowLevelMetadata extends EnvContainer {
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
        _ctx.current(_page.LowLevelMetadataBackupSettingsBtn(TestDB)).scrollToElement().click();
        Assert.assertTrue(_ctx.tryFindBy(_page.DialogForm()),"Element Dialog Form not found");
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

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Cron expression or period must be set properly",
                "Cron expression installed incorrectly");

    }

    @Test( enabled = true, priority = 2)
    @Description(value = "WHEN we leave the field  \"Store metadata in\" empty THEN, the error \"should not be null or empty\"")
    public void testCheckFieldMetadataFolderEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.MetadataFolderField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "the field must not be empty");

    }

    @Test( enabled = true, priority = 3)
    @Description(value = "WHEN we leave the field  \"Max formats\" empty THEN, the error \"is not an integer number\"")
    public void testCheckFieldMaxFormatsEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.MaxFormatsField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" is not an integer number",
                "value must be a number");

    }

    @Test( enabled = true, priority = 4)
    @Description(value = "WHEN we leave the field  \"Date format for folder name\" empty THEN, the error \"should not be null or empty\"")
    public void testCheckFieldDateFormatFolderNameEmptyValue()  {
        // prepare
        InitTest();

        //actions
        _ctx.current(_page.DateFormatFolderNameField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"\"\" should not be null or empty",
                "the field must not be empty");

    }

    private void InitTest() {
        _ctx.current(_page.CheckPeriodField).setValue("200").
                current(_page.MetadataFolderField).setValue("${db.default-directory}/${job.id}").
                current(_page.DateFormatFolderNameField).setValue("{0,date,yyyy.MM.dd_HHmmss}").
                current(_page.MetadataFolderField).setValue("240");

    }


}
