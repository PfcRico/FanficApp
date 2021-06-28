package com.barys.fanficapp.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.Content
import com.barys.fanficapp.databinding.ActivityMainBinding
import com.barys.fanficapp.databinding.FanficListItemBinding
import com.barys.fanficapp.single_fanfic.SingleFanfic

class FanficPagedListAdapter: PagedListAdapter<Content, RecyclerView.ViewHolder>(FanficDiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    class FanficDiffCallback: DiffUtil.ItemCallback<Content>(){
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }
    }

    class FanficViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var binding: FanficListItemBinding
        fun bind (content: Content?, context: Context){
            binding.fanficTitleCv.text = content?.name
            binding.fanficPublish.text = content?.creationDate

            val fanficPoster = content?.picUrl
            com.bumptech.glide.Glide.with(context)
                .load(fanficPoster)
                .into(binding.fanficPosterCv)

            itemView.setOnClickListener{
                val intent = Intent(context, SingleFanfic::class.java)
                intent.putExtra("id", content?.id)
                context.startActivity(intent)
            }
        }
        }

    class NetworkStatusItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private lateinit var binding: ActivityMainBinding

        fun bind(networkStatus: NetworkStatus?) {
            if (networkStatus != null && networkStatus == NetworkStatus.LOADING){
                binding.progressBarFanfics.visibility = View.VISIBLE
            }
            else {
                binding.progressBarFanfics.visibility = View.GONE
            }
            if (networkStatus != null && networkStatus == NetworkStatus.ERROR){
                binding.txtErrorFanfics.visibility = View.VISIBLE
                binding.txtErrorFanfics.text = networkStatus.msg
            }
            else if (networkStatus != null && networkStatus == NetworkStatus.ENDOFLIST){
                binding.txtErrorFanfics.visibility = View.VISIBLE
                binding.txtErrorFanfics.text = networkStatus.msg
            }
            else {
                binding.txtErrorFanfics.visibility = View.GONE
            }
        }
    }
    }

