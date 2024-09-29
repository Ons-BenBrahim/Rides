package com.example.rides
import com.example.rides.utils.InputValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {

	@Test
	fun testValidInput() {
		assertTrue(InputValidator.isValidCount(1))
		assertTrue(InputValidator.isValidCount(50))
		assertTrue(InputValidator.isValidCount(100))
	}

	@Test
	fun testInvalidInput() {
		assertFalse(InputValidator.isValidCount(null))
		assertFalse(InputValidator.isValidCount(0))
		assertFalse(InputValidator.isValidCount(-10))
		assertFalse(InputValidator.isValidCount(101))
		assertFalse(InputValidator.isValidCount(150))
	}
}
