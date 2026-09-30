package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.pages.AmazonSearchProductsPage;
import com.ama.qa.util.TimeWindowUtil;

/**
 * Task Requirements:
 * 1. Automate product search with filters:
 *    - Brand starting with letter 'C' (e.g. Casio, Canon, Crocs, Campus)
 *    - Price Range > ₹2000 (above 2k)
 *    - Customer Rating > 4 Stars
 * 2. Execution Window Restriction: ONLY between 3:00 PM and 6:00 PM.
 */
public class BrandPriceRatingFilterTest extends TestBase {

    AmazonHomePage amazonHomePage;
    AmazonSearchProductsPage amazonSearchProductsPage;
    AmazonProductDetailsPage amazonProductDetailsPage;

    String brandNameFilter = "Casio"; // Starts with 'C'
    double minPriceThreshold = 2000.0; // Above 2k
    double minRatingThreshold = 4.0;    // Above 4 Stars

    @BeforeMethod
    public void setup() {
        initialization();
        amazonHomePage = new AmazonHomePage();
        amazonSearchProductsPage = new AmazonSearchProductsPage();
        amazonProductDetailsPage = new AmazonProductDetailsPage();
    }

    @Test
    public void testProductSearchWithBrandCPrice2kRating4Filters() {
        // Step 1: Execution Window Enforcement (3:00 PM - 6:00 PM)
        Assert.assertTrue(TimeWindowUtil.isWithin3To6PMWindow(),
            "Execution Failure: Test execution is restricted to run ONLY between 3:00 PM and 6:00 PM. Current system time is outside the allowed window.");

        System.out.println("Execution window check passed: Test running within 3:00 PM - 6:00 PM.");

        // Step 2: Validate Brand name starts with letter 'C'
        Assert.assertNotNull(brandNameFilter, "Brand filter must not be null.");
        Assert.assertTrue(brandNameFilter.toUpperCase().startsWith("C"),
            "Brand Validation Failed: Brand name must start with letter 'C'. Actual: " + brandNameFilter);

        System.out.println("Brand validation passed: Brand '" + brandNameFilter + "' starts with letter 'C'.");

        // Step 3: Search for product category
        String searchCategory = "watches";
        System.out.println("Searching for product: " + searchCategory);
        amazonHomePage.searchForProduct(searchCategory);
        amazonHomePage.clickOnSearchButton();

        // Step 4: Apply Filters (Brand C, Price > 2000, Rating > 4 Stars)
        amazonSearchProductsPage.selectBrandStartingWithC(brandNameFilter);
        amazonSearchProductsPage.applyMinPriceFilter("2000");
        amazonSearchProductsPage.applyCustomerRatingFourStarsAndUp();

        // Step 5: Select filtered product & navigate to details page
        amazonSearchProductsPage.clickOnProductWithBrandC_Price2k_Rating4();

        String productTitle = amazonProductDetailsPage.getSearchedProductTitle();
        double productPrice = amazonProductDetailsPage.getProductPriceAsDouble();

        System.out.println("Selected Filtered Product Title: " + productTitle);
        System.out.println("Observed Product Price: ₹" + productPrice);

        // Step 6: Assertions
        Assert.assertFalse(productTitle.isEmpty(), "Product title must not be empty.");
        Assert.assertTrue(productPrice > minPriceThreshold,
            "Price Assertion Failed: Product price (₹" + productPrice + ") must be above ₹2000.");

        System.out.println("Test Passed: Product filters applied successfully. Price (₹" + productPrice + ") > ₹2000, Rating > 4 Stars, Brand starts with 'C'.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
