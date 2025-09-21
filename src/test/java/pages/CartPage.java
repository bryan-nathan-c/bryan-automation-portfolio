package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    private By cartItemsLocator = By.className("cart_item");
    private By checkoutButtonLocator = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void waitForCartPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButtonLocator));
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> cartItems = driver.findElements(cartItemsLocator);
        for (WebElement item : cartItems) {
            WebElement title = item.findElement(By.className("inventory_item_name"));
            if (title.getText().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public void removeProduct(String productName) {
        List<WebElement> cartItems = driver.findElements(cartItemsLocator);
        for (WebElement item : cartItems) {
            WebElement title = item.findElement(By.className("inventory_item_name"));
            if (title.getText().equalsIgnoreCase(productName)) {
                WebElement removeButton = item.findElement(By.tagName("button"));
                clickElement(removeButton);
                break;
            }
        }
    }

    public void clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement checkoutBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("checkout"))
        );
        checkoutBtn.click();
        System.out.println("Clicked Checkout -> URL now: " + driver.getCurrentUrl());
    }

}
