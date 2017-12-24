package selenium;

import org.openqa.selenium.By;

public final class SearchCriteria {
    private By by;

    private SearchCriteria(final String searchBy, final String searchStr) {
        if ("ClassName".equalsIgnoreCase(searchBy)) {
            by = By.className(searchStr);
        } else if ("CssSelector".equalsIgnoreCase(searchBy)) {
            by = By.cssSelector(searchStr);
        } else if ("Id".equalsIgnoreCase(searchBy)) {
            by = By.id(searchStr);
        } else if ("LinkText".equalsIgnoreCase(searchBy)) {
            by = By.linkText(searchStr);
        } else if ("Name".equalsIgnoreCase(searchBy)) {
            by = By.name(searchStr);
        } else if ("PartialLinkText".equalsIgnoreCase(searchBy)) {
            by = By.partialLinkText(searchStr);
        } else if ("TagName".equalsIgnoreCase(searchBy)) {
            by = By.tagName(searchStr);
        } else if ("XPath".equalsIgnoreCase(searchBy)) {
            by = By.xpath(searchStr);
        } else {
            throw new IllegalArgumentException(
                    "Can't recognize type of identifier for searching web element on page: "
                            + searchBy
            );
        }
    }

    public static SearchCriteria get(String stringRepresentation) {
        String configString = stringRepresentation.trim();
        return new SearchCriteria(
                configString.split("=", 2)[0],
                configString.split("=", 2)[1]
        );
    }

    public By getBy() {
        return by;
    }
}