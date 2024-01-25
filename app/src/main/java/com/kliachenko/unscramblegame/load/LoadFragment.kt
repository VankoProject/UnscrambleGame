package com.kliachenko.unscramblegame.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kliachenko.unscramblegame.ProvideViewModel
import com.kliachenko.unscramblegame.databinding.FragmentLoadBinding

class LoadFragment : Fragment() {

    private var _binding: FragmentLoadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel =
            (requireActivity() as ProvideViewModel).viewModel(clasz = LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(savedInstanceState == null)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}