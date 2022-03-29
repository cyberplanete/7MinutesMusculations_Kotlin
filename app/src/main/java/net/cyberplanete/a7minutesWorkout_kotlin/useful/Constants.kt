package net.cyberplanete.a7minutesWorkout_kotlin.useful

import net.cyberplanete.a7minutesWorkout_kotlin.R

object Constants {
    val exerciceList = ArrayList<ExerciceModel>()
    fun defaultExerciceList(): ArrayList<ExerciceModel> {

        val jumpingJacks = ExerciceModel(
            1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false

        )
        exerciceList.add(jumpingJacks)



        val abdominal_crunch_exercice = ExerciceModel(
            2, "abdominal_crunch", R.drawable.ic_abdominal_crunch, false, false

        )
        exerciceList.add(abdominal_crunch_exercice)
        val knees_running_in_place_exercice = ExerciceModel(
            3,
            "knees running_in_place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false

        )
        exerciceList.add(knees_running_in_place_exercice)

        val lunge_exercice = ExerciceModel(
            4, "lunge_exercice", R.drawable.ic_lunge, false, false

        )
        exerciceList.add(lunge_exercice)

        val plank_exercice = ExerciceModel(
            5, "plank exercice", R.drawable.ic_plank, false, false

        )
        exerciceList.add(plank_exercice)

        val push_up_exercice = ExerciceModel(
            6, "push_up_exercice", R.drawable.ic_push_up, false, false

        )
        exerciceList.add(push_up_exercice)

        val push_up_and_rotation_exercice = ExerciceModel(
            7, "push_up_and_rotation_exercice", R.drawable.ic_push_up_and_rotation, false, false

        )
        exerciceList.add(push_up_and_rotation_exercice)

        val side_plank_exercice = ExerciceModel(
            8, "side_plank_exercice", R.drawable.ic_side_plank, false, false

        )
        exerciceList.add(side_plank_exercice)

        val squat_exercice = ExerciceModel(
            9, "squat_exercice", R.drawable.ic_squat, false, false

        )
        exerciceList.add(squat_exercice)

        val step_up_onto_chair = ExerciceModel(
            10, "step_up_onto_chair_exercice", R.drawable.ic_step_up_onto_chair, false, false

        )
        exerciceList.add(step_up_onto_chair)

        val triceps_dip_on_chair_exercice = ExerciceModel(
            11, "triceps_dip_on_chair_exercice", R.drawable.ic_triceps_dip_on_chair, false, false

        )
        exerciceList.add(triceps_dip_on_chair_exercice)

        val wall_sit_exercice = ExerciceModel(
            12, "wall_sit_exercice", R.drawable.ic_wall_sit, false, false

        )

        exerciceList.add(wall_sit_exercice)

        return exerciceList
    }
}