package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityDetailBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Item
import fr.isen.musoles.androidrestaurantv2.model.Shop
import java.io.File

class DetailActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var file : File
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbar2
        super.onCreate(savedInstanceState)
        val myData : Item = intent.getSerializableExtra(SHOPTRANSFERT) as Item
        file = File(cacheDir,"data.json")
        val stock = Shop(ArrayList())
        if(file.exists())
            stock.items = Gson().fromJson(file.readText(),Shop::class.java).items
        else
            file.createNewFile()

        var test : Item? = stock.items.firstOrNull { it.id == myData.id }

        if(test == null)
        {
            test = myData
        }
        update(test)
        binding.titledetail.text = test.name_fr
        binding.descdetail.text = test.ingredients.joinToString { it.toString() }

        test.firstOrDefaultImage().into(binding.presdetail)
        binding.plus.setOnClickListener { test.quantity++ ;update(test)}
        binding.moins.setOnClickListener { if (test.quantity > 0) test.quantity--;update(test) }
        binding.pricedetail.setOnClickListener {
            val pref = getSharedPreferences("info", 0)
            val edit = pref.edit()
            edit.putInt("nbr",pref.getInt("nbr",0) + test.getRealQuantity())
            edit.apply()

            if(stock.items.any { it.id == test.id })
                stock.items.firstOrNull { it.id == test.id }?.quantity == test.quantity
            else
                stock.items += test

            file.writeText(Gson().toJson(stock))
            finish()
        }
    }

    private fun update(test : Item)
    {
        binding.nbrdetail.text = test.getRealQuantity().toString()
        binding.pricedetail.text = (test.getRealQuantity() * test.getPrice()!!).toString() + "€"
    }
}