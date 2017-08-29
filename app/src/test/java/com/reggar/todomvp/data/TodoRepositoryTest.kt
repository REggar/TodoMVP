package com.reggar.todomvp.data

import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.reggar.todomvp.BaseTest
import io.reactivex.Flowable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.Instant

class TodoRepositoryTest : BaseTest() {
    private val todoDao = mock<TodoDao>()
    private val clock = mock<Clock>()
    private val repository = TodoRepository(todoDao, clock, schedulerProvider)

    @Test
    fun getTodos_returnsTodosFromDao() {
        /* Given */
        val mockFlowable = mock<Flowable<List<Todo>>>()

        /* When */
        whenever(todoDao.getAll()).thenReturn(mockFlowable)

        /* Then */
        assertThat(repository.getTodos()).isEqualTo(mockFlowable)
    }

    @Test
    fun createTodo_createsTodoWithSummary() {
        /* Given */
        val text = "text"

        /* When */
        val testSubscribe = repository.createTodo(text).test()

        /* Then */
        verify(todoDao).insert(argThat { this.summary == text })
        testSubscribe.assertNoErrors()
        testSubscribe.assertComplete()
    }

    @Test
    fun setTodoComplete_setTodoCompletWithInstantNow() {
        /* Given */
        val todo = Todo("Test")
        val now = Instant.now()
        val isComplete = true

        /* When */
        whenever(clock.instant()).thenReturn(now)
        val testSubscribe = repository.setTodoComplete(todo, isComplete).test()

        /* Then */
        verify(todoDao).update(todo.copy(isCompleted = isComplete, updatedAt = now))
        testSubscribe.assertNoErrors()
        testSubscribe.assertComplete()
    }

    @Test
    fun removeCompleteTodos_removeComplete() {
        /* Given */

        /* When */
        val testSubscribe = repository.removeCompleteTodos().test()

        /* Then */
        verify(todoDao).removeComplete()
        testSubscribe.assertNoErrors()
        testSubscribe.assertComplete()
    }
}