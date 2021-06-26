package com.barys.fanficapp.single_fanfic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.barys.fanficapp.R
import com.barys.fanficapp.data.api.FanficDBClient
import com.barys.fanficapp.data.api.FanficDBInterface
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.FanficDetails
import com.barys.fanficapp.databinding.ActivitySingleFanficBinding

class SingleFanfic : AppCompatActivity() {

    private lateinit var viewModel: SingleFanficViewModel
    private lateinit var fanficRepo: FanficDetailsRepo
    private lateinit var binding: ActivitySingleFanficBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fanfic)

        binding = ActivitySingleFanficBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val fanficId = intent.getIntExtra("id", 1)

        val apiService: FanficDBInterface = FanficDBClient.getClient()
        fanficRepo = FanficDetailsRepo(apiService)

        viewModel = getViewModel(fanficId)
        viewModel.fanficDetails.observe(this, Observer { bindUI(it) })

        viewModel.networkStatus.observe(this, Observer {
            binding.progressBar.visibility = if (it == NetworkStatus.LOADING) View.VISIBLE else
        View.GONE
        binding.txtError.visibility = if (it == NetworkStatus.ERROR) View.VISIBLE else View.GONE}
        )
    }

    fun bindUI(it:FanficDetails){

        binding.fanficAuthor.text = it.author
        binding.fanficTitle.text = it.name
        binding.fanficPublish.text = it.creationDate
        binding.fanficRating.text = it.rating.toString()

        val fanficPicUrl:String = it.picUrl
        //GlideApp.with(this).load(fanficPicUrl).into(binding.poster)

    }

    private fun getViewModel(fanficId: Int) : SingleFanficViewModel {
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEKED_CAST")
                return SingleFanficViewModel(fanficRepo, fanficId) as T
            }})[SingleFanficViewModel::class.java]
    }
}

