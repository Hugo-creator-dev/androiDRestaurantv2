package fr.isen.musoles.androidrestaurantv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.MenuAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMainBinding
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityMenuBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Data
import fr.isen.musoles.androidrestaurantv2.model.Items
const val CATTRANSFERT  : String = "cttrnsfrt"
class MenuActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMenu
        super.onCreate(savedInstanceState)
        val myData : Data = intent.getSerializableExtra(MENUTRANSFER) as Data
        binding.recyleviewMenu.layoutManager = LinearLayoutManager(this)
        binding.recyleviewMenu.adapter = MenuAdaptater( myData.data ) {
            val intent = Intent(this, CategorieActivity::class.java).apply {
                putExtra(CATTRANSFERT, it)
            }
            startActivity(intent)
        }
    }
}