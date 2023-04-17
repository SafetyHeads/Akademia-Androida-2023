package com.safetyheads.domain.usecases

interface DateUseCase {

    fun actualDate(): String
    fun updateDate(actualVideoDate: String): String
}