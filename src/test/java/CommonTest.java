import utils.PropertyReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterTest;
import pages.YandexPage;
import selenium.TestAgent;

abstract class CommonTest {
    private static final Logger LOG = Logger.getLogger(CommonTest.class);
    private static final String TEST_PROP = "test";
    String browser;
    private TestAgent testAgent;

    YandexPage getYandexPage(String url) {
        if (browser == null) {
            browser = PropertyReader.read(TEST_PROP, "defaultdriver");
        }

        try {
            testAgent = TestAgent.getAgent(browser);
        } catch (WebDriverException snfe) {
            LOG.error("При создании сессии произошла внутренная ошибка webdriver.");
            LOG.error(snfe);
        }
        YandexPage page = new YandexPage(testAgent);
        page.openURL(url);
        return page;
    }

    @AfterTest
    public void stopTest() {
        TestAgent.close(testAgent);
    }
}