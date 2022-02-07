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
        lateinit var file : File
        var shop : Shop = Shop(0, emptyMap())
        var user : User? = null
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
            R.id.cadbar -> startShopActivity()
            R.id.nbrbar -> Toast.makeText(this, "Vous avez commander ${shop.list.size} type de plat different", Toast.LENGTH_SHORT).show()
            R.id.refrech -> {
                finish()
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
            file.writeText(Gson().toJson(this))
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
        file = File(cacheDir,jsonDataName)
        file.apply {

                if (exists())
                    try {
                        Log.d("DATA",readText())
                        val new = Gson().fromJson(readText(), FuckingShopConversion::class.java)
                        shop = new.convertToShop()
                        if (shop.hash != mainData.hashCode()) {
                            delete()
                            resetData()
                            Log.i("DATA","old value, so empty")
                        }
                    } catch (e: JsonSyntaxException) {
                        Log.e("DATA",e.stackTraceToString())
                        delete()
                        resetData()
                        Log.e("DATA","parsing error")
                    }
                else {
                    resetData()
                    Log.i("DATA","No previous data")
                }
                Log.i("DATA", "${shop.hash}:${shop.list}")
                return shop.list
            }

    }

    private fun resetData()
    {
        shop.list = emptyMap()
        shop.hash = mainData.hashCode()
        getSharedPreferences("info", 0).edit().apply {
            putInt("nbr", 0)
            apply()
        }
    }
}