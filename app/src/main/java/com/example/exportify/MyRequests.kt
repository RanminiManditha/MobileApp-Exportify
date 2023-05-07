package com.example.exportify

import com.example.exportify.databinding.ActivityMyRequestsBinding
import com.example.exportify.models.BuyerRequestsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private lateinit var binding: ActivityMyRequestsBinding
private lateinit var auth: FirebaseAuth
private lateinit var databaseRef: DatabaseReference
private lateinit var uid: String
private var mList = ArrayList<BuyerRequestsModel>()
private lateinit var adapter: BuyerRequestsAdapter

