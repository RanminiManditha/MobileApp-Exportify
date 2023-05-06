package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityCreateRequestBinding
import com.example.exportify.databinding.ActivityExporterRegistrationBinding
import com.example.exportify.models.BuyerRequestsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateRequest : AppCompatActivity() {

    private lateinit var binding : ActivityCreateRequestBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("buyer_requests")


        binding.btnRequest.setOnClickListener {

            var topic = binding.etTopic.text.toString()
            var des = binding.etDes.text.toString()
            var priceRange = binding.etPriceRange.text.toString()

            //validate form
            if(topic.isEmpty() || des.isEmpty() || priceRange.isEmpty()){

                if(topic.isEmpty()){
                    binding.etTopic.error = "Enter Topic"
                }
                if(des.isEmpty()){
                    binding.etDes.error = "Enter Description"
                }
                if(priceRange.isEmpty()){
                    binding.etPriceRange.error = "Enter Price Range"
                }
            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val request = BuyerRequestsModel(topic, des, priceRange, id, uid)
                databaseRef.child(id).setValue(request).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, Buyer_dashboard::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Your requests added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}