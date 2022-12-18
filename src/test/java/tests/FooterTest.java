package tests;

import model.ExternalJenkinsPage;
import model.RestApiPage;
import model.XmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;


import static runner.TestUtils.scrollToEnd;

public class FooterTest extends BaseTest {

    private static final By REST_API_LINK = By.xpath("//a[@href='api/']");
    private static final By JENKINS_LINK = By.xpath("//a[@href='https://www.jenkins.io/']");

    @Test
    public void testFooterLinkRestIsDisplayed() {

        Assert.assertTrue(getDriver().findElement(REST_API_LINK).isDisplayed());
    }

    @Test
    public void testFooterLinkRestRedirectToPage() {

        getDriver().findElement(REST_API_LINK).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "REST API");
    }

    @Test
    public void testFooterLinkJenkinsIdDisplayed() {

        Assert.assertTrue(getDriver().findElement(JENKINS_LINK).isDisplayed());
    }

    @Test
    public void testFooterLinkJenkinsRedirectToPage() {

         String textJenkins = new ExternalJenkinsPage(getDriver())
                .clickJenkinsVersion()
                .getTextJenkins();

         Assert.assertTrue(new ExternalJenkinsPage(getDriver()).getCurrentURL().contains("jenkins"));
         Assert.assertEquals(textJenkins, "Jenkins");

    }

    @Test
    public void testFooterRestApiClickOnXmlApiDisplayXML() {
        XmlPage xmlPage = new RestApiPage(getDriver())
                .clickRestApiLink()
                .clickXmlApi();

        Assert.assertEquals(xmlPage.getStructureXML(), "This XML file does not appear to have any "
                + "style information associated with it. The document tree is shown below.");
    }

    @Test
    public void testFooterLinkJenkinsIsVisible() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        scrollToEnd(getDriver());
        new Actions(getDriver()).pause(1500).moveToElement(getDriver().findElement(JENKINS_LINK))
                .perform();

        Assert.assertTrue(getDriver().findElement(JENKINS_LINK).isDisplayed());
    }

    @Test(dependsOnMethods = "testFooterLinkJenkinsIsVisible")
    public void testFooterLinkJenkinsIsClickable() {
        scrollToEnd(getDriver());
        new Actions(getDriver()).pause(1500).moveToElement(getDriver().findElement(JENKINS_LINK))
                .click().perform();

        ArrayList<String> newJenkins = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(newJenkins.get(1));

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='page-title']")).getText()
                , "Jenkins");

        getDriver().switchTo().window(newJenkins.get(0));
    }
}
