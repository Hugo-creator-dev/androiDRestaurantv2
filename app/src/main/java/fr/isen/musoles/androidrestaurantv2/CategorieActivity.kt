package fr.isen.musoles.androidrestaurantv2


import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.CategorieAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityCategorieBinding
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Items

class CategorieActivity : PersonalAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actPosition: IntArray? = getActivityPosition()
        if(actPosition != null && actPosition.size == 1) {
            val position: Int = actPosition[0]
            val myData: Items = mainData[position]
            ActivityCategorieBinding.inflate(layoutInflater).apply {
                setContentView(root)
                setToolBar(toolbarMenu3)
                titleMenu4.text = myData.name_fr
                recyleviewMenu.apply {
                    layoutManager = LinearLayoutManager(this@CategorieActivity)
                    adapter = CategorieAdaptater(myData.items) {
                        startActivity(DATATYPE.ITEM, intArrayOf(position, it))
                    }
                }
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