package com.example.dailer_replacement.Object

import android.annotation.TargetApi
import io.reactivex.Observable
import android.os.Build
import android.telecom.Call
import android.util.Log
import com.example.dailer_replacement.utils.GsmCall
import com.example.dailer_replacement.utils.toGsmCall
import io.reactivex.subjects.BehaviorSubject


@TargetApi(Build.VERSION_CODES.M)
object CallManager {

    private const val LOG_TAG = "CallManager"

    private val subject = BehaviorSubject.create<GsmCall>()

    private var currentCall: Call? = null

    fun updates(): Observable<GsmCall> = subject

    fun updateCall(call: Call?) {
        currentCall = call
        call?.let {
            subject.onNext(it.toGsmCall())
        }
    }

    fun cancelCall() {
        currentCall?.let {
            when (it.state) {
                Call.STATE_RINGING -> rejectCall()
                else               -> disconnectCall()
            }
        }
    }

    fun acceptCall() {
        Log.i(LOG_TAG, "acceptCall")
        currentCall?.let {
            it.answer(it.details.videoState)
        }
    }

    private fun rejectCall() {
        Log.i(LOG_TAG, "rejectCall")
        currentCall?.reject(false, "")
    }

    private fun disconnectCall() {
        Log.i(LOG_TAG, "disconnectCall")
        currentCall?.disconnect()
    }
}
