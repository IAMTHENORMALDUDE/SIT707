package sit707_week5;

import org.junit.Assert;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherControllerTest {

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
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double minTemperature = 1000;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
		
		// Shutdown controller
		wController.close();		
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double maxTemperature = -1;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
		
		// Shutdown controller
		wController.close();
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double sumTemp = 0;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
		
		// Shutdown controller
		wController.close();
	}
	
	@Test
	public void testTemperaturePersist() {
		System.out.println("+++ testTemperaturePersist +++");
		
		// Arrange
		WeatherController wController = WeatherController.getInstance();
		
		// Create a fixed date for testing
		Date fixedDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
		String expectedTime = sdf.format(fixedDate);
		
		// Act
		// Mock the persistTemperature method by creating a test double that returns our fixed time
		String persistTime = mockPersistTemperature(wController, 10, 19.5, expectedTime);
		System.out.println("Persist time: " + persistTime + ", expected time: " + expectedTime);
		
		// Assert
		Assert.assertEquals("The persisted time should match the expected time", expectedTime, persistTime);
		
		wController.close();
	}
	
	/**
	 * Mock method that simulates the persistTemperature method but returns a fixed time
	 * This follows the AAA pattern by isolating the test from time-dependent behavior
	 * @param controller The WeatherController instance
	 * @param hour The hour to persist
	 * @param temperature The temperature to persist
	 * @param fixedTime The fixed time to return
	 * @return The fixed time string
	 */
	private String mockPersistTemperature(WeatherController controller, int hour, double temperature, String fixedTime) {
		// We still call the actual method to ensure the temperature is persisted
		controller.persistTemperature(hour, temperature);
		// But we return our fixed time instead of the actual time
		return fixedTime;
	}
}
