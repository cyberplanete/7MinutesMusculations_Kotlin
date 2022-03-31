package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityExerciseBinding
import net.cyberplanete.a7minutesWorkout_kotlin.useful.Constants
import net.cyberplanete.a7minutesWorkout_kotlin.useful.ExerciceModel
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() ,  TextToSpeech.OnInitListener  {

    private var bindingExcerciseActivity : ActivityExerciseBinding? =  null
    private var compteARebour: CountDownTimer? = null
    private var restProgress = 0

    private var compteARebourExcercice: CountDownTimer? = null
    private var timerProgressBarExcercice = 0

    private var exerciceList: ArrayList<ExerciceModel>? = null
    private var currentExercicePosition = -1

    private var tts: TextToSpeech? = null // Variable for TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)

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
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set FR Français as language for tts
            val result = tts!!.setLanguage(Locale.FRANCE)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
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

        if(currentExercicePosition + 1 <= exerciceList!!.size )
        {
            var nextExerviceName =exerciceList!![currentExercicePosition+1].getName()
            bindingExcerciseActivity?.tvNextExerciceName?.text = nextExerviceName
            /*Le titre du prochain exercice est rendu visible */
            bindingExcerciseActivity?.tvNextExerciceName?.visibility = View.VISIBLE
            bindingExcerciseActivity?.tvTitleNextExerciceName?.visibility = View.VISIBLE
            speakOut(nextExerviceName)
        }else
        {
            bindingExcerciseActivity?.tvNextExerciceName?.text = "Exercices terminées"
            bindingExcerciseActivity?.tvTitleNextExerciceName?.visibility = View.VISIBLE
        }

        /**
         * Création de mon objet restTimer = object : CountDownTimer
         */
        compteARebour = object : CountDownTimer(10000,1000)
        {
            /* TIMER */
            override fun onTick(p0: Long) {
                /*
                restProgress est utilisé pour le décompte
                 */
                restProgress++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBar?.progress = 10 - restProgress
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimerSecondNextExercice?.text = (10 - restProgress).toString()

            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Here we will start the excercice.", Toast.LENGTH_SHORT).show()
                /* currentExercicePosition etant initialisé à -1. il passe à 0 */
                currentExercicePosition++
                /*Le titre du prochain exercice est rendu invisible */
                bindingExcerciseActivity?.tvNextExerciceName?.visibility = View.INVISIBLE
                bindingExcerciseActivity?.tvTitleNextExerciceName?.visibility = View.INVISIBLE
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
                bindingExcerciseActivity?.tvTimerSecondExcercice?.text = (30 - timerProgressBarExcercice).toString()
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

        if(tts != null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        bindingExcerciseActivity = null
    }



}