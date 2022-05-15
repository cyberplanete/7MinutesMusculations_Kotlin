package net.cyberplanete.a7minutesWorkout_kotlin.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History-table")
class HistoryEntity (
    @PrimaryKey
    val date:String

)