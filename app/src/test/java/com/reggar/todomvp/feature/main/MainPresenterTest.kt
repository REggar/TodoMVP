package com.reggar.todomvp.feature.main

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.reggar.todomvp.BasePresenterTest
import com.reggar.todomvp.data.Todo
import com.reggar.todomvp.data.TodoRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class MainPresenterTest : BasePresenterTest<MainPresenter, MainPresenter.View>() {
    private val todoRepository = mock<TodoRepository>()
    private val textChangedObservable = PublishSubject.create<CharSequence>()
    private val buttonClickedObservable = PublishSubject.create<Unit>()
    private val removeCompletedClickedObservable = PublishSubject.create<Unit>()
    private val todoCheckedObservable = PublishSubject.create<Pair<Todo, Boolean>>()
    private val todoDaoGetAll = PublishSubject.create<List<Todo>>()

    override fun createView(): MainPresenter.View = mock {
        on { onTextChanged() } doReturn textChangedObservable
        on { onAddClicked() } doReturn buttonClickedObservable
        on { onRemoveCompletedClicked() } doReturn removeCompletedClickedObservable
        on { onTodoCheckedChange() } doReturn todoCheckedObservable
    }

    override fun createPresenter() = MainPresenter(todoRepository, schedulerProvider)

    override fun beforeAttachView() {
        whenever(todoRepository.getTodos()).doReturn(todoDaoGetAll.toFlowable(BackpressureStrategy.LATEST))
        whenever(todoRepository.createTodo(any())).doReturn(Completable.complete())
        whenever(todoRepository.setTodoComplete(any(), any())).doReturn(Completable.complete())
        whenever(todoRepository.removeCompleteTodos()).doReturn(Completable.complete())

    }

    @Test
    fun fetchTodo_todosEmitted_showTodos() {
        /* Given */
        val completeTodo = mock<Todo> {
            on { this.isCompleted } doReturn true
        }
        val incompleteTodo = mock<Todo> {
            on { this.isCompleted } doReturn false
        }
        val todos = listOf(completeTodo, incompleteTodo)

        /* When */
        todoDaoGetAll.onNext(todos)

        /* Then */
        verify(view).showTodos(listOf(incompleteTodo), listOf(completeTodo))
    }

    @Test
    fun onTextChanged_ifNotBlank_enableSubmit() {
        /* Given */
        val textChange = "textChange"

        /* When */
        textChangedObservable.onNext(textChange)

        /* Then */
        verify(view).toggleSubmit(true)
    }

    @Test
    fun onTextChanged_ifBlank_disableSubmit() {
        /* Given */
        val textChange = ""

        /* When */
        textChangedObservable.onNext(textChange)

        /* Then */
        verify(view).toggleSubmit(false)
    }

    @Test
    fun onTodoChecked_updateTodo() {
        /* Given */
        val todo = Todo("test")
        val isComplete = true

        /* When */
        todoCheckedObservable.onNext(todo to isComplete)

        /* Then */
        verify(todoRepository).setTodoComplete(todo, isComplete)
    }

    @Test
    fun onTodoAdded_ifEmpty_doNotCreateTodo() {
        /* Given */
        val todoText = ""

        /* When */
        buttonClickedObservable.onNext(Unit)
        textChangedObservable.onNext(todoText)

        /* Then */
        verify(todoRepository, never()).createTodo(todoText)
    }

    @Test
    fun onTodoAdded_ifNotEmpty_createTodo() {
        /* Given */
        val todoText = "new todo"

        /* When */
        textChangedObservable.onNext(todoText)
        buttonClickedObservable.onNext(Unit)

        /* Then */
        verify(todoRepository).createTodo(todoText)
    }

    @Test
    fun onTodoAdded_ifNotEmpty_clearText() {
        /* Given */
        val todoText = "new todo"

        /* When */
        textChangedObservable.onNext(todoText)
        buttonClickedObservable.onNext(Unit)

        /* Then */
        verify(view).clearText()
    }

    @Test
    fun onRemoveCompletedClicked_removeComplete() {
        /* Given */

        /* When */
        removeCompletedClickedObservable.onNext(Unit)

        /* Then */
        verify(todoRepository).removeCompleteTodos()
    }
}