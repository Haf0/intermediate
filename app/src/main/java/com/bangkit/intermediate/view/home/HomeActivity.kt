package com.bangkit.intermediate.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.intermediate.R
import com.bangkit.intermediate.databinding.ActivityHomeBinding
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.preference.ViewModelFactory
import com.bangkit.intermediate.view.addStory.AddStoryActivity
import com.bangkit.intermediate.view.first.FirstActivity
import com.bangkit.intermediate.view.maps.MapsActivity
import com.bangkit.intermediate.viewmodel.HomeViewModel.HomeVM
import com.bangkit.intermediate.viewmodel.HomeViewModel.StoryViewModel
import com.bangkit.intermediate.viewmodel.HomeViewModel.StoryViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeVM: HomeVM
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var storiesAdapter: StoriesAdapter
    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(this, SessionPreference.getInstance(dataStore))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.title_home)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.rvStories.setHasFixedSize(true)
        setAdapter()


        homeVM = ViewModelProvider(
            this,
            ViewModelFactory(SessionPreference.getInstance(dataStore))
        )[HomeVM::class.java]


        binding.fab.setOnClickListener {
            finish()
            startActivity(Intent(this,AddStoryActivity::class.java))
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.id_changelanguage ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.id_exit ->{
                homeVM.logout()
                startActivity(Intent(this,FirstActivity::class.java))
                finish()
                true
            }
            R.id.id_maps->{
                startActivity(Intent(this,MapsActivity::class.java))
                true
            }
            else -> false

        }
    }

    fun setAdapter(){
        storiesAdapter = StoriesAdapter()
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.adapter = storiesAdapter
        storyViewModel.getAllStory.observe(this){
            storiesAdapter.submitData(lifecycle,it)
        }
    }
}