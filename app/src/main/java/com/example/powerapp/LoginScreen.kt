package com.example.powerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LoginScreen : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(username, password)
        }
    }

    private fun login(username: String, password: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = "{\"username\":\"$username\",\"password\":\"$password\"}".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://192.168.1.3:3000/login")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle the error
                runOnUiThread {
                    Toast.makeText(this@LoginScreen, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    // Handle the error
                    runOnUiThread {
                        Toast.makeText(this@LoginScreen, "Login failed: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle the successful response
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val table = jsonResponse.getString("Table")

                    // TODO: Use 'table' to open the corresponding collection in your database
                    // For example, navigate to the next screen or display the data
                    runOnUiThread {
                        // Update UI on the main thread
                        Toast.makeText(this@LoginScreen, "Login successful: $table", Toast.LENGTH_SHORT).show()
                        // Example: Navigate to another activity
                        val intent = Intent(this@LoginScreen, MainActivity::class.java)
                        // You can pass any data to MainActivity if needed
                        intent.putExtra("table", table)
                        startActivity(intent)
                        finish()

                    }
                }
            }
        })
    }
}
