package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class LaunchScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val welcomeMessage = findViewById<TextView>(R.id.txt_welcome)
        val welcomeMessageTxT = welcomeMessage.text

        supportFragmentManager.beginTransaction()
            .add(R.id.parent_layout, Footer())
            .commit()

        val shColor = ContextCompat.getColor(this, R.color.p_60)
        val sfStart = welcomeMessageTxT.indexOf(resources.getString(R.string.launch_screen_txt_company_name))
        val sfEnd = sfStart + resources.getString(R.string.launch_screen_txt_company_name).length


        val spannable: Spannable = SpannableString(welcomeMessage.text)
        spannable.setSpan(ForegroundColorSpan(shColor), sfStart, sfEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcomeMessage.setText(spannable, TextView.BufferType.SPANNABLE)
    }
}