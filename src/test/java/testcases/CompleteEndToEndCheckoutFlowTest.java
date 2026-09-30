package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonProductCheckoutPage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.pages.AmazonSearchProductsPage;
import com.ama.qa.pages.ShoppingCartPage;
import com.ama.qa.util.TimeWindowUtil;

/**
 * Task Requirements:
 * Test the complete end-to-end flow of:
 * 1. Searching for a product
 * 2. Adding it to the shopping cart
 * 3. Making a payment and confirming the order
 * 4. Payment must be MORE THAN Rs 500
 * 5. Testing must work ONLY between 6:00 PM and 7:00 PM
 */
public class CompleteEndToEndCheckoutFlowTest extends TestBase {

    AmazonHomePage amazonHomePage;
    AmazonSearchProductsPage amazonSearchProductsPage;
    AmazonProductDetailsPage amazonProductDetailsPage;
    ShoppingCartPage shoppingCartPage;
    AmazonProductCheckoutPage amazonProductCheckoutPage;

    double minimumPaymentThreshold = 500.0;

    @BeforeMethod
    public void setup() {
        initialization();
        amazonHomePage = new AmazonHomePage();
        amazonSearchProductsPage = new AmazonSearchProductsPage();
        amazonProductDetailsPage = new AmazonProductDetailsPage();
        shoppingCartPage = new ShoppingCartPage();
        amazonProductCheckoutPage = new AmazonProductCheckoutPage();
    }

    @Test
    public void testCompleteProductSearchAddToCartAndCheckoutFlow() {
        // Step 1: Execution Window Enforcement (6:00 PM - 7:00 PM)
        Assert.assertTrue(TimeWindowUtil.isWithin6To7PMWindow(),
            "Execution Failure: Test execution is restricted to run ONLY between 6:00 PM and 7:00 PM. Current system time is outside the allowed window.");

        System.out.println("Execution window check passed: Test running within 6:00 PM - 7:00 PM.");

        // Step 2: Search for a product (Product priced > Rs 500)
        String searchProductQuery = "wireless bluetooth headphones";
        System.out.println("Searching for product: " + searchProductQuery);
        amazonHomePage.searchForProduct(searchProductQuery);
        amazonHomePage.clickOnSearchButton();

        // Step 3: Select product and Add to Cart
        amazonSearchProductsPage.clickOnFirstEligibleProduct();

        String productTitle = amazonProductDetailsPage.getSearchedProductTitle();
        double productPrice = amazonProductDetailsPage.getProductPriceAsDouble();

        System.out.println("Selected Product: " + productTitle);
        System.out.println("Product Price: ₹" + productPrice);

        Assert.assertFalse(productTitle.isEmpty(), "Product title should not be empty.");
        Assert.assertTrue(productPrice > 0, "Product price should be valid (> 0).");

        // Click Add to Cart
        amazonProductDetailsPage.clickOnAddToCart();
        System.out.println("Product added to cart successfully.");

        // Step 4: Open Shopping Cart & Verify Payment Amount > Rs 500
        amazonHomePage.clickOnCart();

        double cartTotalPrice = shoppingCartPage.getCartSubtotalPrice();
        System.out.println("Cart Total Amount: ₹" + cartTotalPrice);

        Assert.assertTrue(cartTotalPrice > minimumPaymentThreshold,
            "Payment Validation Failed: Payment amount (₹" + cartTotalPrice + ") must be greater than Rs 500.");

        System.out.println("Payment amount validation passed: Total amount ₹" + cartTotalPrice + " > Rs 500.");

        // Step 5: Proceed to Checkout & Order Confirmation Flow
        shoppingCartPage.clickProceedToBuy();
        System.out.println("Proceeded to checkout screen.");

        // Checkout step - Select Delivery Address
        amazonProductCheckoutPage.clickShipToAddressButton();

        // Place order and payment processing
        amazonProductCheckoutPage.placeOrderAndPay();

        System.out.println("Complete End-to-End Checkout Flow Test Executed Successfully!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
