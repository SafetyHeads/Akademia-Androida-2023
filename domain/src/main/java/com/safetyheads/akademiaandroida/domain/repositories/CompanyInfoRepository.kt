package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Address
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.ContactInfo
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Info
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Social
import kotlinx.coroutines.flow.Flow

interface CompanyInfoRepository {
    fun getAddress(): Flow<Result<Address>>
    fun getInfo(): Flow<Result<Info>>
    fun getSocial(): Flow<Result<Social>>
    fun getContactInfo(): Flow<Result<ContactInfo>>
}