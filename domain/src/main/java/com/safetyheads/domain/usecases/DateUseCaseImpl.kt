package com.safetyheads.domain.usecases

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateUseCaseImpl : DateUseCase {

    override fun actualDate(): String {
        val data = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return data.format(formatter)
    }

    override fun updateDate(actualVideoDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val inputDate = LocalDateTime.parse(actualVideoDate, formatter)
        val modifiedDate = inputDate.minusDays(2)
        return formatter.format(modifiedDate)
    }
}