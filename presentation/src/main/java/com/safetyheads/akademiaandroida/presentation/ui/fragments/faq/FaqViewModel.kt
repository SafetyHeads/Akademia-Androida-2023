package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.faqs.toFaqType
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Question
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Type
import com.safetyheads.akademiaandroida.domain.usecases.AddQuestionUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetFaqUseCase
import kotlinx.coroutines.launch

class FaqViewModel(
    private val getFaqUseCase: GetFaqUseCase,
    private val addQuestionUseCase: AddQuestionUseCase
) : ViewModel() {

    private var selectedType: Type
    private val _typedFaqsList: MutableLiveData<List<Faq>> = MutableLiveData<List<Faq>>().apply {
        getFaqs(Type.BENEFITS)
        selectedType = Type.BENEFITS
    }
    private val _isSendSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val typedFaqsList: LiveData<List<Faq>> = _typedFaqsList
    val isSendSuccess: LiveData<Boolean> = _isSendSuccess
    fun sendQuestion(question: String) {
        val questionModel = Question(text = question)
        viewModelScope.launch {
            addQuestionUseCase.invoke(AddQuestionUseCase.QuestionParam(questionModel, selectedType))
                .collect() { result ->
                    if (result.isSuccess) {
                        _isSendSuccess.postValue(result.getOrNull())
                    } else {
                        _isSendSuccess.postValue(result.getOrNull())
                    }

                }
        }
    }

    fun tabSelected(tab: FaqTab) {
        getFaqs(tab.toFaqType())
        selectedType = tab.toFaqType()
    }

    private fun getFaqs(type: Type) {
        viewModelScope.launch {
            getFaqUseCase.invoke().collect() { result ->
                if (result.isSuccess) {
                    val faqsList = result.getOrNull()
                        ?.filter { it.type == type.type && it.publish }.orEmpty()
                    _typedFaqsList.postValue(faqsList)
                }
            }
        }
    }

}