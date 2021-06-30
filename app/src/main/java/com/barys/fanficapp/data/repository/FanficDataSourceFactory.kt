package com.barys.fanficapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.barys.fanficapp.data.vo.Fanfic
import androidx.paging.DataSource
import com.barys.fanficapp.data.api.FanficDBInterface
import io.reactivex.disposables.CompositeDisposable

class FanficDataSourceFactory(
    private val apiService: FanficDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Fanfic>() {

    val fanficLiveDataSource = MutableLiveData<FanficDataSource>()

    override fun create(): DataSource<Int, Fanfic> {
        val fanficDataSource = FanficDataSource(apiService, compositeDisposable)

        fanficLiveDataSource.postValue(fanficDataSource)
        return fanficDataSource
    }
}