package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqType
import com.safetyheads.akademiaandroida.domain.entities.faqs.toFaqType
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.usecases.GetFaqUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FaqViewModel(private val getFaqUseCase: GetFaqUseCase) : ViewModel() {

    private val _typedFaqsList: MutableLiveData<List<Faq>?> = MutableLiveData<List<Faq>?>().apply {
        getFaqs(FaqType.Benefits)
    }
    var typedFaqsList : LiveData<List<Faq>?> = _typedFaqsList

    private fun getFaqs(type: FaqType) {
        viewModelScope.launch {
            getFaqUseCase.invoke().collect() { result ->
                if (result.isSuccess) {
                    val faqsList = result.getOrNull()
                    if(faqsList != null) {
                        val typedFaqs : MutableList<Faq> = mutableListOf()
                        for(faq in faqsList) {
                            if(faq.type == type.tag && faq.publish) {
                                typedFaqs.add(faq)
                                _typedFaqsList.postValue(typedFaqs)
                            }
                        }
                    }


                }
                else {
                    //TODO: error
                }
            }
        }
    }

    fun tabSelected(tab: FaqTab) {
        getFaqs(tab.toFaqType())
    }
}