package com.barys.fanficapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.barys.fanficapp.data.api.FIRST_PAGE
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.vo.Content
import com.barys.fanficapp.data.vo.FanficResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FanficDataSource (private val apiService: FanficDBInterface,
                        private val compositeDisposable: CompositeDisposable)
                        : PageKeyedDataSource<Int,Content>() {

    private var page = FIRST_PAGE

    val networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Content>) {
        networkStatus.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getFanfics(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key){
                        callback.onResult(it.content,  params.key+1)
                        networkStatus.postValue(NetworkStatus.LOADED)
                    }else{networkStatus.postValue(NetworkStatus.ENDOFLIST)}
                },{ networkStatus.postValue(NetworkStatus.ERROR)
                    it.message?.let { it1 -> Log.e("FanficDataSource", it1) }}
                )
        )}

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Content>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Content>
    ) {
        networkStatus.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getFanfics(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.content, null, page + 1)
                    networkStatus.postValue(NetworkStatus.LOADED)
                }, {
                    networkStatus.postValue(NetworkStatus.ERROR)
                    it.message?.let { it1 -> Log.e("FanficDataSource", it1) }
                }
                )
        )

    }
}
