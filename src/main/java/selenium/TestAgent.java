package selenium;

import utils.PropertyReader;
import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestAgent {
    private static final Logger LOG = Logger.getLogger(TestAgent.class);
    private static final String SELENIUM_PROP = "selenium";
    private WebDriver _driver;

    private TestAgent(WebDriver wDriver) {
        _driver = wDriver;
    }

    public static TestAgent getAgent(String browser) {
        WebDriver driver = SeleniumManager.getDriver(browser);
        driver.manage().window().maximize();
        return new TestAgent(driver);
    }

    public static void close(TestAgent testAgent) {
        try {
            testAgent.getWebDriver().quit();
            LOG.debug("The driver was successfully closed.");
        } catch (WebDriverException ex) {
            LOG.error("it can already be dead or unreachable");
            LOG.error(ex);
        }
    }

    private static Long getLongFromProp(String name) {
        return Long.valueOf(PropertyReader.read(SELENIUM_PROP, name));
    }

    private static String getStringFromProp(String name) {
        return PropertyReader.read(SELENIUM_PROP, name);
    }

    private WebDriver getWebDriver() {
        return _driver;
    }

    public String getCurrentURL() {
        return _driver.getCurrentUrl();
    }

    private Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
        return driver1 -> {
            WebElement element;
            element = driver1.findElement(locator);
            try {
                if (element.isDisplayed()) return element;
            } catch (final Exception ignore) {
            }
            return null;
        };
    }

    private WebElement getElement(final By locator) {
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(_driver, getLongFromProp("timeout"), getLongFromProp("sleeptime"));
            wait.ignoring(WebDriverException.class)
                    .ignoring(StaleElementReferenceException.class);
        } catch (final RuntimeException e) {
            LOG.error("It was runtime exception !! \n" + e);
        }
        return wait.until(presenceOfElementLocated(locator));
    }

    public void openUrl(final String URL) {
        _driver.navigate().to(URL);
    }

    public String getTitle() {
        return _driver.getTitle();
    }

    public String getInnerText(final SearchCriteria criteria) {
        return getElement(criteria.getBy()).getAttribute("innerText");
    }

    public void setValue(final SearchCriteria criteria, final String value) {
        final WebElement element = getElement(criteria.getBy());
        element.clear();
        element.sendKeys(value);
    }

    public void click(final SearchCriteria criteria) {
        getElement(criteria.getBy()).click();
    }

    public boolean isDisplayed(SearchCriteria criteria) {
        return getElement(criteria.getBy()).isDisplayed();
    }
}