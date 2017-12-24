package selenium;

import utils.PropertyReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumManager {
    private static final String SELENIUM_PROP = "selenium";
    private static final Logger LOG = Logger.getLogger("myLogger");

    public static WebDriver getDriver(final String browser) {
        return getDriver(retrieveCapabilities(browser));
    }

    private static WebDriver getDriver(Capabilities capabilities) {
        String browserType = capabilities.getBrowserName();
        if (browserType.startsWith("internet explorer")) {
            LOG.debug("IE driver was successfully launched!");
            return new InternetExplorerDriver(capabilities);
        } else if (browserType.startsWith("chrome")) {
            LOG.debug("Chrome driver was successfully launched!");
            return new ChromeDriver(capabilities);
        }
        throw new Error("Unrecognized browser type: " + browserType);
    }

    private static DesiredCapabilities retrieveCapabilities(String browserName) {
        DesiredCapabilities capabilities = null;
        if ("iexplorer".equalsIgnoreCase(browserName)) {
            System.setProperty("webdriver.ie.driver", PropertyReader.read(SELENIUM_PROP, "iedriverhome"));
            capabilities = DesiredCapabilities.internetExplorer();
        } else if ("chrome".equalsIgnoreCase(browserName)) {
            System.setProperty("webdriver.chrome.driver", PropertyReader.read(SELENIUM_PROP, "chromedriverhome"));
            capabilities = DesiredCapabilities.chrome();
        }
        return capabilities;
    }
}