package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityCardDetailsBinding
import com.example.exportify.models.CardDetailsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CardDetails : AppCompatActivity() {
    private lateinit var binding: ActivityCardDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cards")


        binding.btnCardProceed.setOnClickListener {
            var cardNo = binding.etCardNo.text.toString()
            var expDate = binding.etExpDate.text.toString()
            var cvv = binding.etCVV.text.toString()

            if( cardNo.isNotEmpty() || expDate.isNotEmpty() || cvv.isNotEmpty() ) {
                var id = databaseRef.push().key!!
                val cardDetails= CardDetailsModel(cardNo, expDate, cvv, id)
                databaseRef.child(id).setValue(cardDetails).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, Buyer_dashboard::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong, try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                if (cardNo.isEmpty()) {
                    binding.etCardNo.error = "Enter card number"
                }
                if (expDate.isEmpty()) {
                    binding.etExpDate.error = "Enter expiration date"
                }
                if (cvv.isEmpty()) {
                    binding.etCVV.error = "Enter CVV"
                }
            }
        }
    }
}