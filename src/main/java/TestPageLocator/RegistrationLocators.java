package TestPageLocator;

import Helpers.Helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class RegistrationLocators {
    private Helper ctx;
    WebDriver _driver;

    public RegistrationLocators(WebDriver driver) {

        this._driver = driver;
        ctx = new Helper(_driver);
    }

    @FindBy(xpath = "//*[@class='regcollapsebtn']")
    public WebElement RegWidgetBtn;
    @FindBy(xpath = "(//*[@id='form-section-RegisterdType'])[3]")
    public WebElement EnterpriseCheckbox;
    @FindBy(id = "form-RegisterdEmail")
    public WebElement EmailField;
    @FindBy(id = "form-RegisterdPwd")
    public WebElement PasswordField;
    @FindBy(xpath = "//*[@id='dgRegInputDataDivID']//*[contains(@class,'btn-dialog-ok')]")
    public WebElement ActiveButton;

    @FindBy(xpath = "//*[@id='registration-status-widget']//*[@class='alert alert-danger']")
    public WebElement AllertRegistr;

    /**
     * Register Firebird server
     */

    public boolean isDisplayAlertReg(){
        try {
            _driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            AllertRegistr.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
