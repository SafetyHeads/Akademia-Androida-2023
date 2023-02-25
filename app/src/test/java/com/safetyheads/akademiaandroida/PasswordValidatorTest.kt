package com.safetyheads.akademiaandroida

import android.content.Context
import android.widget.EditText
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test


class PasswordValidatorTest {

    private val validator = PasswordValidator()

    @Test
    fun validateAttach() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        validator.attach(editText, context)
        verify { editText.addTextChangedListener(any()) }
    }

    @Test
    fun `password with valid format`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password12nn@"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isTrue
    }

    @Test
    fun `password without special character`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password1238888"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isTrue
    }

    @Test
    fun validateName_isCorrect() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password1238888"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isTrue
    }

    @Test
    fun `password without uppercase letter`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "password0123@"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isTrue
    }

    @Test
    fun `password without lowercase letter`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "PASSWORD123@"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isTrue
    }

    @Test
    fun `password with first letter special character`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "@123ASSWORD123@"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isFalse
    }

    @Test
    fun `password with fewer than 8 characters`() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Pwd"
        val result = validator.isValid(password, editText, context)
        assertThat(result).isFalse
    }
}