package fr.isen.musoles.androidrestaurantv2.generalStatic

object ConvertClass {
    @JvmStatic
    fun convertIntArrayToPair( list : IntArray) : Pair<Int,Int>?
    {
        return if(list.size == 2) Pair(list[0],list[1]) else null
    }
    @JvmStatic
    fun convertPairToIntArray( pair : Pair<Int,Int>) : IntArray
    {
        return intArrayOf(pair.first,pair.second)
    }
}