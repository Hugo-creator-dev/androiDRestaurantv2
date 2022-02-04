package fr.isen.musoles.androidrestaurantv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMainBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Data
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

val MENUTRANSFER : String = "mntrsf"
class HomeActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var timer : Timer = Timer()
    private val lsv : List<Int> = ArrayList<Int>().apply { add(R.string.main_tips_1);add(R.string.main_tips_2) }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.load).into(binding.mainImageLoad)

        timer.schedule(timerTask {
            this@HomeActivity.runOnUiThread(java.lang.Runnable {
                binding.mainTips.setText(lsv.random())
            })
        },5000,2000)

        timer.schedule(timerTask {
            this@HomeActivity.runOnUiThread(java.lang.Runnable {
                binding.mainInformation.setText(R.string.main_information_msg_long)
            })
        },5000)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/menu",
            JSONObject("{\"id_shop\":\"1\"}"),
            { response ->
                Log.d("Request",response.toString())
                val responseObj = Gson().fromJson(response.toString(), Data::class.java)
                binding.mainInformation.setText(R.string.main_information_msg_end)
                val intent = Intent(this, MenuActivity::class.java).apply {
                    putExtra(MENUTRANSFER, responseObj)
                }
                timer.schedule(timerTask {
                    startActivity(intent)
                    finish()
                },1000)
            },
            { error ->
                error.printStackTrace()
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    override fun finish() {
        timer.cancel()
        timer.purge()
        super.finish()
    }
}