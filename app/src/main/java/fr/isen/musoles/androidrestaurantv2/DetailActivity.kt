package fr.isen.musoles.androidrestaurantv2

import android.os.Bundle
import android.util.Log
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityDetailBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Item

class DetailActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var item : Item
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ACTIVITY","start DetailActivity")
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        binding.apply {
            val position : IntArray? = getActivityPosition()
            if(position != null && position.size == 2) {
                item = mainData[position]
                val shop = getShop()
                var quantity = 0
                if (shop.containsKey(Pair(position[0], position[1])))
                    quantity = shop[Pair(position[0], position[1])]!!

                setContentView(root)
                setToolBar(toolbar2)
                item.apply {
                    titledetail.text = name_fr
                    descdetail.text = getIngredients()
                    presdetail.apply {
                        firstOrDefaultImage().into(this)
                        setOnClickListener {
                            getNextImage().into(this)
                        }
                    }
                }


                update(quantity)

                plus.setOnClickListener {
                    update(++quantity)
                }
                moins.setOnClickListener {
                    if (quantity > 0) update(--quantity)
                }
                pricedetail.setOnClickListener {
                    updateShop(Pair(position[0], position[1]), quantity)
                    val redirect = getActivityRedirection()
                    if (redirect != null)
                        startActivity(redirect)
                    finish()
                }
            }
            else
            {
                Log.e("POSITION","bad data position meet")
                startActivity(DATATYPE.ERROR)
                finish()
            }
        }
    }

    private fun update(quantity : Int)
    {
        binding.apply {
            nbrdetail.text = quantity.toString()
            val myString : String = (quantity * item.getPrice()!!).toString() + "€"
            pricedetail.text =  myString
        }
    }

    override fun startShopActivity() {
        finish()
        super.startShopActivity()
    }
}