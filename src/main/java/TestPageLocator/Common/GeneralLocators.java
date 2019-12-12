package TestPageLocator.Common;

import Helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GeneralLocators {
    private Helper ctx;
    WebDriver _driver;

    public GeneralLocators(WebDriver driver) {

        this._driver = driver;
        ctx = new Helper(_driver);
    }


}
