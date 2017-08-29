package com.reggar.todomvp.feature.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import com.reggar.todomvp.R
import com.reggar.todomvp.common.adapter.ViewType
import com.reggar.todomvp.common.adapter.ViewTypeDelegateAdapter
import com.reggar.todomvp.common.extensions.inflate
import com.reggar.todomvp.data.Todo

class TodoDelegateAdapter(
        private val onTodoSelectedListener: (Pair<Todo, Boolean>) -> Unit
) : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TodoViewHolder(parent, onTodoSelectedListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as? TodoViewHolder)?.bind(item as Todo)
    }

    class TodoViewHolder(
            parent: ViewGroup,
            private val listener: (Pair<Todo, Boolean>) -> Unit
    ) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.layout_todo)
    ) {
        @BindView(R.id.checkbox_todo) lateinit var checkbox: CheckBox

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(todo: Todo) {
            checkbox.apply {
                setOnCheckedChangeListener(null)
                text = todo.summary
                isChecked = todo.isCompleted
                setOnCheckedChangeListener { _, isChecked -> listener.invoke(todo to isChecked) }
            }
        }
    }
}