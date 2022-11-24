package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<String> getTextFromListWebElement(List<WebElement> webElementList){
        return webElementList.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static String createRandomName(int stringLength){
        return RandomStringUtils.randomAlphanumeric(stringLength);
    }
}
