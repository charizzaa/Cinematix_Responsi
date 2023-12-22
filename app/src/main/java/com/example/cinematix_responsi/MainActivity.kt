package com.example.cinematix_responsi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.cinematix_responsi.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    // Deklarasi variabel
    private lateinit var binding : ActivityMainBinding
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menginisialisasi tabLayout dan viewPager
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        // Mendapatkan nilai selectedTab dari intent
        val selectedTab = intent.getIntExtra("SELECTED_TAB", 0)

        // Mengakses Shared Preferences untuk mendapatkan data pengguna yang sudah login
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val loggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val userType = sharedPreferences.getString("userType", "guest")

        // Mengecek apakah ada pengguna yang sudah login
        if (loggedIn){
            if(userType.equals("admin")){
                startActivity(Intent(this,AdminMainHome::class.java))
            }else {
                startActivity(Intent(this,UserMainMenu::class.java))
            }

        }

        // Mengatur adapter untuk viewPager dan konfigurasi tabLayout
        with(binding) {
            viewPager.adapter = TabAdapter(this@MainActivity)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Login"
                    1 -> "Register"
                    else -> ""
                }
            }.attach()
            viewPager.setCurrentItem(selectedTab, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Menampilkan menu pada toolbar
        menuInflater.inflate(R.menu.menu_loginregister, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menangani item yang dipilih pada toolbar
        return when(item.itemId) {
            R.id.action_login -> {
                Toast.makeText(this@MainActivity,"Login", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_register -> {
                Toast.makeText(this@MainActivity,"Register", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}