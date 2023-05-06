package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exportify.databinding.ActivityBuyerRegistrationBinding
import com.example.exportify.models.UserModel
import com.example.exportify.models.validations.ValidationResult
import com.example.exportify.models.validations.userRegValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Buyer_registration : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerRegistrationBinding
    private var validityCount = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storageReference: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        binding.btnCancel.setOnClickListener {
            intent = Intent(applicationContext, User_direction::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            validateFormData()
            if (validityCount == 11) {
                createUser()
            }
        }
    }

    private fun createUser() {

        val fname = binding.etFName.text.toString()
        val lname = binding.etLName.text.toString()
        val pwd = binding.etPwd.text.toString()
        val confirmPwd = binding.etConPwd.text.toString()
        val email = binding.etEmail.text.toString()
        val contactNo = binding.etContactNo.text.toString()
        val country = binding.spinner.selectedItem.toString()
        val state = binding.etState.text.toString()
        val zipcode = binding.etZipCode.text.toString()
        val des = binding.etDes.text.toString()

        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {

            if (it.isSuccessful) {
                //store user details in the database
                val databaseRef =
                    database.reference.child("users").child(auth.currentUser!!.uid)
                val user= UserModel(fname,lname,pwd,confirmPwd,email,contactNo,country,state,zipcode,des,"buyer",auth.currentUser!!.uid)
                databaseRef.setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, Login::class.java)
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
                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }



    private fun validateFormData() {
        validityCount = 0

        //bind data to variables
        val fname = binding.etFName.text.toString()
        val lname = binding.etLName.text.toString()
        val pwd = binding.etPwd.text.toString()
        val confirmPwd = binding.etConPwd.text.toString()
        val email = binding.etEmail.text.toString()
        val contactNo = binding.etContactNo.text.toString()
        val country = binding.spinner.selectedItem.toString()
        val state = binding.etState.text.toString()
        val zipcode = binding.etZipCode.text.toString()
        val des = binding.etDes.text.toString()

        //create validations object
        var formVal = userRegValidation(fname, lname, pwd, email, contactNo, state, zipcode, des)

        when (formVal.validatefName()) {
            is ValidationResult.Empty -> {
                binding.etFName.error =
                    (formVal.validatefName() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {}
        }

        when (formVal.validatelName()) {
            is ValidationResult.Empty -> {
                binding.etLName.error =
                    (formVal.validatelName() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {}
        }

        when (formVal.validatePwd()) {
            is ValidationResult.Empty -> {
                binding.etPwd.error = (formVal.validatePwd() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etPwd.error =
                    (formVal.validatePwd() as ValidationResult.Invalid).errorMessage
            }
        }

        when (formVal.validateEmail()) {
            is ValidationResult.Empty -> {
                binding.etEmail.error =
                    (formVal.validateEmail() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etEmail.error =
                    (formVal.validateEmail() as ValidationResult.Invalid).errorMessage
            }
        }

        when (formVal.validateContactNo()) {
            is ValidationResult.Empty -> {
                binding.etContactNo.error =
                    (formVal.validateContactNo() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etContactNo.error =
                    (formVal.validateContactNo() as ValidationResult.Invalid).errorMessage
            }
        }

        when (formVal.validateState()) {
            is ValidationResult.Empty -> {
                binding.etState.error =
                    (formVal.validateState() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etState.error =
                    (formVal.validateState() as ValidationResult.Invalid).errorMessage
            }
        }

        when (formVal.validateZipCode()) {
            is ValidationResult.Empty -> {
                binding.etZipCode.error =
                    (formVal.validateZipCode() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etZipCode.error =
                    (formVal.validateZipCode() as ValidationResult.Invalid).errorMessage
            }
        }

        when (formVal.validateDescription()) {
            is ValidationResult.Empty -> {
                binding.etDes.error =
                    (formVal.validateDescription() as ValidationResult.Empty).errorMessage
            }
            is ValidationResult.Valid -> {
                validityCount++
            }
            is ValidationResult.Invalid -> {
                binding.etDes.error =
                    (formVal.validateDescription() as ValidationResult.Invalid).errorMessage
            }
        }

        if (binding.checkBox.isChecked) {
            validityCount++
        } else {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
        }

        if (binding.spinner.selectedItemPosition != 0) {
            validityCount++
        } else {
            Toast.makeText(this, "Please select your country.", Toast.LENGTH_SHORT).show()
        }

        //validate passwords
        if (confirmPwd == pwd) {
            validityCount++

        } else {
            binding.etConPwd.error = "Passwords do not match. Please try again."
        }

    }


}