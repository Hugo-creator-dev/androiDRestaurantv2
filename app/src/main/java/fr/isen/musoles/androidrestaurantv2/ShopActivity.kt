package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.ShopAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityShopBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.generalStatic.ConvertClass
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class ShopActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityShopBinding.inflate(layoutInflater).apply {
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
                //startActivity(DATATYPE.SOLD, intArrayOf(0))
            }
            buttonCo.setOnClickListener {
                startActivity(DATATYPE.LOGIN, intArrayOf(0),true)
            }
        }
    }

    override fun startShopActivity()
    {
        super.startShopActivity()
        finish()
    }
}