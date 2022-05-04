package net.cyberplanete.a7minutesWorkout_kotlin.useful

import net.cyberplanete.a7minutesWorkout_kotlin.models.IMC

class IMCBrain() {

    private var imc : IMC? = null

    private val listIMC: List<IMC> = listOf<IMC>(
        IMC(
            16.5F,
            0F,
            "Dénutrition",
            "Surveillez votre corpulence, cet IMC est très faible "
        ),
        IMC(
            18.5F,
            16.5F,
            "Maigreur ",
            "Surveillez votre corpulence, cet IMC est peu élevé. "
        ),
        IMC(
            25F,
            18.5F,
            "Corpulence Normale ",

            "Votre corpulence est dans la norme, entre 18.5 et 25, c'est très bien "
        ),
        IMC(
            30F,
            25F,
            "Surpoids ",

            "Votre IMC est compris entre 25 et 30. Cela signifie que vous êtes en surpoids.  "
        ),
        IMC(
            35F,
            30F,
            "Obésité modérée (Classe 1) ",

            "Votre IMC est compris entre 30 et 35. Vous êtes donc dans la première moitié de la fourchette dIMC correspondant à une obésité de classe 1 dite modérée.  "
        ),
        IMC(
            40F,
            35F,
            "Obésité sévère (Classe 2) ",

            "Votre IMC est compris entre 35 et 40. Vous êtes donc dans la deuxième moitié de la fourchette dIMC correspondant à une obésité de classe 2 dite sévère.  "
        ),
        IMC(
            100F,
            40F,
            "Obésite morbide ou massive ",

            "Votre IMC est supérieur à 40, cest le stade de l'obésité morbide (Classe 3). Vous devriez essayez de perdre du poids en évitant les régimes privatifs (néfastes pour la santé).  "
        ),
    )

    fun listIMC(): List<IMC> {
        return listIMC
    }

//TODO Prendre en compte age lors du calcul et homme ou femme
    fun getIMCObject(indiceIMC : Float) : IMC? {
    //var indiceIMC = (poids / pow((taille / 100).toDouble(), 2.0))
        for(element in listIMC) {
            if (indiceIMC >= element.minIMC && indiceIMC < element.maxIMC) {
                imc = element;
            }
        }
        return imc;
    }

}







