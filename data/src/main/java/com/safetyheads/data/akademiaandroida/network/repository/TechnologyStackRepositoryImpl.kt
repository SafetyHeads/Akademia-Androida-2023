package com.safetyheads.data.akademiaandroida.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.safetyheads.domain.entities.firebasefirestore.SubCategory
import com.safetyheads.domain.entities.firebasefirestore.TechnologyStack
import com.safetyheads.domain.entities.firebasefirestore.TechnologyStackItem
import com.safetyheads.domain.repositories.TechnologyStackRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TechnologyStackRepositoryImpl(firebaseFirestore: FirebaseFirestore) :
    TechnologyStackRepository {

    private val technologyStackRef =
        firebaseFirestore.collection("applicationData").document("technologyStack")

    override fun getTechnologyStack(): Flow<Result<TechnologyStack>> = callbackFlow {
        val listener = technologyStackRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Result.failure(error))
            } else {
                snapshot?.let { document ->
                    val technologyStackCategories = document.get("categories") as ArrayList<HashMap<Any, Any>>
                    val technologyStackList = mutableListOf<TechnologyStackItem>()

                    for (technologyStackItem in technologyStackCategories) {
                        val categoryName = technologyStackItem["categoryName"].toString()
                        val subCategories = technologyStackItem["subCategories"] as ArrayList<HashMap<Any, Any>>
                        val subCategoriesList = mutableListOf<SubCategory>()

                        for (subCategoriesItem in subCategories) {
                            val name = subCategoriesItem["name"].toString()
                            val items = subCategoriesItem["items"] as ArrayList<String>
                            val itemsList = mutableListOf<String>()

                            for (item in items) {
                                itemsList.add(item)
                            }
                            subCategoriesList.add(SubCategory(itemsList, name))
                        }
                        technologyStackList.add(TechnologyStackItem(categoryName, subCategoriesList))
                    }
                    val technologyStack = TechnologyStack(technologyStackList)
                    trySend(Result.success(technologyStack))
                }
            }
        }
        awaitClose { listener.remove() }
    }
}