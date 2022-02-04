package fr.isen.musoles.androidrestaurantv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.musoles.androidrestaurantv2.adaptater.CategorieAdaptater
import fr.isen.musoles.androidrestaurantv2.databinding.ActivityCategorieBinding
import fr.isen.musoles.androidrestaurantv2.implementation.PersonalAppCompatActivity
import fr.isen.musoles.androidrestaurantv2.model.Items

const val SHOPTRANSFERT : String = "shptrnsft"
class CategorieActivity : PersonalAppCompatActivity() {
    private lateinit var binding : ActivityCategorieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMenu3
        super.onCreate(savedInstanceState)
        val myData : Items = intent.getSerializableExtra(CATTRANSFERT) as Items
        binding.titleMenu4.text = myData.name_fr
        binding.recyleviewMenu.layoutManager = LinearLayoutManager(this)
        binding.recyleviewMenu.adapter = CategorieAdaptater( myData.items ) {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(SHOPTRANSFERT, it)
            }
            startActivity(intent)
        }
    }
}