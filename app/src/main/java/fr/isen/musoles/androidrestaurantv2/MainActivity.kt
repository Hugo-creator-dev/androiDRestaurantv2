package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMainBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask

class HomeActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val generalTimer : Timer = Timer(true)
    private lateinit var charged : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ACTIVITY","start HomeActivity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            setToolBar(toolbarMain)
            Glide.with(this@HomeActivity).load(R.drawable.load).into(mainImageLoad)

        startCharged()

        generalTimer.schedule(timerTask {
            this@HomeActivity.runOnUiThread{
                mainTips.text = resources.getStringArray(R.array.main_tips).random()
            }
        },5000,5000)

        val jsonObjectRequest = RequestOnAPI.setRequestOfData {
            if(it != null)
            {
                mainData = it
                stopCharged(true)
            }
            else
            {
                stopCharged(false)
            }
        }
        Volley.newRequestQueue(this@HomeActivity).add(jsonObjectRequest)

        /* Set all click listener */
            seeMenu.setOnClickListener {
                startActivity(DATATYPE.DATA)
            }
            connect.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(0))
            }
            inscript.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(1))
            }
            retryCharge.setOnClickListener {
                startCharged()
                Volley.newRequestQueue(this@HomeActivity).add(jsonObjectRequest)
            }
            keepOrder.setOnClickListener {
                startActivity(DATATYPE.PAST_ORDER)
            }
            deco.setOnClickListener {
                setNotConnected()
                deco.visibility = View.GONE
                keepOrder.visibility = View.GONE
                inscript.visibility = View.VISIBLE
                connect.visibility = View.VISIBLE
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
                if(isConnected())
                {
                    deco.visibility = View.VISIBLE
                    keepOrder.visibility = View.VISIBLE
                }
                else {
                    inscript.visibility = View.VISIBLE
                    connect.visibility = View.VISIBLE
                }
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
            keepOrder.visibility = View.GONE
            seeMenu.visibility = View.GONE
            deco.visibility = View.GONE
        }
    }

    override fun finish() {
        charged.apply { cancel(); purge() }
        generalTimer.apply { cancel(); purge() }
        super.finish()
    }

    override fun onResume() {

        binding.apply {
            if (isConnected()) {
                deco.visibility = View.VISIBLE
                keepOrder.visibility = View.VISIBLE
                inscript.visibility = View.GONE
                connect.visibility = View.GONE
            } else {
                inscript.visibility = View.VISIBLE
                connect.visibility = View.VISIBLE
                deco.visibility = View.GONE
                keepOrder.visibility = View.GONE
            }
        }
        super.onResume()
    }
}