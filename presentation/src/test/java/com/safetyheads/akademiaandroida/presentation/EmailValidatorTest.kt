package com.safetyheads.akademiaandroida.presentation

import android.content.Context
import android.widget.EditText
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EmailValidatorTest {

    companion object {
        private const val ERROR_TEXT = "Invalid email"
    }

    @RelaxedMockK
    lateinit var editText: EditText

    @RelaxedMockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { editText.context } returns context
        every { context.getString(R.string.invalid_email) } returns ERROR_TEXT
    }

    @Test
    fun `attach should add text changed listener to EditText`() {
        // When
        EmailValidator.attach(editText)

        // Then
        verify { editText.addTextChangedListener(any()) }
    }

    @Test
    fun `validateEmail should set error message if email is invalid`() {
        val invalidEmail = "drewno@"

        // When
        EmailValidator.validateEmail(invalidEmail, editText)

        // Then
        verify { editText.error = ERROR_TEXT }
    }

    @Test
    fun `validateEmail should not set error message if email is valid`() {
        // Given
        val editText = mockk<EditText>(relaxed = true)

        val validEmail = "drewno@gmail.com"

        // When
        EmailValidator.validateEmail(validEmail, editText)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun `isValid should return true if email is valid`() {
        // Given
        val validEmail = "drewno@gmail.com"

        // When
        val result = EmailValidator.isValid(validEmail)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isValid should return false if email is invalid`() {
        // Given
        val invalidEmail = "drewno@"

        // When
        val result = EmailValidator.isValid(invalidEmail)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isValid should return true if email contain a dot in address field`() {
        //Given
        val validEmail = "drewno.drewno@gmail.com"

        //When
        val result = EmailValidator.isValid(validEmail)

        //Then
        assertTrue(result)
    }

    @Test
    fun `isValid should return true if email contain a plus sign in address field`() {
        //Given
        val validEmail = "drewno+drewno@gmail.com"

        //When
        val result = EmailValidator.isValid(validEmail)

        //Then
        assertTrue(result)
    }


    @Test
    fun `isValid should return true if email contain digits in address field `() {
        //Given
        val validEmail = "drewno1234@gmail.com"

        //When
        val result = EmailValidator.isValid(validEmail)

        //Then
        assertTrue(result)
    }

    @Test
    fun `isValid should return false if @ symbol is missing`() {
        // Given
        val invalidEmail = "drewnogmail.com"

        // When
        val result = EmailValidator.isValid(invalidEmail)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isValid should return false if dot symbol appears at the end of email id text box`() {
        // Given
        val invalidEmail = "drewno@gmail..com"

        // When
        val result = EmailValidator.isValid(invalidEmail)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isValid should return false if username missing`() {
        // Given
        val invalidEmail = "@gmail.com"

        // When
        val result = EmailValidator.isValid(invalidEmail)

        // Then
        assertFalse(result)
    }
}
