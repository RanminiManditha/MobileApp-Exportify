package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exportify.Adapters.BuyerRequestsAdapter
import com.example.exportify.databinding.ActivityExporterDashboardBinding
import com.example.exportify.databinding.ActivityMyRequestsBinding
import com.example.exportify.models.BuyerRequestsModel
import com.example.exportify.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private lateinit var binding: ActivityMyRequestsBinding
private lateinit var auth: FirebaseAuth
private lateinit var databaseRef: DatabaseReference
private lateinit var uid: String
private var mList = ArrayList<BuyerRequestsModel>()
private lateinit var adapter: BuyerRequestsAdapter

class MyRequests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("buyer_requests")

        var recyclerView = binding.recyclerview

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = BuyerRequestsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: BuyerRequestsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, UpdateRequest::class.java).also {
                    it.putExtra("topic", mList[position].topic)
                    it.putExtra("des", mList[position].description)
                    it.putExtra("priceRange", mList[position].priceRange)
                    it.putExtra("reqId", mList[position].id)
                    startActivity(it)
                }
            }
        })
    }
    private fun retrieveData() {
        val query = databaseRef.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.totCount.text =snapshot.childrenCount.toString()
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(BuyerRequestsModel::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyRequests, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun addDataToList(){
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
        mList.add(BuyerRequestsModel("topic", "This is des","150000 - 210000","",""))
    }
}