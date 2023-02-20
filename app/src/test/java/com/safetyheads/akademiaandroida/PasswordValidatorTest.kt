package com.safetyheads.akademiaandroida

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PasswordValidatorTest {

    @Test
    fun attach() {
    }

    @Test
    fun `password with valid format`() {

        val password = "Password12nn@"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertTrue(result)
    }

    @Test
    fun `password without special character`() {

        val password = "Password1238888"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertTrue(result)
    }

    @Test
    fun `password without uppercase letter`() {

        val password = "password0123@"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertTrue(result)
    }

    @Test
    fun `password without lowercase letter`() {

        val password = "PASSWORD123@"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertTrue(result)
    }

    @Test
    fun `password with first letter special character`() {

        val password = "@123ASSWORD123@"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertFalse(result)
    }

    @Test
    fun `password with fewer than 8 characters`() {

        val password = "Pwd"
        val validator = PasswordValidator()
        val result = validator.isValid(password)
        assertFalse(result)
    }
}