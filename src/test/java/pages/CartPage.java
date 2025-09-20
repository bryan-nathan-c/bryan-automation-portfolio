package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    @FindBy(xpath = "//td[contains(text(),'Samsung galaxy s6')]")
    private WebElement samsungProduct;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductElement(String productName) {
        // hanya contoh untuk Samsung galaxy s6
        return samsungProduct;
    }

    public boolean isProductInCart(String productName) {
        return samsungProduct.isDisplayed();
    }
}
