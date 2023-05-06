package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityBuyerProfileUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BuyerProfileUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerProfileUpdateBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")


        val fname = intent.getStringExtra("fname")
        val lname = intent.getStringExtra("lname")
        val mobile = intent.getStringExtra("mobile")
        val des = intent.getStringExtra("des")

        //bind values to editTexts
        binding.edFirstname.setText(fname)
        binding.edSecondname.setText(lname)
        binding.edContactnumber.setText(mobile)
        binding.edDiscription.setText(des)


        binding.btnCancel.setOnClickListener {
            intent = Intent(applicationContext, BuyerProfile::class.java)
            startActivity(intent)
        }


        binding.btnUpdate.setOnClickListener {

            var firstname = binding.edFirstname.text.toString()
            var lastname = binding.edSecondname.text.toString()
            var contactNumber = binding.edContactnumber.text.toString()
            var description = binding.edDiscription.text.toString()

            if( firstname.isEmpty() || lastname.isEmpty() || contactNumber.isEmpty() || description.isEmpty()) {
                if(firstname.isEmpty()) {
                    binding.edFirstname.error = "Please enter first name"
                }

                if(lastname.isEmpty()) {
                    binding.edSecondname.error = "Please enter last name"
                }

                if(contactNumber.isEmpty()) {
                    binding.edContactnumber.error = "Please enter contact number"
                }
                if(description.isEmpty()) {
                    binding.edDiscription.error = "Please enter description"
                }

            } else if ( contactNumber.length != 10 ) {
                binding.edContactnumber.error = "Contact number is not valid"
            } else {
                //update record
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["fname"] = firstname
                map["lname"] = lastname
                map["dis"] = description
                map["phone"] = contactNumber


                //update database from hashMap
                databaseRef.child(uid).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, BuyerProfile::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}