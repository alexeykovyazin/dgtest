import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
//import SuiteListener;

public class Listener extends TestListenerAdapter
{
    private int m_count = 0;

    @Override
    public void onStart(ITestContext context) {
      log("===================\n");
      log("Start tests - " + context.getName()+"\n");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log(iTestResult.getName()+" - Success\n");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log(iTestResult.getName()+" - Failure\n");
        SuiteListener.Failed += 1;
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log(iTestResult.getName()+" - Skipped\n");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        //SuiteListener.Failed += iTestContext.getFailedTests().size();
    }

    private void log(String string) {
        SuiteListener.log(string);
    }


}