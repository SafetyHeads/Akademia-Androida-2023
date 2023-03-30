package com.safetyheads.akademiaandroida.sign_up

data class RegistrationFormState(

    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,

)
