package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.pages.AmazonSearchProductsPage;
import com.ama.qa.pages.ShoppingCartPage;
import com.ama.qa.util.TimeWindowUtil;
import com.ama.qa.util.UsernameValidatorUtil;

/**
 * Task Requirements:
 * 1. Automate adding multiple products to the shopping cart.
 * 2. Verify that the total price is MORE THAN 2000 Rupees.
 * 3. Verify that the user name contains ONLY 10 characters and NO special characters.
 * 4. Ensure testing is executable ONLY between 6:00 PM and 7:00 PM.
 */
public class MultipleProductsCartAndPriceTest extends TestBase {

    AmazonHomePage amazonHomePage;
    AmazonSearchProductsPage amazonSearchProductsPage;
    AmazonProductDetailsPage amazonProductDetailsPage;
    ShoppingCartPage shoppingCartPage;

    // Standard 10-character alphanumeric username without any special characters
    String testUsername = "UserLalit1"; // 10 chars: U-s-e-r-L-a-l-i-t-1

    @BeforeMethod
    public void setup() {
        initialization();
        amazonHomePage = new AmazonHomePage();
        amazonSearchProductsPage = new AmazonSearchProductsPage();
        amazonProductDetailsPage = new AmazonProductDetailsPage();
        shoppingCartPage = new ShoppingCartPage();
    }

    @Test
    public void testMultipleProductsAdditionAndTotalPrice6To7PM() {
        // Requirement 1: Time Window restriction check (Must run between 6 PM to 7 PM)
        Assert.assertTrue(TimeWindowUtil.isWithin6To7PMWindow(),
            "Execution Failure: This test is restricted to work ONLY between 6:00 PM and 7:00 PM. Current time is outside the allowed window.");

        System.out.println("Time window verification passed: Current time is between 6:00 PM and 7:00 PM.");

        // Requirement 2: Username validation (10 characters, no special characters)
        Assert.assertNotNull(testUsername, "Username must not be null.");
        Assert.assertEquals(testUsername.length(), 10,
            "Username validation failed: Username length must be exactly 10 characters. Actual length: " + testUsername.length());
        Assert.assertTrue(UsernameValidatorUtil.isValidUsername(testUsername),
            "Username validation failed: Username must contain only 10 alphanumeric characters with NO special characters. Found: " + testUsername);

        System.out.println("Username validation passed for: " + testUsername);

        // Requirement 3: Add multiple products to the shopping cart
        // Add Product 1 (e.g. Wireless Headphones)
        amazonHomePage.searchForProduct("wireless headphones");
        amazonHomePage.clickOnSearchButton();
        amazonSearchProductsPage.clickOnFirstEligibleProduct();
        amazonProductDetailsPage.clickOnAddToCart();

        // Add Product 2 (e.g. Smart Watch)
        amazonHomePage.searchForProduct("smart watch");
        amazonHomePage.clickOnSearchButton();
        amazonSearchProductsPage.clickOnFirstEligibleProduct();
        amazonProductDetailsPage.clickOnAddToCart();

        // Navigate to Shopping Cart Page
        amazonHomePage.clickOnCart();

        // Requirement 4: Verify total price > 2000 Rupees
        double totalPrice = shoppingCartPage.getCartSubtotalPrice();
        System.out.println("Calculated Total Price in Cart: ₹" + totalPrice);

        Assert.assertTrue(totalPrice > 2000.0,
            "Total Price Assertion Failed: Total cart price (₹" + totalPrice + ") must be greater than 2000 Rupees.");

        System.out.println("Test Passed: Total price (₹" + totalPrice + ") is greater than 2000 Rupees.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
