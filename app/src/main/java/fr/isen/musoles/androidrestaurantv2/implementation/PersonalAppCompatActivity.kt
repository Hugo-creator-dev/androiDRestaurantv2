package fr.isen.musoles.androidrestaurantv2.implementation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import fr.isen.musoles.androidrestaurantv2.R
import fr.isen.musoles.androidrestaurantv2.ShopActivity

open class PersonalAppCompatActivity() : AppCompatActivity(){
    protected lateinit var toolBar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolBar)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        update()
        return super.onCreateOptionsMenu(menu)
    }
    override fun onResume() {
        update()
        super.onResume()
    }

    open fun update()
    {
        toolBar.menu.findItem(R.id.nbrbar)?.title = getSharedPreferences("info", 0).getInt("nbr",0).toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cadbar -> {
                startActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    open fun startActivity()
    {
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }
}