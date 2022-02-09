package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class Items(val name_fr: String, val name_en : String, var items : List<Item>) : Serializable
{
    override fun toString(): String {
        return name_fr + ": " + listOfName()
    }
    private fun listOfName() : String {
        return items.joinToString { it.name_fr }
    }

    override fun equals(other: Any?): Boolean {
        return items.containsAll((other as Items).items) && name_fr == other.name_fr

    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    operator fun get(i: Int): Item {
        return items[i]
    }
}
