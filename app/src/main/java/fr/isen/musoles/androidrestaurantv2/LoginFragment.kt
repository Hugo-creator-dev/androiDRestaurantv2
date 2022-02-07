package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.toolbox.Volley
import fr.isen.musoles.androidrestaurantv2.databinding.FragmentLoginBinding
import fr.isen.musoles.androidrestaurantv2.generalStatic.PersonalString
import fr.isen.musoles.androidrestaurantv2.generalStatic.RequestOnAPI

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("ACTIVITY","start LoginFragment")
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            (activity as? LoginActivity)?.getAuthentication().also {
                switch2.isChecked = it != null
                if (it != null ) {
                    emailCo.setText(it.first)
                    passCo.setText(it.second)
                }
            }
            ch1.setOnClickListener {
                (activity as? LoginActivity)?.loginToSignUp()
            }

            CoCo.setOnClickListener {
                val password: String = passCo.text.toString()
                val email: String = emailCo.text.toString()
                if (!PersonalString.isCorrectEmail(email))
                    Toast.makeText(context, R.string.error_mail, Toast.LENGTH_LONG).show()
                else if (!PersonalString.isCorrectPassword(password))
                    Toast.makeText(context, R.string.error_password, Toast.LENGTH_LONG).show()
                else {
                    (activity as? LoginActivity)?.callMeBackAuthentication(
                        switch2.isChecked,
                        email,
                        password
                    )
                    Volley.newRequestQueue(context).add(
                        RequestOnAPI.setRequestOfConnexion({ email }, { password })
                        { if (it != null) (activity as? LoginActivity)?.validating(it) }
                    )
                }
            }
        }
    }
}