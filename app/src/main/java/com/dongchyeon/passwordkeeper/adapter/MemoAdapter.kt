package com.dongchyeon.passwordkeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.data.room.Memo

class MemoAdapter : ListAdapter<Memo, MemoAdapter.ViewHolder>(MemoComparator()) {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position)
        }
    }

    fun getMemo(position: Int) : Memo {
        return getItem(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.titleText)
        private val image = itemView.findViewById<ImageView>(R.id.imageView)

        companion object {
            fun create(parent: ViewGroup) : ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.memo, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(memo: Memo) {
            title.text = memo.title
        }
    }

    class MemoComparator : DiffUtil.ItemCallback<Memo>() {
        override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem == newItem
        }
    }
}