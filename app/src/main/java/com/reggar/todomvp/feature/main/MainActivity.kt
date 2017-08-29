package com.reggar.todomvp.feature.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.editorActions
import com.jakewharton.rxbinding2.widget.textChanges
import com.reggar.todomvp.R
import com.reggar.todomvp.data.Todo
import com.reggar.todomvp.feature.main.adapter.MainAdapter
import dagger.android.AndroidInjection
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : Activity(), MainPresenter.View {
    @BindView(R.id.toolbar_main) lateinit var toolbar: Toolbar
    @BindView(R.id.recyclerview_main) lateinit var recyclerView: RecyclerView
    @BindView(R.id.edittext_maion) lateinit var editText: EditText
    @BindView(R.id.button_main_add) lateinit var addButton: ImageButton

    @Inject lateinit var presenter: MainPresenter

    private val onTodoChecked = PublishSubject.create<Pair<Todo, Boolean>>()
    private val onClearCompletedClicked = PublishSubject.create<Unit>()

    private val todosAdapter = MainAdapter {
        onTodoChecked.onNext(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setActionBar(toolbar)
        presenter.attachView(this)
        recyclerView.apply {
            adapter = todosAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                onClearCompletedClicked.onNext(Unit)
            }
        }

        return true
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onTextChanged(): Observable<CharSequence> = editText.textChanges().toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun onTodoCheckedChange(): Observable<Pair<Todo, Boolean>> = onTodoChecked.toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun onAddClicked(): Observable<Unit> = addButton.clicks().mergeWith(editText.editorActions().filter { it == EditorInfo.IME_ACTION_DONE }.map { Unit })

    override fun onRemoveCompletedClicked(): Observable<Unit> = onClearCompletedClicked.toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun showTodos(incompleteTodos: List<Todo>, completeTodos: List<Todo>) {
        todosAdapter.setItems(incompleteTodos, completeTodos)
    }

    override fun clearText() {
        editText.setText("")
    }

    override fun toggleSubmit(isEnabled: Boolean) {
        addButton.isEnabled = isEnabled
    }
}
