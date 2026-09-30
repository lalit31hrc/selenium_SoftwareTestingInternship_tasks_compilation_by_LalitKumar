package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonSearchProductsPage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.util.TimeWindowUtil;

/**
 * Task: Select a product from search results that is
 * - NOT in the Electronics category
 * - Does NOT have a title starting with A, B, C, or D
 * Verify its product page details. Allowed only between 3 PM - 6 PM.
 */
public class ProductFilterTest extends TestBase {

    AmazonHomePage amazonHomePage;
    AmazonSearchProductsPage amazonSearchProductsPage;
    AmazonProductDetailsPage amazonProductDetailsPage;

    @BeforeTest
    public void setup() {
        initialization();
        amazonHomePage = new AmazonHomePage();
        amazonSearchProductsPage = new AmazonSearchProductsPage();
        amazonProductDetailsPage = new AmazonProductDetailsPage();
    }

    @Test
    public void selectNonElectronicProductNotStartingWithAtoD() {
        Assert.assertTrue(TimeWindowUtil.isWithinAllowedWindow(),
            "This test is only allowed to run between 3:00 PM and 6:00 PM. Current time is outside the allowed window.");

        amazonHomePage.selectCategory("Home & Kitchen");
        amazonHomePage.searchForProduct("bags");
        amazonHomePage.clickOnSearchButton();

        amazonSearchProductsPage.clickOnFirstEligibleProduct();

        String title = amazonProductDetailsPage.getSearchedProductTitle();
        String price = amazonProductDetailsPage.getSearchedProductPrice();

        Assert.assertFalse(title.isEmpty(), "Product title should not be empty");
        Assert.assertFalse(price.isEmpty(), "Product price should not be empty");

        char firstChar = Character.toUpperCase(title.charAt(0));
        Assert.assertFalse(firstChar == 'A' || firstChar == 'B' || firstChar == 'C' || firstChar == 'D',
            "Selected product title should not start with A, B, C, or D but was: " + title);
    }
}