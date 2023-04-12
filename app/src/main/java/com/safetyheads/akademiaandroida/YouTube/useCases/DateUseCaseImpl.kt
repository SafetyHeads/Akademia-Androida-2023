package com.safetyheads.akademiaandroida.YouTube.useCases

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUseCaseImpl: DateUseCase {

    @SuppressLint("NewApi")
    override fun actualDate(): String {
        val data = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return data.format(formatter)
    }

    @SuppressLint("NewApi")
    override fun updateDate(actualVideoDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val inputDate = LocalDateTime.parse(actualVideoDate, formatter)
        val modifiedDate = inputDate.minusSeconds(1)
        return formatter.format(modifiedDate)
    }
}