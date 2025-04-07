package sit707_tasks;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Ahsan Habib
 */
public class DateUtilTest {
	
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
	public void testMaxJanuary31ShouldIncrementToFebruary1() {
		// January max boundary area: max+1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldIncrementToFebruary1 > " + date);
        date.increment();
        System.out.println(date);
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(1, date.getDay());
	}
	
	@Test
	public void testMaxJanuary31ShouldDecrementToJanuary30() {
		// January max boundary area: max-1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldDecrementToJanuary30 > " + date);
        date.decrement();
        System.out.println(date);
        Assert.assertEquals(30, date.getDay());
        Assert.assertEquals(1, date.getMonth());
	}
	
	@Test
	public void testNominalJanuary() {
		int rand_day_1_to_31 = 1 + new Random().nextInt(31);
        DateUtil date = new DateUtil(rand_day_1_to_31, 1, 2024);
        System.out.println("testJanuaryNominal > " + date);
        date.increment();
        System.out.println(date);
	}
	
	/*
	 * Complete below test cases.
	 */
	
	@Test
	public void testMinJanuary1ShouldIncrementToJanuary2() {
	    DateUtil date = new DateUtil(1, 1, 2024);
	    System.out.println("testMinJanuary1ShouldIncrementToJanuary2 > " + date);
	    date.increment();
	    System.out.println(date);
	    Assert.assertEquals(2, date.getDay());
	    Assert.assertEquals(1, date.getMonth());
	    Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testMinJanuary1ShouldDecrementToDecember31() {
	    DateUtil date = new DateUtil(1, 1, 2024);
	    System.out.println("testMinJanuary1ShouldDecrementToDecember31 > " + date);
	    date.decrement();
	    System.out.println(date);
	    Assert.assertEquals(31, date.getDay());
	    Assert.assertEquals(12, date.getMonth());
	    Assert.assertEquals(2023, date.getYear());
	}

	
	/*
	 * Write tests for rest months of year 2024.
	 */
	
	@Test
	public void testMaxJune30ShouldIncrementToJuly1() {
	    DateUtil date = new DateUtil(30, 6, 1994);
	    date.increment();
	    Assert.assertEquals(1, date.getDay());
	    Assert.assertEquals(7, date.getMonth());
	    Assert.assertEquals(1994, date.getYear());
	}

	@Test
	public void testMaxJune30ShouldDecrementToJune29() {
	    DateUtil date = new DateUtil(30, 6, 1994);
	    date.decrement();
	    Assert.assertEquals(29, date.getDay());
	    Assert.assertEquals(6, date.getMonth());
	    Assert.assertEquals(1994, date.getYear());
	}

	
	@Test
	public void testFebruary28LeapYearShouldIncrementToFebruary29() {
	    DateUtil date = new DateUtil(28, 2, 2024);
	    date.increment();
	    Assert.assertEquals(29, date.getDay());
	    Assert.assertEquals(2, date.getMonth());
	    Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testFebruary29LeapYearShouldIncrementToMarch1() {
	    DateUtil date = new DateUtil(29, 2, 2024);
	    date.increment();
	    Assert.assertEquals(1, date.getDay());
	    Assert.assertEquals(3, date.getMonth());
	    Assert.assertEquals(2024, date.getYear());
	}

	
	@Test
	public void testJune15ShouldIncrementToJune16() {
	    DateUtil date = new DateUtil(15, 6, 1994);
	    date.increment();
	    Assert.assertEquals(16, date.getDay());
	    Assert.assertEquals(6, date.getMonth());
	    Assert.assertEquals(1994, date.getYear());
	}
	
	// Helper method to check if a year is a leap year
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Test
    public void testLeapYearFebruaryBoundaries() {
        // Define the year range from the task sheet
        int startYear = 1700;
        int endYear = 2024;

        for (int year = startYear; year <= endYear; year++) {
            // Test 28th Feb -> 29th Feb
            DateUtil dateFeb28 = new DateUtil(28, 2, year);
            dateFeb28.increment();
            assertEquals("Day should be 29 for " + year, 29, dateFeb28.getDay());
            assertEquals("Month should be 2 for " + year, 2, dateFeb28.getMonth());
            assertEquals("Year should be " + year, year, dateFeb28.getYear());

            // Test 29th Feb -> 1st March
            DateUtil dateFeb29 = new DateUtil(29, 2, year);
            dateFeb29.increment();
            assertEquals("Day should be 1 for " + year, 1, dateFeb29.getDay());
            assertEquals("Month should be 3 for " + year, 3, dateFeb29.getMonth());
            assertEquals("Year should be " + year, year, dateFeb29.getYear());
        }
    }

    // Test all leap years in the range (for broader coverage)
    @Test
    public void testAllLeapYearsFebruaryBoundaries() {
        int startYear = 1700;
        int endYear = 2024;
        int leapYearCount = 0;

        for (int year = startYear; year <= endYear; year++) {
            if (isLeapYear(year)) {
                leapYearCount++;
                // Test 28th Feb -> 29th Feb
                DateUtil dateFeb28 = new DateUtil(28, 2, year);
                dateFeb28.increment();
                assertEquals("Day should be 29 for " + year, 29, dateFeb28.getDay());
                assertEquals("Month should be 2 for " + year, 2, dateFeb28.getMonth());
                assertEquals("Year should be " + year, year, dateFeb28.getYear());

                // Test 29th Feb -> 1st March
                DateUtil dateFeb29 = new DateUtil(29, 2, year);
                dateFeb29.increment();
                assertEquals("Day should be 1 for " + year, 1, dateFeb29.getDay());
                assertEquals("Month should be 3 for " + year, 3, dateFeb29.getMonth());
                assertEquals("Year should be " + year, year, dateFeb29.getYear());
            }
        }
        System.out.println("Tested " + leapYearCount + " leap years between " + startYear + " and " + endYear);
    }
    
    @Test
    public void testFebruaryTransitionsAllYears() {
        int startYear = 1700;
        int endYear = 2024;
        for (int year = startYear; year <= endYear; year++) {
            boolean isLeap = year % 4 == 0;
            int febMax = isLeap ? 29 : 28;

            // Test last day of February
            DateUtil date = new DateUtil(febMax, 2, year);
            date.increment();
            assertEquals("Day should be 1 after Feb " + febMax + " " + year, 1, date.getDay());
            assertEquals("Month should be 3 after Feb " + febMax + " " + year, 3, date.getMonth());
            assertEquals("Year should be " + year, year, date.getYear());
        }
    }

}
