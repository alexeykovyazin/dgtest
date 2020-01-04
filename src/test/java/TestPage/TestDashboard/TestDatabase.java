package TestPage.TestDashboard;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.*;

public class TestDatabase extends EnvContainer {
    private WebDriver _driver;

    public String _url, _standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _page;
    private Helper _ctx;

    @BeforeClass
    public void suiteSetUp()
    {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, Database.class);
        openUrl();

        _ctx.hoverAndClick(_page.DatabaseSettingsBtn);
        _ctx.current(_page.DbNameField).waitelementToBeClickable();
    }
    @AfterMethod
    public void tearDown() {
        Helper.interceptionJSonPage(_driver);
    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        _ctx.current(_page.DatabaseSettingsBtn).waitelementToBeClickable();
    }

    // WHEN we add a database with empty fields THEN the error "DB path or DB alias values must be defined"
    @Test( enabled = true, priority = 1)
    public void testAddDbEmptyPath()  {

        //actions
        _ctx.current(_page.DbNameField).setValue("").
                current(_page.DbPathField).setValue("").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.DbAllertDanger.getText(),"One of DB path or DB alias values must be defined, but both are empty",
                "DB path or DB alias not be empty");
    }


    // WHEN we add a database without a name THEN we get the error "Database name must not be empty"
    @Test( enabled = true, priority = 2)
    public void testAddDbEmptyName()  {

        //actions
        _ctx.current(_page.DbNameField).setValue("").
                current(_page.DbPathField).setValue(DB_TestA_FDB).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.DbAllertDanger.getText(),"\"\" should not be null or empty",
                "Database name must be filled");
    }

    // When we add a database with an incorrect path to the database THEN the error "Path not found" is displayed
    @Test( enabled = true, priority = 3)
    public void testAddDbIncorrectPath()  {

        String path = "testpath";

        //actions
        _ctx.current(_page.DbNameField).setValue("TestName").
                current(_page.DbPathField).setValue(path).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.DbAllertDanger.getText(),"[C:\\HQbird\\Firebird DataGuard\\"+path+"] not found",
                "Path not found");
    }

    // When we add a database with an incorrect alias to the database THEN the error "Database alias is unknown ..." is displayed
    @Test( enabled = true, priority = 4)
    public void testAddDbIncorrectAlias()  {

        String path1 = "testpathaliase";

        //actions
        _ctx.current(_page.DbNameField).setValue("TestName").
                current(_page.DbAliasRadioBtn).click().waitUpdate();
        _ctx.current(_page.DbAliasField).waitelementToBeClickable();
        _ctx.current(_page.DbAliasField).setValue(path1).
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.DbAllertDanger.getText(),
                "Database alias ["+path1+"] is unknown, not exists in server aliases file (alias.conf or database.conf)",
                "Database alias incorrect");
    }

    // WHEN we add the database with the correct values THEN the database is successfully added
    @Test( enabled = true, priority = 5)
    public void testAddDbCorrect()  {

        //actions
        _ctx.current(_page.DbNameField).setValue(NameBDA).
                current(_page.DbPathRadioBtn).click().
                current(_page.DbPathField).setValue(DB_TestC_FDB).
                current(_page.DbSaveBtn).click().waitUpdate();
        _ctx.implicitlyWaitElement();

        // verification
        Assert.assertTrue(_ctx.isdisplayedElement(_page.NameBD(NameBDA)),"database is not successfully add");
    }

    // WHEN we add the database with the correct values THEN the database is successfully added
    @Test( enabled = true, priority = 6)
    public void testAddDbAlreadyExist()  {
        // prepare
        openUrl();

        // actions
        _ctx.hoverAndClick(_page.DatabaseSettingsBtn);
        _ctx.current(_page.DbNameField).waitelementToBeClickable();
        _ctx.current(_page.DbNameField).setValue(NameBDA).
                current(_page.DbPathField).setValue(DB_TestC_FDB).
                current(_page.DbSaveBtn).click().waitUpdate();
        _ctx.implicitlyWaitElement();

        // verification
        Assert.assertTrue( _page.DbAllertDanger.getText().contains("Database ["+DB_TestC_FDB+"] is already registered:"),
                "database is not successfully add");
    }
}
