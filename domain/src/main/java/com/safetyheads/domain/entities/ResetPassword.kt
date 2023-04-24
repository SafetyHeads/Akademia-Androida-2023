package com.safetyheads.domain.entities

data class ResetPassword(
    val isSuccess: Boolean,
    val error: Throwable?
)
