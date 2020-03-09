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
    @FindBy(id = "performance-db")
    public WebElement PerformancePageCheck;
    @FindBy(xpath = "//*[@id='dqTimers']//li//i")
    public WebElement PanelPageRefreshBtn;
    @FindBy(xpath = "//*[@id='dqTimers']//*[@id='0']")
    public WebElement StopRefreshBtn;

    @FindBy(xpath = "(//*[@class='alert alert-danger']//span)[2]")
    public WebElement BackupAllertDanger;
    @FindBy(xpath = "(//*[@class='alert alert-info']//span)[2]")
    public WebElement BackupAllertnfo;

    /**
     * Add server locators
     */
    @FindBy(xpath = "(//*[@class='control'])[1]")
    public WebElement AddFirebirdButton;
    @FindBy(id = "form-server-installation")
    public WebElement ServerInstallFolderButton;
    @FindBy(id = "form-server-bin")
    public WebElement ServerBinaryFolderButton;
    @FindBy(xpath = "(//*[@class='modal-content']//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement SaveServerButton;

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
    @FindBy(id = "form-unpack_directory")
    public WebElement UnpackDirectoryField;
    @FindBy(id = "form-archive_file_template")
    public WebElement FilenameTemplateField;
    @FindBy(id = "form-attempts_to_fail")
    public WebElement FiledConnectionFtpField;
    @FindBy(id = "form-keep_n_source_files")
    public WebElement KeepNsourceFilesField;
    @FindBy(xpath = "//*[@id='form-FreshBackup']//ancestor::label//span")
    public WebElement PerformFreshBackupCheckbox;
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
    @FindBy(id = "form-deleay_limit_to_alert_ms")
    public WebElement LimitMsField;
    @FindBy(id = "form-sqltext")
    public WebElement SqlQueryField;
    @FindBy(id = "form-max_fileage_torestore_h")
    public WebElement MaxFileageTorestoreHField;
    @FindBy(id = "form-restore_dir")
    public WebElement RestoreDirField;
    @FindBy(xpath = "(//*[@id='form-section-delete_existing_db']//label[2])[1]")
    public WebElement ReplaceExistingDb;
    @FindBy(id = "form-restore_dbname")
    public WebElement RestoreDbNameField;
    @FindBy(id = "form-addname_to_backup_extisting_db")
    public WebElement AppendSuffixFileNameField;
    @FindBy(id = "form-max-duration")
    public WebElement LimitRestoreProcTimeField;
    @FindBy(id = "form-RestoreTimeLimit_min")
    public WebElement RestoreTimeLimitField;
    /**
     * REPLICA
     */
    public WebElement ReplicaBtn(String nameDB) {return _driver.findElement(By.xpath("//*[@class='db-item-tab']//span[text()='"+nameDB+"']//ancestor::div[1]//img[@class='status-icon icon-replication']")); }
    @FindBy(xpath = "//*[@id='form-section-db-replication_role']//*[@value='master']//ancestor::label[2]")
    public WebElement MasterBtn;
    @FindBy(xpath = "//*[@id='form-section-db-replication_role']//*[@value='replica']//ancestor::label[2]")
    public WebElement ReplicaBtn;
    @FindBy(xpath = "//*[@id='form-section-db-replication_mode']//*[@value='async']//ancestor::label[2]")
    public WebElement AsyncBtn;
    @FindBy(xpath = "//*[@id='form-section-db-replication_mode']//*[@value='sync']//ancestor::label[2]")
    public WebElement SyncBtn;
    @FindBy(id = "form-db-replica_database")
    public WebElement ReplicaDatabaseField;
    @FindBy(id = "form-db-repparam_log_directory")
    public WebElement LogDirectoryField;
    @FindBy(id = "form-db-repparam_log_archive_directory")
    public WebElement LogArchiveDirectoryField;
    @FindBy(id = "form-db-repparam_log_archive_timeout")
    public WebElement LogArchiveTimeoutField;
    @FindBy(xpath = "//*[@id='link-reinitialyze-repl']//button")
    public WebElement ReinitializeReplicaDatabaseBtn;
    @FindBy(id = "btnReplMoL")
    public WebElement MoreBtn;
    public WebElement ReinitializeReplicaStatus(String puthDB)
    {return _driver.findElement(By.xpath("//*[@class='modal-body']//tr[3]//*[contains(text(),'"+puthDB+"')]")); }

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
    @FindBy(xpath = "//*[@id='form-section-FTPpass']//*[@id='form-FTPpass']")
    public WebElement FtpPasswordField;
    @FindBy(xpath = "//*[@id='form-section-FTPStoreFolder']//*[@id='form-FTPStoreFolder']")
    public WebElement FtpStoreFolderField;
    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[3]")
    public WebElement FtpSaveBtn;
    @FindBy(xpath = "(//*[@class='alert alert-danger']//span)[last()]")
    public WebElement BackupAllertDangerFtp;
    @FindBy(xpath = "//*[@class='dq-grid-ftp']//*[@id='col-status']//img")
    public WebElement StatusFtp;

    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[2]")
    public WebElement DbSaveBtn;

    @FindBy(xpath = "(//*[contains(@class,'btn-dialog-ok')])[3]")
    public WebElement OkBtn;

    /**
     * Tranzactions
     */
    @FindBy(id = "form-max-transactions")
    public WebElement MaxTransactionsField;
    @FindBy(id = "form-unusual-transaction-gap")
    public WebElement OSTOITField;
    @FindBy(id = "form-big-transaction-gap")
    public WebElement NextOATField;

    /**
     * Lockprint
     */
    @FindBy(id = "form-max-deadlock-scans")
    public WebElement DeadlockScansThresholdField;
    @FindBy(id = "form-max-deadlocks")
    public WebElement DeadlockThresholdField;
    @FindBy(id = "form-max-mutex-wait")
    public WebElement MutexWaitThresholdField;
    @FindBy(id = "form-min-hashslots")
    public WebElement HashSlotsField;
    @FindBy(id = "form-max-minhash-lengths")
    public WebElement MinLengthField;
    @FindBy(id = "form-max-avghash-lengths")
    public WebElement AverageLengthField;
    @FindBy(id = "form-max-owners")
    public WebElement OwnersLimitField;
    @FindBy(id = "form-max-free-owners")
    public WebElement FreeOwnersLimitField;
    @FindBy(id = "form-max-lengh-size")
    public WebElement LockTableSizeField;
    @FindBy(id = "form-maxBuffers")
    public WebElement BuffersMaxField;
    /**
     * Sweep schedule
     */
    @FindBy(id = "form-disconnect_minutes")
    public WebElement DisconnectProcessesField;
    /**
     * Delta
     */
    @FindBy(id = "form-max-delta-size")
    public WebElement MaxDeltaBytesField;
    @FindBy(id = "form-max-delta-age")
    public WebElement MaxDeltaMinutesField;

    /**
     * Space
     */
    @FindBy(id = "form-free-space-limit-percent")
    public WebElement FreeSpaceMinPercentField;
    @FindBy(id = "form-free-space-limit-bytes")
    public WebElement FreeSpaceMinBytesField;

    /**
     * DB Status
     */
    @FindBy(id = "form-folder")
    public WebElement StoreStatisticField;
    @FindBy(id = "form-max-depth")
    public WebElement StatisticsArchiveDepthField;
    @FindBy(id = "form-file-name-pattern")
    public WebElement StatisticsFileNamePatternField;


    /**
     * Index statistics
     */
    @FindBy(id = "form-size-limit")
    public WebElement DBSizeToSwitchField;

    /**
     * Low level metadata backup
     */
    @FindBy(id = "form-metadata-folder")
    public WebElement MetadataFolderField;
    @FindBy(id = "form-date-pattern")
    public WebElement DateFormatFolderNameField;
    @FindBy(id = "form-max-formats")
    public WebElement MaxFormatsField;

    /**
     *  PERFORMANCE PAGE
     */
    public WebElement ConfigureTransactionBtn(String text) {return _driver.findElement(By.xpath("//*[@id='performance_tabletools']//*[text()='"+text+"']//ancestor::tr//td[6]")); }
    public WebElement ConfigurePerformanceBtn(String text) {return _driver.findElement(By.xpath("//*[@id='performance_tabletools']//*[text()='"+text+"']//ancestor::tr//td[7]")); }

    @FindBy(id = "form-output_folder")
    public WebElement OutputFolderField;
    @FindBy(id = "form-trn_time_threshold_min")
    public WebElement ShowTransactionsOlderField;
    @FindBy(id = "form-trn_alert_threshold_min")
    public WebElement SendAlertOldestActiveTransactionField;
    @FindBy(id = "form-top_first")
    public WebElement ShowOnlyNNOldestActiveTransactionsField;
    @FindBy(id = "form-time_threshold_ms")
    public WebElement LogSQLsWithExecutionеimeField;
    @FindBy(id = "form-cron-expression-end")
    public WebElement StopTraceSessionField;

    /**
     *  settings button
     */
    public WebElement SettingsBtn(String text,String classname) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default "+classname+"')]//*[@class='control'][@name='configure']")); }
    public WebElement VerifiedBackupSettingsBtn(String text) { return SettingsBtn(text,"tooltip-verified-backup"); }
    public WebElement DumpBackupSettingsBtn(String text) { return SettingsBtn(text,"tooltip-dump-backup"); }
    public WebElement IncrementalBackupSettingsBtn(String text) { return SettingsBtn(text,"tooltip-incremental-backup"); }
    public WebElement CloudBackupSettingsBtn(String text) { return SettingsBtn(text,"tooltip-cloud-backup"); }
    public WebElement CloudBackupReceiverSettingsBtn(String text) { return SettingsBtn(text,"tooltip-cloud-backup-receiver"); }
    public WebElement ValidateDbSettingsBtn(String text) { return SettingsBtn(text,"tooltip-validate-db"); }
    public WebElement RestoreDbSettingsBtn(String text) { return SettingsBtn(text,"tooltip-restoredb"); }
    public WebElement SqlPingSettingsBtn(String text) { return SettingsBtn(text,"tooltip-sql-ping"); }
    public WebElement TransactionsSettingsBtn(String text) { return SettingsBtn(text,"tooltip-transactions"); }
    public WebElement LockprintSettingsBtn(String text) { return SettingsBtn(text,"tooltip-lockprint"); }
    public WebElement SweepScheduleSettingsBtn(String text) { return SettingsBtn(text,"tooltip-sweep-schedule"); }
    public WebElement DeltaSettingsBtn(String text) { return SettingsBtn(text,"tooltip-delta"); }
    public WebElement DiskSpaceSettingsBtn(String text) { return SettingsBtn(text,"tooltip-disk-space"); }
    public WebElement DBStatusSettingsBtn(String text) { return SettingsBtn(text,"tooltip-db-stats"); }
    public WebElement IndexStatisticsSettingsBtn(String text) { return SettingsBtn(text,"tooltip-index-statistics-recalculation"); }
    public WebElement LowLevelMetadataBackupSettingsBtn(String text) { return SettingsBtn(text,"tooltip-low-level-metadata-backup"); }


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
    public WebElement DumpBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-dump-backup')]//strong")); }
    public WebElement DumpBackupPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-dump-backup')]//font")); }

    /**
     * incremental-backup panel
     */
    public WebElement IncrementalBackupPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//tr[3]/td/b[1]")); }
    public WebElement IncrementalBackupPanel(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-incremental-backup')]//tr[2]/td/b")); }

    /**
     * cloud-backup panel
     */
    public WebElement CloudBackupPanelOk(String text) {return _driver.findElement(By.xpath("(//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//tr//strong)[1]")); }
    public WebElement CloudBackupPanelPeriod(String text) {return _driver.findElement(By.xpath("(//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//tr//font)[1]")); }
    public WebElement CloudBackupPanelLastSendFile (String dbName) {return _driver.findElement(By.xpath("(//*[@class='head-name panel-heading']//*[text()='"+dbName+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup')]//tr[3]//b)[1]")); }

    /**
     * cloud-backup-receiver panel
     */
    public WebElement CloudBackupReceiverPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//tr//strong")); }
    public WebElement CloudBackupReceiverPanelPeriod(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//tr//font")); }
    public WebElement CloudBackupReceiverPanelLastSendFile (String dbName) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+dbName+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-cloud-backup-receiver')]//tr[3]//b")); }

    /**
     * validate-db panel
     */
    public WebElement ValidateDbPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//tr//strong")); }
    public WebElement ValidateDbPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-validate-db')]//font")); }

    /**
     * restoredb panel
     */
    public WebElement RestoreDbPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-restoredb')]//tr[1]//strong")); }
    public WebElement RestoreDbSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-restoredb')]//tr[1]//font")); }

    /**
     * sql-ping panel
     */
    public WebElement SqlPingPanelOk(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-sql-ping')]//strong")); }
    public WebElement SqlPingPanelSchedule(String text) {return _driver.findElement(By.xpath("//*[@class='head-name panel-heading']//*[text()='"+text+"']" +
            "//ancestor::div[2]//*[contains(@class,'panel panel-default tooltip-sql-ping')]//font")); }

}
