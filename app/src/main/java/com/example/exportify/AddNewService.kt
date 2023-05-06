package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityAddNewServiceBinding
import com.example.exportify.models.ServiceGig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewService : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewServiceBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("service_gigs")

        binding.btnInsert.setOnClickListener {
            val topic = binding.edServiceTopic.text.toString()
            val type = binding.edServiceType.text.toString()
            val des = binding.edDescription.text.toString()
            val noOfUnits = binding.edUnits.text.toString()
            val price = binding.edPrice.text.toString()


            //checking if the input fields are empty
            if (topic.isEmpty() || type.isEmpty() || des.isEmpty() || noOfUnits.isEmpty() || price.isEmpty()) {
                if (topic.isEmpty()) {
                    binding.edServiceTopic.error = "Enter sevice topic"
                }
                if (type.isEmpty()) {
                    binding.edServiceType.error = "Enter service type"
                }
                if (des.isEmpty()) {
                    binding.edDescription.error = "Enter service description"
                }
                if (noOfUnits.isEmpty()) {
                    binding.edUnits.error = "Enter number of units unit available"
                }
                if (price.isEmpty()) {
                    binding.edPrice.error = "Enter price"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }

            //validate noOfUnits
            else if (noOfUnits.length == 0) {
                binding.edUnits.error = "Enter a valid phone number"
            } else {
                var id = databaseRef.push().key!!
                val servicegig: ServiceGig =
                    ServiceGig(topic, type, des, noOfUnits, price, id, uid,)
                databaseRef.child(id).setValue(servicegig).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, Exporter_dashboard::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong, try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}