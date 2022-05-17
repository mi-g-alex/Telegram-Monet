package com.c3r5b8.telegram_monet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HowToInstall : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_install)

        val goMainMenu: Button = findViewById(R.id.goMainMenu)

        goMainMenu.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            this.startActivity(myIntent)
        }
    }
}