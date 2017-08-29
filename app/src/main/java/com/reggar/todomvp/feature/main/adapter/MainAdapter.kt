package com.reggar.todomvp.feature.main.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.reggar.todomvp.R
import com.reggar.todomvp.common.adapter.ViewType
import com.reggar.todomvp.common.adapter.ViewTypeDelegateAdapter
import com.reggar.todomvp.data.Todo
import com.reggar.todomvp.feature.main.models.HeaderItem

class MainAdapter(
        onTodoSelectedListener: (Pair<Todo, Boolean>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var items = ArrayList<ViewType>()

    init {
        delegateAdapters.put(AdapterConstants.TODO, TodoDelegateAdapter(onTodoSelectedListener))
        delegateAdapters.put(AdapterConstants.HEADER, HeaderDelegateAdapter())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return items.get(position).viewType
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    fun setItems(incompleteTodos: List<Todo>, completedTodos: List<Todo>) {
        items.clear()
        items.add(HeaderItem(R.string.main_incomplete))
        items.addAll(incompleteTodos)
        items.add(HeaderItem(R.string.main_complete))
        items.addAll(completedTodos)
        notifyDataSetChanged()
    }
}