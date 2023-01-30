package com.safetyheads.akademiaandroida

class Validator {

    fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9]\\w{11,}$")
        return password.matches(pattern)
    }
}