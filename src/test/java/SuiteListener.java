
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.testng.ISuiteListener;
import org.testng.Reporter;
import org.testng.ISuite;

public class SuiteListener implements  ISuiteListener {
    public static  Integer Failed = 0;

    
    @Override
    public void onStart(ISuite arg0) {
        //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out), 1), true));
        
        log("Starting " + arg0.getAllMethods().size() + " tests: ");
    }

    // This belongs to ISuiteListener and will execute, once the Suite is finished

    @Override
    public void onFinish(ISuite arg0) {

        if (Failed != 0) {
            log("\nFailed " + Failed + " of " + arg0.getAllMethods().size() + "\n\n\n");
        }
        else {
            log("OK\n\n");
        }
        System.out.println("");
        // Reporter.log("About to end executing Suite " + arg0.getName(), true);

    }

    public static void log(String string) {
        PrintStream s = System.err;
        s.print(string);
        s.flush();
    }

}