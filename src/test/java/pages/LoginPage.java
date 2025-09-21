package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object model for SauceDemo login page.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter username into username input field.
     */
    public void enterUsername(String username) {
        enterText(usernameInput, username);
    }

    /**
     * Enter password into password input field.
     */
    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    /**
     * Click the login button.
     */
    public void clickLogin() {
        clickElement(loginButton);
    }

    /**
     * Get displayed error message text if present.
     * Returns empty string if no message is displayed.
     */
    public String getErrorMessage() {
        try {
            return getElementText(errorMessage);
        } catch (Exception e) {
            // Could log exception here for troubleshooting
            return "";
        }
    }
}
