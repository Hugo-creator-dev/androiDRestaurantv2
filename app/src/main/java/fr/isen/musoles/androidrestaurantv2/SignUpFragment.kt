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
import fr.isen.musoles.androidrestaurantv2.databinding.FragmentSignUpBinding
import org.json.JSONObject


class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ch2.setOnClickListener {
            (activity as? LoginActivity)?.signupToSignIn()
        }

        binding.InIn.setOnClickListener {
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailIn.text.toString()).find())
                Toast.makeText(context,R.string.error_mail, Toast.LENGTH_LONG).show()
            else if (binding.pass1In.text.length < 9)
                Toast.makeText(context,R.string.error_password, Toast.LENGTH_LONG).show()
            else if (binding.pass1In.text.toString() != binding.pass2In.text.toString())
                Toast.makeText(context,R.string.error_password_not_same, Toast.LENGTH_LONG).show()
            else if (binding.editTextTextPostalAddress.text.isNullOrEmpty() || binding.nomIn.text.isNullOrEmpty() || binding.prenomIn.text.isNullOrEmpty())
                Toast.makeText(context,R.string.error_not_all_enter, Toast.LENGTH_LONG).show()
            else
            {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://test.api.catering.bluecodegames.com/user/register",
                    //TODO : secure this
                    JSONObject("{\"id_shop\":\"1\",\"firstname\":\"${binding.prenomIn.text}\",\"lastname\":\"${binding.nomIn.text}\",\"address\":\"${binding.editTextTextPostalAddress.text}\",\"email\":\"${binding.emailIn.text}\",\"password\":\"${binding.pass1In.text}\"}"),
                    { response ->
                        Log.d("Response",response.toString())
                        (activity as? LoginActivity)?.signupToSignIn()
                    },
                    { error ->
                        error.printStackTrace()
                        Log.e("Response",error.networkResponse.data.toString())
                        Toast.makeText(context,R.string.error_inscription_back, Toast.LENGTH_LONG).show()
                    }
                )
                Volley.newRequestQueue(context).add(jsonObjectRequest)
            }
        }
    }
}