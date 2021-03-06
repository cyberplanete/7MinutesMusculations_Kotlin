package net.cyberplanete.a7minutesWorkout_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var bindingViewMainActivity: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Prepare binding objet to be used by setContentView
         */
        bindingViewMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingViewMainActivity?.root)

        bindingViewMainActivity?.frameLayoutStart?.setOnClickListener {
            Toast.makeText(this, "Here we will start the exercise", Toast.LENGTH_SHORT).show()

            /**
             * Démarrer la page ExcerciseActivity
             */
            val intentExerciceActivity = Intent(this, ExerciseActivity::class.java)
            startActivity(intentExerciceActivity)
        }

        /* Pour afficher la page permettant de calculer l'IMC */
        bindingViewMainActivity?.flIMC?.setOnClickListener()
        {
            val intent = Intent(this, IMCActivity::class.java)
            startActivity(intent)
        }

        /* Pour afficher la page historique des entrainements */
        bindingViewMainActivity?.flHistory?.setOnClickListener()
        {
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * To avoid memory leak
     */
    override fun onDestroy() {
        super.onDestroy()
        bindingViewMainActivity = null
    }
}