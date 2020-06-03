package tech.blur.lab5.api

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller("TestController")
class TestController @Autowired constructor(
) {

    @GetMapping(path = ["$TESTS_ROOT/send"])
    @ResponseBody
    fun sendPush(
            @RequestParam(name = "token", required = true) token: String
    ): String {
        val message: Message = Message.builder()
                .putData("revive", "true")
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(AndroidConfig.Priority.HIGH)
                                .build()
                )
                .setToken(token)
                .build()

        val response = FirebaseMessaging.getInstance().send(message)

        println("Successfully sent message: $response")
        return "Successfully sent message: response"
    }

    companion object {
        const val TESTS_ROOT = "push"
    }

}