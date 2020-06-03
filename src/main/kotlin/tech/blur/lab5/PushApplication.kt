package tech.blur.lab5

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PushApplication

fun main(args: Array<String>) {
    val options: FirebaseOptions = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

    FirebaseApp.initializeApp(options)

    runApplication<PushApplication>(*args)
}