import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GroupATeamSauceDemoInventoryTest extends GroupATeamSauceDemoBaseTest {
    private static final By TITLE_LOCATOR = By.cssSelector("span.title");
    private static final By SIDEBAR_MENU_BTN_LOCATOR = By.id("react-burger-menu-btn");
    private static final By SORT_CONTAINER_LOCATOR = By.cssSelector("select.product_sort_container");
    private static final By SORT_CONTAINER_TITLE_LOCATOR = By.cssSelector("span.active_option");
    private static final By SHOPPING_CART_LOCATOR = By.cssSelector("a.shopping_cart_link");
    private static final By INVENTORIES_LOCATOR = By.xpath("//div[@class='inventory_list']/div");
    private static final By INVENTORY_LABEL_LOCATOR = By.xpath(".//div[@class='inventory_item_label']/a");
    private static final By INVENTORY_NAME_LOCATOR = By.xpath(".//div[@class='inventory_item_name']");
    private static final By INVENTORY_PRICE_LOCATOR = By.xpath(".//div[@class='inventory_item_price']");
    private static final By CART_ITEMS_LOCATOR = By.xpath("//div[@class='cart_list']/div[@class='cart_item']");
    private static final By ADD_OR_REMOVE_BTN_LOCATOR = By.xpath(".//button");

    private static class Inventory {
        String id;
        String title;
        double price;

        Inventory(String id, String title, double price) {
            this.id = id;
            this.title = title;
            this.price = price;
        }

        String getTitle() {
            return title;
        }

        double getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object obj) {
            Inventory inventory = (Inventory) obj;
            return this.id.equals(inventory.id) && this.title.equals(inventory.title) && this.price == inventory.price;
        }
    }

    @BeforeMethod
    private void signIn() {
        loginIn(GroupATeamSauceDemoUtils.STANDARD_USER, GroupATeamSauceDemoUtils.CORRECT_PASSWORD);
    }

    @Test
    public void testSidebarMenuForItems() {
        clickOnDocumentElement(SIDEBAR_MENU_BTN_LOCATOR);

        final List<String> expectedMenuItemNames = List.of("ALL ITEMS", "ABOUT", "LOGOUT", "RESET APP STATE");
        List<WebElement> actualMenuItems = new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(getDriver()
                        .findElements(By.xpath("//nav[@class='bm-item-list']/a"))));
        final List<String> actualMenuItemNames = new ArrayList<>();
        actualMenuItems.forEach(webElement -> actualMenuItemNames.add(webElement.getText()));

        Assert.assertEquals(actualMenuItemNames, expectedMenuItemNames);
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testAllItemsLinkFromSidebarMenu() {
        goClickOnSidebarMenuElements("inventory_sidebar_link");
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(TITLE_LOCATOR).getText(), GroupATeamSauceDemoUtils.TITLE_PRODUCTS);
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testAboutUsLinkFromSideBarMenu() {
        goClickOnSidebarMenuElements("about_sidebar_link");
        Assert.assertEquals(getDriver().getTitle()
                , "Cross Browser Testing, Selenium Testing, Mobile Testing | Sauce Labs");
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testLogOutFromSideBarMenu() {
        goClickOnSidebarMenuElements("logout_sidebar_link");
        Assert.assertEquals(getDriver().getCurrentUrl(), GroupATeamSauceDemoUtils.URL_SAUCE_DEMO);
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
    }

    @Ignore
    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testCloseSidebarMenu() {
        goClickOnSidebarMenuElements("react-burger-cross-btn");

        final Boolean sidebarMenuIsHidden = new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.bm-menu")));

        Assert.assertTrue(sidebarMenuIsHidden);
    }

    @Test
    public void testShoppingCartBadge() {
        clickOnDocumentElement(SHOPPING_CART_LOCATOR);
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(TITLE_LOCATOR).getText(), "YOUR CART");
    }

    @Test
    public void testFooterTwitterLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_twitter']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("Sauce Labs (@saucelabs) / Twitter"));
    }

    @Test
    public void testFooterFacebookLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_facebook']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("Facebook"));
    }

    @Test
    public void testFooterLinkedInLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_linkedin']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("LinkedIn"));
    }

    @Test
    public void testSortContainerByNameA2Z() {
        final List<Inventory> inventories = getInventories();

        final String sortingValue = "Name (A to Z)";
        new Select(getDriver().findElement(SORT_CONTAINER_LOCATOR)).selectByVisibleText((sortingValue));

        final List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getTitle));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(SORT_CONTAINER_TITLE_LOCATOR).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByNameZ2A() {
        final List<Inventory> inventories = getInventories();

        final String sortingValue = "Name (Z to A)";
        new Select(getDriver().findElement(SORT_CONTAINER_LOCATOR)).selectByVisibleText((sortingValue));

        final List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getTitle, Comparator.reverseOrder()));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(SORT_CONTAINER_TITLE_LOCATOR).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByPriceLow2High() {
        final List<Inventory> inventories = getInventories();

        final String sortingValue = "Price (low to high)";
        new Select(getDriver().findElement(SORT_CONTAINER_LOCATOR)).selectByVisibleText((sortingValue));

        final List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getPrice));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(SORT_CONTAINER_TITLE_LOCATOR).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByPriceHigh2Low() {
        final List<Inventory> inventories = getInventories();

        final String sortingValue = "Price (high to low)";
        new Select(getDriver().findElement(SORT_CONTAINER_LOCATOR)).selectByVisibleText((sortingValue));

        final List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getPrice, Comparator.reverseOrder()));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(SORT_CONTAINER_TITLE_LOCATOR).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testAddToCartBtn() {
        WebElement firstInventory = getDriver().findElement(INVENTORIES_LOCATOR);

        final String firstInventoryId = firstInventory.findElement(INVENTORY_LABEL_LOCATOR).getAttribute("id");
        String shoppingCartBadge = getDriver().findElement(SHOPPING_CART_LOCATOR).getText();
        final int shoppingCartBadgeNumber = shoppingCartBadge.isEmpty() ? 0 : Integer.parseInt(shoppingCartBadge);

        clickOnDocumentElement(firstInventory.findElement(ADD_OR_REMOVE_BTN_LOCATOR));

        Assert.assertEquals(Integer.parseInt(getDriver().findElement(SHOPPING_CART_LOCATOR).getText())
                , shoppingCartBadgeNumber + 1);
        Assert.assertEquals(firstInventory.findElement(ADD_OR_REMOVE_BTN_LOCATOR).getText(), "REMOVE");

        clickOnDocumentElement(SHOPPING_CART_LOCATOR);

        final String firstCartItemId = getDriver()
                .findElement(By.xpath("//div[@class='cart_list']/div//a")).getAttribute("id");
        Assert.assertEquals(firstCartItemId, firstInventoryId);
    }

    @Test
    public void testRemoveFromCartBtn() {
        clickOnDocumentElement(getDriver().findElement(By.xpath("//div[@class='inventory_list']/div//button")));
        clickOnDocumentElement(SHOPPING_CART_LOCATOR);

        final WebElement firstCartItem = getDriver().findElement(CART_ITEMS_LOCATOR);
        clickOnDocumentElement(firstCartItem.findElement(ADD_OR_REMOVE_BTN_LOCATOR));

        Assert.assertTrue(!(firstCartItem.isDisplayed()));

    }

    @Test
    public void testContinueShoppingBtn() {
        clickOnDocumentElement(SHOPPING_CART_LOCATOR);
        clickOnDocumentElement(By.id("continue-shopping"));
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(TITLE_LOCATOR).getText(), GroupATeamSauceDemoUtils.TITLE_PRODUCTS);
    }

    @Test
    public void testCheckout() {
        List<WebElement> webInventories = getDriver().findElements(INVENTORIES_LOCATOR);
        final List<Inventory> inventories = new ArrayList<>();
        double summary = 0;

        for (int i = 0; i < 2; i++) {
            WebElement webInventory = webInventories.get(i);
            clickOnDocumentElement(webInventory.findElement(ADD_OR_REMOVE_BTN_LOCATOR));
            final Inventory inventory = getInventory(webInventory);
            inventories.add(inventory);
            summary += inventory.price;
        }

        clickOnDocumentElement(SHOPPING_CART_LOCATOR);
        clickOnDocumentElement(By.id("checkout"));
        getAction().moveToElement(getDriver().findElement(By.id("first-name"))).click().sendKeys("Firstname")
                .moveToElement(getDriver().findElement(By.id("last-name"))).click().sendKeys("Lastname")
                .moveToElement(getDriver().findElement(By.id("postal-code"))).click().sendKeys("77001")
                .moveToElement(getDriver().findElement(By.id("continue"))).click().perform();

        List<WebElement> webCheckoutItems = getDriver().findElements(CART_ITEMS_LOCATOR);
        final List<Inventory> checkoutItems = new ArrayList<>();
        final By cartItemLabelLocator = By.xpath(".//div[@class='cart_item_label']/a");

        webCheckoutItems.forEach(checkoutItem -> checkoutItems.add(new Inventory(
                checkoutItem.findElement(cartItemLabelLocator).getAttribute("id")
                , checkoutItem.findElement(INVENTORY_NAME_LOCATOR).getText()
                , GroupATeamSauceDemoUtils.getDouble(checkoutItem.findElement(INVENTORY_PRICE_LOCATOR), "$"))));

        final double sum = GroupATeamSauceDemoUtils
                .getDouble(getDriver().findElement(By.cssSelector("div.summary_subtotal_label")), "Item total: $");
        final double tax = GroupATeamSauceDemoUtils
                .getDouble(getDriver().findElement(By.cssSelector("div.summary_tax_label")), "Tax: $");
        final double total = GroupATeamSauceDemoUtils
                .getDouble(getDriver().findElement(By.cssSelector("div.summary_total_label")), "Total: $");

        Assert.assertEquals(checkoutItems, inventories);
        Assert.assertEquals(sum, summary);
        Assert.assertEquals(tax, Math.round(sum * 8) / 100.0);
        Assert.assertEquals(total, sum + tax);

        clickOnDocumentElement(By.id("finish"));
        Assert.assertEquals(getDriver().findElement(By.cssSelector("h2.complete-header")).getText()
                , "THANK YOU FOR YOUR ORDER");
    }

    private void clickOnDocumentElement(WebElement webElement) {
        getAction().moveToElement(webElement).click().perform();
    }

    private void clickOnDocumentElement(By locator) {
        clickOnDocumentElement(getDriver().findElement(locator));
    }

    private void goClickOnSidebarMenuElements(String elementId) {
        clickOnDocumentElement(SIDEBAR_MENU_BTN_LOCATOR);

        WebElement link = new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(By.id(elementId)));

        clickOnDocumentElement(link);
    }

    private Boolean newFirstOpenedTabTitleContains(String title) {
        String newOpenedTab = getDriver().getWindowHandles().toArray(new String[2])[1];
        getDriver().switchTo().window(newOpenedTab);
        return new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .until(ExpectedConditions.titleContains(title));
    }

    private Inventory getInventory(WebElement inventory) {
        return new Inventory(inventory.findElement(INVENTORY_LABEL_LOCATOR).getAttribute("id")
                , inventory.findElement(INVENTORY_NAME_LOCATOR).getText()
                , GroupATeamSauceDemoUtils.getDouble(inventory.findElement(INVENTORY_PRICE_LOCATOR), "$"));
    }

    private List<Inventory> getInventories() {
        List<WebElement> webInventories = getDriver().findElements(INVENTORIES_LOCATOR);
        return webInventories.stream().map(this::getInventory).collect(Collectors.toList());
    }
}