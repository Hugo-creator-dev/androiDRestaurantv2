package fr.isen.musoles.androidrestaurantv2


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
                // Map point based on address
                val mapIntent: Intent = Uri.parse(
                    "geo:0,0?q=ISEN+Yncréa+Méditerranée+-+Campus+de+Toulon,+Place+Georges+Pompidou,+Toulon"
                ).let { location ->Log.d("map","pleaseeeeed")
                    // Or map point based on latitude/longitude
                    // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
                    Intent(Intent.ACTION_VIEW, location)

                }
                startActivity(mapIntent)
                Log.d("map","pleaseeeee")
            }
        }
    }
}