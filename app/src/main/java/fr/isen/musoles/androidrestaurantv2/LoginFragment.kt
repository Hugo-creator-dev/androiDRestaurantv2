package fr.isen.musoles.androidrestaurantv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityLoginBinding
import fr.isen.musoles.androidrestaurantv2.databinding.FragmentLoginBinding
import fr.isen.musoles.androidrestaurantv2.model.Data
import org.json.JSONObject
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timerTask

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ch1.setOnClickListener {
            (activity as? LoginActivity)?.loginToSignup()
        }

        binding.CoCo.setOnClickListener {

            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailCo.text).find())
                Toast.makeText(context,"Bad email",Toast.LENGTH_LONG)
            else if (binding.emailCo.text.length < 9)
                Toast.makeText(context,"Bad password",Toast.LENGTH_LONG)
            else
            {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://test.api.catering.bluecodegames.com/user/login",
                    JSONObject("{\"id_shop\":\"1\",\"email\":\"${binding.emailCo.text}\",\"password\":\"${binding.passCo.text}\"}"),
                    { response ->
                        Log.d("Reponse",response.toString())
                    },
                    { error ->
                        error.printStackTrace()
                        Log.e("Reponse",error.networkResponse.data.toString())
                    }
                )
                Volley.newRequestQueue(context).add(jsonObjectRequest)
            }
        }
    }
}