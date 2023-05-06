package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityPaymentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentDetails : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("payments")

        binding.btnPaymentProceed.setOnClickListener {
            var name = binding.edtName.text.toString()
            var number = binding.edtNumber.text.toString()
            var email = binding.edtEmail.text.toString()
            var address = binding.edtAddress.text.toString()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (name.isEmpty() || number.isEmpty() || email.isEmpty() || address.isEmpty()) {
                if (name.isEmpty()) {
                    binding.edtName.error = "Enter your name"
                }
                if (number.isEmpty()) {
                    binding.edtNumber.error = "Enter mobile number"
                }
                if (email.isEmpty()) {
                    binding.edtEmail.error = "Enter service email"
                }
                if (address.isEmpty()) {
                    binding.edtAddress.error = "Enter your address"
                }

            }   else if ( number.length != 10 ) {
                binding.edtNumber.error = "Enter your mobile number"
            }   else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.edtEmail.error = "Enter a valid email address"
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if ( !binding.checkBox.isChecked) {
                Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            }   else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val request = com.example.exportify.models.PaymentDetails( name, number, email,address,id,)
                databaseRef.child(id).setValue(request).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, Buyer_dashboard::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Payment details added", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, CardDetails::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}