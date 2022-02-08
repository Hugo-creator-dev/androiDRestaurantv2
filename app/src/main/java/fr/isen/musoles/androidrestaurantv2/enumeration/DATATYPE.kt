package fr.isen.musoles.androidrestaurantv2.enumeration

import fr.isen.musoles.androidrestaurantv2.*
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityPastOrderBinding
import java.io.Serializable

enum class DATATYPE(val forExtra : String, val javaClass : Class<*>) : Serializable {
    DATA("DATA", MenuActivity::class.java),
    ITEM("ITEM", DetailActivity::class.java),
    ITEMS("ITEMS",CategorieActivity::class.java),
    SHOP("SHOP",ShopActivity::class.java),
    LOGIN("LOGIN",LoginActivity::class.java),
    ORDER("ORDER",OrderWaitActivity::class.java),
    DEFAULT("DEFAULT",HomeActivity::class.java),
    ERROR("ERROR",HomeActivity::class.java),
    PAST_ORDER("PAST_ORDER",PastOrderActivity::class.java),
    NOTHING("",Any::class.java)
}
