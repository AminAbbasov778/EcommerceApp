package com.example.ecommerceapp.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapp.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
         navigation()

    }

    private  fun navigation(){

        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@SplashActivity,MainActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }
}