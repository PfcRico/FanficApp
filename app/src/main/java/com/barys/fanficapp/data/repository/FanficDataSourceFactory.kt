package com.barys.fanficapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.barys.fanficapp.data.vo.Content
import androidx.paging.DataSource
import com.barys.fanficapp.data.api.FanficDBInterface
import io.reactivex.disposables.CompositeDisposable

class FanficDataSourceFactory(private val apiService: FanficDBInterface,
                              private val compositeDisposable: CompositeDisposable)
                            : DataSource.Factory<Int, Content>() {

    val fanficLiveDataSource = MutableLiveData<FanficDataSource>()

    override fun create(): DataSource<Int, Content> {
        val fanficDataSource = FanficDataSource(apiService, compositeDisposable)

        fanficLiveDataSource.postValue(fanficDataSource)
        return fanficDataSource
    }
}