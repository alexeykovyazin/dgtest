import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import org.testng.ISuiteListener;
import org.testng.Reporter;
//import SuiteListener;

public class Listener extends TestListenerAdapter
{
    private int m_count = 0;
    
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log("Success");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log("Failure");
        SuiteListener.Failed += 1;
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log("Skipped");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        //SuiteListener.Failed += iTestContext.getFailedTests().size();
    }

    private void log(String string) {
        SuiteListener.log(string);
    }

}