package com.example.rides.utils

object InputValidator {
	fun isValidCount(count: Int?): Boolean {
		return count != null && count in 1..100
	}
}
