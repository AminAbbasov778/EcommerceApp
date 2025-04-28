package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          clickOn()


    }


    private fun clickOn(){
        binding.getStartedButton.setOnClickListener(){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignupFragment())
        }
        binding.haveAccountText.setOnClickListener(){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginFragment())
        }
        binding.forwardIconCard.setOnClickListener(){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginFragment())

        }
    }

}