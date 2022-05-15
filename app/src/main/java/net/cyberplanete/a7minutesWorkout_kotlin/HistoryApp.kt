package net.cyberplanete.a7minutesWorkout_kotlin

import android.app.Application
import net.cyberplanete.a7minutesWorkout_kotlin.dao.HistoryDatabase

/*
* application class and initialize the database
* */
class HistoryApp : Application()
{
    val historyDatabase by lazy { HistoryDatabase.getInstance(this) }
}