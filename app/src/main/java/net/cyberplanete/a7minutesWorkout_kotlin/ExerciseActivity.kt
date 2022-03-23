package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var bindingExcerciseActivity : ActivityExerciseBinding? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingExcerciseActivity = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(bindingExcerciseActivity?.root)


        setSupportActionBar(bindingExcerciseActivity?.toolbarExcercise)

        if (supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }


        bindingExcerciseActivity?.toolbarExcercise?.setNavigationOnClickListener()
        {
            onBackPressed()
        }

    }
}