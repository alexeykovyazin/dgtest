package TestPageLocator.DashboardPage;

import Helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Backup {
    private Helper ctx;
    WebDriver _driver;

    public Backup(WebDriver driver) {

        this._driver = driver;
        ctx = new Helper(_driver);
    }

    @FindBy(xpath = "(//*[@class='control'])[1]")
    public WebElement AddFirebirdButton;


    @FindBy(xpath = "(//*[@class='alert alert-danger']//span)[2]")
    public WebElement BackupAllertDanger;
    @FindBy(xpath = "(//*[@class='alert alert-info']//span)[2]")
    public WebElement BackupAllertnfo;



    /**
     * verified-backup settings
     */
    @FindBy(xpath = "//*[contains(@class,'panel panel-default tooltip-verified-backup')]//*[@class='control']")
    public WebElement VerifiedBackupSettingsBtn;
    @FindBy(xpath = "//*[@id='form-job-enabled']//ancestor::div[@class='checkbox']")
    public WebElement EnabledCheckboxSeting;
    @FindBy(id = "form-cron-expression")
    public WebElement ScheduleFieldSeting;
    @FindBy(id = "form-max-backups")
    public WebElement MaxNumbFilesFieldSeting;
    @FindBy(id = "form-backup-name")
    public WebElement NamePatternFieldSeting;
    @FindBy(id = "form-backup-extension")
    public WebElement ExtensionFieldSeting;
    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement DbSaveBtn;

    /**
     *
     * verified panel
     */

    @FindBy(xpath = "//*[contains(@class,'panel panel-default tooltip-verified-backup')]//strong")
    public WebElement VerifiedBakupPanelOk;
    @FindBy(xpath = "//*[contains(@class,'panel panel-default tooltip-verified-backup')]//font")
    public WebElement VerifiedBakupPanelSchedule;
    @FindBy(xpath = "//*[contains(@class,'panel panel-default tooltip-verified-backup')]//tr[3]/td")
    public WebElement VerifiedBakupPanelTotal;
    public WebElement NameBDText(String text) {return _driver.findElement(By.xpath("//*[@class='db-item-tab']//span[text()='"+text+"']")); }
}
