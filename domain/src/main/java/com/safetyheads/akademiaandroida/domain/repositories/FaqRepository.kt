package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Question
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Type
import kotlinx.coroutines.flow.Flow

interface FaqRepository {

    fun getFaq(): Flow<Result<MutableList<Faq>>>

    fun addQuestion(question: Question, type: Type): Flow<Result<Boolean>>

}
