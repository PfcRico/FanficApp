package com.barys.fanficapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.FanficDetails
import com.barys.fanficapp.data.repository.FanficDetailsRepo
import io.reactivex.disposables.CompositeDisposable

class SingleFanficViewModel(private val fanficRepo: FanficDetailsRepo, fanficId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val fanficDetails: LiveData<FanficDetails> by lazy {
        fanficRepo.fetchSingleFanficDetails(compositeDisposable, fanficId)
    }

    val networkStatus: LiveData<NetworkStatus> by lazy {
        fanficRepo.getFanficNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}