package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Samsung galaxy s6')]")
    private WebElement samsungGalaxyS6;

    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Getter untuk step definitions
    public WebElement getSamsungGalaxyS6() {
        return samsungGalaxyS6;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickProduct(String productName) {
        // hanya satu produk di contoh
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(samsungGalaxyS6));
        samsungGalaxyS6.click();
    }

    public void goToCart() {
        cartLink.click();
    }
}
