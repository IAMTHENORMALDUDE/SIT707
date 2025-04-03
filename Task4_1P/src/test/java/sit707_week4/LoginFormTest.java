package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Ahsan Habib
 */
public class LoginFormTest
{
    @Test
    public void testStudentIdentity() {
        String studentId = "222470713";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Farid Vazirnia";
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login(null, null);
        Assert.assertFalse("Login should fail with empty username and password", status.isLoginSuccess());
    }

    @Test
    public void testFailEmptyUsernameAndWrongPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login(null, "xyz");
        Assert.assertFalse("Login should fail with empty username and wrong password", status.isLoginSuccess());
    }

    @Test
    public void testFailEmptyUsernameAndCorrectPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login(null, "ahsan_pass");
        Assert.assertFalse("Login should fail with empty username and correct password", status.isLoginSuccess());
    }

    @Test
    public void testFailWrongUsernameAndEmptyPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login("abc", null);
        Assert.assertFalse("Login should fail with wrong username and empty password", status.isLoginSuccess());
    }

    @Test
    public void testFailWrongUsernameAndWrongPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login("abc", "xyz");
        Assert.assertFalse("Login should fail with wrong username and wrong password", status.isLoginSuccess());
    }

    @Test
    public void testFailWrongUsernameAndCorrectPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login("abc", "ahsan_pass");
        Assert.assertFalse("Login should fail with wrong username and correct password", status.isLoginSuccess());
    }

    @Test
    public void testFailCorrectUsernameAndEmptyPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login("ahsan", null);
        Assert.assertFalse("Login should fail with correct username and empty password", status.isLoginSuccess());
    }

    @Test
    public void testFailCorrectUsernameAndWrongPasswordAndDontCareValCode() {
        LoginStatus status = LoginForm.login("ahsan", "xyz");
        Assert.assertFalse("Login should fail with correct username and wrong password", status.isLoginSuccess());
    }

    @Test
    public void testSuccessCorrectUsernameAndCorrectPasswordAndEmptyValCode() {
        LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
        Assert.assertTrue("Login should succeed with correct username and password", status.isLoginSuccess());
        Assert.assertFalse("Validation should fail with empty code", LoginForm.validateCode(null));
    }

    @Test
    public void testSuccessCorrectUsernameAndCorrectPasswordAndWrongValCode() {
        LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
        Assert.assertTrue("Login should succeed with correct username and password", status.isLoginSuccess());
        Assert.assertFalse("Validation should fail with wrong code", LoginForm.validateCode("abcd"));
    }

    @Test
    public void testSuccessCorrectUsernameAndCorrectPasswordAndCorrectValCode() {
        LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
        Assert.assertTrue("Login should succeed with correct username and password", status.isLoginSuccess());
        Assert.assertTrue("Validation should succeed with correct code", LoginForm.validateCode("123456"));
    }
}
