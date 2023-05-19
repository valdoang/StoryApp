package com.dicoding.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.api.ApiConfig
import com.dicoding.storyapp.databinding.ActivityLoginBinding
import com.dicoding.storyapp.model.LoginResponse
import com.dicoding.storyapp.preference.LoginPreference
import com.dicoding.storyapp.utils.AuthViewModel
import com.dicoding.storyapp.view.ViewModelFactory
import com.dicoding.storyapp.view.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreference.getInstance(dataStore), this)
        )[AuthViewModel::class.java]
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.edLoginEmail.error = "Masukkan Email"
                }
                password.isEmpty() -> {
                    binding.edLoginPassword.error = "Masukkan Password"
                }
                password.length < 8 -> {
                    binding.edLoginPassword.error = "Minimal 8 Karakter"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.edLoginEmail.error = "Alamat Email Tidak Valid"
                }
                else -> {
                    showLoading(true)
                    val client = ApiConfig.getApiService().login(email,password)
                    client.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            showLoading(false)
                            val resp = response.body()
                            val token = resp?.loginResult?.token
                            if(response.isSuccessful){
                                if (token != null) {
                                    authViewModel.login(token)
                                }
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                                Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@LoginActivity, "Email dan Kata Sandi Tidak Sesuai", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
            }
        }
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.ivLogin, View.ALPHA, 1F).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1F).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1F).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1F).setDuration(500)
        val passEdit = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1F).setDuration(500)

        AnimatorSet().apply {
            playSequentially(image,title,email,emailEdit,pass,passEdit,login)
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}