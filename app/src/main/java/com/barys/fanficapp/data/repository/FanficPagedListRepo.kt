package com.barys.fanficapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.api.POST_PER_PAGE
import com.barys.fanficapp.data.vo.Content
import io.reactivex.disposables.CompositeDisposable

class FanficPagedListRepo (private val apoService: FanficDBInterface){

    lateinit var fanficPagedList: LiveData<PagedList<Content>>
    lateinit var fanficDataSourceFactory: FanficDataSourceFactory

    fun fetchFanficPagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Content>>{
        fanficDataSourceFactory = FanficDataSourceFactory(apoService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        fanficPagedList = LivePagedListBuilder(fanficDataSourceFactory, config).build()

        return fanficPagedList
    }

    fun getNetworkStatus(): LiveData<NetworkStatus>{
        return Transformations.switchMap<FanficDataSource, NetworkStatus>(fanficDataSourceFactory.fanficLiveDataSource,
        FanficDataSource::networkStatus)
    }

}
