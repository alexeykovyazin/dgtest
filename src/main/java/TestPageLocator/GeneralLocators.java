package TestPageLocator;

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

    @FindBy(id = "servers")
    public WebElement DashboardMenuBtn;
    @FindBy(id = "notifications")
    public WebElement AlertsMenuBtn;
    @FindBy(id = "registration")
    public WebElement RegistrationMenuBtn;
    @FindBy(id = "graphs")
    public WebElement GraphsGalleryMenuBtn;
    @FindBy(id = "performance")
    public WebElement PerformanceMenuBtn;

}
