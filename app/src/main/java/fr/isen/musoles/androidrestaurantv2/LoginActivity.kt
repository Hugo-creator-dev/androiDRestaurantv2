package fr.isen.musoles.androidrestaurantv2


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityLoginBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.User

class LoginActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val actPosition : IntArray? = getActivityPosition()
        if(actPosition != null && actPosition[0] == 0)
        {
            signupToSignIn()
        }
        else
        {
            loginToSignUp()
        }
    }

    fun loginToSignUp() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerView.id, SignUpFragment()).commit()
    }

    fun signupToSignIn() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerView.id, LoginFragment()).commit()
    }

    fun validating(user : User) {
        setUser(user)
        startActivity()
        finish()
    }
}