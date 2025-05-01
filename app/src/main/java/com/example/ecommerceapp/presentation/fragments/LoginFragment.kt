package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickOn()
        observe()
    }


    private fun clickOn() {
        binding.cancelButton.setOnClickListener() {
            findNavController().popBackStack()
        }
        binding.invisibleIcon.setOnClickListener(){
            clickInvisibleIcon()
        }
        binding.visibleIcon.setOnClickListener(){
            clickVisibleIcon()
        }
        binding.nextButton.setOnClickListener(){
            val email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            viewModel.getLoginValidationResult(email,password)
        }
    }

    private fun observe(){
        viewModel.loginValidation.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> successfulValidation(it.data)
                is UiState.Error -> failureValidation(it.message)
                else -> failureValidation(R.string.unknown_error)
            }
        }
        viewModel.loginResult.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> successfulLogin(it.data)
                is UiState.Error -> failureLogin(it.message)
                else ->  binding.loading.show()
            }
        }
    }

    fun successfulValidation(message: Int ) {
        var email = binding.emailEditText.text.toString().trim()
        var password = binding.passwordEditText.text.toString().trim()
        binding.errorInfoIcon.setGone()
        binding.errorInfoText.setGone()
        binding.successInfoIcon.show()
        binding.successInfoText.show()
        binding.successInfoText.text = getString(message)
        viewModel.loginUser(email, password)


    }

    fun failureValidation(message: Int) {
        binding.successInfoIcon.setGone()
        binding.successInfoText.setGone()
        binding.errorInfoIcon.show()
        binding.errorInfoText.show()
        binding.errorInfoText.text =getString(message)
    }


    fun successfulLogin(message: Int) {
        binding.errorInfoIcon.setGone()
        binding.errorInfoText.setGone()
        binding.successInfoIcon.show()
        binding.successInfoText.show()
        binding.loading.setGone()
        binding.successInfoText.text = getString(message)

        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    fun failureLogin(message: Int) {
        binding.successInfoIcon.setGone()
        binding.successInfoText.setGone()
        binding.errorInfoIcon.show()
        binding.errorInfoText.show()
        binding.loading.setGone()
        binding.errorInfoText.text =getString(message)

    }






    private fun clickVisibleIcon() {
        binding.invisibleIcon.show()
        binding.visibleIcon.setGone()
        passwordEdittextInvisibleInputType()
    }

    private fun clickInvisibleIcon() {
        binding.visibleIcon.show()
        binding.invisibleIcon.setGone()
        passwordEdittextVisibleInputType()
    }
    private fun passwordEdittextInvisibleInputType() {
        var text = binding.passwordEditText.text
        text?.let {
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordEditText.setSelection(it.length)
        }

    }

    private fun passwordEdittextVisibleInputType() {
        var text = binding.passwordEditText.text
        text?.let {
            binding.passwordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordEditText.setSelection(it.length)
        }
    }

}