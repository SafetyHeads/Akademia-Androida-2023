package com.safetyheads.akademiaandroida.usersessionmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityUserSessionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserSessionManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSessionBinding
    private val viewModel by viewModel<UserSessionManagerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createScopeButton.setOnClickListener {
            viewModel.createSession()
        }

        binding.displayInfoButton.setOnClickListener {
            viewModel.checkSession()
        }

        binding.deleteScopeButton.setOnClickListener {
            viewModel.deleteSession()
        }
        viewModel.isLoggedIn.observe(this) {isLogged ->
            val msg = if(isLogged) "The user is logged" else "The user is not logged"
            showSnackbar(msg)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}

