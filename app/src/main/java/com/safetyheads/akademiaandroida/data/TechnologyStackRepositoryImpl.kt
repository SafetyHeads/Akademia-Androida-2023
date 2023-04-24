package com.safetyheads.akademiaandroida.data

import com.safetyheads.domain.entities.technologystack.TechnologyStack
import com.safetyheads.domain.entities.technologystack.TechnologyStackType
import com.safetyheads.domain.repositories.TechnologyStackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TechnologyStackRepositoryImpl : TechnologyStackRepository {
    private val mobileApp: List<TechnologyStack> = listOf(
        TechnologyStack(
            name = "Android Aplications",
            details = listOf(
                "Kotlin",
                "Java",
                "JVM-based languages",
                "C/ C++",
                "RxJava/RxKotlin",
                "DI(Dagger2,Koin)",
                "Android SDK",
                "Jetpack components.",
                "Flutter"
            )
        ),
        TechnologyStack(
            name = "IOS Applications",
            details = listOf(
                "Swift",
                "Objective-C",
                "C/ C++",
                "UIKit",
                "SwiftUI",
                "RxSwift",
                "Combine",
                "Apple Pay"
            )
        )
    )

    private val webApp: List<TechnologyStack> = listOf(
        TechnologyStack(
            name = "Frontend development",
            details = listOf(
                "Angular",
                "React",
                "Vue"
            )
        ),
        TechnologyStack(
            name = "Backend development",
            details = listOf(
                ".Net",
                ".Node.js",
                "Python"
            )
        ),
        TechnologyStack(
            name = "Data Base",
            details = listOf(
                "Microsoft Azure Cloud",
                "Amazon Web Services",
                "Google Cloud Platform"
            )
        ),
        TechnologyStack(
            name = "Server Side",
            details = listOf(
                "SQL Database",
                "NoSql Databases",
                "PostgreSQL"
            )
        )
    )
    private val others: List<TechnologyStack> = listOf(
        TechnologyStack(
            name = "Low-code",
            details = listOf(
                "Proprietary Low-code Platform",
                "OutsyStems",
                "Microsoft Power Apps"
            )
        ),
        TechnologyStack(
            name = "Other",
            details = listOf(
                "AI/ML/NLP",
                "CyberSecurity",
                "UX/UI",
                "Business Analysis",
                "Quality Assurance",
                "Project Management",
            )
        )
    )

    override fun getTechnologyStack(type: TechnologyStackType): Flow<Result<List<TechnologyStack>>> =
        flow {

            val stack = when (type) {
                TechnologyStackType.Mobile -> mobileApp
                TechnologyStackType.Web -> webApp
                TechnologyStackType.Others -> others
            }
            emit(Result.success(stack))
        }
}