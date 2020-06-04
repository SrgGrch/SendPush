package tech.blur.lab5.api

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

    companion object {
        const val TESTS_ROOT = "push"
    }

}