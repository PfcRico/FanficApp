package com.barys.fanficapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.barys.fanficapp.data.repository.FanficPagedListRepo
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.Content
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val fanficRepo: FanficPagedListRepo) : ViewModel() {

    private val compositeDispodable = CompositeDisposable()

    val fanficPagedList: LiveData<PagedList<Content>> by lazy {
        fanficRepo.fetchFanficPagedList(compositeDispodable)
    }

    val networkStatus: LiveData<NetworkStatus> by lazy {
        fanficRepo.getNetworkStatus()
    }

    fun listIsEmpty():Boolean {
        return fanficPagedList.value?.isEmpty()?:true
    }

    override fun onCleared(){
        super.onCleared()
        compositeDispodable.dispose()
    }

}