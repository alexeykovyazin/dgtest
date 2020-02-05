package TestPage.TestDashboard.Replica;

import Constants.InitData;
import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Database;
import TestPageLocator.GeneralLocators;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static Constants.InitData.*;

public class TestReplica extends EnvContainer {
    private WebDriver _driver;

    public String _url, _standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _pagedatabase;
    private GeneralLocators _page;
    private Helper _ctx;

    @BeforeClass
    public void classSetUp() {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, GeneralLocators.class);
        _pagedatabase = PageFactory.initElements(_driver, Database.class);
        openUrl();
        Helper.waitSetup(_driver, 1000);


    }

    //    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//    }
    private void openUrl() {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);

    }

    @Test( enabled = true, priority = 1)
    @Description(value ="WHEN we create a replica \"master sync\" with an empty field \"ReplicaDatabase\" THEN, the error \"Incorrect connection string: Do not use aliases! Use explicit path to the database!\"")
    public void testCreateMasterSync_EmptyReplicaDatabaseField()  {
          ClickAndCheckBd(MasterSyncBD);
        //actions
        _ctx.current(_page.MasterBtn).click().waitUpdate().
                current(_page.SyncBtn).click().waitUpdate().
                current(_page.ReplicaDatabaseField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).doubleClick().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Incorrect connection string: Do not use aliases! Use explicit path to the database!",
                "Incorrect connection string: Do not use aliases! Use explicit path to the database!");

    }

    @Test( enabled = true, priority = 2)
    @Description(value ="WHEN we create a replica \"master sync\" with the correct fields THEN replica \"master sync\" the successfully created")
    public void testCreateMasterSync_Correct()  {

        //actions
        _ctx.current(_page.ReplicaDatabaseField).setValue("//sysdba:masterkey@replicaserver:"+Replica_Sync_DB_Path+"").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertTrue( _page.ReplicaBtn(MasterSyncBD).getAttribute("src").contains("replicationactive.png"), "Icon replicationactive should change");

    }

    @Test( enabled = true, priority = 3)
    @Description(value ="WHEN we create a replica \"replica sync\" with the correct fields THEN replica \"master sync\" the successfully created")
    public void testCreateReplicaSync_Correct()  {
        openUrl();
        ClickAndCheckBd(ReplicaSyncBD);
        //actions
        _ctx.current(_page.ReplicaBtn).click().waitUpdate().
                current(_page.SyncBtn).click().waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertTrue( _page.ReplicaBtn(ReplicaSyncBD).getAttribute("src").contains("replicationslave.png"), "Icon replicationslave should change");

    }


    @Test(enabled = true, priority = 4)
    @Description(value = "WHEN we create a replica \"master async\" with an empty field \"LogDirectory\" THEN, the error \"Specify log directory\"")
    public void testCreateMasterAsync_EmptyLogDirectoryField() {
        openUrl();
        ClickAndCheckBd(MasterAsyncBD);

        //actions
        _ctx.current(_page.MasterBtn).click().waitUpdate().
                current(_page.AsyncBtn).click().waitUpdate().
                current(_page.MoreBtn).click().waitSetup(_driver,1000);
        _ctx.current(_page.LogDirectoryField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver, 1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(), "Specify log directory",
                "Field Log directory must not be empty");

    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_EmptyLogDirectoryField", priority = 5)
    @Description(value = "WHEN we create a replica \"master async\" with an empty field \"LogArchiveDirectory\" THEN, the error \"Specify log archive directory\"")
    public void testCreateMasterAsync_EmptyLogArchiveDirectoryField() {
        InitAsyncTest();
        //actions
        _ctx.current(_page.LogArchiveDirectoryField).setValue("").
                current(_page.DbSaveBtn).doubleClick().waitUpdate();
        Helper.waitSetup(_driver, 1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(), "Specify log archive directory",
                "Field Log archive directory must not be empty");

    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_EmptyLogArchiveDirectoryField", priority = 6)
    @Description(value = "WHEN we create a replica \"master async\" with an incorrect field \"LogArchiveTimeout\" THEN, the error \"Parameter \"Write commited data every NN seconds\" must be integer (seconds)!\"")
    public void testCreateMasterAsync_IncorrectLogArchiveTimeoutField() {
        InitAsyncTest();
        //actions
        _ctx.current(_page.LogArchiveTimeoutField).setValue("....").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(), "Parameter \"Write commited data every NN seconds\" must be integer (seconds)!",
                "Field must be integer");

    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_IncorrectLogArchiveTimeoutField", priority = 7)
    @Description(value = "WHEN we create a replica \"master async\" with the correct fields THEN replica \"master async\" the successfully created")
    public void testCreateMasterAsync_Correct() throws IOException{
        InitAsyncTest();

        //actions
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver, 1000);

        // verification
        Assert.assertTrue( _page.ReplicaBtn(MasterAsyncBD).getAttribute("src").contains("replicationactive.png"), "Icon replicationactive should change");

    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_Correct", priority = 8)
    @Description(value = "WHEN we run the script to create the table THEN there are no errors in the log and there is a file .arch in folder LogArch")
    public void testCreateMasterAsync_CheckCreateTable() throws IOException{

        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"restartserver.bat -Verb runAs");
        Helper.waitSetup(Driver, 5000);
        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"restartdg.bat -Verb runAs");
        Helper.waitSetup(Driver, 70000);
        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"createTable.bat -Verb runAs");
        Helper.waitSetup(Driver, 5000);
        String textFromFile = _ctx.read(""+Logs_folder_path+"createTable.log");
        Assert.assertFalse(textFromFile.contains("failed"),"the error should not be displayed in the file");
        _ctx.existFileExt(Master_Async_folder_path_LogArch, ".arch");
    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_CheckCreateTable", priority = 9)
    @Description(value = "WHEN we perform Reinitialize Replica Database Master Async THEN Reinitialize successfully completed")
    public void testCreateMasterAsync_ReinitializeReplicaDatabaseCorrect()  {

        openUrl();
        ClickAndCheckBd(MasterAsyncBD);
        // InitAsyncTest();
        //actions
        _ctx.current(_page.MasterBtn).click().waitUpdate().
                current(_page.AsyncBtn).click().waitUpdate().
                current(_page.ReinitializeReplicaDatabaseBtn).click();
        Helper.waitSetup(_driver, 1000);
        _driver.switchTo().alert().accept();
        Helper.waitSetup(_driver, 1000);
        _driver.switchTo().alert().accept();
        Helper.waitSetup(_driver, 10000);

        //verification
        Assert.assertTrue(_page.ReinitializeReplicaStatus(Master_Async_DB_Path).isDisplayed(), "Reinitialize db path must be displayed");
        _ctx.current(_page.OkBtn).click().waitUpdate();
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
    }

    @Test(enabled = true,dependsOnMethods="testCreateMasterAsync_ReinitializeReplicaDatabaseCorrect", priority = 10)
    @Description(value = "WHEN we create a replica \"replica async\" with an empty field \"LogArchiveDirectory\" THEN, the error \"Specify log archive directory\"")
    public void testCreateReplicaAsync_EmptyLogArchiveDirectoryField() {
        openUrl();
        _ctx.findExpFilesDelete(Master_Async_folder_path,".irreport");
        _ctx.findExpFilesRenameCopy(Master_Async_folder_path,".4replica");
        AddDatabase(ReplicaAsyncBD,Replica_Async_DB_Path);
        ClickAndCheckBd(ReplicaAsyncBD);
        //actions
        _ctx.current(_page.ReplicaBtn).click().waitUpdate().
                current(_page.AsyncBtn).click().waitUpdate().
                current(_page.MoreBtn).click().waitSetup(_driver,1000);
        _ctx.current(_page.LogArchiveDirectoryField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver, 1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(), "Specify log archive directory",
                "Field Log directory must not be empty");

    }

    @Test(enabled = true,dependsOnMethods="testCreateReplicaAsync_EmptyLogArchiveDirectoryField", priority = 11)
    @Description(value = "WHEN we create a replica \"replica async\" with the correct fields THEN replica \"replica async\" the successfully created")
    public void testCreateReplicaAsync_Correct() {

        //actions
        _ctx.current(_page.LogArchiveDirectoryField).setValue("${db.path}.LogArch").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertTrue( _page.ReplicaBtn(ReplicaAsyncBD).getAttribute("src").contains("replicationslave.png"), "Icon replicationslave should change");

    }

    @Test( enabled = true, dependsOnMethods="testCreateReplicaAsync_Correct",priority = 12)
    @Description(value = "WHEN we fill in the settings FTP with the correct data THEN the FTP is successfully created")
    public void testAddFtpCorrect() {
        openUrl();
        Helper.waitSetup(_driver,2000);
        //prepare
        _ctx.current(_page.NameBDText(MasterAsyncBD)).click();
        Helper.waitSetup(_driver,2000);
        _ctx.current(_page.CloudBackupSettingsBtn(MasterAsyncBD)).scrollToElement().click();
        _ctx.current(_page.CheckPeriodField).waitelementToBeClickable();
        _ctx.current(_page.ConfigureFtpBtn).click();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.UploadFtpCheckbox).waitelementToBeClickable();
        _ctx.hoverAndClick(_page.UploadFtpCheckbox);
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.FtpServerField).setValue("localhost").
                current(_page.FtpPortField).setValue("8721").
                current(_page.FtpUserField).setValue("admin2").
                current(_page.FtpPasswordField).setValue("strong password2").
                current(_page.FtpStoreFolderField).setValue("").
                current(_page.FtpSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.StatusFtp.getAttribute("title"),"Active",
                "Status ftp must be Active");

    }

    @Test( enabled = true,dependsOnMethods="testAddFtpCorrect", priority = 13)
    @Description(value = "WHEN we fill in the settings Cloud Backup with the correct data THEN the backup is successfully created")
    public void testAddCloudBackupCorrect()  {
        // prepare
        _ctx.current(_page.EnabledCheckbox).click().
                current(_page.CheckPeriodField).setValue("5").
                current(_page.PerformFreshBackupCheckbox).click();

        //actions
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver,5000);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(MasterAsyncBD)).click();
        Helper.waitSetup(_driver,1000);
        // verification
        Assert.assertEquals(_page.CloudBackupPanelOk(MasterAsyncBD).getText(),"OK",
                "Status Cloud backup not OK");
        Assert.assertEquals(_page.CloudBackupPanelPeriod(MasterAsyncBD).getText(),"[period 5 sec]" ,
                "cron schedule must be displayed");
        Assert.assertTrue( _page.CloudBackupPanelLastSendFile(MasterAsyncBD).getText().contains("MASTERASYNC.FDB.arch-"), "Name file must be displayed");


    }

    //TODO:Add regexp to assertTrue
    @Test( enabled = true,dependsOnMethods="testAddCloudBackupCorrect", priority = 14)
    @Description(value = "WHEN we fill in the settings Cloud Backup Receiver with the correct data THEN the backup is successfully created")
    public void testAddCloudBackupReceiverCorrect() throws IOException {
        //openUrl();
        //actions

        _ctx.current(_page.NameBDText(ReplicaAsyncBD)).click();
        Helper.waitSetup(_driver,2000);
        _ctx.current(_page.CloudBackupReceiverSettingsBtn(ReplicaAsyncBD)).scrollToElement().click();
        _ctx.current(_page.CheckPeriodField).waitelementToBeClickable();
        Helper.waitSetup(_driver,1000);
        _ctx.current(_page.EnabledCheckbox).click().waitUpdate().
             current(_page.CheckPeriodField).setValue("5").
             current(_page.MonitorFolderField).setValue("C:\\dgtest\\src\\test\\resurces\\Ftp").
             current(_page.DbSaveBtn).click().waitUpdate();

        Helper.waitSetup(_driver,5);
        openUrl();
        _ctx.implicitlyWaitElement();
        _ctx.current(_page.NameBDText(ReplicaAsyncBD)).click();

        // verification
        Assert.assertEquals(_page.CloudBackupReceiverPanelOk(ReplicaAsyncBD).getText(),"OK" ,
                "Status cloud backup receiver not OK");
        Assert.assertEquals(_page.CloudBackupReceiverPanelPeriod(ReplicaAsyncBD).getText(),"[period 5 sec]" ,
                "cron schedule must be displayed");
        Assert.assertTrue( _page.CloudBackupReceiverPanelLastSendFile(ReplicaAsyncBD).getText().contains("MASTERASYNC.FDB.arch-"), "Name file must be displayed");

    }

    @Test(enabled = true,dependsOnMethods="testAddCloudBackupReceiverCorrect", priority = 15)
    @Description(value = "WHEN we run the script to select the table THEN there are no errors in the log and value displayed , count 1")
    public void testCreateReplicaAsync_CheckSelectTable() throws IOException{

        Runtime.getRuntime().exec("powershell.exe  Start-Process "+Batfile_folder_path+"selectTable.bat -Verb runAs");
        Helper.waitSetup(Driver, 5000);
        String textFromFile = _ctx.read(""+Logs_folder_path+"selectTable.log");
        Assert.assertTrue(textFromFile.contains("COUNT"),"log must be COUNT");
        Assert.assertTrue(textFromFile.contains("1"),"log must be \"1\"");
    }


    private void InitAsyncTest() {
        _ctx.current(_page.LogDirectoryField).setValue("${db.path}.ReplLog").
                current(_page.LogArchiveDirectoryField).setValue("${db.path}.LogArch").
                current(_page.LogArchiveTimeoutField).setValue("5");

    }

    private void ClickAndCheckBd(String dbname) {
        _ctx.current(_page.NameBDText(dbname)).click();
        _ctx.current(_page.ReplicaBtn(dbname)).click();
        _ctx.current(_page.MasterBtn).waitelementToBeClickable();
        Helper.waitSetup(_driver, 1000);

    }

    private void AddDatabase(String dbName, String dbPath) {
        //actions
        Helper.waitSetup(Driver, 1000);
        _ctx.current(_pagedatabase.DatabaseSettingsBtn).click();
        _ctx.current(_pagedatabase.DbNameField).waitelementToBeClickable();
        _ctx.current(_pagedatabase.DbNameField).setValue(dbName).
                current(_pagedatabase.DbPathField).setValue(dbPath).
                current(_pagedatabase.DbSaveBtn).click().waitUpdate();
        Assert.assertTrue(_ctx.isdisplayedElement(_pagedatabase.NameBD(dbName)), "database is not successfully add");

    }
}
