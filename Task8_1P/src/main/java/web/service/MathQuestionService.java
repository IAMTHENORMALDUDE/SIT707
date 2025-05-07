package web.service;

public class MathQuestionService {

	/**
	 * Calculate Q1 result.
	 * @param number1 First number as string
	 * @param number2 Second number as string
	 * @return The sum of the two numbers, or Double.NaN if inputs are invalid
	 */
	public static double q1Addition(String number1, String number2) {
		try {
			if (number1 == null || number2 == null || number1.isEmpty() || number2.isEmpty()) {
				return Double.NaN;
			}
			double result = Double.valueOf(number1) + Double.valueOf(number2);
			return result;
		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}
	
	/**
	 * Calculate Q2 result.
	 * @param number1 First number as string
	 * @param number2 Second number as string
	 * @return The difference of the two numbers, or Double.NaN if inputs are invalid
	 */
	public static double q2Subtraction(String number1, String number2) {
		try {
			if (number1 == null || number2 == null || number1.isEmpty() || number2.isEmpty()) {
				return Double.NaN;
			}
			double result = Double.valueOf(number1) - Double.valueOf(number2);
			return result;
		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}
	
	/**
	 * Calculate Q3 result.
	 * @param number1 First number as string
	 * @param number2 Second number as string
	 * @return The product of the two numbers, or Double.NaN if inputs are invalid
	 */
	public static double q3Multiplication(String number1, String number2) {
		try {
			if (number1 == null || number2 == null || number1.isEmpty() || number2.isEmpty()) {
				return Double.NaN;
			}
			double result = Double.valueOf(number1) * Double.valueOf(number2);
			return result;
		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}
}
