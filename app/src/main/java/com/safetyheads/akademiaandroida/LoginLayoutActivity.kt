package com.safetyheads.akademiaandroida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class LoginLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_layout)

        val footerFragment = Footer()
        val fragment : Fragment? =
            supportFragmentManager.findFragmentByTag(Footer::class.java.simpleName)

        if(fragment !is Footer){
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_login_layout_constraint_layout, footerFragment, Footer::class.java.simpleName)
                .commit()
        }
    }
}