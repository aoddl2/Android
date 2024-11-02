package com.example.project2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("title")
        val address = intent.getStringExtra("address")
        val imageUrl = intent.getStringExtra("imageUrl")

        val textViewTitle: TextView = findViewById(R.id.textViewTitle)
        val textViewAddress: TextView = findViewById(R.id.textViewAddress)
        val imageView: ImageView = findViewById(R.id.imageView)
        val buttonBack: Button = findViewById(R.id.buttonBack)

        textViewTitle.text = title
        textViewAddress.text = address

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        }

        buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
