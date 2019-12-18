package TestPageLocator.DashboardPage;

import Helpers.Helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Database {
    private Helper ctx;
    WebDriver _driver;

    public Database(WebDriver driver) {

        this._driver = driver;
        ctx = new Helper(_driver);
    }

    /**
     * Databases settings
     */
    @FindBy(xpath = "//*[@name='add database']")
    public WebElement DatabaseSettingsBtn;
    @FindBy(id = "form-db-name")
    public WebElement DbNameField;
    @FindBy(xpath = "//*[@id='form-db-alias']//ancestor::div[@class='row']//*[@class='radio']//i")
    public WebElement DbAliasRadioBtn;
    @FindBy(id = "form-db-alias")
    public WebElement DbAliasField;
    @FindBy(xpath = "//*[@id='form-db-path']//ancestor::div[@class='row']//*[@class='radio']//i")
    public WebElement DbPathRadioBtn;
    @FindBy(id = "form-db-path")
    public WebElement DbPathField;
    @FindBy(id = "form-db-keyname")
    public WebElement DbCryptKeyNameField;
    @FindBy(id = "form-db-keyvalue")
    public WebElement DbCryptKeyValueField;
    @FindBy(id = "form-db-default-directory")
    public WebElement DbOutFolderField;
    @FindBy(xpath = "(//*[@class='alert alert-danger']//span)[2]")
    public WebElement DbAllertDanger;
    @FindBy(xpath = "(//*[@class='alert alert-info']//span)[2]")
    public WebElement DbAllertnfo;

    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement DbSaveBtn;
    /**
     * Databases active
     */
    @FindBy(xpath = "//*[@class='db-item-tab']//span")
    public WebElement NameBD;



}
