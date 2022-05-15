package net.cyberplanete.a7minutesWorkout_kotlin.adapteurs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.cyberplanete.a7minutesWorkout_kotlin.R
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ItemHistoryRowBinding
import net.cyberplanete.a7minutesWorkout_kotlin.models.HistoryEntity

class HistoryAdapteur(
    private val historyOfExerciceList: ArrayList<HistoryEntity>,
   // private val updateListener: (id: String) -> Unit,
    private val deleteListener: (id: String) -> Unit

) : RecyclerView.Adapter<HistoryAdapteur.ViewConteneur>() {
    class ViewConteneur(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {

        val main = binding.llMain
        val historique = binding.tvHistoriquel
       // val edit = binding.ivEdit
        val delete = binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewConteneur {

        return ViewConteneur(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
// Contient mon item_History_row.xml
    override fun onBindViewHolder(holder: ViewConteneur, position: Int) {
        val context = holder.itemView.context
        val anHistoryExercice = historyOfExerciceList[position]

        holder.historique.text = anHistoryExercice.date


        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.main.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.lightGray
                )
            )
        } else {
            holder.main.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        //start
       // holder.edit.setOnClickListener {
         //   updateListener.invoke(anHistoryExercice.date)//invoke updateID de MainActivity ligne 66
        //}
       holder.delete.setOnClickListener {
            deleteListener.invoke(anHistoryExercice.date)//invoke updateID de MainActivity ligne 65
        }
    }

    override fun getItemCount(): Int {
        return historyOfExerciceList.size
    }

}