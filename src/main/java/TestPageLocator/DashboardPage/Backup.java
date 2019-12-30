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
     * General locators
     */

    @FindBy(xpath = "//*[@id='formform-maxDuration_sec-job-enabled']//ancestor::div[@class='checkbox']")
    public WebElement EnabledCheckbox;
    @FindBy(id = "form-cron-expression")
    public WebElement ScheduleField;
    @FindBy(id = "form-max-backups")
    public WebElement MaxNumbFilesField;
    @FindBy(id = "form-backup-name")
    public WebElement NamePatternField;
    @FindBy(id = "form-journal-name")
    public WebElement JournalNameField;
    @FindBy(id = "form-backup-extension")
    public WebElement ExtensionField;
    @FindBy(id = "form-min-dstfreespace-toalert")
    public WebElement MinFreeSpaceField;
    @FindBy(id = "form-maxDuration_sec")
    public WebElement MaxDurationField;
    @FindBy(id = "form-backup-directory")
    public WebElement DirectoryField;
    @FindBy(id = "schcedulle-simple")
    public WebElement ScheduleSimpleBtn;
    @FindBy(id = "schcedulle-crone")
    public WebElement ScheduleAdvencedField;
    @FindBy(id = "form-max_misfire_delay_min")
    public WebElement PerformBackupDelayField;


    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement DbSaveBtn;

    /**
     * verified-backup settings
     */
    public WebElement VerifiedBackupSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-verified-backup')]//*[@class='control']")); }

    public WebElement VerifiedBackupSettingsBtn;

    /**
     *
     * verified panel
     */

    public WebElement VerifiedBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-verified-backup')]//strong")); }
    public WebElement VerifiedBackupPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-verified-backup')]//font")); }
    public WebElement VerifiedBackupPanelTotal(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-verified-backup')]//tr[3]/td")); }

    public WebElement NameBDText(String text) {return _driver.findElement(By.xpath("//*[@class='db-item-tab']//span[text()='"+text+"']")); }

    /**
     * dump-backup panel
     */
    public WebElement DumpBackupSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-dump-backup')]//*[@class='control']")); }

    public WebElement DumpBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-dump-backup')]//strong")); }
    public WebElement DumpBackupPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-dump-backup')]//font")); }

    /**
     * incremental-backup panel
     */
    public WebElement IncrementalBackupSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//*[@class='control']")); }

    public WebElement IncrementalBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//strong")); }
    public WebElement IncrementalBackupPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//font")); }

}
