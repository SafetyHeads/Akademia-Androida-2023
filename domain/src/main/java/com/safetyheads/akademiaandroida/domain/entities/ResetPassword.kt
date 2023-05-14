package com.safetyheads.akademiaandroida.domain.entities

data class ResetPassword(
    val isSuccess: Boolean,
    val error: Throwable?
)
