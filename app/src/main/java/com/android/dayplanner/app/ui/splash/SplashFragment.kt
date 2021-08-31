package com.android.dayplanner.app.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.dayplanner.app.R

class SplashFragment : Fragment(R.layout.splash_fragment) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.doInitialization {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}