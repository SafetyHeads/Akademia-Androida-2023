package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown

import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.ChildModel
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.ParentModel
import kotlinx.coroutines.coroutineScope

class LoadItemsToDropDownListUseCase {

    private val testList: List<ParentModel> = listOf(
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C"))),
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C"))),
        ParentModel("Test", listOf(ChildModel("a"), ChildModel("b"), ChildModel("C")))
    )

    suspend fun execute(): List<ParentModel> {
        return coroutineScope {
            return@coroutineScope testList
        }
    }
}