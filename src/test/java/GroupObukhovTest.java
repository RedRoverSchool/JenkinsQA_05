import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupObukhovTest extends BaseTest {

    private final String URL = "https://urent.ru/";

    private final String URLFranchise = "https://start.urent.ru/";

    private List<WebElement> getMainMenu() {
        return getDriver().findElements(By.xpath("//ul[@class=\"menu-list\"]/li"));
    }

    private List<WebElement> getMenuFranchise() {
        return getDriver().findElements(By.xpath("//ul[@class=\"navigation-list\"]/li"));
    }

    private List<WebElement> checkMenuHelp(String chooseMenu) {
        switch (chooseMenu) {
            case "Часто задаваемые вопросы":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][1]/button"));
            case "Начало аренды":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][2]/button"));
            case "Стоимость аренды и финансы":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][3]/button"));
            case "Во время аренды":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][4]/button"));
            case "Завершение аренды":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][5]/button"));
            case "Другие вопросы":
                return getDriver().findElements(By.xpath("//div[@class = 'faq-block'][6]/button"));
            default:
                return getDriver().findElements(By.xpath("//button[@class = 'accordion']"));
        }
    }

    private void goToHelpPage() {
        getDriver().get(URL);
        getMainMenu().get(1).click();
    }

    private void goToBrandBookPage() {
        getDriver().get(URL);
        getDriver().findElement(By.xpath("//a[contains(text(), 'Брендбук')]")).click();
    }

    private void goToAboutServicePage() {
        getDriver().get(URLFranchise);
        getMenuFranchise().get(0).click();
    }

    private void goToFunctionPage() {
        getDriver().get(URLFranchise);
        getMenuFranchise().get(1).click();
    }

    private void goToOurPartnerPage() {
        getDriver().get(URLFranchise);
        getMenuFranchise().get(2).click();
    }

    private void goToStartPage() {
        getDriver().get(URLFranchise);
        getMenuFranchise().get(3).click();
    }

    @Test
    public void testCheckCountMainMenuButtons() {
        getDriver().get(URL);

        Assert.assertEquals(4, getMainMenu().size());
    }

    @Test
    public void testCheckNamesMainMenuButtons() {
        getDriver().get(URL);
        List<String> expectedResult = Arrays.asList("Главная", "Помощь", "Вакансии", "Франшиза");

        for (int i = 0; i < getMainMenu().size(); i++) {
            Assert.assertEquals(getMainMenu().get(i).getText(), expectedResult.get(i));
        }
    }

    @Test
    public void testHelpLink() {
        getDriver().get(URL);
        goToHelpPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://urent.ru/rules/index.html");
    }

    @Test
    public void testHelpMenuHeaders() {
        getDriver().get(URL);
        goToHelpPage();

        List<WebElement> actualResult = getDriver().findElements(By.xpath("//h3"));
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(0, "📌 Часто задаваемые вопросы:");
        expectedResult.add(1, "🚦 Начало аренды:");
        expectedResult.add(2, "💸 Стоимость аренды и финансы:");
        expectedResult.add(3, "🛴 🚲 Во время аренды:");
        expectedResult.add(4, "🏁 Завершение аренды:");
        expectedResult.add(5, "⚙️ Другие вопросы:");

        for (int i = 0; i < actualResult.size(); i++) {
            Assert.assertEquals(actualResult.get(i).getText(), expectedResult.get(i));
        }
    }

    @Test
    public void testCountHelpsMenuPointsInHeaders() {
        getDriver().get(URL);
        goToHelpPage();

        Assert.assertEquals(checkMenuHelp("Стоимость аренды и финансы").size(), 16);
    }

    @Test
    public void testHelpsMenuContent() {
        getDriver().get(URL);
        goToHelpPage();

        List<String> expectedResult = Arrays.asList(
                "Часовой тариф",
                "Тариф \"Пока не сядет\"",
                "Daily Pass",
                "Стоимость аренды",
                "Где можно арендовать Urent?",
                "Как начать аренду?",
                "Как связаться с поддержкой?",
                "Проблемы с завершением аренды",
                "Как работает страхование?");

        List<String> menuHelp1 = new ArrayList<>();
        for (WebElement w : checkMenuHelp("Часто задаваемые вопросы")) {
            menuHelp1.add(w.getText());
        }

        Assert.assertEquals(menuHelp1, expectedResult);
    }

    @Test
    public void testCheckUrentPageButton() {
        getDriver().get(URL);
        goToHelpPage();
        getDriver().findElement(By.cssSelector(".logotype-img")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Привет! Мы — Юрент, шеринг электросамокатов и велосипедов");
    }

    @Test
    public void testCheckFooterMenu() {
        getDriver().get(URL);
        List<WebElement> footerMenu = getDriver().findElements(By.cssSelector(".footer div p"));

        List<String> footerHeaders = Arrays.asList("Документы", "Мы тут", "Контакты");
        for (int i = 0; i < footerMenu.size(); i++) {
            Assert.assertEquals(footerMenu.get(i).getText(), footerHeaders.get(i));
        }
    }

    @Test
    public void testLinkToBrandBookPage() {
        goToBrandBookPage();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".logotype-img")).getAttribute("alt"), "Логотип Urent");
    }

    @Test
    public void testLinkPrivacyPolicy() {
        getDriver().get(URL);
        WebElement linkPrivacyPolicy = getDriver().findElement(By.xpath("//a[@href='/docs/privacy.html'] "));
        linkPrivacyPolicy.click();
        WebElement titlePrivacyPolicy = getDriver().findElement(By.xpath("//h1[text()='Политика конфиденциальности']"));
        Assert.assertEquals(titlePrivacyPolicy.getText(), "Политика конфиденциальности");
    }

    @Test
    public void testLinkContractJoin() {
        getDriver().get(URL);
        WebElement linkContractJoin = getDriver().findElement(By.xpath("//a [@href='/docs/accession.html']"));
        linkContractJoin.click();
        WebElement titleContractJoin = getDriver().findElement(By.xpath("//h1 [text() = 'Договор о предоставлении права использования Сервиса Юрент']"));
        Assert.assertEquals(titleContractJoin.getText(), "Договор о предоставлении права использования Сервиса Юрент");
    }

    @Test
    public void testCheckBrandBookBasicColorsHEX() {

        goToBrandBookPage();
        getDriver().findElement(By.linkText("Палитра")).click();
        List<WebElement> colors = getDriver().findElements(By.xpath("//div[@id = 'palette-three']//div[@class = 'colorchart']"));
        List<String> expectedResult = Arrays.asList("#804AFF", "#000000", "#FFFFFF");

        for (int i = 0; i < colors.size(); i++) {
            Assert.assertEquals(colors.get(i).getText().substring(0, 7), expectedResult.get(i));
        }
    }

    @Test
    public void testCheckBrandBookAdditionalColorsHEX() {

        goToBrandBookPage();
        getDriver().findElement(By.linkText("Палитра")).click();
        List<WebElement> colors = getDriver().findElements(By.xpath("//div[@id = 'palette-four']//div[@class = 'colorchart']"));
        List<String> expectedResult = Arrays.asList("#FFC65B", "#FF73D5", "#9FD7FF");

        for (int i = 0; i < colors.size(); i++) {
            Assert.assertEquals(colors.get(i).getText().substring(0, 7), expectedResult.get(i));
        }
    }

    @Test
    public void testCheckCountMenuFranchiseButtons() {
        getDriver().get(URLFranchise);

        Assert.assertEquals(4, getMenuFranchise().size());
    }

    @Test
    public void testCheckNamesMenuFranchiseButtons() {
        getDriver().get(URLFranchise);
        List<String> expectedResult = Arrays.asList("О сервисе", "Функции", "Наш партнер", "Запуск");

        for (int i = 0; i < getMenuFranchise().size(); i++) {
            Assert.assertEquals(getMenuFranchise().get(i).getText(), expectedResult.get(i));
        }
    }

    @Test
    public void testAboutServiceLink() {
        getDriver().get(URLFranchise);
        goToAboutServicePage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#about-service");
    }

    @Test
    public void testFunctionLink() {
        getDriver().get(URLFranchise);
        goToFunctionPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#function");
    }

    @Test
    public void testOurPartnerLink() {
        getDriver().get(URLFranchise);
        goToOurPartnerPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#our-partner");
    }

    @Test
    public void testStartLink() {
        getDriver().get(URLFranchise);
        goToStartPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#start");
    }
}