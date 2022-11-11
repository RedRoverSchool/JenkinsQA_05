import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GroupObukhovTest extends BaseTest {

    private final String MAIN_PAGE = "https://urent.ru/";
    private final String URL_FRANCHISE = "https://start.urent.ru/";
    private final List<String> LINK_TO_CHECK_BASIC_COLORS = List.of(
            "https://design.urent.ru/img/colors/804aff.svg",
            "https://design.urent.ru/img/colors/000000.svg",
            "https://design.urent.ru/img/colors/ffffff.svg");

    private List<WebElement> getMainMenu() {
        return getDriver().findElements(By.xpath("//ul[@class='menu-list']/li"));
    }

    private List<WebElement> getMenuFranchise() {
        return getDriver().findElements(By.xpath("//ul[@class='navigation-list']/li"));
    }

    private List<WebElement> getMenuHelp(String chooseMenu) {
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
        getDriver().get(MAIN_PAGE);
        getMainMenu().get(1).click();
    }

    private void goToBrandBookPage() {
        getDriver().get(MAIN_PAGE);
        getDriver().findElement(By.xpath("//a[contains(text(), 'Брендбук')]")).click();
    }

    private void goToAboutServicePage() {
        getDriver().get(URL_FRANCHISE);
        getMenuFranchise().get(0).click();
    }

    private void goToFunctionPage() {
        getDriver().get(URL_FRANCHISE);
        getMenuFranchise().get(1).click();
    }

    private void goToOurPartnerPage() {
        getDriver().get(URL_FRANCHISE);
        getMenuFranchise().get(2).click();
    }

    private void goToStartPage() {
        getDriver().get(URL_FRANCHISE);
        getMenuFranchise().get(3).click();
    }

    private void goToPalette() {
        goToBrandBookPage();
        getDriver().findElement(By.linkText("Палитра")).click();
    }

    private void goToStartBusinessPage() {
        getDriver().get(MAIN_PAGE);
        getMainMenu().get(3).click();
    }

    private void goToRoundButtonOnHelpPage() {
        goToHelpPage();
        getWait(10).until(ExpectedConditions.elementToBeClickable(By.id("uw-main-button"))).click();
    }

    private static String getRandomString(int length) {
        return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    private static String getRandomDomen() {
        String[] array = new String[]{".com", ".org", ".ru", ".info", ".edu", ".de", ".kz", ".by"};
        int a = (int) (Math.random() * array.length);
        return array[a];
    }

    private static String getRandomEmail(int a, int b) {
        return getRandomString(a).concat("@").concat(getRandomString(b)).concat(getRandomDomen());
    }

    private Actions getAction() {
        return new Actions(getDriver());
    }

    private WebDriverWait getWait(int timeWaiting) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeWaiting));
    }

    @Test
    public void testCountMainMenuButtons() {
        getDriver().get(MAIN_PAGE);

        Assert.assertEquals(4, getMainMenu().size());
    }

    @Test
    public void testNamesMainMenuButtons() {
        final List<String> MAIN_MENU = List.of("Главная", "Помощь", "Вакансии", "Франшиза");

        getDriver().get(MAIN_PAGE);
        List<String> actualNames = new ArrayList<>();
        for(WebElement w : getMainMenu()) {
            actualNames.add(w.getText());
        }

        Assert.assertEquals(actualNames, MAIN_MENU);
    }

    @Test
    public void testHelpPageLink() {
        goToHelpPage();

        Assert.assertEquals(getDriver().getTitle(), "Юрент - как пользоваться сервисом / Поддержка сервиса Urent");
    }

    @Test
    public void testHelpMenuHeaders() {
        final List<String> headers = List.of(
             "📌 Часто задаваемые вопросы:",
             "🚦 Начало аренды:",
             "💸 Стоимость аренды и финансы:",
             "🛴 🚲 Во время аренды:",
             "🏁 Завершение аренды:",
             "⚙️ Другие вопросы:");

        goToHelpPage();
        List<String> actualResult = new ArrayList<>();
        for (WebElement w : getDriver().findElements(By.xpath("//h3"))) {
            actualResult.add(w.getText());
        }

        Assert.assertEquals(actualResult, headers);
    }

    @Test
    public void testCountHelpsMenuPointsInHeaders() {
        goToHelpPage();

        Assert.assertEquals(getMenuHelp("Стоимость аренды и финансы").size(), 16);
    }

    @Test
    public void testHelpsMenuContent() {
        final List<String> expectedResult = Arrays.asList(
                "Часовой тариф",
                "Тариф \"Пока не сядет\"",
                "Daily Pass",
                "Стоимость аренды",
                "Где можно арендовать Urent?",
                "Как начать аренду?",
                "Как связаться с поддержкой?",
                "Проблемы с завершением аренды",
                "Как работает страхование?");

        goToHelpPage();
        List<String> helpMenuContent = new ArrayList<>();
        for (WebElement w : getMenuHelp("Часто задаваемые вопросы")) {
            helpMenuContent.add(w.getText());
        }

        Assert.assertEquals(helpMenuContent, expectedResult);
    }

    @Test
    public void testUrentPageButton() {
        goToHelpPage();
        getDriver().findElement(By.cssSelector(".logotype-img")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),
                "Привет! Мы — Юрент, шеринг электросамокатов и велосипедов");
    }

    @Test
    public void testFooterMenu() {
        final List<String> footerHeaders = Arrays.asList("Документы", "Мы тут", "Контакты");

        getDriver().get(MAIN_PAGE);
        List<WebElement> footerMenu = getDriver().findElements(By.cssSelector(".footer div p"));
        List<String> footerMenuNames = new ArrayList<>();
        for (int i = 0; i < footerMenu.size(); i++) {
            footerMenuNames.add(i, footerMenu.get(i).getText());
        }

        Assert.assertEquals(footerMenuNames, footerHeaders);
    }

    @Test
    public void testLinkToBrandBookPage() {
        goToBrandBookPage();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".logotype-img")).getAttribute("alt"),
                "Логотип Urent");
    }

    @Test
    public void testLinkPrivacyPolicy() {
        getDriver().get(MAIN_PAGE);

        WebElement linkPrivacyPolicy = getDriver().findElement(By.xpath("//a[@href='/docs/privacy.html'] "));
        linkPrivacyPolicy.click();
        WebElement titlePrivacyPolicy = getDriver().findElement(By.xpath("//h1[text()='Политика конфиденциальности']"));

        Assert.assertEquals(titlePrivacyPolicy.getText(), "Политика конфиденциальности");
    }

    @Test
    public void testLinkContractJoin() {
        getDriver().get(MAIN_PAGE);

        WebElement linkContractJoin = getDriver().findElement(By.xpath("//a[@href='/docs/accession.html']"));
        linkContractJoin.click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(),
                "Договор о предоставлении права использования Сервиса Юрент");
    }

    @Test
    public void testBrandBookBasicColorsHEX() {
        final List<String> BASIC_COLORS_HEX = List.of("#804AFF", "#000000", "#FFFFFF");

        goToPalette();
        List<WebElement> colors = getDriver().findElements(
                By.xpath("//div[@id = 'palette-three']//div[@class = 'colorchart']"));

        for (int i = 0; i < colors.size(); i++) {
            Assert.assertEquals(colors.get(i).getText().substring(0, 7), BASIC_COLORS_HEX.get(i));
        }
    }

    @Test
    public void testBrandBookAdditionalColorsHEX() {
        final List<String> expectedResult = Arrays.asList("#FFC65B", "#FF73D5", "#9FD7FF");

        goToPalette();
        List<WebElement> colors = getDriver().findElements(
                By.xpath("//div[@id = 'palette-four']//div[@class = 'colorchart']"));
        List<String> actualColors = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            actualColors.add(i, colors.get(i).getText().substring(0, 7));
        }

        Assert.assertEquals(actualColors, expectedResult);
    }

    @Test
    public void testDownloadAppButtonTextColor() {
        getDriver().get(MAIN_PAGE);

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".menu-button"))
                .getCssValue("color"), "rgba(255, 255, 255, 1)");
    }

    @Test
    public void testDownloadAppButtonBackGroundColor() {
        getDriver().get(MAIN_PAGE);

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".menu-button"))
                .getCssValue("background-color"), "rgba(128, 74, 255, 1)");
    }

    @Test
    public void testHeroButtonColors() {
        getDriver().get(MAIN_PAGE);
        WebElement heroButton = getDriver().findElement(By.cssSelector(".hero-button-text"));

        Assert.assertEquals(heroButton.getCssValue("color"), "rgba(128, 74, 255, 1)");
    }

    @Test
    public void testCheckHeroButtonColorsAfterNavigateMouse() {
        getDriver().get(MAIN_PAGE);
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
        final String propertyActiveButton = "2px solid rgb(128, 74, 255)";
        final List<String> stepsNames = List.of("Найди самокат", "Отсканируй QR", "Выбери тариф", "Можно ехать!");
        final List<String> stepsDescriptions = List.of(
                "На карте в приложении отмечены все наши самокаты, доступные для аренды." +
                        "В жизни они фиолетовые, иногда на них бывает брендинг города",
                "Когда самокат перед тобой – нажми на круглую кнопку сканирования." +
                        "Отсканируй QR-код.Он будет на руле самоката",
                "Осталось выбрать тариф.Поминутный выгоден для небольших поездок." +
                        "Если собрался кататься долго – лучше взять “Пока не сядет” или проездной на день",
                "Выбрал тариф?Жми “Старт” и можно ехать.Когда решишь завершить поездку – найди на карте парковку." +
                        "Завершить поездку можно только на одной из них."
        );

        getDriver().get(MAIN_PAGE);

        List<WebElement> stepNumbersHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid span"));
        List<WebElement> stepNamesHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid li"));
        List<WebElement> descriptionHowToUseService = getDriver().findElements(By.cssSelector(".block3-grid p"));

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
        getDriver().get(MAIN_PAGE);

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
        getDriver().get(MAIN_PAGE);

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
        getDriver().get(URL_FRANCHISE);

        Assert.assertEquals(getMenuFranchise().size(), 4);
    }

    @Test
    public void testCheckNamesMenuFranchiseButtons() {
        final List<String> expectedResult = Arrays.asList("О сервисе", "Функции", "Наш партнер", "Запуск");

        getDriver().get(URL_FRANCHISE);
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < getMenuFranchise().size(); i++) {
            actualResult.add(getMenuFranchise().get(i).getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testAboutServiceLink() {
        getDriver().get(URL_FRANCHISE);
        goToAboutServicePage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#about-service");
    }

    @Test
    public void testFunctionLink() {
        getDriver().get(URL_FRANCHISE);
        goToFunctionPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#function");
    }

    @Test
    public void testOurPartnerLink() {
        getDriver().get(URL_FRANCHISE);
        goToOurPartnerPage();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/#our-partner");
    }

    @Test
    public void testStartLink() {
        getDriver().get(URL_FRANCHISE);
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

    @Ignore // тест рабочий. поставили игнор, что бы запросами не перегружать сайт.
    @Test
    public void testFillFieldsStartPage() {
        final String successStart = "Форма отправлена! Мы скоро свяжемся с вами.";

        goToStartBusinessPage();
        Set<String> openBrowserPages = getDriver().getWindowHandles();
        getDriver().switchTo().window(openBrowserPages.toArray()[1].toString());

        List<String> data = Arrays.asList(
                getRandomString(6),
                RandomStringUtils.random(10, "0123456789"),
                getRandomEmail(8, 10),
                getRandomString(5)

        );
        List<WebElement> fields = getDriver().findElements(By
                .xpath("//div[@class = 'content-grid-form']//form[@class = 'call-respond']/input[@type][@placeholder]"));

        Actions action = new Actions(getDriver());
        for (int i = 0; i < fields.size(); i++) {
            getWait(2).until(ExpectedConditions.elementToBeClickable(fields.get(i)));
            action.moveToElement(fields.get(i)).click().sendKeys(data.get(i)).perform();
        }
        WebElement submitButton = getDriver().findElement(By
                .xpath("//div[@class = 'content-grid-form']//form[@class = 'call-respond']/button[@type = 'submit']"));
        getAction()
                .moveToElement(submitButton)
                .click()
                .perform();

        if (getDriver().getCurrentUrl().equals("https://start.urent.ru/thank-you.html")) {

            Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class = 'text-center']")).getText().contains(successStart));
        } else {

            Assert.assertEquals(getDriver().getCurrentUrl(), "https://start.urent.ru/js/mail.php");
        }
    }

    @Test
    public void testCheckRoundButtonHelpMenu() {
        goToRoundButtonOnHelpPage();

        Assert.assertTrue(getDriver().findElement(By.id("uw-button-chat")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("uw-button-telegram")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("uw-main-button-close")).isDisplayed());
    }

    @Test
    public void testCheckRoundButtonHelpMenuLinkToTelegram() {
        goToRoundButtonOnHelpPage();

        getDriver().findElement(By.id("uw-button-telegram")).click();
        final Set<String> openPages = getDriver().getWindowHandles();

        String actualString = getDriver().switchTo().window(openPages.toArray()[1].toString()).getCurrentUrl();
        Assert.assertEquals(actualString, "https://t.me/Urent_support_bot");
    }

    @Test
    public void testRoundButtonHelpMenuLinkToLiveChat() {
        final String expectedFrameText = "чат";

        goToRoundButtonOnHelpPage();
        getWait(10).until(ExpectedConditions.elementToBeClickable(By.id("uw-button-chat"))).click();
        WebElement liveChatFrame = getWait(10).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'uw__messenger-layout__frame')]")));

        Assert.assertTrue(liveChatFrame.getText().toLowerCase().contains(expectedFrameText));
    }

    @Test
    public void testChangeActivedElementOfMainMenu() {
        getDriver().get(MAIN_PAGE);

        List<WebElement> nowMainMenu = getDriver().findElements(By.cssSelector(".menu-link"));
        int size = nowMainMenu.size();
        for (int i = 0; i < size - 2; i++) {
            int activeElement = size;
            int countInactiveElement = 0;
            nowMainMenu.get(i).click();
            nowMainMenu = getDriver().findElements(By.cssSelector(".menu-link"));
            for (int j = 0; j < size; j++) {
                if ((nowMainMenu.get(j).getAttribute("class")).contains("menu-active-link")) {
                    activeElement = j;
                } else {
                    countInactiveElement++;
                }
            }

            Assert.assertTrue(activeElement == i && size - countInactiveElement == 1);
        }
    }
}