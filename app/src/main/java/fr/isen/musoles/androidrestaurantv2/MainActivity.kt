package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMainBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Data
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class HomeActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val generalTimer : Timer = Timer(true)
    private lateinit var charged : Timer
    private val lsv : List<Int> = ArrayList<Int>().apply { add(R.string.main_tips_1);add(R.string.main_tips_2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            setToolBar(toolbarMain)
            Glide.with(this@HomeActivity).load(R.drawable.load).into(mainImageLoad)
        }
        startCharged()

        generalTimer.schedule(timerTask {
            this@HomeActivity.runOnUiThread{
                binding.mainTips.setText(lsv.random())
            }
        },5000,5000)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/menu",
            JSONObject("{\"id_shop\":\"1\"}"),
            {
                Log.d("Request",it.toString())
                mainData = Gson().fromJson(it.toString(), Data::class.java)
                stopCharged(true)
            },
            { error ->
                error.printStackTrace()
                stopCharged(false)
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

        /* Set all click listener */
        binding.apply {
            seeMenu.setOnClickListener {
                startActivity(DATATYPE.DATA)
            }
            connect.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(0),DATATYPE.DATA)
            }
            inscript.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(1),DATATYPE.DATA)
            }
            retryCharge.setOnClickListener {
                startCharged()
                Volley.newRequestQueue(this@HomeActivity).add(jsonObjectRequest)
            }
        }
    }

    private fun stopCharged(check : Boolean)
    {
        charged.apply { cancel(); purge() }
        binding.mainImageLoad.visibility = View.INVISIBLE
        binding.apply {
            if (check) {
                mainInformation.setText(R.string.main_information_msg_end)
                seeMenu.visibility = View.VISIBLE
                inscript.visibility = View.VISIBLE
                connect.visibility = View.VISIBLE
            } else {
                mainInformation.setText(R.string.main_information_msg_error)
                retryCharge.visibility = View.VISIBLE
            }
        }
    }

    private fun startCharged()
    {
        charged = Timer(true)
        charged.schedule(timerTask {
            this@HomeActivity.runOnUiThread {
                binding.mainInformation.setText(R.string.main_information_msg_long)
            }
        },5000)

        binding.apply {
            mainImageLoad.visibility = View.VISIBLE
            connect.visibility = View.GONE
            inscript.visibility = View.GONE
            retryCharge.visibility = View.GONE
            seeMenu.visibility = View.GONE
        }
    }

    override fun finish() {
        charged.apply { cancel(); purge() }
        generalTimer.apply { cancel(); purge() }
        super.finish()
    }
}