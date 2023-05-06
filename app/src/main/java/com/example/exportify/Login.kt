package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.LoginBinding
import com.example.exportify.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Login : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        if( auth.currentUser != null ) {
            checkUserType()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPwd.text.toString()

            if(email.isEmpty() || password.isEmpty()){

                if(email.isEmpty()){
                    binding.etEmail.error = "Enter your email address"
                }
                if(password.isEmpty()){
                    binding.etPwd.error = "Enter your password"
                }
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            } else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.etEmail.error = "Enter a valid email address"
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length <=5){
                //validate passwords
                binding.etPwd.error = "Password must be at least 6 characters."
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            } else{
                //Log in
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        checkUserType()
                    }else{
                            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            //Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, User_direction::class.java)
            startActivity(intent)
        }
    }

    private fun checkUserType() {
        databaseReference.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(UserModel::class.java)!!

                if( user.type == "buyer") {
                    intent = Intent(applicationContext, Buyer_dashboard::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(applicationContext, Exporter_dashboard::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Login, "Failed to retrieve user", Toast.LENGTH_SHORT).show()
            }
        })
    }

}