package com.example.dailer_replacement.utils

data class GsmCall(val status: GsmCall.Status, val displayName: String?) {

    enum class Status {
        CONNECTING,
        DIALING,
        RINGING,
        ACTIVE,
        DISCONNECTED,
        UNKNOWN
    }
}