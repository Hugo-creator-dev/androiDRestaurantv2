package fr.isen.musoles.androidrestaurantv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ACTIVITY","start ErrorActivity")
        super.onCreate(savedInstanceState)
        ActivityErrorBinding.inflate(layoutInflater).apply {
            setContentView(root)
            backsafe.setOnClickListener {
                finish()
            }
        }
    }
}