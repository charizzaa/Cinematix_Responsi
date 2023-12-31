package com.example.cinematix_responsi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematix_responsi.database.ItemDao
import com.example.cinematix_responsi.database.ItemRoomDatabase
import com.example.cinematix_responsi.databinding.FragmentUserHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentUserHomeBinding
    private lateinit var database : DatabaseReference
    private lateinit var recyclerViewItem : RecyclerView
    private lateinit var itemAdapter : RecyclerViewAdapterUser
//    private lateinit var itemList : ArrayList<ItemData>

    private lateinit var dao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserHomeBinding.inflate(layoutInflater)

        recyclerViewItem = binding.userRecyclerView
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(requireActivity())

        itemAdapter = RecyclerViewAdapterUser(emptyList())
        recyclerViewItem.adapter = itemAdapter



        // Initialize Room database
        dao = ItemRoomDatabase.getDatabase(requireContext()).dao()

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference("Admin")

        // Fetch data from Firebase and update itemList
        fetchFilmFromFirebase()


        return binding.root
    }


    private fun fetchFilmFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filmList = mutableListOf<ItemDatabase>()

                for (dataSnapshot in snapshot.children) {
                    val filmEntity = dataSnapshot.getValue(ItemDatabase::class.java)
                    Log.e("eror",dataSnapshot.toString())
                    filmEntity?.let { filmList.add(it) }
                }

                // Update Room database with the new data from Firebase
                GlobalScope.launch(Dispatchers.IO) {
                    dao.deleteAllFilm()
                    dao.insertFilm(filmList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed
            }
        })

        // Observe changes in the LiveData from Room and update the adapter
        dao.getAllFilm().observe(viewLifecycleOwner, Observer { films ->
            itemAdapter.updateData(films)
        })




    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}