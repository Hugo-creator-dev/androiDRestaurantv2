package fr.isen.musoles.androidrestaurantv2.enumeration

import fr.isen.musoles.androidrestaurantv2.activity.basicNavigation.CategorieActivity
import fr.isen.musoles.androidrestaurantv2.activity.basicNavigation.DetailActivity
import fr.isen.musoles.androidrestaurantv2.activity.basicNavigation.MenuActivity
import fr.isen.musoles.androidrestaurantv2.activity.order.OrderWaitActivity
import fr.isen.musoles.androidrestaurantv2.activity.order.PastOrderActivity
import fr.isen.musoles.androidrestaurantv2.activity.order.ShopActivity
import fr.isen.musoles.androidrestaurantv2.activity.specialPage.ErrorActivity
import fr.isen.musoles.androidrestaurantv2.activity.specialPage.HomeActivity
import fr.isen.musoles.androidrestaurantv2.activity.user.LoginActivity
import java.io.Serializable

enum class DATATYPE(val forExtra : String, val javaClass : Class<*>) : Serializable {
    DATA("DATA", MenuActivity::class.java),
    ITEM("ITEM", DetailActivity::class.java),
    ITEMS("ITEMS", CategorieActivity::class.java),
    SHOP("SHOP", ShopActivity::class.java),
    LOGIN("LOGIN", LoginActivity::class.java),
    ORDER("ORDER", OrderWaitActivity::class.java),
    DEFAULT("DEFAULT", HomeActivity::class.java),
    ERROR("ERROR", ErrorActivity::class.java),
    PAST_ORDER("PAST_ORDER", PastOrderActivity::class.java),
    NOTHING("",Any::class.java)
}
