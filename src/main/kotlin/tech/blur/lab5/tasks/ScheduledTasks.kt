package tech.blur.lab5.tasks

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ScheduledTasks {

    @Scheduled(fixedRate = 1000 * 60 * 5)
    fun sendPushes() {
        File("tokens").forEachLine {
            val message: Message = Message.builder()
                    .putData("revive", "true")
                    .setAndroidConfig(
                            AndroidConfig.builder()
                                    .setPriority(AndroidConfig.Priority.HIGH)
                                    .build()
                    )
                    .setToken(it)
                    .build()

            FirebaseMessaging.getInstance().send(message)

            println("Successfully sent message to $it")
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(ScheduledTasks::class.java)
        private val dateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
    }
}