package com.android.dayplanner.app.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "createDate") var createDate: String = Calendar.getInstance().time.toString(),
    @ColumnInfo(name = "status")var status: Status = Status.PENDING
) : Parcelable {
    fun performValidation(block: (Boolean, String) -> Unit) {
        when {
            title.isBlank() -> return block.invoke(false, "Title is empty")
            description.isBlank() -> return block.invoke(false, "Description is empty")
            else -> block.invoke(true, "Task is valid")
        }
    }
}

enum class Status {
    COMPLETED,
    PENDING,
    CANCELLED
}
