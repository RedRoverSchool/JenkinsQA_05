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
    private final By titleLocator = By.cssSelector("span.title");
    private final By sidebarMenuBtnLocator = By.id("react-burger-menu-btn");
    private final By sortContainerLocator = By.cssSelector("select.product_sort_container");
    private final By sortContainerTitleLocator = By.cssSelector("span.active_option");
    private final By shoppingCartLocator = By.cssSelector("a.shopping_cart_link");
    private final By inventoriesLocator = By.xpath("//div[@class='inventory_list']/div");
    private final By inventoryLabelLocator = By.xpath(".//div[@class='inventory_item_label']/a");
    private final By inventoryNameLocator = By.xpath(".//div[@class='inventory_item_name']");
    private final By inventoryPriceLocator = By.xpath(".//div[@class='inventory_item_price']");
    private final By cartItemsLocator = By.xpath("//div[@class='cart_list']/div[@class='cart_item']");
    private final By addOrRemoveBtnLocator = By.xpath(".//button");

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
        clickOnDocumentElement(sidebarMenuBtnLocator);

        List<String> expectedMenuItemNames = List.of("ALL ITEMS", "ABOUT", "LOGOUT", "RESET APP STATE");
        List<WebElement> actualMenuItems = new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(getDriver()
                        .findElements(By.xpath("//nav[@class='bm-item-list']/a"))));
        List<String> actualMenuItemNames = new ArrayList<>();
        actualMenuItems.forEach(webElement -> actualMenuItemNames.add(webElement.getText()));

        Assert.assertEquals(actualMenuItemNames, expectedMenuItemNames);
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testAllItemsLinkFromSidebarMenu() {
        goClickOnSidebarMenuElements("inventory_sidebar_link");
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(titleLocator).getText(), GroupATeamSauceDemoUtils.TITLE_PRODUCTS);
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
        Assert.assertEquals(getDriver().getCurrentUrl(), GroupATeamSauceDemoUtils.URL);
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
    }

    @Ignore
    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testCloseSidebarMenu() {
        goClickOnSidebarMenuElements("react-burger-cross-btn");

        Boolean sidebarMenuIsHidden = new WebDriverWait(getDriver(), GroupATeamSauceDemoUtils.MAX_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.bm-menu")));

        Assert.assertTrue(sidebarMenuIsHidden);
    }

    @Test
    public void testShoppingCartBadge() {
        clickOnDocumentElement(shoppingCartLocator);
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(titleLocator).getText(), "YOUR CART");
    }

    @Test
    public void testFooterTwitterLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_twitter']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("Sauce Labs (@saucelabs) / Twitter"));
    }

    @Test
    public void testFooterFacebookLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_facebook']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("Sauce Labs | Facebook"));
    }

    @Test
    public void testFooterLinkedInLink() {
        clickOnDocumentElement(By.xpath("//footer//li[@class='social_linkedin']/a"));
        Assert.assertTrue(newFirstOpenedTabTitleContains("LinkedIn"));
    }

    @Test
    public void testSortContainerByNameA2Z() {
        List<Inventory> inventories = getInventories();

        String sortingValue = "Name (A to Z)";
        new Select(getDriver().findElement(sortContainerLocator)).selectByVisibleText((sortingValue));

        List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getTitle));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(sortContainerTitleLocator).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByNameZ2A() {
        List<Inventory> inventories = getInventories();

        String sortingValue = "Name (Z to A)";
        new Select(getDriver().findElement(sortContainerLocator)).selectByVisibleText((sortingValue));

        List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getTitle, Comparator.reverseOrder()));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(sortContainerTitleLocator).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByPriceLow2High() {
        List<Inventory> inventories = getInventories();

        String sortingValue = "Price (low to high)";
        new Select(getDriver().findElement(sortContainerLocator)).selectByVisibleText((sortingValue));

        List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getPrice));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(sortContainerTitleLocator).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testSortContainerByPriceHigh2Low() {
        List<Inventory> inventories = getInventories();

        String sortingValue = "Price (high to low)";
        new Select(getDriver().findElement(sortContainerLocator)).selectByVisibleText((sortingValue));

        List<Inventory> inventoriesAfterSorting = getInventories();
        inventories.sort(Comparator.comparing(Inventory::getPrice, Comparator.reverseOrder()));

        Assert.assertEquals(inventoriesAfterSorting, inventories);
        Assert.assertEquals(getDriver().findElement(sortContainerTitleLocator).getText(), sortingValue.toUpperCase());
    }

    @Test
    public void testAddToCartBtn() {
        WebElement firstInventory = getDriver().findElement(inventoriesLocator);

        String firstInventoryId = firstInventory.findElement(inventoryLabelLocator).getAttribute("id");
        String shoppingCartBadge = getDriver().findElement(shoppingCartLocator).getText();
        int shoppingCartBadgeNumber = shoppingCartBadge.isEmpty() ? 0 : Integer.parseInt(shoppingCartBadge);

        clickOnDocumentElement(firstInventory.findElement(addOrRemoveBtnLocator));

        Assert.assertEquals(Integer.parseInt(getDriver().findElement(shoppingCartLocator).getText())
                , shoppingCartBadgeNumber + 1);
        Assert.assertEquals(firstInventory.findElement(addOrRemoveBtnLocator).getText(), "REMOVE");

        clickOnDocumentElement(shoppingCartLocator);

        String firstCartItemId = getDriver()
                .findElement(By.xpath("//div[@class='cart_list']/div//a")).getAttribute("id");
        Assert.assertEquals(firstCartItemId, firstInventoryId);
    }

    @Test
    public void testRemoveFromCartBtn() {
        clickOnDocumentElement(getDriver().findElement(By.xpath("//div[@class='inventory_list']/div//button")));
        clickOnDocumentElement(shoppingCartLocator);

        WebElement firstCartItem = getDriver().findElement(cartItemsLocator);
        clickOnDocumentElement(firstCartItem.findElement(addOrRemoveBtnLocator));

        Assert.assertTrue(!(firstCartItem.isDisplayed()));

    }

    @Test
    public void testContinueShoppingBtn() {
        clickOnDocumentElement(shoppingCartLocator);
        clickOnDocumentElement(By.id("continue-shopping"));
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(titleLocator).getText(), GroupATeamSauceDemoUtils.TITLE_PRODUCTS);
    }

    @Test
    public void testCheckout() {
        List<WebElement> webInventories = getDriver().findElements(inventoriesLocator);
        List<Inventory> inventories = new ArrayList<>();
        double summary = 0;

        for (int i = 0; i < 2; i++) {
            WebElement webInventory = webInventories.get(i);
            clickOnDocumentElement(webInventory.findElement(addOrRemoveBtnLocator));
            Inventory inventory = getInventory(webInventory);
            inventories.add(inventory);
            summary += inventory.price;
        }

        clickOnDocumentElement(shoppingCartLocator);
        clickOnDocumentElement(By.id("checkout"));
        getAction().moveToElement(getDriver().findElement(By.id("first-name"))).click().sendKeys("Firstname")
                .moveToElement(getDriver().findElement(By.id("last-name"))).click().sendKeys("Lastname")
                .moveToElement(getDriver().findElement(By.id("postal-code"))).click().sendKeys("77001")
                .moveToElement(getDriver().findElement(By.id("continue"))).click().perform();

        List<WebElement> webCheckoutItems = getDriver().findElements(cartItemsLocator);
        List<Inventory> checkoutItems = new ArrayList<>();
        final By cartItemLabelLocator = By.xpath(".//div[@class='cart_item_label']/a");

        webCheckoutItems.forEach(checkoutItem -> checkoutItems.add(new Inventory(
                checkoutItem.findElement(cartItemLabelLocator).getAttribute("id")
                , checkoutItem.findElement(inventoryNameLocator).getText()
                , GroupATeamSauceDemoUtils.getDouble(checkoutItem.findElement(inventoryPriceLocator), "$"))));

        double sum = GroupATeamSauceDemoUtils
                .getDouble(getDriver().findElement(By.cssSelector("div.summary_subtotal_label")), "Item total: $");
        double tax = GroupATeamSauceDemoUtils
                .getDouble(getDriver().findElement(By.cssSelector("div.summary_tax_label")), "Tax: $");
        double total = GroupATeamSauceDemoUtils
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
        clickOnDocumentElement(sidebarMenuBtnLocator);

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
        return new Inventory(inventory.findElement(inventoryLabelLocator).getAttribute("id")
                , inventory.findElement(inventoryNameLocator).getText()
                , GroupATeamSauceDemoUtils.getDouble(inventory.findElement(inventoryPriceLocator), "$"));
    }

    private List<Inventory> getInventories() {
        List<WebElement> webInventories = getDriver().findElements(inventoriesLocator);
        return webInventories.stream().map(this::getInventory).collect(Collectors.toList());
    }
}