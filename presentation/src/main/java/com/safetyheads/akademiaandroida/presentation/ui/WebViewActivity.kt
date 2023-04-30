package com.safetyheads.akademiaandroida.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val jobUrl = intent.getStringExtra("job_url")
        val webView = binding.webView
        if (jobUrl != null) {
            webView.loadUrl(jobUrl)
        }

    }
}