package sit707_week6;

public class LoopExample {

    /**
     * Function with a simple conditional loop (for loop).
     * Calculates the sum of numbers from 1 to count.
     * @param count The upper limit of the loop (exclusive).
     * @return The sum of numbers from 1 up to (count - 1). Returns 0 if count <= 1.
     */
    public int sumUpTo(int count) {
        int sum = 0;
        // Loop from 1 up to (but not including) count
        for (int i = 1; i < count; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Function with a conditional loop (for-each loop) containing a conditional statement (if).
     * Counts the number of positive integers in an array.
     * @param numbers An array of integers.
     * @return The count of positive numbers in the array. Returns 0 if the array is null or empty.
     */
    public int countPositiveNumbers(int[] numbers) {
        if (numbers == null) {
            return 0; // Handle null array case
        }
        
        int positiveCount = 0;
        for (int number : numbers) {
            // Conditional statement inside the loop
            if (number > 0) {
                positiveCount++;
            }
        }
        return positiveCount;
    }
}
