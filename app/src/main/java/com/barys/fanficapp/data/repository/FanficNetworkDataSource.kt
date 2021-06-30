package com.barys.fanficapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.vo.FanficDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FanficNetworkDataSource(
    private val apiService: FanficDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkState: LiveData<NetworkStatus>
        get() = _networkState

    private val _downloadedFanficResponse = MutableLiveData<FanficDetails>()
    val downloadedFanficResponse: LiveData<FanficDetails>
        get() = _downloadedFanficResponse

    fun fetchFanficDetails(fanficID: Int) {
        _networkState.postValue(NetworkStatus.LOADING)

        try {
            compositeDisposable.add(
                apiService.getFanfic(fanficID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedFanficResponse.postValue(it)
                            _networkState.postValue(NetworkStatus.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkStatus.ERROR)
                            it.message?.let { it1 -> Log.e("FanficDataSource", it1) }
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("FanficDataSource", e.message.toString())
        }

    }
}