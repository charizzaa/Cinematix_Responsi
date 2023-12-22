package com.example.cinematix_responsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cinematix_responsi.databinding.ActivityUserMainMenuBinding

class UserMainMenu : AppCompatActivity() {
    private lateinit var binding: ActivityUserMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        // Menetapkan listener untuk item yang dipilih pada BottomNavigationView
        binding.bottomNavBar.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navProfile -> replaceFragment(ProfileFragment())

                else -> {}
            }
            true
        }
    }

    // Fungsi untuk menggantikan fragment saat ini dengan fragment yang diberikan
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}