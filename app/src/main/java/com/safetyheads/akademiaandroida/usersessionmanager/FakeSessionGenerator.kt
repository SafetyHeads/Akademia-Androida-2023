package com.safetyheads.akademiaandroida.usersessionmanager


internal class FakeSessionGenerator {
    var fakeSessionId = 0

    fun generateFakeSession(): Session {
        return Session("email+${fakeSessionId++}@gmail.com")
    }
}