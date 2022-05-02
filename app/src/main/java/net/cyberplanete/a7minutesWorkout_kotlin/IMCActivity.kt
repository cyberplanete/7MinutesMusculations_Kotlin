package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityImcBinding

class IMCActivity : AppCompatActivity() {

    private var bindingIMC: ActivityImcBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        bindingIMC = ActivityImcBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingIMC?.root)

        /**
         *  Configuration Toolbar
         */
        setSupportActionBar(bindingIMC?.toolbarImcActivity)
        if (supportActionBar != null) {/* Afficher la fleche pour permettre le retour*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calcul d'IMC" // Ajout du titre à droite du bouton retour
        }
        /* Bouton retour   <-  vers la page principale  */
        bindingIMC?.toolbarImcActivity?.setNavigationOnClickListener()
        {
            onBackPressed()
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        bindingIMC = null
    }
}