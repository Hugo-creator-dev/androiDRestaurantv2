package fr.isen.musoles.androidrestaurantv2.model

import android.util.Log
import java.io.Serializable

data class FuckingShopConversion(var hash : Int, var list : Map<String,Int>) : Serializable
{
    fun convertToShop() : Shop
    {
         return Shop(hash,list.mapKeys {
            Log.d("yo",it.key)
            val ok = it.key.replace(" ","").replace("(","").replace(")","") .split(",")
            Pair(ok[0].toInt(),ok[1].toInt())
        })
    }
}
