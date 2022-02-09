package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import fr.isen.musoles.androidrestaurantv2.adaptater.OrderAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityPastOrderBinding
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.BigShop

class PastOrderActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = getUserId()
        if(userId != null)
        ActivityPastOrderBinding.inflate(layoutInflater).apply {
            setContentView(root)
            val jsonObjectRequest = RequestOnAPI.setRequestOfPastOrder({userId}){
                if(it == null)
                {
                    //Nothing...
                }
                else
                {
                    recyclerViewOfOrder.apply {
                        layoutManager = LinearLayoutManager(this@PastOrderActivity)
                        adapter = OrderAdaptater(it.data.map {
                            Log.i("truc",it.message)
                            try {
                                Gson().getAdapter(BigShop(0,"",0.0,0,ArrayList())::class.java).fromJson(it.message)
                            }
                            catch (e : JsonSyntaxException)
                            {
                                BigShop()
                            }
                        }) {
                            Log.i("DATA",it.list.toString())
                            if(updateShop(it))
                            {
                                startShopActivity()
                            }
                            else
                            {
                                Toast.makeText(this@PastOrderActivity, "La commande ne correspond plus au menu actuel", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            Volley.newRequestQueue(this@PastOrderActivity).add(jsonObjectRequest)
        }
    }
}