package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityImcBinding
import net.cyberplanete.a7minutesWorkout_kotlin.models.IMC
import net.cyberplanete.a7minutesWorkout_kotlin.useful.IMCBrain
import kotlin.math.roundToInt

class IMCActivity : AppCompatActivity() {

    private var bindingIMC: ActivityImcBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingIMC = ActivityImcBinding.inflate(layoutInflater)
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


        /**
         * OnClickListenner pour le bouton calculIMC
         */
        bindingIMC?.btnCalculateIMC?.setOnClickListener ()
        {
            /* vérifie si les données obligatoire pour le calcul sont bien présentent */
            if (validateMetricUnits())
            {
                val heightValue : Float = bindingIMC?.etMetricUnitHeight?.text.toString().toFloat() /100 // La valeur est entrée en Mètre que je divise par 100 pour une valeur en cm
                val weightValue : Float = bindingIMC?.etMetricUnitWeight?.text.toString().toFloat()
                /* Formule IMC */
                val imc : Float = weightValue / (heightValue*heightValue)

                val imcObject = IMCBrain().getIMCObject(imc)

                //TODO affichage du resultat de l'IMC
                displayIMCResult(imcObject,imc)
            }
            else
            {
                Toast.makeText(this@IMCActivity, "Veuillez entrer des données valides pour le calcul de votre IMC",Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Cette méthode vérifie si les données obligatoires pour le calcul IMC sont bien présentent
     */
    private fun validateMetricUnits() : Boolean
    {
        var isValid = true
        /* Vérification du champ taille si n'est pas vide */
        if (bindingIMC?.etMetricUnitHeight?.text.toString().isEmpty())
        {
            isValid = false
        }
        /* Vérification du champ poids si n'est pas vide */
        else if (bindingIMC?.etMetricUnitWeight?.text.toString().isEmpty())
        {
            isValid = false
        }

        return isValid
    }

    /**
     * Méthode utilisée pour l'affichage du resultat de l'IMC
     */
    private fun displayIMCResult(imcObject: IMC?, imc: Float?)
    {
        val imcLabel: String
        val imcDescription: String

        /* Entre 0 et 15 */
//        if (imc.compareTo(15f) <= 0) {
//            imcLabel = "Très en dessous du poids normal"
//            imcDescription = "Vous devez prendre un peu plus soins de vous! Mangez plus !"
//        }
//        /* Entre 15 et 16 */
//        else if(imc.compareTo(15f) > 0 && imc.compareTo(16f) <= 0)
//        {
//            imcLabel = "En dessous du poids normal"
//            imcDescription = "Vous devez prendre un peu plus soins de vous! Mangez plus !"
//        }
        bindingIMC?.llDisplayIMCResult?.visibility = View.VISIBLE //Reactivation de la visibility
        bindingIMC?.tvIMCValeur?.text = imc?.roundToInt().toString()
        bindingIMC?.tvIMCInterpretation?.text = imcObject?.interpretation
        bindingIMC?.tvIMCRecommandation?.text = imcObject?.recommandation


    }


    override fun onDestroy() {
        super.onDestroy()
        bindingIMC = null
    }
}