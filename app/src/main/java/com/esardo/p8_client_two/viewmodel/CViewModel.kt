package com.esardo.p8_client_two.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.esardo.p8_client_two.MyBroadcastReceiver
import com.esardo.p8_client_two.MyBroadcastReceiver.Companion.MY_ACTION_RECEIVER_ACTION
import com.esardo.p8_client_two.MyBroadcastReceiver.Companion.OTHER_ACTION_RECEIVER_ACTION
import com.esardo.p8_client_two.MyBroadcastReceiver.Companion.OTHER_ACTION_RECEIVER_EXTRA
import com.esardo.p8_client_two.model.Message
import java.text.SimpleDateFormat
import java.util.*

class CViewModel(application: Application): AndroidViewModel(application) {
    val context: Context = application

    private lateinit var br: BroadcastReceiver

    val messageLD = MutableLiveData<MutableList<Message>>()
    val myMessageLD = MutableLiveData<Message>()

    private val messageList = mutableListOf<Message>()

    init{
        bindReceiver()
    }

    //Binds the broadcast received
    private fun bindReceiver(){
        IntentFilter().apply {
            addAction(MY_ACTION_RECEIVER_ACTION)
            br = MyBroadcastReceiver(){
                val msg = it
                val date = getDate()
                val message = Message(msg, date, false)
                receiveMessage(message)
            }
            context.registerReceiver(br, this)
        }
    }

    //Send the message to the other app
    fun sendMessage(message:Message){
        Intent().apply {
            action = OTHER_ACTION_RECEIVER_ACTION
            putExtra(OTHER_ACTION_RECEIVER_EXTRA, message.text)
            context.sendOrderedBroadcast(this, null)
        }
        myMessageLD.postValue(message)
    }

    //When a message is received joins date and message text
    private fun receiveMessage(message:Message){
        messageList.add(message)
        messageLD.postValue(messageList)
    }

    //"Notifies" that the message has been delivered clearing the messageList
    fun notifyDeliver(){
        messageList.clear()
    }

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(br)
    }

    //Function to get Date and define its pattern
    fun getDate():String{
        val sdf = SimpleDateFormat("dd/MM/yy HH:mm")
        return sdf.format(Date())
    }

}