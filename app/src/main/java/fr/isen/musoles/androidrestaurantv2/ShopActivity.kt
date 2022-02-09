package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.ShopAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMainBinding
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityShopBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.generalStatic.ConvertClass
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class ShopActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ACTIVITY","start ShopActivity")
        binding = ActivityShopBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            val shop = getShop()
            val stock = shop.map { mainData[it.key] }
            val stockInverse = shop.mapKeys { mainData[it.key] }
            val stockReverse=  shop.mapValues { mainData[it.key] }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@ShopActivity)
                stock.apply {
                    adapter = ShopAdaptater(this,
                        { item ->
                            startActivity(DATATYPE.ITEM,stockReverse.firstNotNullOf { if(it.value == item) ConvertClass.convertPairToIntArray(it.key) else null },true)
                            finish()
                    },
                        { item ->
                            stockInverse[item]!!
                    })

                }
            }
            if(isConnected())
                button.visibility = View.VISIBLE
            else
                buttonCo.visibility = View.VISIBLE

            button.setOnClickListener {
                startActivity(DATATYPE.ORDER)
                finish()
            }
            buttonCo.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(0))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(isConnected()) {
            binding.button.visibility = View.VISIBLE
            binding.buttonCo.visibility = View.GONE
        }
        else {
            binding.buttonCo.visibility = View.VISIBLE
            binding.button.visibility = View.GONE
        }
    }
}