package tech.blur.lab5.api

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import tech.blur.lab5.DelayContainer
import java.io.File


@Controller("TestController")
class PushController @Autowired constructor(
) {

    @GetMapping(path = ["$TESTS_ROOT/send"])
    @ResponseBody
    fun sendPush(
            @RequestParam(name = "token", required = true) token: String,
            @RequestParam(name = "delay", required = true) delay: Int
    ): String {
        if (token != "") {
            val file = File("tokens")

            var isContains = false

            file.forEachLine {
                if (it == "$token\n" || it == token) {
                    isContains = true
                    return@forEachLine
                }
            }

            if (!isContains)
                file.appendText("$token\n")

        }

        if (delay != 0)
            DelayContainer.delay = delay * 1000 * 60

        return "Successfully sent message: response"
    }

    @PostMapping(path = [TESTS_ROOT])
    @ResponseBody
    fun sendPushWithoutSchedule(
        @RequestParam(name = "token", required = true) token: String,
        @RequestParam(name = "text", required = true) text: String,
        @RequestParam(name = "title", required = true) title: String,
        @RequestBody answerModel: AnswerModel
    ): String {
        println(Gson().toJson(answerModel))

        val message: Message = Message.builder()
            .putData("text", text)
            .putData("title", title)
            .putData("newAnswer", Gson().toJson(answerModel))
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .setToken(token)
            .build()

        FirebaseMessaging.getInstance().send(message)

        return "Successfully sent message: response"
    }

    companion object {
        const val TESTS_ROOT = "push"
    }
}