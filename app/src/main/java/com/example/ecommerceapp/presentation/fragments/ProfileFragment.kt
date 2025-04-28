package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.databinding.FragmentProfileBinding
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickOn()
        observe()

    }

    private fun clickOn() {
        binding.settingsImgCard.setOnClickListener() {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())

        }
    }

    private fun observe() {

        viewModel.profileData.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                   binding.loading.setGone()
                    binding.profileImg.setImageBitmap(it.data.imageBitmap)
                    binding.helloText.text = "Hello, ${it.data.username}"
                }


                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> binding.loading.show()

            }
        }

    }

}