package pages;

import selenium.TestAgent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class YandexPage extends Page {
    private enum Variables {
        set_as_run_page_link("xpath=//a[contains(text(),'Сделать стартовой')]"),
        search_string("xpath=//*[@id=\"text\"]"),
        search_button("xpath=(//button[@type='submit'])[2]"),
        no_results_message("className=misspell__message"),
        found_results_link("CssSelector=div.serp-adv__found"),
        more_results_button("linktext=дальше"),
        err404("CssSelector=h1.content__title"),
        th_element("xpath=//li[8]/div/h2/a");

        private String criteria;

        Variables(String criteria) {
            this.criteria = criteria;
        }

        public String get() {
            return criteria;
        }
    }

    public YandexPage(TestAgent testAgent) {
        super(testAgent);
    }

    public YandexPage setSearchString(String value) {
        setValue(Variables.search_string.get(), value);
        return this;
    }

    public YandexPage assertTitle(String value) {
        assertEquals(getTitle(), value);
        return this;
    }

    public YandexPage runSearch() {
        click(Variables.search_button.get());
        return this;
    }

    public YandexPage isSetAsStartPageLinkPresent() {
        assertTrue(isDisplayed(Variables.set_as_run_page_link.get()));
        return this;
    }

    public YandexPage isSearchResultPresent() throws Exception {
        if (isElementPresent(Variables.no_results_message.get())) {
            if (getInnerText(Variables.no_results_message.get()).equalsIgnoreCase("По вашему запросу ничего не нашлось"))
                throw new Exception("Nothing was found!");
        }
        assertTrue(isElementPresent(Variables.found_results_link.get()));
        return this;
    }

    public String getSearchResultsMassage() {
        return getInnerText(Variables.found_results_link.get());
    }

    public YandexPage isMoreResultsButtonPresent() {
        assertTrue(isElementPresent(Variables.more_results_button.get()));
        return this;
    }

    public Boolean is404OnPage() {
        return isElementPresent(Variables.err404.get());
    }

    public Boolean isTHElementPresent() {
        return isElementPresent(Variables.th_element.get());
    }
}