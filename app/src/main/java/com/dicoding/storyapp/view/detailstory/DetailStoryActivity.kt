package com.dicoding.storyapp.view.detailstory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.storyapp.databinding.ActivityDetailStoryBinding
import com.dicoding.storyapp.preference.LoginPreference
import com.dicoding.storyapp.utils.AuthViewModel
import com.dicoding.storyapp.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "detail_story")

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var viewModel: DetailStoryViewModel
    private lateinit var authViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = DetailStoryViewModel()
        authViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreference.getInstance(dataStore), this)
        )[AuthViewModel::class.java]

        val id = intent.getStringExtra(EXTRA_ID)

        val bundle = Bundle()
        bundle.putString(EXTRA_ID, id)

        authViewModel.getUser().observe(this){user ->
            if (id != null){
                viewModel.setStoryDetail(user.token, id)
                showLoading(true)
            }
        }

        viewModel.getStoryDetail().observe(this){
            if (it != null){
                binding.apply {
                    Glide.with(this@DetailStoryActivity)
                        .load(it.photoUrl)
                        .into(ivDetailPhoto)
                    tvDetailName.text = it.name
                    tvDetailDescription.text = it.description
                }
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}