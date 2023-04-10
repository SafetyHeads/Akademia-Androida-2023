package com.safetyheads.akademiaandroida.sign_up

import kotlinx.coroutines.flow.Flow

interface UserRepository {
     fun createUser(fullName: String, email: String, password: String) : Flow<User>
}