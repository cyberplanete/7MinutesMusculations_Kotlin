package net.cyberplanete.a7minutesWorkout_kotlin.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.cyberplanete.a7minutesWorkout_kotlin.models.HistoryEntity

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Update
    suspend fun update(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("select * from `history-table`")
    fun fetchAllHistory():Flow<List<HistoryEntity>>

    @Query("select * from `history-table` where date=:date")
    fun fetchAnHistoryByDate(date:String):Flow<HistoryEntity>



}