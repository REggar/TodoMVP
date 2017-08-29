package com.reggar.todomvp.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): Flowable<List<Todo>>

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Query("DELETE FROM todo WHERE isCompleted=1")
    fun removeComplete()
}