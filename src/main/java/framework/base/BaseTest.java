package framework.base;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    public static String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        Configuration.getResourcesFromPropertyFile();
        baseUrl = Configuration.getUrl();
    }

    @AfterMethod(alwaysRun = true)
    public void afterAll(ITestResult result) {

    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
