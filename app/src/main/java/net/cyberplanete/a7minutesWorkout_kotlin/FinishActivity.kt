package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var finishBinding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(finishBinding?.root)

        /* 1 - Affichage de la toolbar RETOUR  */
        setSupportActionBar(finishBinding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        /* 2 - setOnClickListener sur la barre de RETOUR vers le début des exercices */
        finishBinding?.toolbarFinishActivity?.setOnClickListener()
        {
            onBackPressed()
        }

        /* Fermeture de la page FinishActivity */
        finishBinding?.btFinish?.setOnClickListener { finish() }



    }
}