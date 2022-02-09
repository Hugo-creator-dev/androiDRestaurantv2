package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityLoginBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.User

class LoginActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ACTIVITY","start LoginActivity")
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getActivityPosition().also {
            if (it != null && it[0] == 0) {
                signupToSignIn()
            } else {
                loginToSignUp()
            }
        }
    }

    fun loginToSignUp() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, SignUpFragment()).commit()
    }

    fun signupToSignIn() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, LoginFragment()).commit()
    }

    fun validating(user : User) {
        setUser(user)
        finish()
    }
}