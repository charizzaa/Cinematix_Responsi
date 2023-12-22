package com.example.cinematix_responsi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematix_responsi.databinding.ActivityAdminMainHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class AdminMainHome : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAdminMainHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var itemAdapter: RecyclerViewAdapterAdmin
    private lateinit var itemList: ArrayList<Item>
    private lateinit var recyclerViewItem: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menginisialisasi variabel recyclerViewItem dengan elemen RecyclerView dari layout.
        recyclerViewItem = binding.adminRecyclerView
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(this)

        // Inisialisasi itemAdapter dengan RecyclerViewAdapterAdmin yang menggunakan itemList.
        // Menetapkan adapter RecyclerView untuk menggunakan itemAdapter.
        itemList = arrayListOf()
        itemAdapter = RecyclerViewAdapterAdmin(itemList)
        recyclerViewItem.adapter = itemAdapter

        with(binding) {
            adminAddButton.setOnClickListener {
                startActivity(Intent(this@AdminMainHome, AdminMovieAdd::class.java))
            }
            auth = Firebase.auth
            logoutButton.setOnClickListener {

                val sharedPreference = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                sharedPreference.edit().putBoolean("isLoggedIn", false).apply()

                auth.signOut()
                val intent = Intent(this@AdminMainHome, SplashActivity::class.java)
                startActivity(intent)
            }


            }

            // Menginisialisasi objek DatabaseReference untuk "Admin" di Firebase Realtime Database.
            database = FirebaseDatabase.getInstance().getReference("Admin")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Clear the existing list
                    itemList.clear()

                    // Iterate through the snapshot and add items to the list
                    for (dataSnapshot in snapshot.children) {
                        val item = dataSnapshot.getValue(Item::class.java)
                        if (item != null) {
                            itemList.add(item)
                        }
                    }

                    // Notify the adapter that the data has changed
                    itemAdapter.notifyDataSetChanged()
                    Log.d("msg", itemList.size.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors if needed
                    Toast.makeText(this@AdminMainHome, "Data retrieval failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            })


        }
    }

