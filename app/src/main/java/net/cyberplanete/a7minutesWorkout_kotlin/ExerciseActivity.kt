package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var bindingExcerciseActivity : ActivityExerciseBinding? =  null
    private var compteARebour: CountDownTimer? = null
    private var restProgress = 0

    private var compteARebourExcercice: CountDownTimer? = null
    private var restTimerProgressBarExcercice = 0

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
        /*
        Verification que le timer n'est pas déja en fonctionnement
         */
        setupRestView()


    }

    /**
     * Cette fonction permet de s'assurer que le timer n'est pas deja en marche ex: Après un click sur retour puis à nouveau start
     * Si oui, il est reintialisé
     */
    private fun setupRestView() {
        if (compteARebour != null)
        {
            compteARebour?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }


    private fun setupRestViewExercice() {
        /* Le Timer précedent est invisible si view.gone la contrainte du text view est perdue app:layout_constraintBottom_toTopOf="@id/flProgressBarTimerForStart" et est mal positionnée */
        bindingExcerciseActivity?.flProgressBarTimerForStart?.visibility = View.INVISIBLE
        /* Le titre au dessus du timer est modifié*/
        bindingExcerciseActivity?.tvTitle?.text = "Nom de l'exercice"
        /* Le timer pour l'excercice est affiché */
        bindingExcerciseActivity?.flProgressBarTimerForExcercice?.visibility = View.VISIBLE
        /* Cette fonction permet de s'assurer que le timer n'est pas deja en marche */
        if (compteARebourExcercice != null)
        {
            compteARebourExcercice?.cancel()
            restTimerProgressBarExcercice = 0
        }
        setRestTimerProgressBarExcercice()
    }

    private fun setRestProgressBar()
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
                /* Quand le timer de démmarage est terminé, je lance le timer pour l'excercice */
                setupRestViewExercice()
            }

        }.start()
    }

    /**
     * Methode gérant la durée de l'excercice
     */
    private fun setRestTimerProgressBarExcercice()
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
                restTimerProgressBarExcercice++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBarExcerciceTimer?.progress = 30 - restTimerProgressBarExcercice
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimerExcercice?.text = (30 - restTimerProgressBarExcercice).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"30 secondes are over", Toast.LENGTH_SHORT).show()

            }

        }.start()
    }



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
            restTimerProgressBarExcercice = 0
        }


        bindingExcerciseActivity = null
    }

}