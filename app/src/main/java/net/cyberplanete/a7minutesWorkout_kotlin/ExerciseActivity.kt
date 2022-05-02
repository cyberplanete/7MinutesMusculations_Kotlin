package net.cyberplanete.a7minutesWorkout_kotlin

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityExerciseBinding
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.DialogCustomBackConfirmationBinding
import net.cyberplanete.a7minutesWorkout_kotlin.useful.Constants
import net.cyberplanete.a7minutesWorkout_kotlin.useful.ExerciceModel
import net.cyberplanete.a7minutesWorkout_kotlin.useful.ExerciceStatusAdaptateur
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var bindingExcerciseActivity: ActivityExerciseBinding? = null
    private var compteARebour: CountDownTimer? = null
    private var restProgress = 0

    private var compteARebourExcercice: CountDownTimer? = null
    private var timerProgressBarExcercice = 0

    private var exerciceList: ArrayList<ExerciceModel>? = null
    private var currentExercicePosition = -1

    private var tts: TextToSpeech? = null // Variable for TextToSpeech
    private var mediaPlayer: MediaPlayer? = null

    private var exerciceStatusAdaptateur: ExerciceStatusAdaptateur? = null

    private var exerciseTimerDuration: Long = 30
    private var restTimerDuration: Long = 10
    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)

        super.onCreate(savedInstanceState)
        bindingExcerciseActivity = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(bindingExcerciseActivity?.root)


        setSupportActionBar(bindingExcerciseActivity?.toolbarExcercise)

        if (supportActionBar != null) {/* Afficher la fleche pour permettre le retour*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        /* Initialisation de l'exercice par defaut  */
        exerciceList = Constants.defaultExerciceList()

        /* Bouton retour   <-  vers le début de l'exercice  */
        bindingExcerciseActivity?.toolbarExcercise?.setNavigationOnClickListener()
        {
            //onBackPressed()
            customDialogForBackButton()

        }
        /*
        Verification que le timer n'est pas déja en fonctionnement
         */
        setupRestView()

        setupExerciceStatusRecyclerView()

    }


    /**
     * Function is used to set up the recycler view to UI.
     * Binding adapter class to recycler view and setting the recycler view layout manager and passing a list to the adapter
     */
    private fun setupExerciceStatusRecyclerView() {
        /* Defining a layout manager for the recycle view */
        /* Here we have used a LinearLayout Manager with horizontal scroll. */
        bindingExcerciseActivity?.rvExerciceStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        /* As the adapter expects the exercises list and context so initialize it passing it. */
        exerciceStatusAdaptateur = ExerciceStatusAdaptateur(exerciceList!!)
        /* Adapter class is attached to recycler view */
        bindingExcerciseActivity?.rvExerciceStatus?.adapter = exerciceStatusAdaptateur
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

    //Setting up the Get Ready View with 10 seconds of timer
    //START
    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView() {

        /* LECTURE D'UNE MUSIQUE LORS DU DEPART DE CHAQUE EXERCICE*/
        try {
            val soundURI =
                Uri.parse("android.resource://net.cyberplanete.a7minutesWorkout_kotlin/" + R.raw.press_start)
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            /* is looping = false afin de ne pas permettre une boucle infinie du fichier audio*/
            mediaPlayer?.isLooping = false
            /* Lecture du fichier son*/
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        /* Le Timer précedent est invisible si view.gone la contrainte du text view est perdue app:layout_constraintBottom_toTopOf="@id/flProgressBarTimerForStart" et est mal positionnée */
        bindingExcerciseActivity?.flMainViewTimer?.visibility = View.VISIBLE
        /* Le titre au dessus du timer est configuré invisible*/
        bindingExcerciseActivity?.tvTitle?.visibility = View.VISIBLE
        /* Affichage du prochain exercice */
        bindingExcerciseActivity?.tvTitleNextExerciceName?.visibility = View.VISIBLE
        /* Le timer pour l'excercice est affiché */
        bindingExcerciseActivity?.flExcerciceView?.visibility = View.INVISIBLE
        /*Affichage du nom de l'exercice -  setVisible */
        bindingExcerciseActivity?.tvExerciceName?.visibility = View.INVISIBLE
        /*Affichage de l'image de l'exercice*/
        bindingExcerciseActivity?.ivImage?.visibility = View.INVISIBLE
        if (compteARebour != null) {
            compteARebour?.cancel()
            restProgress = 0
        }
        // Setting the upcoming exercise name in the UI element
        // START
        // Here we have set the upcoming exercise name to the text view
        // Here as the current position is -1 by default so to selected from the list it should be 0 so we have increased it by +1.
        bindingExcerciseActivity?.tvTitleNextExerciceName?.text =
            exerciceList!![currentExercicePosition + 1].getName()
        // This function is used to set the progress details.
        setupTimerRestView()
    }
    //END

    private fun setupTimerRestView() {

        bindingExcerciseActivity?.progressBar?.progress =
            restProgress // Sets the current progress to the specified value.

        /**
         * Création de mon objet restTimer = object : CountDownTimer
         */
        compteARebour = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            /* TIMER */
            override fun onTick(p0: Long) {
                /*
                restProgress est utilisé pour le décompte
                 */
                restProgress++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBar?.progress = 10 - restProgress
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimerSecondNextExercice?.text =
                    (10 - restProgress).toString()

            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here we will start the excercice.",
                    Toast.LENGTH_SHORT
                ).show()
                /* currentExercicePosition etant initialisé à -1. il passe à 0 */
                currentExercicePosition++
                /*Le titre du prochain exercice est rendu invisible */
                bindingExcerciseActivity?.tvNextExerciceName?.visibility = View.INVISIBLE
                bindingExcerciseActivity?.tvTitleNextExerciceName?.visibility = View.INVISIBLE

                /* Represente une partie de la logique permettant d'afficher le petit cercle d'une couleur differente de l'exercice en cours dans la vue de l'exercice setExerciceView() */
                exerciceList!![currentExercicePosition].setIsSelected(true)
                exerciceStatusAdaptateur!!.notifyDataSetChanged() // Notification permettant de relancer les methodes de l'adaptateur -- Si  cette ligne est absente!! La couleur de l'exercice en cours ne fonctionne pas  !!

                /* Quand le timer de démmarage est terminé, je lance le timer pour l'excercice */
                setExerciceView()
            }


        }.start()
    }


    // Setting up the Exercise View with a 30 seconds timer
    // START
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View.
     */
    private fun setExerciceView() {

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


        /**
         * Here firstly we will check if the timer is running and it is not null then cancel the running timer and start the new one.
         * And set the progress to the initial value which is 0.
         */
        if (compteARebourExcercice != null) {
            compteARebourExcercice?.cancel()
            timerProgressBarExcercice = 0
        }
        var nextExerviceName = exerciceList!![currentExercicePosition].getName()
        speakOut(nextExerviceName)
        /*setup de l'image à la current exercice position laquelle est incrementée onFinish */
        bindingExcerciseActivity?.ivImage?.setImageResource(exerciceList!![currentExercicePosition].getImage())
        /**/
        bindingExcerciseActivity?.tvExerciceName?.text =
            exerciceList!![currentExercicePosition].getName()
        setTimerExcerciceView()
    }
    // END


    // After REST View Setting up the 30 seconds timer for the Exercise view and updating it continuously
    // START
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View for 30 Seconds
     */
    private fun setTimerExcerciceView() {
        bindingExcerciseActivity?.progressBar?.progress = restProgress
        /**
         * Création de mon objet restTimer = object : CountDownTimer
         */
        compteARebourExcercice = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                /*
                restProgress est utilisé pour le décompte
                 */
                timerProgressBarExcercice++
                // Décompte pour la progressBar
                bindingExcerciseActivity?.progressBarExcerciceTimer?.progress =
                    exerciseTimerDuration.toInt() - timerProgressBarExcercice
                //Decompte textuelle 10 . 9 . 8. 7 .6 ...
                bindingExcerciseActivity?.tvTimerSecondExcercice?.text =
                    (exerciseTimerDuration.toInt() - timerProgressBarExcercice).toString()
            }

            override fun onFinish() {


                // Updating the view after completing the 30 seconds exercise
                // START
                // Si la fin de la liste des exercices n'est pas atteinte
                if (currentExercicePosition < exerciceList?.size!! - 1) {
                    /* Logique permettant d'afficher la progression dans une liste d'excercice représentée par des cercles de couleur */
                    exerciceList!![currentExercicePosition].setIsSelected(false) // Pas Modification de la couleur du cercle pour afficher la progression quand un exercice est en cours
                    exerciceList!![currentExercicePosition].setIsCompleted(true) // Modification de la couleur du cercle pour afficher la progression quand un exercice est terminée
                    exerciceStatusAdaptateur!!.notifyDataSetChanged() // Notification permettant de relancer les methodes de l'adaptateur -- Si  cette ligne est absente!! Il n'y a pas de modification des couleurs des cercles de l'exercice !!
                    /* Retour vers une configuration de la page timer repos 10 secondes  */
                    setupRestView()
                } else /* sinon un message de fécilitation*/ {
                    /* Fermeture de la page exercice*/
                    finish()
                    /* La page FinishActivity est affichée*/
                    val intent = Intent(
                        this@ExerciseActivity,
                        FinishActivity::class.java
                    ) // intent est dans l'object "compteARebourExcercice = object". Il est par conséquent obligatoire d'être plus précis en ajoutant:  this@ExerciseActivity
                    startActivity(intent)
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Bravo, vous avez terminé tous les exercices",
                        Toast.LENGTH_SHORT

                    ).show()
                }

            }

        }.start()
    }
    // ********************************************* Gestion des boutons retour android *************************************
    /* Gestion du bouton retour du menu android */
    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    /* Gestion da la fleche retour du haut par un Custom Dialog which is used to ask user if he is sure to go back to the start */
    private fun customDialogForBackButton() {
        /* Je cree un dialog */
        val customDialog = Dialog(this)
        /* Binding sur le fichier xml dialog_custom_back_confirmation pour pouvoir écouter si il y a pression sur le bouton oui ou non*/
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        /* Affichage de la page */
        customDialog.setContentView(dialogBinding.root)
        /* Ne pas permettre l'annulation lorsque que l'utilisateur click en dehors du dialog de confirmation */
        customDialog.setCanceledOnTouchOutside(false)
        /* Bouton oui*/
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish() //Si oui - Fermeture de l'activité Exercice
            customDialog.dismiss()// Fermeture du customDialog
        }
        /* Bouton non */
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()//Fermeture du customDialog

        }
        customDialog.show()
    }
    // ********************************************* END -- Gestion des boutons retour *************************************

    /* Destroying the timer when closing the activity or app */
    // START
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (compteARebour != null) {
            compteARebour?.cancel()
            restProgress = 0
        }

        if (compteARebourExcercice != null) {
            compteARebourExcercice?.cancel()
            timerProgressBarExcercice = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (mediaPlayer != null) {
            mediaPlayer!!.stop()

        }
        bindingExcerciseActivity = null
    }


}