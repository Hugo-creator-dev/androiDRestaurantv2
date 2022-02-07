package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class Price(val id : Int, val id_pizza: Int, val price : Double, val create_date : String,val update_date : String,val size: String) : Serializable
{
    override fun toString(): String {
        return price.toString() + "€"
    }

    override fun equals(other: Any?): Boolean {
        return price == (other as Price).price
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
