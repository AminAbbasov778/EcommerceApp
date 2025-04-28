package com.example.ecommerceapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.databinding.FragmentSearchBinding
import com.example.ecommerceapp.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel by viewModels<SearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditText()

        observe()


    }

    private fun setupEditText() {
        binding.searchEdittext.post {
            binding.searchEdittext.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.searchEdittext,
                InputMethodManager.SHOW_IMPLICIT
            )
        }

        binding.searchEdittext.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEdittext.text.toString()
                viewModel.sendQuery(query)
                true
            } else {
                false
            }
        }


    }

    fun observe() {
        viewModel.searchQuery.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                        it,
                        null
                    )
                )
                viewModel.clearData()
            }


        }
    }

}