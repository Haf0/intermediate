package com.bangkit.intermediate.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.databinding.ItemRowStoryBinding
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bangkit.intermediate.view.detail.DetailActivity
import com.bumptech.glide.Glide

class StoriesAdapter(private val listStories: List<ListStoryItem?>?):RecyclerView.Adapter<StoriesAdapter.storiesAdapter>() {
    inner class storiesAdapter (val binding: ItemRowStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): storiesAdapter {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return storiesAdapter(binding)
    }

    override fun onBindViewHolder(holder: storiesAdapter, position: Int) {
        val story = listStories!![position]
        holder.binding.tvStorytittle.text = story!!.name
        Glide.with(holder.itemView.context)
            .load(story.photoUrl)
            .fitCenter()
            .into(holder.binding.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, story)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = listStories!!.size
}