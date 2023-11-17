package com.kataco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

class StringCalculatorTest {

	@Test
	void testAddEmptyString() {
		try {
			int result = StringCalculator.add("");
			assertEquals(0, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddEmptyOneNumber() {
		try {
			int result = StringCalculator.add("1");
			assertEquals(1, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddEmptyTwoNumbers() {
		try {
			int result = StringCalculator.add("1,2");
			assertEquals(3, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddNewLines() {
		try {
			int result = StringCalculator.add("1\n2,3");
			assertEquals(6, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddDelimitedSemiColonParameter() {
		try {
			int result = StringCalculator.add("//;\n1;2");
			assertEquals(3, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddDelimitedParameter() {
		try {
			int result = StringCalculator.add("//:\n1:7:3:5");
			assertEquals(16, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddNegativeValue() {
		Exception exception = assertThrows(Exception.class, () -> {
			StringCalculator.add("-1,2");
		});
		String expectedMessage = "Negatives not allowed:-1";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testAddParameterAndNegativeValue() {

		Exception exception = assertThrows(Exception.class, () -> {
			StringCalculator.add("//:\n1:7:-3:5");
		});
		String expectedMessage = "Negatives not allowed:-3";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testAddMultipleNegativeValue() {

		Exception exception = assertThrows(Exception.class, () -> {
			StringCalculator.add("2,-4,3,-5");
		});
		String expectedMessage = "Negatives not allowed:-4,-5";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testAddMultipleIgnoreValuesOver1000() {
		try {
			int result = StringCalculator.add("1001,2");
			assertEquals(2, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddAnyLengthDelimiter() {
		try {
			int result = StringCalculator.add("//[|||]\n1|||2|||3");
			assertEquals(6, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddAnyLengthAplhaNumericDelimiter() {
		try {
			int result = StringCalculator.add("//[ab1]\n1ab12ab13");
			assertEquals(6, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testGetDelimiters() {
		try {
			List<String> result = StringCalculator.getDelimiters("//[|||][%%%]");
			assertEquals("|||", result.get(0));
			assertEquals("%%%", result.get(1));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddMulitpleMultiLengthDelimters() {
		try {
			int result = StringCalculator.add("//[|||][%%%]\n4|||5%%%7");
			assertEquals(16, result);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testAddMulitpleMultiLengthDelimters2() {
		try {
			int result = StringCalculator.add("//[|||][%%%]\n11|||6|||7%%%5");
			assertEquals(29, result);
		} catch (Exception e) {
			fail();
		}
	}
}
