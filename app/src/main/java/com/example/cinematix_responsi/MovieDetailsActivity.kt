package com.example.cinematix_responsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cinematix_responsi.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengakses elemen UI
        val detailTitle = binding.detailTitle
        val detailAuthor = binding.detailAuthor
        val detailDescription = binding.detailDescription

        // Mendapatkan URL gambar dari intent dan menggunakan Glide untuk menampilkan gambar
        val originalImageUrl = intent.getStringExtra("imgId")
        Glide.with(this@MovieDetailsActivity)
            .load(originalImageUrl)
            .skipMemoryCache(true) // Skip caching in memory
            .diskCacheStrategy(DiskCacheStrategy.NONE) // Skip caching on disk
            .into(binding.detailImg)

        // Mengisi teks pada elemen UI dengan data yang diterima dari intent
        detailTitle.setText(intent.getStringExtra("title"))
        detailAuthor.setText(intent.getStringExtra("author"))
        detailDescription.setText(intent.getStringExtra("description"))

        binding.back.setOnClickListener{
            startActivity(Intent(this@MovieDetailsActivity,UserMainMenu::class.java))
        }

    }
}