package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends HomePage {

    @FindBy(xpath = "//div[@id='main-panel']/ol/li/a")
    private List<WebElement> searchResults;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getSearchResultList() {
        List<String> resultsList = new ArrayList<>();
        for (WebElement result : searchResults) {
            resultsList.add(result.getText().toLowerCase());
        }

        return resultsList;
    }
}
