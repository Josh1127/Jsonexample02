package com.eric.jsonexample02

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var ubikes = ArrayList<Ubike>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JsonTask().execute("https://data.ntpc.gov.tw/api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json/preview")

    }

    inner class JsonTask:AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val stream =InputStreamReader(url.openStream())
            val bufferedReader = BufferedReader(stream)
            var stringBuilder= StringBuilder()
            bufferedReader.forEachLine {
                stringBuilder.append(it)
            }
            return stringBuilder.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            parseJson(result)
        }
    }

    private fun parseJson(result: String?) {
        val jsonArray=JSONArray(result)
        (0 until jsonArray.length()).forEach {
            val jsonObject=jsonArray.getJSONObject(it)
            Log.d("jsonObject", "${jsonObject.getString("sna")} ${jsonObject.getString("sbi")} ");
            val ubike= Ubike(jsonObject)
            ubikes.add(ubike)
        }
    }

    class Ubike(jsonObject: JSONObject){
        var sna= jsonObject.getString("sna")
        var sbi= jsonObject.getString("sbi")
    }
}
