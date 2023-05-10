package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow


class LoginUseCase(private val userRepository: UserRepository) {
    fun login(email: String, password: String): Flow<User> {
        return userRepository.loginUser(email, password)
    }
}
