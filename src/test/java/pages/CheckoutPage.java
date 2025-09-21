package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");
    private By confirmationMessage = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterText(driver.findElement(firstNameInput), firstName);
        enterText(driver.findElement(lastNameInput), lastName);
        enterText(driver.findElement(postalCodeInput), postalCode);
        clickElement(driver.findElement(continueButton));
    }

    public void clickFinish() {
        clickElement(driver.findElement(finishButton));
    }

    public String getConfirmationMessage() {
        return getElementText(driver.findElement(confirmationMessage));
    }
}
