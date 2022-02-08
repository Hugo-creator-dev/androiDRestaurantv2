package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import com.android.volley.toolbox.Volley
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityPastOrderBinding
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class PastOrderActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = getUserId()
        if(userId != null)
        ActivityPastOrderBinding.inflate(layoutInflater).apply {
            setContentView(root)
            val jsonObjectRequest = RequestOnAPI.setRequestOfPastOrder({userId}){
                textView2.text = it.toString()
            }
            Volley.newRequestQueue(this@PastOrderActivity).add(jsonObjectRequest)
        }
    }
}