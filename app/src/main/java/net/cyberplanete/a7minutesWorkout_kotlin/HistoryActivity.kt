package net.cyberplanete.a7minutesWorkout_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ActivityHistoryBinding
import net.cyberplanete.a7minutesWorkout_kotlin.adapteurs.HistoryAdapteur
import net.cyberplanete.a7minutesWorkout_kotlin.dao.HistoryDAO
import net.cyberplanete.a7minutesWorkout_kotlin.models.HistoryEntity

class HistoryActivity : AppCompatActivity() {

    private var bindingHistory: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHistory = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(bindingHistory?.root)

        val historyDAO = (application as HistoryApp).historyDatabase.historyDAO()

        lifecycleScope.launch {
            historyDAO.fetchAllHistory().collect {
                val historyList = ArrayList(it)
                setupHistoryListIntoRecyclerView(historyList, historyDAO)
            }
        }


        /* Action BAR*/
        setSupportActionBar(bindingHistory?.toolbarHistory)
        if (supportActionBar != null) {/* Afficher la fleche pour permettre le retour*/
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Historique"
        }

        /* Bouton retour   <-  vers le début de l'exercice  */
        bindingHistory?.toolbarHistory?.setNavigationOnClickListener()
        {
            onBackPressed()
            //  customDialogForBackButton()

        }
        /* END - Action BAR*/


    }

    //Logique pour l'affichage de la liste
    private fun setupHistoryListIntoRecyclerView(
        historyList: ArrayList<HistoryEntity>,
        historyDAO: HistoryDAO
    ) {
        if (historyList.isNotEmpty()) {
            val anHistoryRow = HistoryAdapteur(historyList) { deleteHistoryRow ->
                lifecycleScope.launch {
                    historyDAO.fetchAnHistoryByDate(deleteHistoryRow).collect {
                        if (it != null) {
                            deleteRecordAlertDialog(deleteHistoryRow, historyDAO, it )
                        }
                    }
                }
            }
            bindingHistory?.rvHistoryList?.layoutManager = LinearLayoutManager(this)
            bindingHistory?.rvHistoryList?.adapter = anHistoryRow
            bindingHistory?.rvHistoryList?.visibility = View.VISIBLE
        } else {
            bindingHistory?.rvHistoryList?.visibility = View.GONE
        }
    }

    /*
UI Alert Dialog - deleteRecordAlertDialog
 */
    private fun deleteRecordAlertDialog(
        date: String,
        historiqueDAO: HistoryDAO,
        historyEntity: HistoryEntity
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${historyEntity.date}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        /*
        performing positive action
         */
        builder.setPositiveButton("Yes") { monDialog, _ ->
            lifecycleScope.launch {
                historiqueDAO.delete(HistoryEntity(date))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                monDialog.dismiss() // Dialog will be dismissed
            }
        }
        /*
      END performing positive action
      */

        /*
         performing negative action
        */
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        /*
        *  FINALISATION DE L'ALERTE DIALOG
        * */
        val alertDialogForDeleteRecord: AlertDialog = builder.create()
        alertDialogForDeleteRecord.setCancelable(false)
        alertDialogForDeleteRecord.show()

    }




    private fun customDialogForBackButton() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingHistory = null
    }
}