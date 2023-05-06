package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exportify.databinding.ActivityUserDirectionBinding
import com.example.exportify.databinding.LoginBinding

class User_direction : AppCompatActivity() {

    private lateinit var binding: ActivityUserDirectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDirectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuyer.setOnClickListener {
            intent = Intent(applicationContext, Buyer_registration::class.java)
            startActivity(intent)
        }
        binding.btnExporter.setOnClickListener {
            intent = Intent(applicationContext, Exporter_registration::class.java)
            startActivity(intent)
        }


    }
}