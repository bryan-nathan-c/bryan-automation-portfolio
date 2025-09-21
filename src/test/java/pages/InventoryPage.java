package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getFirstProduct() {
        return products.get(0);
    }

    public void addProductToCart(String productName) {
        for (WebElement product : products) {
            WebElement title = product.findElement(org.openqa.selenium.By.className("inventory_item_name"));
            if (title.getText().equalsIgnoreCase(productName)) {
                WebElement addButton = product.findElement(org.openqa.selenium.By.tagName("button"));
                clickElement(addButton);
                break;
            }
        }
    }

    public void goToCart() {
        clickElement(cartIcon);
    }
}
