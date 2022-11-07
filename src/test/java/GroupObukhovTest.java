import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GroupObukhovTest extends BaseTest {

    private final String URL = "https://urent.ru/";

    private final String URLFranchise = "https://start.urent.ru/";

    private final List<String> BASIC_COLORS_HEX = Arrays.asList("#804AFF", "#000000", "#FFFFFF");

    List<String> LINK_TO_CHECK_BASIC_COLORS = Arrays.asList(
            "https://design.urent.ru/img/colors/804aff.svg",
            "https://design.urent.ru/img/colors/000000.svg",
            "https://design.urent.ru/img/colors/ffffff.svg");

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

    private void goToPalette() {
        goToBrandBookPage();
        getDriver().findElement(By.linkText("Палитра")).click();
    }

    private void goToStartBusinessPage() {
        getDriver().get(URL);
        getMainMenu().get(3).click();
    }

    private static String getRandomString(int length) {
        return RandomStringUtils.random(length,
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    private static String getRandomEmail() {
        return getRandomString(5).concat("@").concat(getRandomString(5)).concat(".com").toLowerCase();
    }

    private Actions getAction() {
        return new Actions(getDriver());
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
    public void testActiveElementsOfMainMenu(){
        getDriver().get(URL);
        List<WebElement> nowMainMenu = getDriver().findElements(By.cssSelector(".menu-list"));
        //List<String> expectedMainMenu = Arrays.asList("Главная", "Помощь", "Вакансии", "Франшиза");
        int size = nowMainMenu.size();
        int activeElement = size;
        int countInactiveElement = 0;
        int countClick = 0;
        for (int i = 0; i < size - 2; i++){
            if(activeElement < size){ // проверяем элемент "Главная" два раза, при начальной загрузке сайта и при клике.
                if(countClick == 0){
                    i = 0;
                }
                nowMainMenu.get(i).click();
                countClick++;
            }// пропускаем первый раз.
            for (int j = 0; j < size; j++) {
                if (nowMainMenu.get(j).getAttribute("class").contains("menu-active-link")) {
                    activeElement = j;
                } else {
                    countInactiveElement++;
                }
            }

            Assert.assertTrue(activeElement == i && size - countInactiveElement == 1);

            countInactiveElement = 0;
        }

    }

    @Test
    public void testCheckBrandBookBasicColorsHEX() {
        goToPalette();
        List<WebElement> colors = getDriver().findElements(By.xpath("//div[@id = 'palette-three']//div[@class = 'colorchart']"));

        for (int i = 0; i < colors.size(); i++) {
            Assert.assertEquals(colors.get(i).getText().substring(0, 7), BASIC_COLORS_HEX.get(i));
        }
    }

    @Test
    public void testCheckBrandBookAdditionalColorsHEX() {
        goToPalette();
        List<WebElement> colors = getDriver().findElements(By.xpath("//div[@id = 'palette-four']//div[@class = 'colorchart']"));
        List<String> expectedResult = Arrays.asList("#FFC65B", "#FF73D5", "#9FD7FF");

        for (int i = 0; i < colors.size(); i++) {
            Assert.assertEquals(colors.get(i).getText().substring(0, 7), expectedResult.get(i));
        }
    }

    @Test
    public void testCheckDownloadAppButtonColors() {
        getDriver().get(URL);
        WebElement downloadAppButton = getDriver().findElement(By.cssSelector(".menu-button"));

        Assert.assertEquals(downloadAppButton.getCssValue("color"), "rgba(255, 255, 255, 1)");
        Assert.assertEquals(downloadAppButton.getCssValue("background-color"), "rgba(128, 74, 255, 1)");
    }

    @Test
    public void testCheckHeroButtonColors() {
        getDriver().get(URL);
        WebElement heroButton = getDriver().findElement(By.cssSelector(".hero-button-text"));

        Assert.assertEquals(heroButton.getCssValue("color"), "rgba(128, 74, 255, 1)");
        Assert.assertEquals(heroButton.getCssValue("background-color"), "rgba(0, 0, 0, 0)");
    }

    @Test
    public void testCheckHeroButtonColorsAfterNavigateMouse() {
        getDriver().get(URL);
        WebElement heroButton = getDriver().findElement(By.cssSelector(".hero-button"));

        String startBackgroundColor = Color.fromString(heroButton.getCssValue("background-color")).asRgb();
        getAction()
                .moveToElement(heroButton)
                .perform();
        String afterMouseNavigateBackgroundColor = Color.fromString(heroButton.getCssValue("background-color")).asRgb();

        Assert.assertNotEquals(startBackgroundColor, afterMouseNavigateBackgroundColor);
        Assert.assertEquals(afterMouseNavigateBackgroundColor, "rgb(128, 74, 255)");
    }

    @Test
    public void testCheckHowToUseService() {
        getDriver().get(URL);

        List<WebElement> stepNumbersHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid span"));
        List<WebElement> stepNamesHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid li"));
        List<WebElement> descriptionHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid p"));

        String propertyActiveButton = "2px solid rgb(128, 74, 255)";
        List<String> stepsNames = Arrays.asList("Найди самокат", "Отсканируй QR", "Выбери тариф", "Можно ехать!");
        List<String> stepsDescriptions = Arrays.asList(
                "На карте в приложении отмечены все наши самокаты, доступные для аренды.В жизни они фиолетовые, иногда на них бывает брендинг города",
                "Когда самокат перед тобой – нажми на круглую кнопку сканирования.Отсканируй QR-код.Он будет на руле самоката",
                "Осталось выбрать тариф.Поминутный выгоден для небольших поездок.Если собрался кататься долго – лучше взять “Пока не сядет” или проездной на день",
                "Выбрал тариф?Жми “Старт” и можно ехать.Когда решишь завершить поездку – найди на карте парковку.Завершить поездку можно только на одной из них."
        );

        for (int i = 0; i < stepNumbersHowToUseService.size(); i++) {
            stepNumbersHowToUseService.get(i).click();
            Assert.assertEquals(stepNumbersHowToUseService.get(i).getCssValue("border"), propertyActiveButton);
            Assert.assertEquals(stepNumbersHowToUseService.get(i).getText(), String.valueOf(i + 1));
            Assert.assertEquals(stepNamesHowToUseService.get(i).getText().substring(2), stepsNames.get(i));
            Assert.assertEquals(descriptionHowToUseService
                    .get(i).getText().replace("\n", ""), stepsDescriptions.get(i));
        }
    }

    @Test
    public void testLinkVacancy() {
        getDriver().get(URL);
        WebElement linkVacancy = getDriver().findElement(By.xpath("//li[@class = 'menu-item']/a[text()='Вакансии']"));
        Set<String> oldWindowsSet = getDriver().getWindowHandles();
        linkVacancy.click();
        Set<String> newWindowsSet = getDriver().getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        getDriver().switchTo().window(newWindowHandle);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("hh.ru"));
    }

    @Test
    public void testLinkVKontakte() {
        getDriver().get(URL);
        WebElement vKontakte = getDriver().findElement(By.xpath("//a[@href='https://vk.com/urent_russia']"));
        Set<String> oldWindowsSet = getDriver().getWindowHandles();
        vKontakte.click();
        Set<String> newWindowsSet = getDriver().getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        getDriver().switchTo().window(newWindowHandle);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("vk.com"));
    }


    @Test
    public void testCheckCountMenuFranchiseButtons() {
        getDriver().get(URLFranchise);

        Assert.assertEquals( getMenuFranchise().size(), 4);
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

    @Test
    public void testCheckPaletteBasicColorsLinks() {
        goToPalette();
        List<String> actualLinksToCheckBasicColors = new ArrayList<>();

        List<WebElement> basicColorsExamples = getDriver()
                .findElements(By.xpath("//div[@id = 'palette-three']/div[@class= 'block-grid-colorblock']/div[1]"));
        for (int i = 0; i < basicColorsExamples.size(); i++) {
            actualLinksToCheckBasicColors.add(i, basicColorsExamples.get(i).getCssValue("background-image")
                    .replace("url(\"", "").replace("\")", ""));
        }

        Assert.assertEquals(actualLinksToCheckBasicColors, LINK_TO_CHECK_BASIC_COLORS);
    }

    @Test(dependsOnMethods = "testCheckPaletteBasicColorsLinks")
    public void testCheckMatchColorAnnotationAndLinkColor() {
        goToPalette();

        List<String> checkColors = new ArrayList<>();
        List<WebElement> basicColorsTextDescriptionsFromDesignPage = getDriver()
                .findElements(By.xpath("//div[@id = 'palette-three']//div[@class = 'colorchart']"));
        List<String> basicColors = new ArrayList<>();
        for (int i = 0; i < basicColorsTextDescriptionsFromDesignPage.size(); i++) {
            basicColors.add(i, basicColorsTextDescriptionsFromDesignPage.get(i).getText().substring(0, 7));
        }
        for (String str : LINK_TO_CHECK_BASIC_COLORS) {
            getDriver().get(str);
            checkColors.add(Color.fromString(getDriver()
                    .findElement(By.cssSelector(" path")).getCssValue("fill")).asHex().toUpperCase());
        }

        Assert.assertEquals(checkColors, basicColors);
    }

    @Test
    public void testFillFieldsStartPage() {
        goToStartBusinessPage();
        Set<String> openBrowserPages = getDriver().getWindowHandles();
        getDriver().switchTo().window(openBrowserPages.toArray()[1].toString());

        List<String> data = Arrays.asList(
                getRandomString(6),
                RandomStringUtils.random(10, "0123456789"),
                getRandomEmail(),
                getRandomString(5)

        );
        List<WebElement> fields = getDriver().findElements(By
                .xpath("//div[@class = 'content-grid-form']//form[@class = 'call-respond']/input[@type][@placeholder]"));

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        Actions action = new Actions(getDriver());
        for (int i = 0; i < fields.size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(fields.get(i)));
            action.moveToElement(fields.get(i)).click().sendKeys(data.get(i)).perform();
        }
        WebElement submitButton = getDriver().findElement(By
                .xpath("//div[@class = 'content-grid-form']//form[@class = 'call-respond']/button[@type = 'submit']"));
        getAction()
                .moveToElement(submitButton)
                .click()
                .perform();

        String successStart = "Форма отправлена! Мы скоро свяжемся с вами.";

        if (getDriver().getCurrentUrl().equals("https://start.urent.ru/thank-you.html")) {

            Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class = 'text-center']")).getText().contains(successStart));
        } else {

            Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/js/mail.php");
        }
    }
}