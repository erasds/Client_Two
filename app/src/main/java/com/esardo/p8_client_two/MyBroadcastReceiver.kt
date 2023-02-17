package com.esardo.p8_client_two

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

typealias onMessageReceived = (message:String)-> Unit

class MyBroadcastReceiver(): BroadcastReceiver() {
    private val TAG = "MyBroadcastReceiver"
    private lateinit var context: Context
    private var onMessageReceived: onMessageReceived? = null

    override fun onReceive(ctx: Context?, intent: Intent?) {
        context = ctx!!
        when(intent?.action){
            MY_ACTION_RECEIVER_ACTION -> {
                //When the app receives a message this captures the string
                onMessageReceived?.let { it("${intent.getStringExtra(MY_ACTION_RECEIVER_EXTRA)}") }
                Log.d(TAG, "A message is incoming \n ${intent.getStringExtra(MY_ACTION_RECEIVER_EXTRA)}")
                //Defines the text for the notification and captures the message string
                Notifications(context, "Client Two", "Message from Client one","${intent.getStringExtra(MY_ACTION_RECEIVER_EXTRA)}" )
            }
        }
    }

    constructor(mOnMessageReceived: onMessageReceived):this(){
        onMessageReceived = mOnMessageReceived
    }

    //The constants of the actions
    companion object{
        const val MY_ACTION_RECEIVER_ACTION = "com.esardo.p8_client_two.ACTION_RECEIVER"
        const val MY_ACTION_RECEIVER_EXTRA = "com.esardo.p8_client_two.RECEIVER_EXTRA"
        const val OTHER_ACTION_RECEIVER_ACTION = "com.esardo.p8_client_one.ACTION_RECEIVER"
        const val OTHER_ACTION_RECEIVER_EXTRA = "com.esardo.p8_client_one.RECEIVER_EXTRA"
    }


}