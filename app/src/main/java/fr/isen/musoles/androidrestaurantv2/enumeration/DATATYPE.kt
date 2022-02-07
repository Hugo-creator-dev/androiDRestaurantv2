package fr.isen.musoles.androidrestaurantv2.enumeration

import fr.isen.musoles.androidrestaurantv2.*
import java.io.Serializable

enum class DATATYPE(val forExtra : String, val javaClass : Class<*>) : Serializable {
    DATA("DATA", MenuActivity::class.java),
    ITEM("ITEM", DetailActivity::class.java),
    ITEMS("ITEMS",CategorieActivity::class.java),
    SHOP("SHOP",ShopActivity::class.java),
    LOGIN("LOGIN",LoginActivity::class.java),
    DEFAULT("DEFAULT",HomeActivity::class.java),
    ERROR("ERROR",HomeActivity::class.java),
    NOTHING("",Any::class.java)
}