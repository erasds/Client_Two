package com.esardo.p8_client_two.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esardo.p8_client_two.R
import com.esardo.p8_client_two.databinding.TextChatItemBinding
import com.esardo.p8_client_two.model.Message

class MessageAdapter(private val messageList: MutableList<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(layoutInflater.inflate(R.layout.text_chat_item, parent, false))
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = messageList[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = TextChatItemBinding.bind(view)
        fun bind (message: Message) {
            //if is our own message it will align it to the end of the view
            if(message.isOwn) {
                binding.tvDate.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                binding.tvMessage.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                binding.tvMessage.setTextAppearance(R.style.TextChatOwn)
            } //if it's from the other app it will align it to the start and change style
            else{
                binding.tvDate.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                binding.tvMessage.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                binding.tvMessage.setTextAppearance(R.style.TextChat)
            }
            //tvDate will always have the app color and bold text
            binding.tvDate.setTextAppearance(R.style.TextChatOwn)
            binding.tvDate.setTypeface(null, Typeface.BOLD)
            //fill TextViews
            binding.tvDate.text = message.date
            binding.tvMessage.text = message.text
        }
    }
}