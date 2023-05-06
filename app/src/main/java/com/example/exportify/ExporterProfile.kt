package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityBuyerProfileUpdateBinding
import com.example.exportify.databinding.ActivityExporterProfileBinding
import com.example.exportify.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ExporterProfile : AppCompatActivity() {
    private lateinit var binding: ActivityExporterProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExporterProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")

        databaseRef.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(UserModel::class.java)!!

                binding.firstName.text = user.fname
                binding.lastName.text = user.lname
                binding.email.text = user.email
                binding.phoneNo.text = user.phone
                binding.country.text = user.country
                binding.state.text = user.state
                binding.zipcode.text = user.zipcode
                binding.exporterDescription.text=user.dis

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ExporterProfile, "Failed to fetch user", Toast.LENGTH_SHORT).show()

            }
        })

        binding.editBtn.setOnClickListener {
            intent = Intent(applicationContext, ExporterProfileUpdate::class.java).also {
                it.putExtra("fname", user.fname)
                it.putExtra("lname", user.lname)
                it.putExtra("mobile", user.phone)
                it.putExtra("des", user.dis)
                startActivity(it)
            }

        }

        binding.btnMyGigs.setOnClickListener {
            intent = Intent(applicationContext, MyServiceGigs::class.java)
            startActivity(intent)
        }


        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            //redirect user to login page
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            //toast message
            Toast.makeText(this, "Signned out", Toast.LENGTH_SHORT).show()
        }

        binding.deleteBtn.setOnClickListener {
            var currUser = auth.currentUser
            currUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Failed to delete the account", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}