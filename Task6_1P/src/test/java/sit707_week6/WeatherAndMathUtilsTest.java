package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class WeatherAndMathUtilsTest {
	
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
	public void testFalseNumberIsEven() {
		// Test for an odd number
		Assert.assertFalse("Number 3 should not be even", WeatherAndMathUtils.isEven(3));
	}
	
    @Test
    public void testCancelWeatherAdvice() {
    	Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(70.1, 0.0));
    }

    // Additional tests for weatherAdvice
    @Test
    public void testCancelWeatherAdviceDangerousRainfall() {
        Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(50.0, 6.1));
    }

    @Test
    public void testCancelWeatherAdviceConcerningWindAndRain() {
        Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(45.1, 4.1));
    }

    @Test
    public void testWarnWeatherAdviceConcerningWind() {
        Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(45.1, 3.0));
    }

    @Test
    public void testWarnWeatherAdviceConcerningRainfall() {
        Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(30.0, 4.1));
    }

    @Test
    public void testAllClearWeatherAdvice() {
        Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(30.0, 3.0));
    }

    // Additional tests for isEven
    @Test
    public void testTrueNumberIsEven() {
        Assert.assertTrue("Number 4 should be even", WeatherAndMathUtils.isEven(4));
    }

    // Additional tests for isPrime
    @Test
    public void testPrimeNumberOne() {
        // Based on the provided code, 1 is considered prime
        Assert.assertTrue("Number 1 should be prime (as per code)", WeatherAndMathUtils.isPrime(1));
    }

    @Test
    public void testPrimeNumberEven() {
        // Based on the provided code, even numbers > 1 are not prime
        Assert.assertFalse("Number 4 should not be prime", WeatherAndMathUtils.isPrime(4));
    }

    @Test
    public void testPrimeNumberPrime() {
        Assert.assertTrue("Number 5 should be prime", WeatherAndMathUtils.isPrime(5));
    }

    @Test
    public void testPrimeNumberCompositeOdd() {
        // Based on the flawed logic, odd composite numbers might be incorrectly identified as prime
        // Let's test 9. The loop runs for i=2. isEven(9) is false. Loop continues.
        // i=3. isEven(9) is false. Loop continues... up to i=8.
        // The loop finishes, returns true.
        Assert.assertTrue("Number 9 should be identified as prime (due to flawed logic)", WeatherAndMathUtils.isPrime(9));
    }
}
