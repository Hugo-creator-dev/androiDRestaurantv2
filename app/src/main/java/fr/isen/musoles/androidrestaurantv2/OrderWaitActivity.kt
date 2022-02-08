package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityOrderWaitBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class OrderWaitActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userId : Int? = getUserId()
        if(userId != null) {
            super.onCreate(savedInstanceState)
            val shop = getShop()
            Log.i("ACTIVITY", "start OrderWaitActivity")
            ActivityOrderWaitBinding.inflate(layoutInflater).apply {
                setContentView(root)
                Glide.with(this@OrderWaitActivity).load(R.drawable.load).into(imageLoad)
                advertissment.text = "Traitement de la requête en cours"
                val jsonObjectRequest = RequestOnAPI.setRequestOfOrder({ userId }, { Gson().toJson(shop) })
                {
                    if(it != null)
                    {
                        advertissment.text = "Reussie"
                        resetData()
                    }
                    else
                    {
                        advertissment.text = "Echouer"
                    }
                }
                Volley.newRequestQueue(this@OrderWaitActivity).add(jsonObjectRequest)
            }

        }
        else
        {
            startActivity(DATATYPE.ERROR)
            finish()
        }
    }
}