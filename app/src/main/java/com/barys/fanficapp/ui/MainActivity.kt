package com.barys.fanficapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.barys.fanficapp.R
import com.barys.fanficapp.adapter.FanficPagedListAdapter
import com.barys.fanficapp.data.api.FanficDBClient
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.repository.FanficPagedListRepo
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var fanficRepo: FanficPagedListRepo
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService: FanficDBInterface = FanficDBClient.getClient()

        fanficRepo = FanficPagedListRepo(apiService)

        viewModel = getViewModel()

        val fanficAdapter = FanficPagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = fanficAdapter.getItemViewType(position)
                if (viewType == fanficAdapter.FANFIC_VIEW_TYPE) return 1
                else return 3
            }
        }

        binding.rvFanficsList.layoutManager = gridLayoutManager
        binding.rvFanficsList.setHasFixedSize(true)
        binding.rvFanficsList.adapter = fanficAdapter

        viewModel.fanficPagedList.observe(this, Observer {
            fanficAdapter.submitList(it)
        })

        viewModel.networkStatus.observe(this, Observer {
            binding.progressBarFanfics.visibility =
                if (viewModel.listIsEmpty() && it == NetworkStatus.LOADING)
                    View.VISIBLE else View.GONE
            binding.txtErrorFanfics.visibility =
                if (viewModel.listIsEmpty() && it == NetworkStatus.ERROR)
                    View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                fanficAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEKED_CAST")
                return MainActivityViewModel(fanficRepo) as T
            }
        })[MainActivityViewModel::class.java]
    }
}






