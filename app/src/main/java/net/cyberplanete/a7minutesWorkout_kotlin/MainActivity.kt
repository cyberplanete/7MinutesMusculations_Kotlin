package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var bindingViewMainActivity : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Prepare binding objet to be used by setContentView
         */
        bindingViewMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingViewMainActivity?.root)

        bindingViewMainActivity?.frameLayoutStart?.setOnClickListener{
            Toast.makeText(this,"Here we will start the exercise", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * To avoid memory leak
      */
    override fun onDestroy()
    {
        super.onDestroy()
        bindingViewMainActivity = null
    }
}