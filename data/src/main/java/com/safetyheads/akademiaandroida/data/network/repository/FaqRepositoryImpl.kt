package com.safetyheads.akademiaandroida.data.network.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Answer
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Question
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Type
import com.safetyheads.akademiaandroida.domain.repositories.FaqRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FaqRepositoryImpl(firebaseFirestore: FirebaseFirestore) : FaqRepository {

    private val collectionReference = firebaseFirestore.collection("faq")

    override fun getFaq(): Flow<Result<MutableList<Faq>>> = callbackFlow {
        val listener = collectionReference.addSnapshotListener { snapshot, exception ->

            if (exception != null) trySend(Result.failure(exception))
            else {
                snapshot?.let {
                    val faq = it.toObjects(Faq::class.java)

                    trySend(Result.success(faq))
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override fun addQuestion(question: Question, type: Type): Flow<Result<Boolean>> = callbackFlow {
        val faqObject = Faq(Answer(), true, question, type.type)

        val listener = collectionReference.add(faqObject)
            .addOnSuccessListener { document ->
                // here should be setting current user's id to field 'userId'
            }
            .addOnFailureListener { exception ->
                trySend(Result.failure(exception))
            }
            .addOnCompleteListener { task ->
                trySend(Result.success(task.isComplete))
            }
        awaitClose { listener.isCanceled }
    }
}
