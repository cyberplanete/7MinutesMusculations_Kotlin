package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityExerciseBinding
import net.cyberplanete.a7minutesWorkout_kotlin.useful.Constants
import net.cyberplanete.a7minutesWorkout_kotlin.useful.ExerciceModel

class ExerciseActivity : AppCompatActivity() {

    private var bindingExcerciseActivity : ActivityExerciseBinding? =  null
    private var compteARebour: CountDownTimer? = null
    private var restProgress = 0

    private var compteARebourExcercice: CountDownTimer? = null
    private var timerProgressBarExcercice = 0

    private var exerciceList: ArrayList<ExerciceModel>? = null
    private var currentExercicePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingExcerciseActivity = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(bindingExcerciseActivity?.root)


        setSupportActionBar(bindingExcerciseActivity?.toolbarExcercise)

        if (supportActionBar != null)
        {/* Afficher la fleche pour permettre le retour*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        /* Initialisation de l'exercice par defaut  */
        exerciceList = Constants.defaultExerciceList()

        bindingExcerciseActivity?.toolbarExcercise?.setNavigationOnClickListener()
        {
            onBackPressed()
        }
        /*
        Verification que le timer n'est pas déja en fonctionnement
         */
        setupMainView()


    }

    /**
     * Cette fonction permet de s'assurer que le timer n'est pas deja en marche ex: Après un click sur retour puis à nouveau start
     * Si oui, il est reintialisé
     */
    private fun setupMainView() {
        /* Le Timer précedent est invisible si view.gone la contrainte du text view est perdue app:layout_constraintBottom_toTopOf="@id/flProgressBarTimerForStart" et est mal positionnée */
        bindingExcerciseActivity?.flMainViewTimer?.visibility = View.VISIBLE
        /* Le titre au dessus du timer est configuré invisible*/
        bindingExcerciseActivity?.tvTitle?.visibility = View.VISIBLE
        /* Le timer pour l'excercice est affiché */
        bindingExcerciseActivity?.flExcerciceView?.visibility = View.INVISIBLE
        /*Affichage du nom de l'exercice -  setVisible */
        bindingExcerciseActivity?.tvExerciceName?.visibility = View.INVISIBLE
        /*Affichage de l'image de l'exercice*/
        bindingExcerciseActivity?.ivImage?.visibility = View.INVISIBLE
        if (compteARebour != null)
        {
            compteARebour?.cancel()
            restProgress = 0
        }
        setupTimerMainView()
    }


    private fun setupTimerMainView()
    {
        bindingExcerciseActivity?.progressBar?.progress = restProgress
        /**
         * Création de mon objet restTimer = object : CountDownTimer
         */
        compteARebour = object : CountDownTimer(10000,1000)
        {
            override fun onTick(p0: Long) {
                /*
                restProgress est utilisé pour le décompte
                 */
                restProgress++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBar?.progress = 10 - restProgress
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Here we will start the excercice.", Toast.LENGTH_SHORT).show()
                /* currentExercicePosition etant initialisé à -1. il passe à 0 */
                currentExercicePosition++
                /* Quand le timer de démmarage est terminé, je lance le timer pour l'excercice */
                setupViewExercice()
            }

        }.start()
    }
    /**
     * Affichage des éléments pour la page exercice
     */
    private fun setupViewExercice() {
        /* Le Timer précedent est invisible si view.gone la contrainte du text view est perdue app:layout_constraintBottom_toTopOf="@id/flProgressBarTimerForStart" et est mal positionnée */
        bindingExcerciseActivity?.flMainViewTimer?.visibility = View.INVISIBLE
        /* Le titre au dessus du timer est configuré invisible*/
        bindingExcerciseActivity?.tvTitle?.visibility = View.INVISIBLE
        /* Le timer pour l'excercice est affiché */
        bindingExcerciseActivity?.flExcerciceView?.visibility = View.VISIBLE
        /*Affichage du nom de l'exercice -  setVisible */
        bindingExcerciseActivity?.tvExerciceName?.visibility = View.VISIBLE
        /*Affichage de l'image de l'exercice*/
        bindingExcerciseActivity?.ivImage?.visibility = View.VISIBLE
        /* Cette fonction permet de s'assurer que le timer n'est pas deja en marche */
        if (compteARebourExcercice != null)
        {
            compteARebourExcercice?.cancel()
            timerProgressBarExcercice = 0
        }
        /*setup de l'image à la current exercice position laquelle est incrementée onFinish */
        bindingExcerciseActivity?.ivImage?.setImageResource(exerciceList!![currentExercicePosition].getImage())
        /**/
        bindingExcerciseActivity?.tvExerciceName?.text = exerciceList!![currentExercicePosition].getName()
        setupTimerViewExcercice()
    }

    /**
     * Methode gérant la durée de l'excercice
     */
    private fun setupTimerViewExcercice()
    {
        bindingExcerciseActivity?.progressBar?.progress = restProgress
        /**
         * Création de mon objet restTimer = object : CountDownTimer
         */
        compteARebourExcercice = object : CountDownTimer(30000,1000)
        {
            override fun onTick(p0: Long) {
                /*
                restProgress est utilisé pour le décompte
                 */
                timerProgressBarExcercice++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBarExcerciceTimer?.progress = 30 - timerProgressBarExcercice
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimerExcercice?.text = (30 - timerProgressBarExcercice).toString()
            }

            override fun onFinish() {
                /* Si la fin de la liste des exercices n'est pas atteinte alors retour vers la page start */
                if (currentExercicePosition < exerciceList?.size!! - 1)
                {
                setupMainView()
                }else /* sinon un message de fécilitation*/
                    {
                        Toast.makeText(this@ExerciseActivity,"Bravo, vous avez terminé tous les exercices", Toast.LENGTH_SHORT).show()
                    }



            }

        }.start()
    }


    /* Destroying the timer when closing the activity or app */
    // START
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */
    override fun onDestroy() {
        super.onDestroy()
        if(compteARebour != null)
        {
            compteARebour?.cancel()
            restProgress = 0
        }

        if(compteARebourExcercice != null)
        {
            compteARebourExcercice?.cancel()
            timerProgressBarExcercice = 0
        }


        bindingExcerciseActivity = null
    }

}