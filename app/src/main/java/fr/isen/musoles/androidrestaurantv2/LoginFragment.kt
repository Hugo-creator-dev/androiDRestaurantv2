package fr.isen.musoles.androidrestaurantv2

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
import fr.isen.musoles.androidrestaurantv2.databinding.FragmentLoginBinding
import fr.isen.musoles.androidrestaurantv2.model.User
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ch1.setOnClickListener {
            (activity as? LoginActivity)?.loginToSignUp()
        }

        binding.CoCo.setOnClickListener {

            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailCo.text).find())
                Toast.makeText(context,R.string.error_mail,Toast.LENGTH_LONG).show()
            else if (binding.emailCo.text.length < 9)
                Toast.makeText(context,R.string.error_password,Toast.LENGTH_LONG).show()
            else
            {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://test.api.catering.bluecodegames.com/user/login",
                    JSONObject("{\"id_shop\":\"1\",\"email\":\"${binding.emailCo.text}\",\"password\":\"${binding.passCo.text}\"}"),
                    { response ->
                        Log.d("Response",response.toString())
                        (activity as? LoginActivity)?.validating(Gson().fromJson(response.toString().replace("{\"data\":","").replace(",\"code\":200}",""),User::class.java))
                    },
                    { error ->
                        error.printStackTrace()
                        Log.e("Response",error.networkResponse.data.toString())
                    }
                )
                Volley.newRequestQueue(context).add(jsonObjectRequest)
            }
        }
    }
}