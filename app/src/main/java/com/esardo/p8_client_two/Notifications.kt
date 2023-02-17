package com.esardo.p8_client_two

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class Notifications(
    private val context: Context,
    private val title:String,
    private val shortText:String?,
    private val longText:String? )
{
    //Channel info
    private val CHANNEL_NAME = "NotificationsClient2"
    private val CHANNEL_ID: String = "Notification_ID_client2"
    private val NOTIFICATION_ID: Int = Random.nextInt()

    private lateinit var pendingIntent: PendingIntent

    init{
        makePendingIntent()
        makeNotificationChannel()
        makeNotification()
    }

    private fun makePendingIntent() {
        val intent = Intent(context,MainActivity::class.java)
        pendingIntent = PendingIntent.getActivity(context, 0,intent,0)
    }

    //Creates the channel for the notifications
    private fun makeNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        //Notification is visible on the App's icon
        notificationChannel.setShowBadge(true)

        //Creates the notification through the channel
        notificationManager.createNotificationChannel(notificationChannel)
    }

    //Function to set all the notification properties
    private fun makeNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)

        with(builder){
            //icon and its color
            setSmallIcon(R.drawable.notification)
            color = Color.CYAN
            //title and text content
            setContentTitle(title)
            setContentText(shortText?:"")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(longText))
            //priority
            priority = NotificationCompat.PRIORITY_DEFAULT
            //light
            setLights(Color.BLUE, 1000, 1000)
            //vibration
            setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            //sound
            setDefaults(Notification.DEFAULT_SOUND)

            setContentIntent(pendingIntent)

            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            setAutoCancel(true)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(context)

        //Finally, launches the notification
        notificationManagerCompat.notify(NOTIFICATION_ID , builder.build())
    }
}