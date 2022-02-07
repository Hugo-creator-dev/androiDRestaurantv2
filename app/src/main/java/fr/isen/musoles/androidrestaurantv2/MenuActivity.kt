package fr.isen.musoles.androidrestaurantv2


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.MenuAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMenuBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class MenuActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMenuBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setToolBar(toolbarMenu)
            recyleviewMenu.apply {
                layoutManager = LinearLayoutManager(this@MenuActivity)
                adapter = MenuAdaptater(mainData.data) {
                    startActivity(DATATYPE.ITEMS, intArrayOf(it))
                }
            }
        }
    }
}