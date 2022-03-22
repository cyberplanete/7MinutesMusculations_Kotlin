package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Création de ma variable qui hérite d'un frameLayout
         *
         */
        val flStartButton : FrameLayout = findViewById(R.id.frame_layout_start)
        flStartButton.setOnClickListener{
            Toast.makeText(this,"Here we will start the exercise", Toast.LENGTH_SHORT).show()
        }
    }
}