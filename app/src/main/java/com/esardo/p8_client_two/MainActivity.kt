package com.esardo.p8_client_two

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esardo.p8_client_two.adapter.MessageAdapter
import com.esardo.p8_client_two.databinding.ActivityMainBinding
import com.esardo.p8_client_two.model.Message
import com.esardo.p8_client_two.viewmodel.CViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: CViewModel

    private lateinit var adapter: MessageAdapter
    private val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()


        chatViewModel = ViewModelProvider(this)[CViewModel::class.java]

        //Observes the viewModel sendMessage function and calls the function to fill the TextArea
        chatViewModel.myMessageLD.observe(this){ message ->
            fillTextArea(message, true)
        }

        //Observes the viewModel receiveMessage function and calls the function to fill the TextArea
        chatViewModel.messageLD.observe(this){ messageList ->
            for(message:Message in messageList)
                fillTextArea(message, false)
            chatViewModel.notifyDeliver()
        }

        //Everytime that we click on the send button we pass the text
        //written in the editText to the function sendMessage from the viewModel
        binding.ivSend.setOnClickListener {
            if(!binding.etMessage.text.isNullOrEmpty()){
                val date = chatViewModel.getDate()
                val txt = binding.etMessage.text.toString()
                val message = Message(txt, date, true)
                chatViewModel.sendMessage(message)
            }
            hideKeyboard()
        }

    }

    private fun initRecyclerView() {
        adapter = MessageAdapter(messageList)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = adapter
    }

    //Function to fill the TextArea
    private fun fillTextArea(message:Message, isOwn:Boolean = true) {
        val messagelist = mutableListOf<Message>()
        messagelist.add(message)
        if(messagelist.isNotEmpty()){
            messageList.addAll(messagelist)
            binding.rvChat.scrollToPosition(adapter.itemCount - 1)
        }
        binding.etMessage.setText("")
        hideKeyboard()
    }

    //Function to hide Keyboard
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}