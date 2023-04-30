package com.safetyheads.akademiaandroida.data.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.About
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.AdditionalSection
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Address
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.ContactInfo
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Info
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Item
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Social
import com.safetyheads.akademiaandroida.domain.repositories.CompanyInfoRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CompanyInfoRepositoryImpl(firebaseFirestore: FirebaseFirestore) :
        CompanyInfoRepository {

    private val companyInfoRef = firebaseFirestore.collection("applicationData").document("companyInfo")

    override fun getAddress(): Flow<Result<Address>> = callbackFlow {
        val listener = companyInfoRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Result.failure(exception))
            } else {
                snapshot?.let { document ->
                    val companyInfoAddress = Address(
                        document.getString("address.city").orEmpty(),
                        document.getString("address.country").orEmpty(),
                        document.getString("address.streetName").orEmpty(),
                        document.getString("address.streetNumber").orEmpty(),
                        document.getString("address.zipCode").orEmpty()
                    )
                    trySend(Result.success(companyInfoAddress))
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getInfo(): Flow<Result<Info>> = callbackFlow {
        val listener = companyInfoRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Result.failure(exception))
            } else {
                snapshot?.let { document ->
                    val aboutObject = document.get("info.about") as HashMap<*, *>
                    val about = About(
                        aboutObject["name"].toString(),
                        aboutObject["description"].toString(),
                        aboutObject["showCompanyLogo"] as Boolean
                    )
                    val additionalSections = mutableListOf<AdditionalSection>()
                    val sections = document.get("info.additionalSections") as ArrayList<HashMap<String, Any>>
                    for (section in sections) {
                        val sectionName = section["name"].toString()
                        val sectionType = section["type"].toString()
                        val sectionItems = section["items"] as ArrayList<HashMap<String, Any>>
                        val items = mutableListOf<Item>()
                        for (item in sectionItems) {
                            val itemTitle = item["title"].toString()
                            val itemValue = item["value"].toString()
                            items.add(Item(itemTitle, itemValue))
                        }
                        additionalSections.add(
                            AdditionalSection(
                                items,
                                sectionName,
                                sectionType
                            )
                        )
                    }
                    val companyInfo = Info(about, additionalSections)
                    trySend(Result.success(companyInfo))
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getSocial(): Flow<Result<Social>> = callbackFlow {
        val listener = companyInfoRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Result.failure(exception))
            } else {
                snapshot?.let { document ->
                    val facebookId = document.getString("social.facebookId").orEmpty()
                    val instagramId = document.getString("social.instagramId").orEmpty()
                    val linkedinId = document.getString("social.linkedinId").orEmpty()
                    val youtubeChannelId = document.getString("social.youtubeChannelId").orEmpty()
                    val social = Social(facebookId, instagramId, linkedinId, youtubeChannelId)
                    trySend(Result.success(social))
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getContactInfo(): Flow<Result<ContactInfo>> = callbackFlow {
        val listener = companyInfoRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Result.failure(exception))
            } else {
                snapshot?.let { document ->
                    val name = document.getString("name").orEmpty()
                    val email = document.getString("email").orEmpty()
                    val webPage = document.getString("webPage").orEmpty()
                    val phoneNumber = document.getString("phoneNumber").orEmpty()
                    val contactInfo = ContactInfo(name, email, webPage, phoneNumber)
                    trySend(Result.success(contactInfo))
                }
            }
        }
        awaitClose { listener.remove() }
    }
}