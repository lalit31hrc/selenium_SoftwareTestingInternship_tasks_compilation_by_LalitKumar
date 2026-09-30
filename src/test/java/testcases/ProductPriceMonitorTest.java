package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.pages.AmazonSearchProductsPage;
import com.ama.qa.util.EmailNotificationUtil;
import com.ama.qa.util.PriceMonitorService;

/**
 * Test case for Task:
 * Automating a system that monitors price changes for a product and triggers a notification
 * (e.g., email) when it drops below a specified threshold.
 */
public class ProductPriceMonitorTest extends TestBase {

    AmazonHomePage amazonHomePage;
    AmazonSearchProductsPage amazonSearchProductsPage;
    AmazonProductDetailsPage amazonProductDetailsPage;
    PriceMonitorService priceMonitorService;

    // Configurable thresholds & parameters
    double targetThresholdPrice = 5000.0; // Target threshold in Rupees
    String notificationEmail = "user.alert@example.com";

    @BeforeMethod
    public void setup() {
        initialization();
        amazonHomePage = new AmazonHomePage();
        amazonSearchProductsPage = new AmazonSearchProductsPage();
        amazonProductDetailsPage = new AmazonProductDetailsPage();
        priceMonitorService = new PriceMonitorService();
    }

    @Test(priority = 1)
    public void testProductPriceMonitoringAndNotificationTrigger() {
        // Step 1: Search for target product on e-commerce store
        String searchKeyword = "laptop bag";
        System.out.println("Searching for product: " + searchKeyword);
        amazonHomePage.searchForProduct(searchKeyword);
        amazonHomePage.clickOnSearchButton();

        // Step 2: Open target product details page
        amazonSearchProductsPage.clickOnFirstEligibleProduct();

        String productTitle = amazonProductDetailsPage.getSearchedProductTitle();
        double currentPrice = amazonProductDetailsPage.getProductPriceAsDouble();

        System.out.println("Monitored Product Title: " + productTitle);
        System.out.println("Observed Current Price: ₹" + currentPrice);
        System.out.println("Target Price Threshold: ₹" + targetThresholdPrice);

        Assert.assertTrue(currentPrice > 0, "Product price should be greater than 0.");

        // Step 3: Check price against threshold and trigger email notification if price dropped
        boolean notificationTriggered = priceMonitorService.checkAndNotify(
            productTitle, currentPrice, targetThresholdPrice, notificationEmail
        );

        if (currentPrice <= targetThresholdPrice) {
            Assert.assertTrue(notificationTriggered, "Email notification should be dispatched when price is below threshold.");
            System.out.println("SUCCESS: Price dropped below threshold! Email notification sent successfully.");
        } else {
            Assert.assertFalse(notificationTriggered, "Notification should NOT be dispatched when current price is above threshold.");
            System.out.println("INFO: Current price is above threshold. Monitoring active.");
        }
    }

    @Test(priority = 2)
    public void testSimulatedPriceDropTrigger() {
        // Unit/Integration test simulating price drop below threshold
        String simulatedProduct = "Sony WH-1000XM5 Wireless Headphones";
        double currentObservedPrice = 1499.0;
        double thresholdPrice = 2000.0;

        EmailNotificationUtil mockEmailUtil = new EmailNotificationUtil();
        PriceMonitorService customMonitor = new PriceMonitorService(mockEmailUtil);

        boolean notificationSent = customMonitor.checkAndNotify(simulatedProduct, currentObservedPrice, thresholdPrice, notificationEmail);

        Assert.assertTrue(notificationSent, "Notification alert should trigger when simulated price drops below threshold.");
        Assert.assertTrue(mockEmailUtil.isLastNotificationSent(), "Email dispatch status should be true.");
        Assert.assertTrue(mockEmailUtil.getLastNotificationLog().contains("PRICE DROP ALERT"), "Log should contain price drop alert text.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
