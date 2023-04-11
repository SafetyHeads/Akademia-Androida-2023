package com.safetyheads.akademiaandroida

import android.content.Context
import android.widget.EditText
import com.safetyheads.presentation.utils.PhoneNumberValidator
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class PhoneNumberValidatorTest {

    @Test
    fun `attach should add text changed listener to EditText`() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()

        // When
        PhoneNumberValidator.attach(editText, context)

        // Then
        verify { editText.addTextChangedListener(any()) }
    }

    @Test
    fun `validatePhoneNumber should set error message if phone number is invalid`() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val invalidPhoneNumber = "+4812345678910"

        // When
        PhoneNumberValidator.validatePhoneNumber(invalidPhoneNumber, editText, context)

        // Then
        verify { editText.error = "Invalid phone number" }
    }

    @Test
    fun `validatePhoneNumber should not set error message if phone number is valid`() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val validPhoneNumber = "+48123456789"

        // When
        PhoneNumberValidator.validatePhoneNumber(validPhoneNumber, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun `isValid should return true if phone number is valid`() {
        // Given
        val validPhoneNumber = "+48123456789"

        // When
        val result = PhoneNumberValidator.isValid(validPhoneNumber)

        // Then
        assertThat(result).isTrue
    }

    @Test
    fun `isValid should return false if phone number is invalid`() {
        // Given
        val invalidPhoneNumber = "12345678"

        // When
        val result = PhoneNumberValidator.isValid(invalidPhoneNumber)

        // Then
        assertThat(result).isFalse
    }
}
