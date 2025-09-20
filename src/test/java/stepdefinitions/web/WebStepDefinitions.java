package stepdefinitions.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

public class WebStepDefinitions {

    private WebDriver driver;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    @Before("@web")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @After("@web")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open DemoBlaze homepage")
    public void iOpenDemoBlazeHomepage() {
        driver.get("https://www.demoblaze.com/");
        homePage = new HomePage(driver);
        // Tunggu sampai produk terlihat
        homePage.wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(
                        homePage.getSamsungGalaxyS6()
                )
        );
    }

    @When("I check the page title")
    public void iCheckThePageTitle() {
        // Tidak perlu aksi tambahan
    }

    @Then("The page title should contain {string}")
    public void thePageTitleShouldContain(String expected) {
        String actual = homePage.getPageTitle();
        Assertions.assertTrue(actual.contains(expected),
                "Expected title to contain: " + expected + " but was: " + actual);
    }

    @When("I click on {string} product")
    public void iClickOnProduct(String productName) {
        homePage.clickProduct(productName);
        productPage = new ProductPage(driver);
    }

    @When("I click Add to cart button")
    public void iClickAddToCartButton() {
        productPage.clickAddToCart();
    }

    @Then("I should see success message {string}")
    public void iShouldSeeSuccessMessage(String message) {
        String alertText = productPage.getAlertText();
        Assertions.assertTrue(alertText.contains(message),
                "Expected alert to contain: " + message + " but was: " + alertText);
    }

    @When("I go to Cart page")
    public void iGoToCartPage() {
        homePage.goToCart();
        cartPage = new CartPage(driver);
    }

    @Then("I should see {string} in cart")
    public void iShouldSeeInCart(String productName) {
        // Tunggu halaman cart load
        cartPage.wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(
                        cartPage.getProductElement(productName)
                )
        );
        boolean present = cartPage.isProductInCart(productName);
        Assertions.assertTrue(present, "Product " + productName + " should be in cart");
    }
}
