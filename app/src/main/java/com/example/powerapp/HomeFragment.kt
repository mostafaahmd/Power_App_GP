package com.example.powerapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HomeFragment : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView

    // Shared Preferences
    private val PREF_NAME = "user_pref"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.offer_progress_home)
         val table = arguments?.getString("table")
        val textView = view.findViewById<TextView>(R.id.text_view_home)
        val client = OkHttpClient()
        val url = "http://192.168.1.3:3000/api/last-energy/$table"
        val request = Request.Builder().url(url).build()

         // Get username and password from shared preferences
         val sharedPref = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
         val username = sharedPref?.getString(KEY_USERNAME, "mostafa")
         val password = sharedPref?.getString(KEY_PASSWORD, "123")


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    textView.text = "Failed to fetch data: ${e.message}"
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body!!.string()
                    activity?.runOnUiThread {
                        // textView.text = responseData
                        progressBar.progress = responseData.toIntOrNull() ?: 0
                    }
                } else {
                    activity?.runOnUiThread {
                        textView.text = "Error fetching data: ${response.code}"
                    }
                }
            }
        })
    }
}


