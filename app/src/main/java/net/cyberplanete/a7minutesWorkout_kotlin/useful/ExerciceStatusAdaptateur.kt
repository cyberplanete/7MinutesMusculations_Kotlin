package net.cyberplanete.a7minutesWorkout_kotlin.useful

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ItemExerciceStatusBinding

class ExerciceStatusAdaptateur(val items: ArrayList<ExerciceModel>) :
    RecyclerView.Adapter<ExerciceStatusAdaptateur.ViewHolder>() {

    class ViewHolder(binding: ItemExerciceStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /* tvItem de item_exercice_status */
        val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciceStatusBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))
    }
/* Pour chaque item */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val model: ExerciceModel = items[position]
    /* Id de l'excercice assign to holder étant item_exercice_status.xml */
        viewHolder.tvItem.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}