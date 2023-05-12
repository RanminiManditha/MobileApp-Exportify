package com.example.exportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exportify.Adapters.SeviceGigsAdapter
import com.example.exportify.databinding.ActivityMyServiceGigsBinding
import com.example.exportify.models.ServiceGig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Locale


//This activity display current exporters service gigs.
class MyServiceGigs : AppCompatActivity() {
    private lateinit var binding: ActivityMyServiceGigsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var mList = ArrayList<ServiceGig>()
    private lateinit var adapter: SeviceGigsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMyServiceGigsBinding.inflate(layoutInflater)//binding is used to explicit the findViewByID
        setContentView(binding.root)


        //search Function for service gigs
        recyclerView = binding.recyclerview
        searchView = binding.searchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


        //initialize variables and database
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("service_gigs")

        var recyclerView = binding.recyclerview

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = SeviceGigsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object : SeviceGigsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, UpdateServiceGig::class.java).also {
                    it.putExtra("topic", mList[position].topic)
                    it.putExtra("type", mList[position].type)
                    it.putExtra("des", mList[position].description)
                    it.putExtra("noOfUnits", mList[position].noOfUnits)
                    it.putExtra("pricePerUnit", mList[position].price)
                    it.putExtra("reqId", mList[position].id)
                    startActivity(it)
                }
            }
        })
}
    // search filteration is used by the topic/name of the service gigs
    private fun filterList(query: String?) {
        if(query!= null){
            val filteredList = ArrayList<ServiceGig>()
            for (i in mList){
                if (i.topic?.lowercase(Locale.ROOT)?.contains(query?.lowercase(Locale.ROOT) ?: "") == true){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"NO data found",Toast.LENGTH_SHORT).show()
            }else{
                adapter.setFilteredList(filteredList)
            }
        }
    }

    //Retrieve Data(service Gigs|) belongs to the current user only
    private fun retrieveData() {
        val query = databaseRef.orderByChild("uid").equalTo(uid)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.totCount.text =snapshot.childrenCount.toString()

                mList.clear()
                for ( snapshot in snapshot.children){
                    val gig = snapshot.getValue(ServiceGig::class.java)!!
                    if( gig != null){
                        mList.add(gig)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyServiceGigs, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }






}