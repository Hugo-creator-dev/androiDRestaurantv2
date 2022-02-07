package fr.isen.musoles.androidrestaurantv2.generalStatic

object PersonalString {
    @JvmStatic
    fun reduceString(text : String, max : Int, replace : String) : String
    {
        return if(text.length > max) text.replaceRange( max - replace.length,text.length,replace) else text
    }
}