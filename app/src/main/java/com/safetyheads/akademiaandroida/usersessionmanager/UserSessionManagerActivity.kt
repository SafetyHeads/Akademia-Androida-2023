package com.safetyheads.akademiaandroida.usersessionmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityUserSessionBinding
import org.koin.android.ext.android.getKoin

class UserSessionManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSessionBinding
    private val sessionManager: UserSessionManager
        get() = getKoin().getSessionScope().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createScopeButton.setOnClickListener {
            when(sessionManager) {
                is UserSessionManager.Logged -> showSnackbar("Scope already created")
                is UserSessionManager.Unlogged -> (sessionManager as UserSessionManager.Unlogged).logIn()
            }
        }

        binding.displayInfoButton.setOnClickListener {
            val message = (sessionManager as? LoggedSessionManager)?.session?.let {
                "User is logged in with email: ${it.userEmail} and is ${sessionManager.isLoggedIn}"
            } ?: "User is not logged in"
            showSnackbar(message)
        }

        binding.deleteScopeButton.setOnClickListener {
            when(sessionManager) {
                is UserSessionManager.Logged -> {
                    (sessionManager as UserSessionManager.Logged).logOff()
                }
                is UserSessionManager.Unlogged -> showSnackbar("Scope already destroyed")
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}

