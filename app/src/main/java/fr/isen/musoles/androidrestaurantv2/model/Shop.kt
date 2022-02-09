package fr.isen.musoles.androidrestaurantv2.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Shop( var hash : Int,var list : Map<Pair<Int,Int>,Int>) : Serializable
{
    constructor(bigShop: BigShop) :this(
        hash = bigShop.hash,
        list = bigShop.list.associate { Pair(it.access,it.quantity) }
    )
}
