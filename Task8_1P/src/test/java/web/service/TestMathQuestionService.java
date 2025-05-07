package web.service;

import org.junit.Assert;
import org.junit.Test;

public class TestMathQuestionService {

	// Addition tests
	@Test
	public void testValidAddition() {
		Assert.assertEquals(3.0, MathQuestionService.q1Addition("1", "2"), 0);
		Assert.assertEquals(0.0, MathQuestionService.q1Addition("0", "0"), 0);
		Assert.assertEquals(-1.0, MathQuestionService.q1Addition("-2", "1"), 0);
		Assert.assertEquals(3.5, MathQuestionService.q1Addition("1.5", "2"), 0);
	}

	@Test
	public void testAdditionWithEmptyInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("1", "")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("", "")));
	}
	
	@Test
	public void testAdditionWithNullInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition(null, "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("1", null)));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition(null, null)));
	}
	
	@Test
	public void testAdditionWithInvalidInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("abc", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("1", "xyz")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q1Addition("abc", "xyz")));
	}
	
	// Subtraction tests
	@Test
	public void testValidSubtraction() {
		Assert.assertEquals(-1.0, MathQuestionService.q2Subtraction("1", "2"), 0);
		Assert.assertEquals(0.0, MathQuestionService.q2Subtraction("0", "0"), 0);
		Assert.assertEquals(-3.0, MathQuestionService.q2Subtraction("-2", "1"), 0);
		Assert.assertEquals(-0.5, MathQuestionService.q2Subtraction("1.5", "2"), 0);
	}

	@Test
	public void testSubtractionWithEmptyInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("1", "")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("", "")));
	}
	
	@Test
	public void testSubtractionWithNullInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction(null, "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("1", null)));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction(null, null)));
	}
	
	@Test
	public void testSubtractionWithInvalidInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("abc", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("1", "xyz")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q2Subtraction("abc", "xyz")));
	}
	
	// Multiplication tests
	@Test
	public void testValidMultiplication() {
		Assert.assertEquals(2.0, MathQuestionService.q3Multiplication("1", "2"), 0);
		Assert.assertEquals(0.0, MathQuestionService.q3Multiplication("0", "0"), 0);
		Assert.assertEquals(-2.0, MathQuestionService.q3Multiplication("-2", "1"), 0);
		Assert.assertEquals(3.0, MathQuestionService.q3Multiplication("1.5", "2"), 0);
	}

	@Test
	public void testMultiplicationWithEmptyInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("1", "")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("", "")));
	}
	
	@Test
	public void testMultiplicationWithNullInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication(null, "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("1", null)));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication(null, null)));
	}
	
	@Test
	public void testMultiplicationWithInvalidInputs() {
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("abc", "2")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("1", "xyz")));
		Assert.assertTrue(Double.isNaN(MathQuestionService.q3Multiplication("abc", "xyz")));
	}
}
