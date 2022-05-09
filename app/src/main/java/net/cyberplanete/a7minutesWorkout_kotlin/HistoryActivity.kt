package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityHistoryBinding
import net.cyberplanete.a7minutesWorkout_kotlin.useful.Constants

class HistoryActivity : AppCompatActivity() {

    private var bindingHistory: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHistory = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(bindingHistory?.root)

        /* Action BAR*/
        setSupportActionBar(bindingHistory?.toolbarHistory)
        if (supportActionBar != null) {/* Afficher la fleche pour permettre le retour*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Historique"
        }

        /* Bouton retour   <-  vers le début de l'exercice  */
        bindingHistory?.toolbarHistory?.setNavigationOnClickListener()
        {
            //onBackPressed()
            customDialogForBackButton()

        }
        /* END - Action BAR*/



    }

    private fun customDialogForBackButton() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingHistory = null
    }
}