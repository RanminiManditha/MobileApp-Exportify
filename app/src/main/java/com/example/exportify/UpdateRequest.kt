package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityUpdateRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateRequest : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateRequestBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fetch data from the intent
        val topic = intent.getStringExtra("topic")
        val des = intent.getStringExtra("des")
        val priceRange = intent.getStringExtra("priceRange")
        val reqId = intent.getStringExtra("reqId")

        //bind values to editTexts
        binding.etTopic.setText(topic)
        binding.etUpReqDes.setText(des)
        binding.etPr.setText(priceRange)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("buyer_requests")


        binding.btnUpdate.setOnClickListener {
            var topic = binding.etTopic.text.toString()
            var des = binding.etUpReqDes.text.toString()
            var priceRange = binding.etPr.text.toString()

            //validate form
            if(topic.isEmpty() || des.isEmpty() || priceRange.isEmpty()){

                if(topic.isEmpty()){
                    binding.etTopic.error = "Enter Topic"
                }
                if(des.isEmpty()){
                    binding.etUpReqDes.error = "Enter Description"
                }
                if(priceRange.isEmpty()){
                    binding.etPr.error = "Enter Price Range"
                }
            } else {
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["topic"] = topic
                map["description"] = des
                map["priceRange"] = priceRange



                //update database from hashMap
                databaseRef.child(reqId!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, MyRequests::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnDlt.setOnClickListener {
            databaseRef.child(reqId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, MyRequests::class.java)
                    startActivity(intent)
                }
            }
        }


    }
}