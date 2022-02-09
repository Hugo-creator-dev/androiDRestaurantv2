package fr.isen.musoles.androidrestaurantv2.model

import java.io.Serializable

data class UserOrders(val data : List<UserOrder>): Serializable {
    data class UserOrder(
        val id_sender: Int,
        val id_receiver: Int,
        val sender: String,
        val receiver: String,
        val code: String,
        val type_msg: Int,
        val message: String,
        val create_date: String
    ) : Serializable
}