package com.kliachenko.unscramblegame.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.unscramblegame.GameApp
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel:MainViewModel = (application as GameApp).viewModel(MainViewModel::class.java)




    }
}