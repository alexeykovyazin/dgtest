package TestPageLocator.DashboardPage;

import Helpers.Helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Dashboard {
    private Helper ctx;
    WebDriver _driver;

    public Dashboard(WebDriver driver) {

        this._driver = driver;
        ctx = new Helper(_driver);
    }

    @FindBy(xpath = "(//*[@class='control'])[1]")
    public WebElement AddFirebirdButton;

    /**
     * Register Firebird server
     */
    @FindBy(id = "form-server-installation")
    public WebElement ServerInstallField;
    @FindBy(id = "form-server-bin")
    public WebElement BinaryFolderField;
    @FindBy(id = "form-server-log")
    public WebElement LogField;
    @FindBy(id = "form-server-config")
    public WebElement ServerConfigField;
    @FindBy(id = "form-server-aliases")
    public WebElement AliasesField;
    @FindBy(id = "form-server-host")
    public WebElement HostField;
    @FindBy(id = "form-server-port")
    public WebElement PortField;
    @FindBy(id = "form-server-sysdba-login")
    public WebElement SysdbaLoginField;
    @FindBy(id = "form-server-sysdba-password")
    public WebElement PasswordField;
    @FindBy(id = "form-server-default-directory")
    public WebElement OutputFolderField;
    @FindBy(id = "form-server-trusted-auth")
    public WebElement UseTrustedAuthCheckbox;
    @FindBy(xpath = "(//*[@class='modal-content']//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement SaveButton;

    /**
     * Databases
     */
}
