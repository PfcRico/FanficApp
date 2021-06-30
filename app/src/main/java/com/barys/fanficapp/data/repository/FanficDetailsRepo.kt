package com.barys.fanficapp.data.repository

import androidx.lifecycle.LiveData
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.repository.FanficNetworkDataSource
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.FanficDetails
import io.reactivex.disposables.CompositeDisposable

class FanficDetailsRepo(private val apiService: FanficDBInterface) {

    lateinit var fanficNetworkDataSource: FanficNetworkDataSource

    fun fetchSingleFanficDetails(compositeDisposable: CompositeDisposable, fanficId: Int):
            LiveData<FanficDetails> {
        fanficNetworkDataSource = FanficNetworkDataSource(apiService, compositeDisposable)
        fanficNetworkDataSource.fetchFanficDetails(fanficId)

        return fanficNetworkDataSource.downloadedFanficResponse
    }

    fun getFanficNetworkState(): LiveData<NetworkStatus> {
        return fanficNetworkDataSource.networkState
    }
}