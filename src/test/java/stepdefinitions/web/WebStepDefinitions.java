package stepdefinitions.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebStepDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @Before("@web")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");



        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After("@web")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open SauceDemo homepage")
    public void iOpenSauceDemoHomepage() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        // Wait by locator instead of calling getUsernameInput()
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
    }

    @Given("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        inventoryPage = new InventoryPage(driver);
        wait.until(ExpectedConditions.visibilityOf(inventoryPage.getFirstProduct()));
        System.out.println("URL after login: " + driver.getCurrentUrl());
    }

    @Then("The page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitlePart) {
        String actualTitle = driver.getTitle();
        Assertions.assertTrue(actualTitle.contains(expectedTitlePart),
                "Expected page title to contain: " + expectedTitlePart);
    }

    @When("I add product {string} to cart")
    public void iAddProductToCart(String productName) {
        inventoryPage.addProductToCart(productName);
    }

    @When("I go to Cart page")
    public void iGoToCartPage() {
        inventoryPage.goToCart();
        cartPage = new CartPage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart_item")));
    }

    @Then("I should see {string} in cart")
    public void iShouldSeeInCart(String productName) {
        Assertions.assertTrue(cartPage.isProductInCart(productName),
                "Expected product " + productName + " to be in cart");
    }

    @When("I remove {string} from cart")
    public void iRemoveFromCart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("{string} should not be visible in cart")
    public void shouldNotBeVisibleInCart(String productName) {
        Assertions.assertFalse(cartPage.isProductInCart(productName),
                "Expected product " + productName + " NOT to be in cart");
    }

    @When("I login with invalid username and password")
    public void iLoginWithInvalidUsernameAndPassword() {
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("invalidPass");
        loginPage.clickLogin();
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, loginPage.getErrorMessage());
    }

    @When("I click Checkout button")
    public void iClickCheckoutButton() {
        cartPage.clickCheckout();
        System.out.println("URL after checkout: " + driver.getCurrentUrl());
        checkoutPage = new CheckoutPage(driver);
    }



    @When("I enter checkout information with first name {string}, last name {string}, postal code {string}")
    public void iEnterCheckoutInformation(String firstName, String lastName, String postalCode) {
        System.out.println("URL before filling info: " + driver.getCurrentUrl());
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
    }


    @When("I submit order")
    public void iSubmitOrder() {
        checkoutPage.clickFinish();
    }

    @Then("I should see order confirmation message")
    public void iShouldSeeOrderConfirmationMessage() {
        Assertions.assertTrue(checkoutPage.getConfirmationMessage().contains("Thank you for your order!"),
                "Expected order confirmation message.");
    }
}
