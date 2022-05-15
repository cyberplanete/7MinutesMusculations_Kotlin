package net.cyberplanete.a7minutesWorkout_kotlin.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.cyberplanete.a7minutesWorkout_kotlin.models.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract fun historyDAO(): HistoryDAO


    /**
     * Define a companion object, this allows to add functions on the EmployeeDatabase class.
     *
     * For example, classes can call `HistoryDatabase.getInstance(context)` to instantiate
     * a new HistoryDatabase.
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help to avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

             /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         *
         * *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): HistoryDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this)
            {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, HistoryDatabase::class.java, "history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance // Assign INSTANCE to the newly created database.
                }
                return instance // Return instance; smart cast to be non-null.
            }


        }

    }


}