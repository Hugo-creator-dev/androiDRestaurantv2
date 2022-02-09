package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityOrderWaitBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.BigShop
import fr.isen.musoles.androidrestaurantv2.model.Shop
import java.util.*
import kotlin.concurrent.timerTask

class OrderWaitActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userId : Int? = getUserId()
        if(userId != null) {
            super.onCreate(savedInstanceState)
            val shop = getShop()
            Log.i("ACTIVITY", "start OrderWaitActivity")
            ActivityOrderWaitBinding.inflate(layoutInflater).apply {
                setContentView(root)
                button3.setOnClickListener {
                    finish()
                    startActivity(DATATYPE.PAST_ORDER)
                }
                button2.setOnClickListener {
                    finish()
                    startActivity(DATATYPE.DATA)
                }
                Glide.with(this@OrderWaitActivity).load(R.drawable.load).into(imageLoad)
                advertissment.setText(R.string.wait_command)
                var charged = Timer(true)
                var i = 0
                charged.schedule(timerTask {
                    this@OrderWaitActivity.runOnUiThread {
                        i = (i+1)%3
                        val str : String = resources.getString(R.string.wait_command) + "...".subSequence(0,i)
                        advertissment.text = str
                    }
                },1000,1000)
                val jsonObjectRequest = RequestOnAPI.setRequestOfOrder({ userId }, { Gson().toJson(BigShop(
                    Shop(getHash(),shop), mainData)) })
                {
                    charged.purge()
                    charged.cancel()
                    if(it != null)
                    {
                        advertissment.setText(R.string.validation_command)
                        resetData()
                    }
                    else
                    {
                        advertissment.setText(R.string.error_text_message)
                    }
                    button2.visibility = View.VISIBLE
                    button3.visibility = View.VISIBLE
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