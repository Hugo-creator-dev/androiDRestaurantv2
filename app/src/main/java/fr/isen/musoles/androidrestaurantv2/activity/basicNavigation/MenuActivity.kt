package fr.isen.musoles.androidrestaurantv2.activity.basicNavigation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.MenuAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMenuBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity

class MenuActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ACTIVITY","start MenuActivity")
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
            goToMapButton.setOnClickListener {
                val mapIntent: Intent = Uri.parse(
                    "geo:0,0?q=ISEN+Yncréa+Méditerranée+-+Campus+de+Toulon,+Place+Georges+Pompidou,+Toulon"
                ).let { location ->
                    Intent(Intent.ACTION_VIEW, location)
                }
                startActivity(mapIntent)
            }
        }
    }
}