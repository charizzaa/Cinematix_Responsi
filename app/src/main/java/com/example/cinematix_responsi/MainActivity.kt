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
    private lateinit var binding : ActivityMainBinding
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val selectedTab = intent.getIntExtra("SELECTED_TAB", 0)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", null)
        val savedUsername = sharedPreferences.getString("username", null)
        if (savedEmail != null) {
            if (savedEmail == "admincaca@gmail.com") {
                // User is an admin, redirect to Admin activity
                startActivity(Intent(this@MainActivity, AdminMainHome::class.java))
            } else {
                // User is not an admin, redirect to User activity
                startActivity(Intent(this@MainActivity, UserMainMenu::class.java))
            }
        }
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
        menuInflater.inflate(R.menu.menu_loginregister, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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