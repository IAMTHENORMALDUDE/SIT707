package sit707_week5;

import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class WeatherControllerTest {
	// Shared controller instance and temperature data
	private static WeatherController wController;
	private static double[] hourlyTemperatures;
	private static int nHours;
	
	/**
	 * Setup method that runs once before all tests.
	 * This follows the "Arrange" part of AAA for all tests.
	 */
	@BeforeClass
	public static void setUp() {
		System.out.println("+++ Setting up WeatherController for all tests +++");
		
		// Arrange: Initialize controller once for all tests
		wController = WeatherController.getInstance();
		nHours = wController.getTotalHours();
		
		// Store all hourly temperatures to avoid repeated slow calls
		hourlyTemperatures = new double[nHours];
		for (int i = 0; i < nHours; i++) {
			// Hour range: 1 to nHours
			hourlyTemperatures[i] = wController.getTemperatureForHour(i+1);
		}
		
		System.out.println("Setup complete. All hourly temperatures retrieved.");
	}
	
	/**
	 * Teardown method that runs once after all tests.
	 * This follows the "After" part that comes after all tests.
	 */
	@AfterClass
	public static void tearDown() {
		System.out.println("+++ Shutting down WeatherController after all tests +++");
		
		// After: Close the controller once after all tests
		if (wController != null) {
			wController.close();
		}
	}

	@Test
	public void testStudentIdentity() {
		// Arrange
		String studentId = "222470713";
		
		// Act & Assert
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		// Arrange
		String studentName = "Farid Vazirnia";
		
		// Act & Assert
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		
		// Arrange: Calculate expected minimum temperature from stored values
		double minTemperature = 1000;
		for (int i = 0; i < nHours; i++) {
			if (minTemperature > hourlyTemperatures[i]) {
				minTemperature = hourlyTemperatures[i];
			}
		}
		
		// Act: Get the minimum temperature from cache
		double cachedMinTemperature = wController.getTemperatureMinFromCache();
		
		// Assert: Compare calculated and cached values
		Assert.assertEquals(minTemperature, cachedMinTemperature, 0.0001);
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		
		// Arrange: Calculate expected maximum temperature from stored values
		double maxTemperature = -1;
		for (int i = 0; i < nHours; i++) {
			if (maxTemperature < hourlyTemperatures[i]) {
				maxTemperature = hourlyTemperatures[i];
			}
		}
		
		// Act: Get the maximum temperature from cache
		double cachedMaxTemperature = wController.getTemperatureMaxFromCache();
		
		// Assert: Compare calculated and cached values
		Assert.assertEquals(maxTemperature, cachedMaxTemperature, 0.0001);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");
		
		// Arrange: Calculate expected average temperature from stored values
		double sumTemp = 0;
		for (int i = 0; i < nHours; i++) {
			sumTemp += hourlyTemperatures[i];
		}
		double averageTemp = sumTemp / nHours;
		
		// Act: Get the average temperature from cache
		double cachedAverageTemp = wController.getTemperatureAverageFromCache();
		
		// Assert: Compare calculated and cached values
		Assert.assertEquals(averageTemp, cachedAverageTemp, 0.0001);
	}
	
	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
//		System.out.println("+++ testTemperaturePersist +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		String persistTime = wController.persistTemperature(10, 19.5);
//		String now = new SimpleDateFormat("H:m:s").format(new Date());
//		System.out.println("Persist time: " + persistTime + ", now: " + now);
//		
//		Assert.assertTrue(persistTime.equals(now));
//		
//		wController.close();
	}
}
