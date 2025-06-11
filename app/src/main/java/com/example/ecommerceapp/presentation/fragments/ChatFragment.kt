package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentChatBinding
import com.example.ecommerceapp.presentation.adapters.ChatAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    lateinit var binding :  FragmentChatBinding
    var chatAdapter : ChatAdapter? = null
    val viewModel by viewModels<ChatViewModel>()
    val args : ChatFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.getMessages(args.chatdata.productId,args.chatdata.ownerId)
        observe()
        onCLick()
        setData()
    }

    private fun setData(){
        binding.productOwnerImg.setImageResource(args.chatdata.ownerImg)
        binding.productOwnerName.text = args.chatdata.ownerName
    }

    private fun setupAdapter(){
        chatAdapter = ChatAdapter()
        binding.chatRecView.adapter = chatAdapter
    }
    private fun observe(){
        viewModel.messages.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {
                    binding.loading.setGone()
                    chatAdapter?.updateList(it.data)
                }
                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> binding.loading.show()


            }
        }
        viewModel.isMessageSent.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {
                    binding.loading.setGone()
                    binding.chatEditText.text.clear()
                }
                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> binding.loading.show()
            }

        }
    }
    private fun onCLick(){
        binding.sendIconCard.setOnClickListener {
            val message = binding.chatEditText.text.toString()
            viewModel.sendMessage(message,args.chatdata.ownerId,args.chatdata.productId)
        }
    }

}