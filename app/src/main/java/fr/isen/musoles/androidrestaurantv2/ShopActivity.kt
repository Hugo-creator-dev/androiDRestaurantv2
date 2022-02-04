package fr.isen.musoles.androidrestaurantv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.adaptater.ShopAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityShopBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Shop
import java.io.File

class ShopActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityShopBinding
    private lateinit var file : File
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbar
        super.onCreate(savedInstanceState)
        file = File(cacheDir,"data.json")
        var stock = Shop(ArrayList())
        if(file.exists())
            stock = Gson().fromJson(file.readText(), Shop::class.java)
        else
            finish()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ShopAdaptater( stock.items ) { item ->
            Log.e("ok",stock.items.toString())
            stock.items = stock.items.filter { it.id != item.id }
            Log.e("ok2",stock.items.toString())
            val pref = getSharedPreferences("info", 0)
            val edit = pref.edit()
            edit.putInt("nbr",pref.getInt("nbr",0) - item.getRealQuantity())
            edit.apply()
            file.writeText(Gson().toJson(stock))
            updateBarTools()
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun startActivity()
    {
        super.startActivity()
        finish()
    }
}