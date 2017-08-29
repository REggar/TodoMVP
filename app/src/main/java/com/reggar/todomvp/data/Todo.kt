package com.reggar.todomvp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.reggar.todomvp.common.adapter.ViewType
import com.reggar.todomvp.feature.main.adapter.AdapterConstants
import org.threeten.bp.Instant

@Entity(tableName = "todo")
data class Todo(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val summary: String,
        val isCompleted: Boolean,
        val createdAt: Instant,
        val updatedAt: Instant
) : ViewType {
    @Ignore constructor(summary: String): this(0, summary, false, Instant.now(), Instant.now())

    @Ignore override val viewType = AdapterConstants.TODO
}