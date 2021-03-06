package com.bangkit.intermediate.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.intermediate.R
import com.bangkit.intermediate.databinding.ActivityDetailBinding
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail)


        val data = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem
        with(binding){
            tvnama.text = data.name
            tvdesripsi.text = data.description
            Glide.with(this@DetailActivity)
                .load(data.photoUrl)
                .into(image)
        }



    }


    companion object{
        const val EXTRA_DATA = ""
    }
}