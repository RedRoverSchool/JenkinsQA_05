package tests;

import model.ExternalJenkinsPage;
import model.ManageJenkinsPage;
import model.RestApiPage;
import model.XmlPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FooterComponentTest extends BaseTest {

    @Test
    public void testFooterLinkRestRedirectToPage() {

        String urlRestApi = new RestApiPage(getDriver())
                .clickRestApiLink()
                .getCurrentURL();

        Assert.assertTrue(urlRestApi.contains("api"));
        Assert.assertEquals(new RestApiPage(getDriver()).getTextH1RestApi(), "REST API");
    }

    @Test
    public void testFooterLinkJenkinsRedirectToPage() {

         String textJenkins = new ExternalJenkinsPage(getDriver())
                 .clickJenkinsVersion()
                 .getHeaderText();

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

    @Test()
    public void testFooterLinkJenkinsIsClickable() {
        String headerJenkins = new ManageJenkinsPage(getDriver())
                .clickManageJenkins()
                .moveForClinkOnLink()
                .clickJenkinsVersion()
                .getHeaderText();

        Assert.assertEquals(headerJenkins,"Jenkins");
    }
}
