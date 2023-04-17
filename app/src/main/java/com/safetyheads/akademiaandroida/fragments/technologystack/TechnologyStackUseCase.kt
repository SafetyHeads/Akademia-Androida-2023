package com.safetyheads.akademiaandroida.fragments.technologystack

import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ChildModel
import com.safetyheads.akademiaandroida.dropdownlist.dropdown.ParentModel
import kotlinx.coroutines.coroutineScope

class TechnologyStackUseCase {

    private val mobileApp: List<ParentModel> = listOf(
            ParentModel(
                    name = "Android Aplications",
                    itemList = listOf(
                            ChildModel("Kotlin"),
                            ChildModel("Java"),
                            ChildModel("JVM-based languages"),
                            ChildModel("C/ C++"),
                            ChildModel("RxJava/RxKotlin"),
                            ChildModel("DI(Dagger2,Koin)"),
                            ChildModel("Android SDK"),
                            ChildModel("Jetpack components."),
                            ChildModel("Flutter")
                    )
            ),
            ParentModel(
                    name = "IOS Applications",
                    itemList = listOf(
                            ChildModel("Swift"),
                            ChildModel("Objective-C"),
                            ChildModel("C/ C++"),
                            ChildModel("UIKit"),
                            ChildModel("SwiftUI"),
                            ChildModel("RxSwift"),
                            ChildModel("Combine"),
                            ChildModel("Apple Pay")
                    )
            )
    )
    private val webApp: List<ParentModel> = listOf(
            ParentModel(
                    name = "Frontend development",
                    itemList = listOf(
                            ChildModel("Angular"),
                            ChildModel("React"),
                            ChildModel("Vue")
                    )
            ),
            ParentModel(
                    name = "Backend development",
                    itemList = listOf(
                            ChildModel(".Net"),
                            ChildModel(".Node.js"),
                            ChildModel("Python")
                    )
            ),
            ParentModel(
                    name = "Data Base",
                    itemList = listOf(
                            ChildModel("Microsoft Azure Cloud"),
                            ChildModel("Amazon Web Services"),
                            ChildModel("Google Cloud Platform")
                    )
            ),
            ParentModel(
                    name = "Server Side",
                    itemList = listOf(
                            ChildModel("SQL Database"),
                            ChildModel("NoSql Databases"),
                            ChildModel("PostgreSQL")
                    )
            )
    )
    private val others: List<ParentModel> = listOf(
            ParentModel(
                    name = "Low-code",
                    itemList = listOf(
                            ChildModel("Proprietary Low-code Platform"),
                            ChildModel("OutsyStems"),
                            ChildModel("Microsoft Power Apps")
                    )
            ),
            ParentModel(
                    name = "Other",
                    itemList = listOf(
                            ChildModel("AI/ML/NLP"),
                            ChildModel("CyberSecurity"),
                            ChildModel("UX/UI"),
                            ChildModel("Business Analysis"),
                            ChildModel("Quality Assurance"),
                            ChildModel("Project Management"),
                    )
            )
    )

    suspend fun execute(tab: TechnologyStackTab): List<ParentModel> {
        return coroutineScope {
            return@coroutineScope when(tab){
                TechnologyStackTab.Mobile -> mobileApp
                TechnologyStackTab.Web -> webApp
                TechnologyStackTab.Others -> others
            }
        }
    }
}