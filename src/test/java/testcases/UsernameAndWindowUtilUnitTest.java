package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ama.qa.util.TimeWindowUtil;
import com.ama.qa.util.UsernameValidatorUtil;

/**
 * Unit test class to verify Time Window and Username validation rules.
 */
public class UsernameAndWindowUtilUnitTest {

    @Test
    public void testValidUsernames() {
        // Must contain exactly 10 characters and NO special characters
        Assert.assertTrue(UsernameValidatorUtil.isValidUsername("UserLalit1"), "UserLalit1 should be valid (10 chars, alphanumeric)");
        Assert.assertTrue(UsernameValidatorUtil.isValidUsername("SonalGarg1"), "SonalGarg1 should be valid (10 chars, alphanumeric)");
        Assert.assertTrue(UsernameValidatorUtil.isValidUsername("1234567890"), "1234567890 should be valid (10 numeric chars)");
    }

    @Test
    public void testInvalidUsernamesWithSpecialCharacters() {
        // Special characters not allowed
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("User@12345"), "Username with @ should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("Lalit_1234"), "Username with underscore should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("User.Lalit"), "Username with dot should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("User#12345"), "Username with hash should be invalid");
    }

    @Test
    public void testInvalidUsernamesLength() {
        // Length must be strictly 10 characters
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("Short"), "Username with < 10 chars should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername("TooLongUsername123"), "Username with > 10 chars should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername(""), "Empty username should be invalid");
        Assert.assertFalse(UsernameValidatorUtil.isValidUsername(null), "Null username should be invalid");
    }

    @Test
    public void testTimeWindowCheck() {
        boolean is6To7PM = TimeWindowUtil.isWithin6To7PMWindow();
        System.out.println("Current System Time within 6:00 PM to 7:00 PM window: " + is6To7PM);
    }
}
