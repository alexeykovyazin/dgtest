package TestPage.TestCommon;

import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.Common.GeneralLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestDashboard extends EnvContainer {
    private WebDriver _driver;

    public String _url;
    private GeneralLocators _page;
    private Helper ctx;

    @BeforeClass
    public void suiteSetUp()
    {
        _driver = EnvContainer.Driver;
        ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, GeneralLocators.class);
       // openUrl("?page=home&aspect=home");
    }
//    @AfterMethod
//    public void tearDown() {
//        openUrl("?page=home&aspect=home");
//        //ctx.current(_page.HomeItemMenu).click();
//    }
    private void openUrl(String standarturl) {
        _url = EnvContainer.URL + standarturl;
        _driver.navigate().to(_url);
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
    }
    /**
     *
     */
    @Test( enabled = true, priority = 1)
    public void testHome()  {

    }



}
