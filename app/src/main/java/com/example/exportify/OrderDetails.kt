package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exportify.databinding.ActivityOrderDetailsBinding
import com.example.exportify.databinding.ActivityUpdateRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class OrderDetails : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val topic = intent.getStringExtra("topic").toString()
        val type = intent.getStringExtra("type").toString()
        val des = intent.getStringExtra("des").toString()
        val pricePerUnit = intent.getStringExtra("pricePerUnit").toString()
        val noOfUnits = intent.getStringExtra("noOfUnits").toString()
        val reqId = intent.getStringExtra("reqId").toString()


        binding.tvProduct.text = topic
        binding.tvUnitPrice.text = pricePerUnit

        var qty  = 0
        binding.btnAdd.setOnClickListener {
            qty++
            binding.tvQty.text = qty.toString()
            binding.tvTotal.text = (qty * pricePerUnit.toInt()).toString()
        }
        binding.btnMinus.setOnClickListener {
            if( qty > 0 ) {
                qty--
                binding.tvTotal.text = (qty * pricePerUnit.toInt()).toString()
            }
            binding.tvQty.text = qty.toString()
        }

        binding.btnOrderCancel.setOnClickListener {
            intent = Intent(applicationContext, Buyer_dashboard::class.java)
            startActivity(intent)
        }

        binding.btnProceed.setOnClickListener {
            intent = Intent(applicationContext, PaymentDetails::class.java)
            startActivity(intent)
        }










    }
}