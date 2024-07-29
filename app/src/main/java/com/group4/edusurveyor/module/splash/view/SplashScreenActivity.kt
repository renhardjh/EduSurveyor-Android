package com.group4.edusurveyor.module.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.group4.edusurveyor.databinding.SplashScreenActivityBinding
import com.group4.edusurveyor.module.auth.view.LoginActivity
import com.group4.edusurveyor.module.map.view.MapsActivity
import com.group4.edusurveyor.module.splash.viewmodel.SplashViewModel
import com.group4.edusurveyor.repository.local.helper.UserDatabase

class SplashScreenActivity: AppCompatActivity() {
    private lateinit var binding: SplashScreenActivityBinding
    private val viewModel = SplashViewModel(UserDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = SplashScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        Handler(Looper.getMainLooper()).postDelayed({
            val user = viewModel.getUserData().firstOrNull()
            user?.let {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                finish()
            } ?: run {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}