package com.kliachenko.unscramblegame.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.ProvideViewModel
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel = viewModel(MainViewModel::class.java)

        if (savedInstanceState == null) {
            val screen = viewModel.screen()
            screen.show(binding.container.id, supportFragmentManager)
        }
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        return (application as ProvideViewModel).viewModel(clasz)
    }
}