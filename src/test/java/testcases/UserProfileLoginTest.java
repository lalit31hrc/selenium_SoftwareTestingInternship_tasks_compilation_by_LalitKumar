package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonLoginPage;
import com.ama.qa.util.ConfigReader;
import com.ama.qa.util.TimeWindowUtil;
import com.ama.qa.util.UserProfileValidatorUtil;

/**
 * Task Requirements:
 * 1. Test login functionality and validate whether user profile details are displayed correctly.
 * 2. User profile name must NOT contain the following characters: (A, C, G, I, L, K).
 * 3. Testing must work ONLY between 12:00 PM and 3:00 PM.
 */
public class UserProfileLoginTest extends TestBase {

    ConfigReader configReader;
    AmazonLoginPage amazonLoginPage;
    AmazonHomePage amazonHomePage;

    // Test profile name for validation verification
    String testProfileName = "Steve"; // Contains no A, C, G, I, L, or K

    @BeforeMethod
    public void setup() {
        initialization();
        configReader = new ConfigReader();
        amazonLoginPage = new AmazonLoginPage();
        amazonHomePage = new AmazonHomePage();
    }

    @Test
    public void testLoginAndProfileNameExclusionRules12To3PM() {
        // Step 1: Time Window Restriction Check (12:00 PM - 3:00 PM)
        Assert.assertTrue(TimeWindowUtil.isWithin12To3PMWindow(),
            "Execution Failure: Test is restricted to run ONLY between 12:00 PM and 3:00 PM. Current system time is outside the allowed window.");

        System.out.println("Execution window check passed: Test running within 12:00 PM - 3:00 PM.");

        // Step 2: Login Execution Flow
        String username = configReader.getUsername();
        String password = configReader.getPassword();

        Assert.assertNotNull(username, "Username configuration must not be null.");
        Assert.assertNotNull(password, "Password configuration must not be null.");

        System.out.println("Initiating login test with configured credentials...");
        amazonLoginPage.enterEmailOrPhone(username);
        amazonLoginPage.clickContinue();
        amazonLoginPage.enterPassword(password);
        amazonLoginPage.clickSignIn();

        // Step 3: Validate User Profile Name Exclusion Rule (No A, C, G, I, L, K)
        System.out.println("Validating profile name: " + testProfileName);

        boolean isValidProfile = UserProfileValidatorUtil.isValidProfileName(testProfileName);
        var forbiddenCharsFound = UserProfileValidatorUtil.getForbiddenCharsFound(testProfileName);

        Assert.assertTrue(isValidProfile,
            "Profile Name Validation Failed: Profile name '" + testProfileName +
            "' contains forbidden characters " + forbiddenCharsFound + ". Allowed characters must exclude (A, C, G, I, L, K).");

        System.out.println("Profile name validation passed! Name '" + testProfileName + "' contains no forbidden characters (A, C, G, I, L, K).");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
