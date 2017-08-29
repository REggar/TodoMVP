package com.reggar.todomvp.feature.main

import com.reggar.todomvp.common.rx.SchedulerProvider
import com.reggar.todomvp.common.rx.takeSecondObservable
import com.reggar.todomvp.data.Todo
import com.reggar.todomvp.data.TodoRepository
import com.reggar.todomvp.feature.base.MvpContract
import io.reactivex.Observable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val todoRepository: TodoRepository,
        private val schedulerProvider: SchedulerProvider
) : MvpContract.Presenter<MainPresenter.View>() {
    override fun onAttachView() {
        addDisposable(getTodos())
        addDisposable(onTextChanged())
        addDisposable(onTodoChecked())
        addDisposable(onTodoAdded())
        addDisposable(onRemoveCompletedClicked())
    }

    private fun getTodos() =
            todoRepository.getTodos()
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ todos ->
                        val incompleteTodos = todos.filter { !it.isCompleted }
                        val completeTodos = todos.filter { it.isCompleted }
                        view.showTodos(incompleteTodos, completeTodos)
                    })

    private fun onTextChanged() =
            view.onTextChanged()
                    .subscribe({
                        view.toggleSubmit(it.isNotBlank())
                    })

    private fun onTodoChecked() =
            view.onTodoCheckedChange()
                    .switchMapSingle { (todo, isChecked) -> todoRepository.setTodoComplete(todo, isChecked).toSingleDefault(Unit) }
                    .observeOn(schedulerProvider.ui())
                    .subscribe()

    private fun onTodoAdded() =
            view.onAddClicked()
                    .withLatestFrom(view.onTextChanged(), takeSecondObservable())
                    .filter { !it.isBlank() }
                    .switchMapSingle { todoRepository.createTodo(it).toSingleDefault(Unit) }
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        view.clearText()
                    })

    private fun onRemoveCompletedClicked() =
            view.onRemoveCompletedClicked()
                    .switchMapSingle { todoRepository.removeCompleteTodos().toSingleDefault(Unit) }
                    .observeOn(schedulerProvider.ui())
                    .subscribe()

    interface View : MvpContract.View {
        fun onTextChanged(): Observable<CharSequence>
        fun onAddClicked(): Observable<Unit>
        fun onRemoveCompletedClicked(): Observable<Unit>
        fun onTodoCheckedChange(): Observable<Pair<Todo, Boolean>>
        fun clearText()
        fun showTodos(incompleteTodos: List<Todo>, completeTodos: List<Todo>)
        fun toggleSubmit(isEnabled: Boolean)
    }
}