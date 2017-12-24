package pages;

import selenium.SearchCriteria;
import selenium.TestAgent;

class Page {
    private TestAgent _agent;

    Page(TestAgent testAgent) {
        _agent = testAgent;
    }

    public void openURL(String url) {
        _agent.openUrl(url);
    }

    String getInnerText(String s) {
        return _agent.getInnerText(SearchCriteria.get(s));
    }

    String getTitle() {
        return _agent.getTitle();
    }

    boolean isElementPresent(String s) {
        try {
            return _agent.isDisplayed(SearchCriteria.get(s));
        } catch (org.openqa.selenium.TimeoutException te) {
            return false;
        }
    }

    public String getCurrentURL() {
        return _agent.getCurrentURL();
    }

    Boolean isDisplayed(String criteria) {
        return _agent.isDisplayed(SearchCriteria.get(criteria));
    }

    void click(String criteria) {
        _agent.click(SearchCriteria.get(criteria));
    }

    void setValue(String criteria, String value) {
        _agent.setValue(SearchCriteria.get(criteria), value);
    }
}