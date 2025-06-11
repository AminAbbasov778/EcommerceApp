package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentSettingsBinding
import com.example.ecommerceapp.presentation.adapters.AccountSettingsAdapter
import com.example.ecommerceapp.presentation.adapters.PersonalSettingsAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    val viewModel by viewModels<SettingsViewModel>()
    var personalSettingsAdapter: PersonalSettingsAdapter? = null
    var accountSettingsAdapter: AccountSettingsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observe()
        clickOn()
    }


    fun observe() {
        viewModel.logout.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loading.setGone()
                    findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToStartFragment())

                }

                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }

                else -> binding.loading.show()
            }
        }
        viewModel.personalSettingsItem.observe(viewLifecycleOwner){
            personalSettingsAdapter?.updateList(it)
        }
        viewModel.accountSettingsItem.observe(viewLifecycleOwner) {
            accountSettingsAdapter?.updateList(it)
        }
    }

    private fun clickOn() {
        binding.logoutButton.setOnClickListener() {
            viewModel.logout()
        }
    }

    private fun setupAdapters() {
        personalSettingsAdapter = PersonalSettingsAdapter(onSettingsClick = {setting ->
            when(setting){
                R.string.profile -> findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment())
            }
        })
        binding.personalSettingsRecView.adapter = personalSettingsAdapter
        accountSettingsAdapter = AccountSettingsAdapter(onSettingsClick = {setting ->
            when(setting.settingName){
                R.string.language -> findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLanguageFragment())
            }})
        binding.accountSettingsRecView.adapter = accountSettingsAdapter

    }


}