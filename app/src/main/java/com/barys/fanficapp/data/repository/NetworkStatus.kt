package com.barys.fanficapp.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}
class NetworkStatus (val status: Status, val msg: String){

    companion object{
        val LOADED: NetworkStatus
        val LOADING: NetworkStatus
        val ERROR: NetworkStatus

        init {
            LOADED = NetworkStatus(Status.SUCCESS,"Success")
            LOADING = NetworkStatus(Status.RUNNING, "Running")
            ERROR = NetworkStatus(Status.FAILED, "Something went wrong")
        }
    }
}