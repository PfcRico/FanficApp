package com.barys.fanficapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.barys.fanficapp.R
import com.barys.fanficapp.data.repository.NetworkStatus
import com.barys.fanficapp.data.vo.Fanfic
import com.barys.fanficapp.databinding.ActivityMainBinding
import com.barys.fanficapp.databinding.FanficListItemBinding
import com.barys.fanficapp.ui.SingleFanfic

class FanficPagedListAdapter(public val context: Context) : PagedListAdapter<Fanfic,
        RecyclerView.ViewHolder>(FanficDiffCallback()) {

    val FANFIC_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkStatus: NetworkStatus? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == FANFIC_VIEW_TYPE) {
            (holder as FanficViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStatusItemViewHolder).bind(networkStatus)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == FANFIC_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.fanfic_list_item, parent, false)
            return FanficViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_status, parent, false)
            return NetworkStatusItemViewHolder(view)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkStatus != null && networkStatus != NetworkStatus.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            FANFIC_VIEW_TYPE
        }
    }

    class FanficDiffCallback : DiffUtil.ItemCallback<Fanfic>() {
        override fun areItemsTheSame(oldItem: Fanfic, newItem: Fanfic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Fanfic, newItem: Fanfic): Boolean {
            return oldItem == newItem
        }
    }

    class FanficViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val binding = FanficListItemBinding.bind(view)


        fun bind(content: Fanfic?, context: Context) {

            val view = binding.root

            binding.fanficTitleCv.text = content?.name
            binding.fanficPublish.text = content?.creationDate
            binding.fandom.text = content?.fandom

            val fanficPoster = content?.picUrl
            com.bumptech.glide.Glide.with(context)
                .load(fanficPoster)
                .into(binding.fanficPosterCv)

            itemView.setOnClickListener {
                val intent = Intent(context, SingleFanfic::class.java)
                intent.putExtra("id", content?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStatusItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var binding: ActivityMainBinding

        fun bind(networkStatus: NetworkStatus?) {


            if (networkStatus != null && networkStatus == NetworkStatus.LOADING) {
                binding.progressBarFanfics.visibility = View.VISIBLE
            } else {
                binding.progressBarFanfics.visibility = View.GONE
            }
            if (networkStatus != null && networkStatus == NetworkStatus.ERROR) {
                binding.txtErrorFanfics.visibility = View.VISIBLE
                binding.txtErrorFanfics.text = networkStatus.msg
            } else if (networkStatus != null && networkStatus == NetworkStatus.ENDOFLIST) {
                binding.txtErrorFanfics.visibility = View.VISIBLE
                binding.txtErrorFanfics.text = networkStatus.msg
            } else {
                binding.txtErrorFanfics.visibility = View.GONE
            }
        }

    }

    fun setNetworkState(newNetworkStatus: NetworkStatus) {
        val previousState: NetworkStatus? = this.networkStatus
        val hadExtraRow = hasExtraRow()
        this.networkStatus = newNetworkStatus
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow()) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkStatus) {
            notifyItemChanged(itemCount - 1)
        }
    }
}

