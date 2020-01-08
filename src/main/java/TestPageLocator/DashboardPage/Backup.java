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

    @FindBy(xpath = "//*[@id='form-job-enabled']//ancestor::div[@class='checkbox']")
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
    @FindBy(xpath = "//*[@id='schcedulle-simple']//ancestor::label/i")
    public WebElement ScheduleSimpleBtn;
    @FindBy(xpath = "//*[@id='schcedulle-crone']//ancestor::label/i")
    public WebElement ScheduleAdvencedField;
    @FindBy(id = "form-max_misfire_delay_min")
    public WebElement PerformBackupDelayField;
    @FindBy(id = "form-period")
    public WebElement CheckPeriodField;
    @FindBy(id = "form-monitor_directory")
    public WebElement MonitorFolderField;
    @FindBy(id = "form-archive_file_template")
    public WebElement FilenameTemplateField;
    @FindBy(id = "form-attempts_to_fail")
    public WebElement FiledConnectionFtpField;
    @FindBy(id = "form-keep_n_source_files")
    public WebElement KeepNsourceFilesField;
    @FindBy(id = "form-archive_extension")
    public WebElement ArchiveExtentionField;
    @FindBy(id = "form-decrypt_password")
    public WebElement DecryptPasswordField;
    @FindBy(id = "form-alert_file_count")
    public WebElement AlertFileCountField;
    @FindBy(id = "form-alert_file_age_m")
    public WebElement AlertFileAgeField;
    @FindBy(id = "form-shutdown-timeout")
    public WebElement ShutdownTimeoutField;
    @FindBy(id = "form-sqltext")
    public WebElement SqlQueryField;

     /**
     *  FTP settings
      */
    @FindBy(xpath = "(//*[@class='dq-grid-ftp']//*[@class='status-icon icon-config'])[1]")
    public WebElement ConfigureFtpBtn;
    @FindBy(xpath = "//*[@id='form-UploadToFTP']//ancestor::div[@class='checkbox']//label")
    public WebElement UploadFtpCheckbox;
    @FindBy(xpath = "//*[@id='form-section-FTPserver']//*[@id='form-FTPserver']")
    public WebElement FtpServerField;
    @FindBy(xpath = "//*[@id='form-section-FTPPort']//*[@id='form-FTPPort']")
    public WebElement FtpPortField;
    @FindBy(xpath = "//*[@id='form-section-FTPUser']//*[@id='form-FTPUser']")
    public WebElement FtpUserField;
    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[3]")
    public WebElement FtpSaveBtn;
    @FindBy(xpath = "//*[@class='alert alert-danger']//span")
    public WebElement BackupAllertDangerFtp;

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
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//tr[3]/td/b[1]")); }
    public WebElement IncrementalBackupPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//tr[2]/td/b")); }

    /**
     * cloud-backup panel
     */
    public WebElement CloudBackupSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//*[@class='control']")); }
    public WebElement CloudBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//tr[3]/td/b[1]")); }
    public WebElement CloudBackupPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//tr[2]/td/b")); }

    /**
     * cloud-backup-receiver panel
     */
    public WebElement CloudBackupReceiverSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//*[@class='control']")); }
    public WebElement CloudBackupReceiverPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//tr[3]/td/b[1]")); }
    public WebElement CloudBackupReceiverPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//tr[2]/td/b")); }

    /**
     * validate-db panel
     */
    public WebElement ValidateDbSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//*[@class='control']")); }
    public WebElement ValidateDbPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//tr[3]/td/b[1]")); }
    public WebElement ValidateDbPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//font")); }
    public WebElement ValidateDbPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//tr[2]/td/b")); }

     /**
     * restoredb panel
     */
    public WebElement RestoreDbSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-restoredb')]//*[@class='control']")); }
    public WebElement RestoreDbPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-restoredb')]//tr[3]/td/b[1]")); }
    public WebElement RestoreDbPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-restoredb')]//tr[2]/td/b")); }

    /**
     * sql-ping panel
     */
    public WebElement SqlPingSettingsBtn(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-sql-ping')]//*[@class='control']")); }
    public WebElement SqlPingPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-sql-ping')]//strong")); }
    public WebElement SqlPingPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-sql-ping')]//font")); }

}
