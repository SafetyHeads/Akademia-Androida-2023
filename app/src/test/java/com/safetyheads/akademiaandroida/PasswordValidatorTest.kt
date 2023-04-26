package com.safetyheads.akademiaandroida

import android.content.Context
import android.widget.EditText
import com.safetyheads.akademiaandroida.utils.PasswordValidator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test


class PasswordValidatorTest {

    private val validator = PasswordValidator
    @Test
    fun validateAttach() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()

        // When
        validator.attach(editText, context)

        // Then
        verify { editText.addTextChangedListener(any()) }
    }

    @Test
    fun passwordWithValidFormat() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password12nn@"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun passwordWithoutSpecialCharacter() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password1238888"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun validateName_isCorrect() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Password1238888"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun passwordWithoutUppercaseLetter() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "password0123@"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun passwordWithoutLowercaseLetter() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "PASSWORD123@"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun passwordWithFirstLetterSpecialCharacter() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context> {
            every { getString(R.string.invalid_password) } returns "Invalid password"
        }
        val password = "!mASSWORD123@"

        // When
        validator.validatePassword(password, editText, context)

        // Then
         verify { editText.error = "Invalid password" }
    }

    @Test
    fun passwordWithFewerThan12Characters() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()
        val password = "Pwbbbjujnnunuuniknknd"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = null }
    }

    @Test
    fun passwordWithInvalidFormat() {
        // Given
        val editText = mockk<EditText>(relaxed = true)
         val context = mockk<Context> {
            every { getString(R.string.invalid_password) } returns "Invalid password"
        }
        val password = "1@"

        // When
        validator.validatePassword(password, editText, context)

        // Then
        verify { editText.error = "Invalid password" }
    }
}
