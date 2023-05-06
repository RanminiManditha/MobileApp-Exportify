package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityUpdateServiceGigBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateServiceGig : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateServiceGigBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateServiceGigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fetch data from the intent
        val topic = intent.getStringExtra("topic").toString()
        val type = intent.getStringExtra("type").toString()
        val des = intent.getStringExtra("des").toString()
        val pricePerUnit = intent.getStringExtra("pricePerUnit").toString()
        val noOfUnits = intent.getStringExtra("noOfUnits").toString()
        val reqId = intent.getStringExtra("reqId").toString()

        //bind values to editTexts
        binding.edServiceTopic.setText(topic)
        binding.edServiceType.setText(type)
        binding.edDescription.setText(des)
        binding.edUnits.setText(noOfUnits)
        binding.edPrice.setText(pricePerUnit)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("service_gigs")

        binding.btnUpdate.setOnClickListener {
            val map = HashMap<String,Any>()

            var etTopic = binding.edServiceTopic.text.toString()
            var etType = binding.edServiceType.text.toString()
            var etDes = binding.edDescription.text.toString()
            var etPrice = binding.edPrice.text.toString()
            var etNoOfCount = binding.edUnits.text.toString()

            //add data to hashMap
            map["topic"] = etTopic
            map["type"] = etType
            map["description"] = etDes
            map["price"] = etPrice
            map["noOfUnits"] = etNoOfCount

            //update database from hashMap
            databaseRef.child(reqId).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, MyServiceGigs::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Gig updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnDlt.setOnClickListener {
            databaseRef.child(reqId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, MyServiceGigs::class.java)
                    startActivity(intent)
                }
            }
        }








    }
}