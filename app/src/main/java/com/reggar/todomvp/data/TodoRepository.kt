package com.reggar.todomvp.data

import com.reggar.todomvp.common.rx.SchedulerProvider
import io.reactivex.Completable
import org.threeten.bp.Clock
import javax.inject.Inject

/**
 * A repository hiding the implementation of the source of the data, in the future could combine multiple
 * sources, i.e. network, data, memory.
 */
class TodoRepository @Inject constructor(
        private val todoDao: TodoDao,
        private val clock: Clock,
        private val schedulerProvider: SchedulerProvider
) {
    fun getTodos() = todoDao.getAll()

    fun createTodo(text: CharSequence): Completable {
        return Completable.fromAction {
            todoDao.insert(Todo(text.toString()))
        }.subscribeOn(schedulerProvider.io())
    }

    fun setTodoComplete(todo: Todo, isComplete: Boolean): Completable {
        return Completable.fromAction {
            todoDao.update(todo.copy(isCompleted = isComplete, updatedAt = clock.instant()))
        }.subscribeOn(schedulerProvider.io())
    }

    fun removeCompleteTodos(): Completable {
        return Completable.fromAction {
            todoDao.removeComplete()
        }.subscribeOn(schedulerProvider.io())
    }
}