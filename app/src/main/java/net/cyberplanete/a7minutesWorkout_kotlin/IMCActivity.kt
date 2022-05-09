package net.cyberplanete.a7minutesWorkout_kotlin

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityImcBinding
import net.cyberplanete.a7minutesWorkout_kotlin.models.IMC
import net.cyberplanete.a7minutesWorkout_kotlin.useful.IMCBrain
import kotlin.math.roundToInt


class IMCActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW //A variable to hold a value to make a selected view visible

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
         * Par defaut la page configuration de la page Metric FR pour le calcul de l'IMC
         */
        makeVisibleMetricUnitsView()

        /**
         * Méthode utiliser pour configurer la page usMetric ou metricFr on fonction du choix sur le bouton radio
         */
        bindingIMC?.rgUnits?.setOnCheckedChangeListener { _, id: Int ->
            if (id == bindingIMC?.rbMetricsUnits?.id) {
                makeVisibleMetricUnitsView() // Configuration pour la page metric FR
            } else {
                makeVisibleUSMetricUnitsView() // Configuration pour la page metric US
            }

        }


        /**
         * OnClickListenner SUR le bouton calculIMC
         */
        bindingIMC?.btnCalculateIMC?.setOnClickListener()
        {
            if (currentVisibleView == METRIC_UNITS_VIEW) {
                /* vérifie si les données obligatoire pour le calcul sont bien présentent */
                if (validateMetricUnits()) {
                    val heightValue: Float = bindingIMC?.etMetricUnitHeight?.text.toString()
                        .toFloat() / 100 // La valeur est entrée en Mètre que je divise par 100 pour une valeur en cm
                    val weightValue: Float =
                        bindingIMC?.etMetricUnitWeight?.text.toString().toFloat()
                    /* Formule IMC */
                    val imc: Float = weightValue / (heightValue * heightValue)

                    val imcObject = IMCBrain().getIMCObject(imc)

                    //TODO affichage du resultat de l'IMC
                    displayIMCResult(imcObject, imc)
                } else {
                    Toast.makeText(
                        this@IMCActivity,
                        "Veuillez entrer des données valides pour le calcul de votre IMC",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else
            {
                /* vérifie si les données obligatoire pour le calcul sont bien présentent */
                if (validateUSMetricUnits()) {
                    val heightInchValueUS: String = bindingIMC?.etUSMetricHeightInch?.text.toString()
                    val heightFeetValueUS: Float = bindingIMC?.etUSUnitHeightFeet?.text.toString()
                        .toFloat() / 100 // La valeur est entrée en Mètre que je divise par 100 pour une valeur en cm
                    val weightValueUS: Float =
                        bindingIMC?.etUSMetricWeight?.text.toString().toFloat()
                    /*Conversion en INCH */
                    val heightTotalValueInInch = heightInchValueUS.toFloat() + heightFeetValueUS.toFloat() * 12
                    /* Formule IMC US */
                    val imc: Float = 703 * (weightValueUS/ (heightTotalValueInInch*heightTotalValueInInch) )

                    val imcObject = IMCBrain().getIMCObject(imc)

                    //TODO affichage du resultat de l'IMC
                    displayIMCResult(imcObject, imc)
                } else {
                    Toast.makeText(
                        this@IMCActivity,
                        "Veuillez entrer des données valides pour le calcul de votre IMC",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }

    /**
     * Méthode utilisée pour la configuration de la page Metrique FR
     */
    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        bindingIMC?.tilMetricUnitHeight?.visibility = View.VISIBLE
        bindingIMC?.tilMetricUnitWeight?.visibility = View.VISIBLE
        bindingIMC?.tilUSMetricHeightFeet?.visibility = View.GONE
        bindingIMC?.tilUSMetricWeight?.visibility = View.GONE
        bindingIMC?.tilUSMetricHeightInch?.visibility = View.GONE

        bindingIMC?.etMetricUnitHeight?.text!!.clear() //Height is cleared
        bindingIMC?.etMetricUnitWeight?.text!!.clear() //Weight is cleared
        bindingIMC?.llDisplayIMCResult?.visibility =
            View.INVISIBLE //Resultat est invisible par défaut à chague changement de page

    }

    /**
     * Méthode utilisée pour la configuration de la page Metrique US
     */
    private fun makeVisibleUSMetricUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        bindingIMC?.tilMetricUnitHeight?.visibility =
            View.INVISIBLE // Invisible plutot que gone pour garder le bon positionnement des éléments
        bindingIMC?.tilMetricUnitWeight?.visibility =
            View.INVISIBLE // Invisible plutot que gone pour garder le bon positionnement
        bindingIMC?.tilUSMetricHeightFeet?.visibility = View.VISIBLE
        bindingIMC?.tilUSMetricWeight?.visibility = View.VISIBLE
        bindingIMC?.tilUSMetricHeightInch?.visibility = View.VISIBLE

        bindingIMC?.etUSMetricWeight?.text!!.clear() //Height is cleared
        bindingIMC?.etUSUnitHeightFeet?.text!!.clear() //Weight is cleared
        bindingIMC?.etUSMetricHeightInch?.text!!.clear() //Weight is cleared
        bindingIMC?.llDisplayIMCResult?.visibility =
            View.INVISIBLE //Resultat est invisible par défaut à chague changement de page

    }


    /**
     * Cette méthode vérifie si les données obligatoires pour le calcul IMC sont bien présentent
     */
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        /* Vérification du champ taille si n'est pas vide */
        if (bindingIMC?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        /* Vérification du champ poids si n'est pas vide */
        else if (bindingIMC?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid


    }

    /**
     * Cette méthode vérifie si les données obligatoires pour le calcul IMC sont bien présentent
     */
    private fun validateUSMetricUnits(): Boolean {
        var isValid = true

            /* Vérification du champ poids si n'est pas vide */
            if (bindingIMC?.etUSMetricHeightInch?.text.toString().isEmpty()) {
                isValid = false
            }
            /* Vérification des champs feet ou inch si n'est pas vide */
            else if (bindingIMC?.etUSMetricWeight?.text.toString().isEmpty()) {
                isValid = false

            }
            else if ( bindingIMC?.etUSUnitHeightFeet?.text.toString().isEmpty()) {
                isValid = false

            }
            return isValid
        }




    /**
     * Méthode utilisée pour l'affichage du resultat de l'IMC
     */
    private fun displayIMCResult(imcObject: IMC?, imc: Float?) {
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