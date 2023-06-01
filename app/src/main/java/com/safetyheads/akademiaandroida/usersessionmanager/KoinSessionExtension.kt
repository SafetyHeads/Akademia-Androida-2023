package com.safetyheads.akademiaandroida.usersessionmanager

import com.safetyheads.akademiaandroida.domain.entities.Session
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

private const val SESSION_SCOPE_ID = "SESSION_ID"
internal const val SESSION_SCOPE_NAME = "SESSION_SCOPE_NAME"


fun Scope.getSessionScope(): Scope {
    return getKoin().getSessionScope()
}

//region internal helper extensions
@Synchronized
internal fun Koin.getSessionScope(): Scope {
    return getOrCreateScope(SESSION_SCOPE_ID, named(SESSION_SCOPE_NAME))
}

@Synchronized
internal fun Koin.reloadSessionScope(session: Session?) {
    getSessionScope().close()
    session?.let {
        getSessionScope().declare(it)
    }
}
