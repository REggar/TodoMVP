package com.reggar.todomvp.feature.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.reggar.todomvp.R
import com.reggar.todomvp.common.adapter.ViewType
import com.reggar.todomvp.common.adapter.ViewTypeDelegateAdapter
import com.reggar.todomvp.common.extensions.inflate
import com.reggar.todomvp.feature.main.models.HeaderItem

class HeaderDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HeaderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as? HeaderViewHolder)?.bind(item as HeaderItem)
    }

    class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.layout_todoheader)
    ) {

        fun bind(headerItem: HeaderItem) = with(itemView as TextView?) {
            this?.setText(headerItem.stringRes)
        }
    }
}