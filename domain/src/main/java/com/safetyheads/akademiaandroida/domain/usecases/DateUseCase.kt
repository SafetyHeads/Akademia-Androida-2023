package com.safetyheads.akademiaandroida.domain.usecases

interface DateUseCase {

    fun actualDate(): String
    fun updateDate(actualVideoDate: String): String
}