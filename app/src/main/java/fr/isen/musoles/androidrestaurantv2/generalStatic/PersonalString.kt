package fr.isen.musoles.androidrestaurantv2.generalStatic

import android.util.Patterns

object PersonalString {
    @JvmStatic
    fun reduceString(text : String, max : Int, replace : String) : String
    {
        return if(text.length > max) text.replaceRange( max - replace.length,text.length,replace) else text
    }
    fun isCorrectEmail(email : String?) : Boolean
    {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).find()
    }
    fun isCorrectPassword(pass : String?) : Boolean
    {
        return pass.run { this != null && isNotEmpty() && isNotBlank() && length > 8 }
    }
    fun isCorrectName(name : String?) : Boolean
    {
        return name.run { this != null && isNotEmpty() && isNotBlank() && length > 1 }
    }
    fun isCorrectAddress(address : String?) : Boolean
    {
        return address.run { this != null && isNotEmpty() && isNotBlank() && length > 1 }
    }
}