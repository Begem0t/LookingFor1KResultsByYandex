import org.apache.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.YandexPage;

public class YandexTest extends CommonTest {
    private static final Logger LOG = Logger.getLogger(CommonTest.class);

    @Parameters({"browser", "searchstring"})
    @Test(description = "check search")
    public void lookingFor1KResultsByYandex(@Optional("browser") String browser, @Optional("searchstring") String searchString) throws Exception {
        this.browser = browser;
        YandexPage page = getYandexPage("https://www.yandex.ru/");
        page.assertTitle("Яндекс")
                .isSetAsStartPageLinkPresent()
                .setSearchString(searchString)
                .runSearch()
                .isSearchResultPresent()
                .isMoreResultsButtonPresent();
        String srMessage = page.getSearchResultsMassage();
        if (!checkResults(srMessage))
            throw new Exception("The search results contain less thousands of results. " +
                    "Actual results is '" + srMessage + "'");
        LOG.info("Search result is - " + srMessage);

        /*//Yandex prepares only 65 pages of search results(the page numbering starts with 0),
        //even though it is found more results. The following block will always return an error.
        //the first and the second page contains 11 results, other 10
        String pageNumber = "99";
        String URL = page.getCurrentURL();
        if (URL == null)
            throw new RuntimeException("Null url was getting.");
        page.openURL(prepareURL(URL, pageNumber));
        if (page.is404OnPage()) {
            LOG.info("Page number 99 was not found. This was to be expected.");
        } else if (page.isTHElementPresent())
            LOG.info("TH element was not found. This was to be expected.");*/
    }

    private Boolean checkResults(String text) {
        text = text.replace("\u00a0", " ");
        String[] stringElements = text.split(" ");
        if (stringElements.length < 3) return false;
        if (stringElements[2].equalsIgnoreCase("млн"))
            return Integer.valueOf(stringElements[1]) >= 1;
        if (stringElements[2].equalsIgnoreCase("тыс."))
            return Integer.valueOf(stringElements[1]) >= 1;
        if (stringElements[2].equalsIgnoreCase("результатов"))
            return Integer.valueOf(stringElements[1]) >= 1000;
        return false;
    }

    private String prepareURL(String s, String pageNumber) {
        return s + "&p=" + pageNumber;
    }
}