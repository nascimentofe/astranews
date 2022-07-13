package com.fenascimento.astranews.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fenascimento.astranews.R
import com.fenascimento.astranews.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private val binding : FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        initBinding()
        initAdapter()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initAdapter() {

    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}