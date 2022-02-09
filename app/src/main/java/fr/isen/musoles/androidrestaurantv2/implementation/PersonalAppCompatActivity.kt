package fr.isen.musoles.androidrestaurantv2.implementation

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import fr.isen.musoles.androidrestaurantv2.R
import fr.isen.musoles.androidrestaurantv2.enumeration.DATATYPE
import fr.isen.musoles.androidrestaurantv2.model.*
import java.io.File

const val jsonDataName = "data.json"
const val REDIRECTION = "REDIRECTION"
open class PersonalAppCompatActivity : AppCompatActivity(){
    private var toolBar : Toolbar? = null

    companion object {
        var mainData : Data = Data(ArrayList())
        var shop : Shop = Shop(0, emptyMap())
        var user : User? = null
    }

    open fun getHash() : Int
    {
        return shop.hash
    }

    open fun getUserId() : Int?
    {
        return user?.id
    }

    open fun setNotConnected()
    {
        user = null
    }

    open fun setUser(userIn : User)
    {
        user = userIn
    }

    open fun isConnected() : Boolean
    {
        return user != null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        updateBarTools()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        updateBarTools()
        super.onResume()
    }

    protected fun setToolBar( myToolbar : Toolbar)
    {
        toolBar = myToolbar
        setSupportActionBar(toolBar)
    }

    open fun updateBarTools()
    {
        if(toolBar != null) {
            toolBar!!.menu.findItem(R.id.nbrbar)?.title =
                getSharedPreferences("info", 0).getInt("nbr", 0).toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cadbar ->
            {
                if(shop.list.isNotEmpty())
                    startShopActivity()
                else
                    Toast.makeText(this, "Pensez a remplire d'abord votre panier !", Toast.LENGTH_SHORT).show()
            }
            R.id.nbrbar -> { Toast.makeText(this, "Vous avez commander ${shop.list.size} type de plat different", Toast.LENGTH_SHORT).show() }
            R.id.refrech ->
            {
                finishAffinity()
                startActivity(DATATYPE.DEFAULT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    open fun startShopActivity()
    {
        startActivity(DATATYPE.SHOP)
    }

    open fun startActivity()
    {
        val redirect = getActivityRedirection()
        if(redirect != null && redirect != DATATYPE.NOTHING) {
            val isType = getDataType(redirect)
            val intent = Intent(this, isType.javaClass)
            startActivity(intent)
        }
    }

    open fun startActivity( type : DATATYPE)
    {
        val isType = getDataType(type)
        val intent = Intent(this, isType.javaClass)
        startActivity(intent)
    }

    open fun startActivity( type : DATATYPE,position : IntArray)
    {
        val isType = getDataType(type)
        val intent = Intent(this, isType.javaClass)
        intent.putExtra(isType.forExtra,position)
        startActivity(intent)
    }

    open fun startActivity( type : DATATYPE,redirection : DATATYPE)
    {
        val isType = getDataType(type)
        val intent = Intent(this, isType.javaClass)
        intent.putExtra(REDIRECTION,redirection)
        startActivity(intent)
    }

    open fun startActivity( type : DATATYPE,position : IntArray,redirection : DATATYPE)
    {
        val isType = getDataType(type)
        val intent = Intent(this, isType.javaClass)
        intent.putExtra(isType.forExtra,position)
        intent.putExtra(REDIRECTION,redirection)
        startActivity(intent)
    }

    open fun startActivity( type : DATATYPE,position : IntArray,redirection : Boolean)
    {
        val isType = getDataType(type)
        val intent = Intent(this, isType.javaClass)
        intent.putExtra(isType.forExtra,position)
        if(redirection)
            intent.putExtra(REDIRECTION,getDataType())
        startActivity(intent)
    }

    open fun getActivityPosition(type : DATATYPE? = null) : IntArray?
    {
        return intent.getIntArrayExtra(getDataType(type).forExtra)
    }

    open fun getActivityRedirection() : DATATYPE?
    {
        return intent.getSerializableExtra(REDIRECTION) as? DATATYPE
    }

    private fun getDataType(type : DATATYPE? = null) : DATATYPE
    {
        return (type ?: DATATYPE.values().firstOrNull { javaClass == it.javaClass && it != DATATYPE.NOTHING }?: DATATYPE.DEFAULT)
    }

    open fun updateShop(key : Pair<Int,Int>, value : Int)
    {
        shop.apply {
            if (value > 0) {
                list += Pair(key, value)
            } else {
                list = list.filter { it.value > 0 && it.key != key }
            }

            getSharedPreferences("info", 0).edit().apply {
                putInt("nbr", list.values.sum())
                apply()
            }
            File(cacheDir,jsonDataName).writeText(Gson().toJson(this))
        }
    }

    open fun updateShop(shopper : Shop) : Boolean
    {
        shop.apply {
            return if(shopper.hash == hash) {
                list = shopper.list
                getSharedPreferences("info", 0).edit().apply {
                    putInt("nbr", list.values.sum())
                    apply()
                }
                File(cacheDir,jsonDataName).writeText(Gson().toJson(this))
                true
            } else {
                false
            }
        }
    }

    open fun getAuthentication() : Pair<String,String>?
    {
        return getSharedPreferences("user", 0).run {
            if (contains("email"))
                Pair(getString("email", "")!!, getString("password", "")!!)
            else
                null
        }
    }

    open fun callMeBackAuthentication(bool : Boolean, email : String, password : String)
    {
        getSharedPreferences("user", 0).edit().apply {
            if(bool)
            {
                putString("email",email)
                putString("password",password)
            }
            else
            {
                clear()
            }
            apply()
        }
    }

    open fun getShop() : Map<Pair<Int,Int>,Int>
    {
        File(cacheDir,jsonDataName).apply {
            if (exists())
                try {
                    val text: String = readText()
                    Log.d("DATA", text)
                    val new = Gson().fromJson(text, FuckingShopConversion::class.java)
                    shop = new.convertToShop()
                    if (shop.hash != mainData.hashCode()) {
                        resetData()
                        Log.i("DATA", "old value, so empty")
                    }
                } catch (e: JsonSyntaxException) {
                    Log.e("DATA", e.stackTraceToString())
                    resetData()
                    Log.e("DATA", "parsing error")
                }
            else {
                resetData()
                Log.i("DATA", "No previous data")
            }
            Log.i("DATA", "${shop.hash}:${shop.list}")
            return shop.list

        }
    }

    open fun resetData()
    {
        File(cacheDir,jsonDataName).delete()
        shop.list = emptyMap()
        shop.hash = mainData.hashCode()
        getSharedPreferences("info", 0).edit().apply {
            putInt("nbr", 0)
            apply()
        }
    }
}