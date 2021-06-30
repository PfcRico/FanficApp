package com.barys.fanficapp.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    ENDOFLIST
}

class NetworkStatus(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkStatus
        val LOADING: NetworkStatus
        val ERROR: NetworkStatus
        val ENDOFLIST: NetworkStatus

        init {
            LOADED = NetworkStatus(Status.SUCCESS, "Success")
            LOADING = NetworkStatus(Status.RUNNING, "Running")
            ERROR = NetworkStatus(Status.FAILED, "Something went wrong")
            ENDOFLIST = NetworkStatus(Status.ENDOFLIST, "You have reach the end")
        }
    }
}