package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.ChatItemBinding
import com.example.ecommerceapp.presentation.uimodels.ChatMessageUiModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    val messageList = arrayListOf<ChatMessageUiModel>()

    inner class ChatViewHolder(val binding : ChatItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChatViewHolder {
             val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int,
    ) {
            holder.binding.chat = messageList[position]
    }

    override fun getItemCount(): Int {
            return messageList.size
    }
    fun updateList(newList: List<ChatMessageUiModel>) {
        val diffCallback = GenericDiffUtil(
            oldList = messageList,
            newList = newList,
            areItemsSame = { old, new -> old.id == new.id },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        messageList.clear()
        messageList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}