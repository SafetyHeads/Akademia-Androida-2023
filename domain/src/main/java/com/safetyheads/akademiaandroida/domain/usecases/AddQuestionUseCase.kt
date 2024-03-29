package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Question
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Type
import com.safetyheads.akademiaandroida.domain.repositories.FaqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddQuestionUseCase(private val repository: FaqRepository): BaseUseCase<AddQuestionUseCase.QuestionParam , Boolean> {
    class QuestionParam(val question: Question, val type: Type): BaseUseCase.Params
    override suspend fun invoke(parameter: QuestionParam): Flow<Result<Boolean>> {
        return flow {
            try {
                repository.addQuestion(parameter.question, parameter.type).collect { boolean ->
                    emit(boolean)
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
