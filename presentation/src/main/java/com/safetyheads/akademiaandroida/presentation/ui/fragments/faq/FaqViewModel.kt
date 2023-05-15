package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqType
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.usecases.GetFaqUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FaqViewModel(private val getFaqUseCase: GetFaqUseCase) : ViewModel() {
    private val selectedTab = MutableStateFlow(FaqTab.Benefits)

    private val _faqsList: MutableLiveData<List<Faq>?> = MutableLiveData()
    var faqsList : LiveData<List<Faq>?> = _faqsList

    fun getFaqs() {
        viewModelScope.launch {
            getFaqUseCase.invoke().collect() { result ->
                if (result.isSuccess) {
                    val faqsList = result.getOrNull()
                    _faqsList.postValue(faqsList)
                }
                else {
                    //TODO: error
                }
            }
        }
    }

    fun tabSelected(tab: FaqTab) {
        selectedTab.tryEmit(tab)
    }
}