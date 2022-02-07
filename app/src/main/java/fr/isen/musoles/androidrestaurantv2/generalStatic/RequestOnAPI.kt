package fr.isen.musoles.androidrestaurantv2.generalStatic

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import fr.isen.musoles.androidrestaurantv2.model.Data
import fr.isen.musoles.androidrestaurantv2.model.User
import org.json.JSONObject

const val BASEURL = "http://test.api.catering.bluecodegames.com/"
object RequestOnAPI {

    private fun setRequestOf(target : String, data : JSONObject, debrouilleToi : Boolean = false,callBackFunction: (String?) -> Unit): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASEURL + target,
            data.put("id_shop", 1),
            { response ->
                when (response.optString("code")) {
                    "NOK" -> {
                        Log.e("Response", "bad data")
                        callBackFunction(null)
                    }
                    else -> {
                        Log.d("Response", response.toString())
                        if(debrouilleToi)
                            callBackFunction(response.toString())
                        else
                            callBackFunction(response.getJSONObject("data").toString())
                    }
                }
            },
            { error ->
                error.printStackTrace()
                Log.e("Response", error.networkResponse.data.toString())
                callBackFunction(null)
            }
        )
    }

    @JvmStatic
    fun setRequestOfData(callBackFunction: (Data?) -> Unit): JsonObjectRequest {
        return setRequestOf("menu",
            JSONObject(),true)
        {
            if(it != null)
                callBackFunction(Gson().fromJson(it,Data::class.java))
            else
                callBackFunction(null)
        }
    }

    @JvmStatic
    fun setRequestOfConnexion(
        email: () -> String,
        password: () -> String,
        callBackFunction: (User?) -> Unit
    ): JsonObjectRequest {
        return setRequestOf("user/login",
            JSONObject()
                .put("email", email())
                .put("password", password()))
        {
            if(it != null)
                callBackFunction(Gson().fromJson(it,User::class.java))
            else
                callBackFunction(null)
        }
    }

    @JvmStatic
    fun setRequestOfInscription(
        email: () -> String,
        firstname: () -> String,
        lastname: () -> String,
        address: () -> String,
        password: () -> String,
        callBackFunction: (User?) -> Unit
    ) : JsonObjectRequest
    {
        return setRequestOf("user/register",
            JSONObject()
                .put("email", email())
                .put("password", password())
                .put("firstname",firstname())
                .put("lastname",lastname())
                .put("address",address()))
        {
            if(it != null)
                callBackFunction(Gson().fromJson(it,User::class.java))
            else
                callBackFunction(null)
        }
    }
}