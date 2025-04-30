package web.service;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for LoginService.login() method.
 * Developer point-of-view: test all logic branches and edge cases.
 */
public class LoginServiceUnitTest {

    @Test
    public void testValidCredentials() {
        assertTrue(LoginService.login("ahsan", "ahsan_pass", "1990-01-01"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(LoginService.login("wronguser", "ahsan_pass", "1990-01-01"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(LoginService.login("ahsan", "wrongpass", "1990-01-01"));
    }

    @Test
    public void testInvalidDob() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "2000-12-31"));
    }

    @Test
    public void testAllFieldsInvalid() {
        assertFalse(LoginService.login("user", "pass", "2000-01-01"));
    }

    @Test
    public void testEmptyUsername() {
        assertFalse(LoginService.login("", "ahsan_pass", "1990-01-01"));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse(LoginService.login("ahsan", "", "1990-01-01"));
    }

    @Test
    public void testEmptyDob() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", ""));
    }

    @Test
    public void testNullUsername() {
        assertFalse(LoginService.login(null, "ahsan_pass", "1990-01-01"));
    }

    @Test
    public void testNullPassword() {
        assertFalse(LoginService.login("ahsan", null, "1990-01-01"));
    }

    @Test
    public void testNullDob() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", null));
    }

    @Test
    public void testWrongDateFormat() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "01-01-1990"));
    }
}
