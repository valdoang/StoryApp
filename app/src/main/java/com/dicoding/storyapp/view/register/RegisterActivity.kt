package com.dicoding.storyapp.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.api.ApiConfig
import com.dicoding.storyapp.databinding.ActivityRegisterBinding
import com.dicoding.storyapp.model.RegisterResponse
import com.dicoding.storyapp.view.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
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


    private fun setupAction() {

        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.edRegisterName.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = "Masukkan Email"
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = "Masukkan Password"
                }
                password.length < 8 -> {
                    binding.edRegisterPassword.error = "Minimal 8 Karakter"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.edRegisterEmail.error = "Alamat Email Tidak Valid"
                }
                else -> {
                    showLoading(true)
                    val client = ApiConfig.getApiService().register(name,email,password)
                    client.enqueue(object : Callback<RegisterResponse>{
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            showLoading(false)
                            if(response.isSuccessful){
                                startActivity(Intent(this@RegisterActivity, WelcomeActivity::class.java))
                                finish()
                                Toast.makeText(this@RegisterActivity, "Akun Berhasil Dibuat", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this@RegisterActivity, "Email Sudah Ada Sebelumnya", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                }
            }
        }
    }

    private fun playAnimation() {

        val image = ObjectAnimator.ofFloat(binding.ivSignup, View.ALPHA, 1F).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1F).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1F).setDuration(500)
        val nameEdit = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1F).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1F).setDuration(500)
        val passEdit = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1F).setDuration(500)

        AnimatorSet().apply {
            playSequentially(image,title,name,nameEdit,email,emailEdit,pass,passEdit,signup)
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}