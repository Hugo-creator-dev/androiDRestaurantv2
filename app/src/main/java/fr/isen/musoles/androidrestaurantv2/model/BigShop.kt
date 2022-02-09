package fr.isen.musoles.androidrestaurantv2.model

import android.util.Log
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class BigShop(val hash : Int,val date: String , val totalPrice : Double , val quantity: Int, val list : List<ShopItem>) : Serializable
{
    constructor(shop: Shop,data: Data) : this (
        shop.hash,
        Date().toString(),
        shop.list.entries.sumOf { data[it.key].getPrice() * it.value },
        shop.list.values.sum(),
        shop.list.map { ShopItem(it.key,data[it.key],it.value) }
    )

    constructor() : this(
        0,
        "",
        0.0,
        0,
        ArrayList()
    )

    data class ShopItem(var access : Pair<Int,Int>, var item : Item, var quantity : Int) : Serializable
}
