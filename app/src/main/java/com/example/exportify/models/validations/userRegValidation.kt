package com.example.exportify.models.validations

class userRegValidation(
    private var fName: String,
    private var lName: String,
    private var pwd: String,
    private var email: String,
    private var contactNo: String,
    private var state: String,
    private var zipCode: String,
    private var description: String
) {
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun validatefName(): ValidationResult {
        return if(fName.isEmpty()){
            ValidationResult.Empty("Please enter first name")
        } else {
            ValidationResult.Valid
        }
    }
    fun validatelName(): ValidationResult {
        return if(lName.isEmpty()){
            ValidationResult.Empty("Please enter last name")
        } else {
            ValidationResult.Valid
        }
    }

    fun validatePwd(): ValidationResult {
        return if(pwd.isEmpty()){
            ValidationResult.Empty("Please enter Password")
        } else if(pwd.length <= 5) {
            ValidationResult.Invalid("Password should have least 6 characters")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateEmail(): ValidationResult {
        return if(email.isEmpty()){
            ValidationResult.Empty("Email address should not be empty")
        } else if(!email.matches(emailPattern.toRegex())) {
            ValidationResult.Invalid("Email format is wrong")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateContactNo(): ValidationResult {
        return if(contactNo.isEmpty()){
            ValidationResult.Empty("Please enter Contact number")
        } else if(contactNo.length != 10) {
            ValidationResult.Invalid("Enter a valid contact number")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateState(): ValidationResult {
        return if(state.isEmpty()){
            ValidationResult.Empty("Please enter state")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateZipCode(): ValidationResult {
        return if(zipCode.isEmpty()){
            ValidationResult.Empty("Please enter zip code")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDescription(): ValidationResult {
        return if(description.isEmpty()){
            ValidationResult.Empty("Please enter description")
        } else {
            ValidationResult.Valid
        }
    }










}