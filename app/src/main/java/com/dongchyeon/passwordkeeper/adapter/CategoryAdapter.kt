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

class CategoryAdapter : ListAdapter<String, CategoryAdapter.ViewHolder>(CategoryComparator()) {
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

    fun getCategory(position: Int) : String {
        return getItem(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.titleText)
        private val image = itemView.findViewById<ImageView>(R.id.imageView)

        companion object {
            fun create(parent: ViewGroup) : ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: String) {
            title.text = item
            if (item == "새 항목 추가") {
                image.setImageResource(R.drawable.ic_add_button)
            } else if (item == "비밀번호 변경") {
                image.setImageResource(R.drawable.ic_reset_password)
            } else {
                image.setImageResource(R.drawable.lock_icon)
            }
        }
    }

    class CategoryComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}