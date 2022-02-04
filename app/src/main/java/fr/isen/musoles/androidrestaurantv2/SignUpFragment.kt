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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentSignUpBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ch2.setOnClickListener {
            (activity as? LoginActivity)?.signupToLogin()
        }

        binding.InIn.setOnClickListener {
            //if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailIn.text).find())
            //    Toast.makeText(context,"Bad email", Toast.LENGTH_LONG)
            //else if (binding.pass1In.text.length < 9 || binding.pass1In.text != binding.pass2In.text)
            //    Toast.makeText(context,"Bad password", Toast.LENGTH_LONG)
            //else
            //{
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://test.api.catering.bluecodegames.com/user/register",
                    JSONObject("{\"id_shop\":\"1\",\"firstname\":\"${binding.prenomIn.text}\",\"lastname\":\"${binding.nomIn.text}\",\"address\":\"${binding.editTextTextPostalAddress.text}\",\"email\":\"${binding.emailIn.text}\",\"password\":\"${binding.pass1In.text}\"}"),
                    { response ->
                        Log.d("Reponse",response.toString())
                    },
                    { error ->
                        error.printStackTrace()
                        Log.e("Reponse",error.networkResponse.data.toString())
                    }
                )
                Volley.newRequestQueue(context).add(jsonObjectRequest)
           // }
        }
    }
}