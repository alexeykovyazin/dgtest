package TestPage.TestDashboard.Replica;

import Constants.InitData;
import Helpers.Helper;
import TestPage.EnvContainer;
import TestPageLocator.DashboardPage.Backup;
import TestPageLocator.DashboardPage.Database;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Constants.InitData.*;

public class TestReplica extends EnvContainer {
    private WebDriver _driver;

    public String _url,_standarturl = "http://localhost:8082/static/dashboard.html";
    private Database _pagedatabase;
    private Backup _page;
    private Helper _ctx;

    @BeforeClass
    public void classSetUp()
    {
        _driver = EnvContainer.Driver;
        _ctx = new Helper(_driver);
        _page = PageFactory.initElements(_driver, Backup.class);
        _pagedatabase = PageFactory.initElements(_driver, Database.class);
        openUrl(MasterSyncBD);
        Helper.waitSetup(_driver, 1000);
        //_ctx.current(_page.NameBDText(MasterSyncBD)).click();

    }
//    @AfterMethod
//    public void methodTearDown() {
//       // openUrl();
//        Helper.interceptionJSonPage(_driver);
//    }
    private void openUrl(String nameBD) {
        _url = EnvContainer.URL + _standarturl;
        _driver.navigate().to(_url);
        Helper.interceptionJSonPage(_driver);
        Helper.waitUpdate(_driver);
        _ctx.current(_page.NameBDText(nameBD)).click();
    }

    @Test( enabled = true, priority = 1)
    @Description(value ="WHEN we create a replica \"master sync\" with an empty field \"ReplicaDatabase\" THEN, the error \"Incorrect connection string: Do not use aliases! Use explicit path to the database!\"")
    public void testCreateMasterSync_EmptyReplicaDatabaseField()  {
        _ctx.current(_page.ReplicaBtn(MasterSyncBD)).click();
        //actions
        _ctx.current(_page.MasterBtn).click().waitUpdate().
                current(_page.SyncBtn).click().waitUpdate().
                current(_page.ReplicaDatabaseField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).doubleClick().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Incorrect connection string: Do not use aliases! Use explicit path to the database!",
                "Incorrect connection string: Do not use aliases! Use explicit path to the database!");

    }

    //TODO::Add correct link, regex
    @Test( enabled = true, priority = 2)
    @Description(value ="WHEN we create a replica \"master sync\" with the correct fields THEN replica \"master sync\" the successfully created")
    public void testCreateMasterSync_Correct()  {

        //actions
        _ctx.current(_page.ReplicaDatabaseField).setValue("//sysdba:masterkey@replicaserver:/C:/dgtest/src/test/resurces/WorkDB/ReplicaSynch.fdb").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.ReplicaBtn(MasterSyncBD).getAttribute("src"),"http://admin:strong%20password@localhost:8082/static/img/icons32/replicationactive.png",
                "Icon should change");

    }

    @Test( enabled = true, priority = 3)
    @Description(value ="WHEN we create a replica \"replica sync\" with the correct fields THEN replica \"master sync\" the successfully created")
    public void testCreateReplicaSync_Correct()  {
        openUrl(ReplicaSyncBD);
        _ctx.current(_page.ReplicaBtn(ReplicaSyncBD)).click();
        //actions
        _ctx.current(_page.ReplicaBtn).click().waitUpdate().
                current(_page.SyncBtn).click().waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.ReplicaBtn(ReplicaSyncBD).getAttribute("src"),"http://admin:strong%20password@localhost:8082/static/img/icons32/replicationslave.png",
                "Icon should change");

    }

    @Test( enabled = true, priority = 4)
    @Description(value ="WHEN we create a replica \"master async\" with an empty field \"LogDirectory\" THEN, the error \"Specify log directory\"")
    public void testCreateMasterAsync_EmptyLogDirectoryField()  {
        openUrl(MasterAsyncBD);
        _ctx.current(_page.ReplicaBtn(MasterAsyncBD)).click();
        //actions
        _ctx.current(_page.MasterBtn).click().waitUpdate().
                current(_page.AsyncBtn).click().waitUpdate().
                current(_page.MoreBtn).click().waitUpdate().
                current(_page.LogDirectoryField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Specify log directory",
                "Field Log directory must not be empty");

    }

    @Test( enabled = true, priority = 5)
    @Description(value ="WHEN we create a replica \"master async\" with an empty field \"LogArchiveDirectory\" THEN, the error \"Specify log archive directory\"")
    public void testCreateMasterAsync_EmptyLogArchiveDirectoryField()  {
        InitAsyncTest();
        //actions
        _ctx.current(_page.LogArchiveDirectoryField).setValue("").waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Specify log archive directory",
                "Field Log archive directory must not be empty");

    }

    @Test( enabled = true, priority = 6)
    @Description(value ="WHEN we create a replica \"master async\" with an incorrect field \"LogArchiveTimeout\" THEN, the error \"Parameter \"Write commited data every NN seconds\" must be integer (seconds)!\"")
    public void testCreateMasterAsync_IncorrectLogArchiveTimeoutField()  {
        InitAsyncTest();
        //actions
        _ctx.current(_page.LogArchiveTimeoutField).setValue("....").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Parameter \"Write commited data every NN seconds\" must be integer (seconds)!",
                "Field must be integer");

    }

   //TODO::Alert windows
    @Test( enabled = true, priority = 7)
    @Description(value ="not found")
    public void testCreateMasterAsync_ReinitializeReplicaDatabaseCorrect()  {
//        InitAsyncTest();
//        //actions
//        _ctx.current(_page.ReinitializeReplicaDatabaseBtn).click().waitUpdate();
//        _driver.switchTo().alert().accept();
//        _driver.switchTo().alert().accept();
//        Helper.waitSetup(_driver, 1000);

        // verification
      //  Assert.assertEquals(_page.BackupAllertDanger.getText(),"Incorrect connection string: Do not use aliases! Use explicit path to the database!",
      //          "Incorrect connection string: Do not use aliases! Use explicit path to the database!");

    }

    @Test( enabled = true, priority = 8)
    @Description(value ="WHEN we create a replica \"master async\" with the correct fields THEN replica \"master async\" the successfully created")
    public void testCreateMasterAsync_Correct()  {
        InitAsyncTest();

        //actions
        _ctx.current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.ReplicaBtn(MasterSyncBD).getAttribute("src"),"http://admin:strong%20password@localhost:8082/static/img/icons32/replicationactive.png",
                "Icon should change");

    }


    @Test( enabled = true, priority = 9)
    @Description(value ="WHEN we create a replica \"replica async\" with an empty field \"LogArchiveDirectory\" THEN, the error \"Specify log archive directory\"")
    public void testCreateReplicaAsync_EmptyLogArchiveDirectoryField()  {
        openUrl(ReplicaAsyncBD);
        _ctx.current(_page.ReplicaBtn(ReplicaAsyncBD)).click();
        //actions
        _ctx.current(_page.ReplicaBtn).click().waitUpdate().
                current(_page.AsyncBtn).click().waitUpdate().
                current(_page.MoreBtn).click().waitUpdate().
                current(_page.LogArchiveDirectoryField).setValue("").waitUpdate().
                current(_page.DbSaveBtn).click().waitUpdate();
        Helper.waitSetup(_driver, 1000);
        // verification
        Assert.assertEquals(_page.BackupAllertDanger.getText(),"Specify log archive directory",
                "Field Log directory must not be empty");

    }

    @Test( enabled = true, priority = 10)
    @Description(value ="WHEN we create a replica \"replica async\" with the correct fields THEN replica \"replica async\" the successfully created")
    public void testCreateReplicaAsync_Correct()  {

        //actions
        _ctx.current(_page.LogArchiveDirectoryField).setValue("${db.path}.LogArch").
                current(_page.DbSaveBtn).click().waitUpdate();

        // verification
        Assert.assertEquals(_page.ReplicaBtn(ReplicaAsyncBD).getAttribute("src"),"http://admin:strong%20password@localhost:8082/static/img/icons32/replicationslave.png",
                "Icon should change");

    }
    private void InitAsyncTest() {
        _ctx.current(_page.LogDirectoryField).setValue("${db.path}.ReplLog").
                current(_page.LogArchiveDirectoryField).setValue("${db.path}.LogArch").
                current(_page.LogArchiveTimeoutField).setValue("90");

    }
}
