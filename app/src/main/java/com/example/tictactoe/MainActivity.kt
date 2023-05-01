package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

var singleplayer=false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    fun one_player(view: View) {
        singleplayer=true
        startActivity(Intent(this@MainActivity,playground::class.java))
    }

    fun two_player(view: View) {

        singleplayer=false
        startActivity(Intent(this@MainActivity,playground::class.java))
    }

    fun online(view: View) {
        singleplayer=false
        startActivity(Intent(this@MainActivity,code_generator::class.java))
    }

}