package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class Data(val data : List<Items>) : Serializable
{
    override fun toString(): String {
        return "We found :" + data.joinToString { items: Items -> items.name_fr + " with " + items.items.size + " sous item" }
    }

    override fun equals(other: Any?): Boolean {
        if (other != null) {
            if (this.javaClass == other.javaClass)
            {
                return data.containsAll((other as Data).data)
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    operator fun get(position: Int): Items {
        return data[position]
    }

    operator fun get(position: IntArray): Item {
        return data[position[0]][position[1]]
    }

    operator fun get(position: Pair<Int,Int>): Item {
        return data[position.first][position.second]
    }

    operator fun get(position: List<Int>): Item {
        return data[position[0]][position[1]]
    }
}
