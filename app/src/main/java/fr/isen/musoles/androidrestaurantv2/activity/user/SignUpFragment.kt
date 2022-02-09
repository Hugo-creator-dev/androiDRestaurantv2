package fr.isen.musoles.androidrestaurantv2.activity.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.toolbox.Volley
import fr.isen.musoles.androidrestaurantv2.R
import fr.isen.musoles.androidrestaurantv2.databinding.FragmentSignUpBinding
import fr.isen.musoles.androidrestaurantv2.generalStatic.PersonalString
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI


class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.i("ACTIVITY","start SignUpFragment")
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ch2.setOnClickListener {
                (activity as? LoginActivity)?.signupToSignIn()
            }

            InIn.setOnClickListener {
                val email = emailIn.text.toString()
                val pass1 = pass1In.text.toString()
                val pass2 = pass2In.text.toString()
                val address = editTextTextPostalAddress.text.toString()
                val firstname = prenomIn.text.toString()
                val lastname = nomIn.text.toString()

                if (!PersonalString.isCorrectEmail(email))
                    Toast.makeText(context, R.string.error_mail, Toast.LENGTH_LONG).show()
                else if (!PersonalString.isCorrectPassword(pass1))
                    Toast.makeText(context, R.string.error_password, Toast.LENGTH_LONG).show()
                else if (pass1 != pass2)
                    Toast.makeText(context, R.string.error_password_not_same, Toast.LENGTH_LONG)
                        .show()
                else if (!PersonalString.isCorrectAddress(address) || !PersonalString.isCorrectName(lastname) || !PersonalString.isCorrectName(firstname))
                    Toast.makeText(context, R.string.error_not_all_enter, Toast.LENGTH_LONG).show()
                else {
                    val jsonObjectRequest = RequestOnAPI.setRequestOfInscription({email},{firstname},{lastname},{address},{pass1})
                    {
                        if(it != null) {
                            (activity as? LoginActivity)?.callMeBackAuthentication(true,email,pass1)
                            (activity as? LoginActivity)?.signupToSignIn()
                        }
                        else
                            Toast.makeText(context, R.string.error_inscription_back, Toast.LENGTH_LONG).show()
                    }
                    Volley.newRequestQueue(context).add(jsonObjectRequest)
                }
            }
        }
    }
}