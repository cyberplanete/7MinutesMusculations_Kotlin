package net.cyberplanete.a7minutesWorkout_kotlin.adapteurs

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.cyberplanete.a7minutesWorkout_kotlin.R
import net.cyberplanete.a7minutesWorkout_kotlin.databinding.ItemExerciceStatusBinding
import net.cyberplanete.a7minutesWorkout_kotlin.models.ExerciceModel

class ExerciceStatusAdaptateur(val items: ArrayList<ExerciceModel>) :
    RecyclerView.Adapter<ExerciceStatusAdaptateur.ViewHolder>() {

    class ViewHolder(binding: ItemExerciceStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /* tvItem de item_exercice_status */
        val tvItem = binding.tvItem


    }

    /**
     * Inflates the item view which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciceStatusBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val model: ExerciceModel = items[position]
        /* Id de l'excercice assigned to holder item_exercice_status.xml */
        viewHolder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                /* Le petit cercle de progression de l'exercice en cours est affiché avec des parametres d'affichage differents */
                /* Le numero de l'exercice en cour est affiché en blanc et bordure verte */
                viewHolder.tvItem.background = ContextCompat.getDrawable(viewHolder.itemView.context,
                    R.drawable.item_circular_color_thin_color_accent_border)
                viewHolder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted() -> {
                /* Le petit cercle de progression de l'exercice en cours est affiché avec des parametres d'affichage differents */
                /* Le numero de l'exercice terminée est affiché en vert */
                viewHolder.tvItem.background = ContextCompat.getDrawable(viewHolder.itemView.context,
                    R.drawable.item_circular_color_accent_background)
                viewHolder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else  ->   {
                /* Le petit cercle de progression de l'exercice en cours est affiché avec des parametres d'affichage differents */
                viewHolder.tvItem.background = ContextCompat.getDrawable(viewHolder.itemView.context,
                    R.drawable.item_circular_color_gray_background)
                viewHolder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }



    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

}