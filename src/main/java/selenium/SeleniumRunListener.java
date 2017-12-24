package selenium;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;

public class SeleniumRunListener extends TestListenerAdapter {
    private static final Logger LOG = Logger.getLogger(SeleniumRunListener.class);

    @Override
    public void onTestSkipped(ITestResult testResult) {
        try {
            Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
            LOG.error("Skipped: " + testMethod.getName(), testResult.getThrowable());
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        LOG.info("Success: " + testMethod.getName(), testResult.getThrowable());
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        LOG.info("Failed: " + testMethod.getName(), testResult.getThrowable());
    }
}