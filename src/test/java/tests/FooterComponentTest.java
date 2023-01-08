package tests;

import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FooterComponentTest extends BaseTest {

    @Test
    public void testFooterLinkRestRedirectToPage() {

        String urlRestApi = new HomePage(getDriver())
                .getFooter()
                .clickRestApiLink()
                .getCurrentURL();

        Assert.assertTrue(urlRestApi.contains("api"));
        Assert.assertEquals(new RestApiPage(getDriver()).getTextH1RestApi(), "REST API");
    }

    @Test
    public void testFooterLinkJenkinsRedirectToPage() {

        String textJenkins = new HomePage(getDriver())
                .getFooter()
                .clickJenkinsVersion()
                .getHeaderText();

        Assert.assertTrue(new ExternalJenkinsPage(getDriver()).getCurrentURL().contains("jenkins"));
        Assert.assertEquals(textJenkins, "Jenkins");

    }

    @Test
    public void testFooterRestApiClickOnXmlApiDisplayXML() {
        XmlPage xmlPage = new HomePage(getDriver())
                .getFooter()
                .clickRestApiLink()
                .clickXmlApi();

        Assert.assertEquals(xmlPage.getStructureXML(), "This XML file does not appear to have any "
                + "style information associated with it. The document tree is shown below.");
    }

//    @Test
//    public void testFooterLinkJenkinsIsClickable() {
//        String headerJenkins = new ManageJenkinsPage(getDriver())
//                .clickManageJenkins()
//                .moveForClinkOnLink()
//                .clickJenkinsVersion()
//                .getHeaderText();
//
//        Assert.assertEquals(headerJenkins, "Jenkins");
//    }
}
