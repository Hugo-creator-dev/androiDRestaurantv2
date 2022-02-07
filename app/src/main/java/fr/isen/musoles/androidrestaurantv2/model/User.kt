package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class User(val id : Int,val id_shop : Int, val code : String, val firstname : String, val lastname : String, val birth_date : String, val email : String, val phone : String, val address : String , val postal_code : String, val town : String, val token : String, val gcmtoken : String, val points : Int, val create_date : String, val update_date : String ): Serializable
