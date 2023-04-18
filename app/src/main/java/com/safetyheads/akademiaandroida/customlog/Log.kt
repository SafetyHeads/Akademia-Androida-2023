package com.safetyheads.akademiaandroida.customlog

import android.util.Log
import com.safetyheads.data.network.`object`.CustomLog

class Log : CustomLog {

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}