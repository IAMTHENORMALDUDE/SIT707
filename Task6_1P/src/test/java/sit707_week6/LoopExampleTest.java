package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class LoopExampleTest {

    private LoopExample loopExample = new LoopExample();

    // Tests for sumUpTo method
    @Test
    public void testSumUpToCountZero() {
        // Loop should not execute
        Assert.assertEquals("Sum should be 0 for count 0", 0, loopExample.sumUpTo(0));
    }

    @Test
    public void testSumUpToCountOne() {
        // Loop should not execute (i=1; 1 < 1 is false)
        Assert.assertEquals("Sum should be 0 for count 1", 0, loopExample.sumUpTo(1));
    }

    @Test
    public void testSumUpToPositiveCount() {
        // Loop executes for i = 1, 2, 3, 4. Sum = 1+2+3+4 = 10
        Assert.assertEquals("Sum should be 10 for count 5", 10, loopExample.sumUpTo(5));
    }

    // Tests for countPositiveNumbers method
    @Test
    public void testCountPositiveNumbersNullArray() {
        // Test the initial null check
        Assert.assertEquals("Count should be 0 for null array", 0, loopExample.countPositiveNumbers(null));
    }

    @Test
    public void testCountPositiveNumbersEmptyArray() {
        // Loop should not execute
        Assert.assertEquals("Count should be 0 for empty array", 0, loopExample.countPositiveNumbers(new int[]{}));
    }

    @Test
    public void testCountPositiveNumbersAllPositive() {
        // If condition should always be true
        int[] numbers = {1, 5, 10, 2};
        Assert.assertEquals("Count should be 4 for all positive numbers", 4, loopExample.countPositiveNumbers(numbers));
    }

    @Test
    public void testCountPositiveNumbersAllNonPositive() {
        // If condition should always be false
        int[] numbers = {0, -5, -10, -2};
        Assert.assertEquals("Count should be 0 for all non-positive numbers", 0, loopExample.countPositiveNumbers(numbers));
    }

    @Test
    public void testCountPositiveNumbersMixed() {
        // If condition should be true and false
        int[] numbers = {-1, 2, 0, -4, 5, 10};
        Assert.assertEquals("Count should be 3 for mixed numbers", 3, loopExample.countPositiveNumbers(numbers));
    }
}
