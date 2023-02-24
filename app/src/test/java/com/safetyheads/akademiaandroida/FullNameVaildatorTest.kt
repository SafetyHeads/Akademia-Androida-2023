package com.safetyheads.akademiaandroida

import android.content.Context
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.utils.FullNameValidator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test


class FullNameVaildatorTest {

    @Test
    fun checkIsNotSpace_isCorrect() {
        val source = 'A'

        val result = FullNameValidator.checkIsNotSpace(source)

        assertThat(result).isTrue
    }

    @Test
    fun checkIsNotSpace_isUnCorrect() {
        val source = ' '

        val result = FullNameValidator.checkIsNotSpace(source)

        assertThat(result).isFalse
    }

    @Test
    fun checkIsNotFirstChar_isCorrect() {
        val upperCase = 2

        val result = FullNameValidator.isNotFirstChar(upperCase)

        assertThat(result).isTrue
    }

    @Test
    fun checkIsNotFirstChar_isUnCorrect() {
        val upperCase = 1

        val result = FullNameValidator.isNotFirstChar(upperCase)

        assertThat(result).isFalse
    }

    @Test
    fun checkListSizeIsTwo_isCorrect() {
        val splitNames: List<String> = arrayListOf("First", "Second")

        val result = FullNameValidator.checkListSizeIsTwo(splitNames)

        assertThat(result).isTrue
    }

    @Test
    fun checkListSizeIsTwo_isUnCorrect() {
        val splitNames: List<String> = arrayListOf("First")

        val result = FullNameValidator.checkListSizeIsTwo(splitNames)

        assertThat(result).isFalse
    }

    @Test
    fun validateName_isCorrect() {
        val name: String = "John"
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()

        val result = FullNameValidator.validateName(name, splitNames, editText, context)

        assertThat(result).isTrue
    }

    @Test
    fun validateName_invalidateName_isUnCorrect() {
        val name: String = "J"
        val splitNames: List<String> = arrayListOf("J", "Abramovich")
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_name_message) } returns "error"
        }

        FullNameValidator.validateName(name, splitNames, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateName_invalidateEmpty_isUnCorrect() {
        val name = ""
        val splitNames: List<String> = arrayListOf("", "Abramovich")
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_empty_message) } returns "error"
        }

        FullNameValidator.validateName(name, splitNames, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateName_invalidateNameFirstIsNotUpperCase_isUnCorrect() {
        val name = "john"
        val splitNames: List<String> = arrayListOf("john", "Abramovich")
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_name_message) } returns "error"
        }

        FullNameValidator.validateName(name, splitNames, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateSurname_invalidateSurnameMessage_isUnCorrect() {
        val surname = "A"
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val spacesAmount = 1
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_surname_message) } returns "error"
        }

        FullNameValidator.validateSurname(surname, splitNames, spacesAmount, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateSurname_invalidateTooMuchSpaces_isUnCorrect() {
        val surname = "Abramovich"
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val spacesAmount = 2
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_spaces_message) } returns "error"
        }

        FullNameValidator.validateSurname(surname, splitNames, spacesAmount, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateSurname_invalidateSurnameIsEmpty_isUnCorrect() {
        val surname = ""
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val spacesAmount = 1
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_surname_message) } returns "error"
        }

        FullNameValidator.validateSurname(surname, splitNames, spacesAmount, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateSurname_invalidateSurnameIsNotUpperCase_isUnCorrect() {
        val surname = "abramovich"
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val spacesAmount = 1
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>() {
            every { getString(R.string.invalid_surname_message) } returns "error"
        }

        FullNameValidator.validateSurname(surname, splitNames, spacesAmount, editText, context)

        verify { editText.error = "error" }
    }

    @Test
    fun validateSurname_isCorrect() {
        val surname = "Abramovich"
        val splitNames: List<String> = arrayListOf("John", "Abramovich")
        val spacesAmount = 1
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()

        val result = FullNameValidator.validateSurname(surname, splitNames, spacesAmount, editText, context)

        assertThat(result).isTrue
    }

    @Test
    fun validateAttach() {
        val editText = mockk<EditText>(relaxed = true)
        val context = mockk<Context>()

        FullNameValidator.attach(editText, context)

        verify { editText.addTextChangedListener(any()) }
    }
}