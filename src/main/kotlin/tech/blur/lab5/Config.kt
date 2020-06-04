package tech.blur.lab5

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.io.File
import java.util.*
import java.util.concurrent.Executors


@Configuration
@EnableScheduling
class Config : SchedulingConfigurer {
    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(2))
        taskRegistrar.addTriggerTask(
                {
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
                },
                { triggerContext ->
                    val nextExecutionTime: Calendar = GregorianCalendar()
                    val lastActualExecutionTime: Date? = triggerContext.lastActualExecutionTime()
                    nextExecutionTime.time = lastActualExecutionTime ?: Date()
                    nextExecutionTime.add(Calendar.MILLISECOND, DelayContainer.delay)
                    nextExecutionTime.time
                }
        )
    }

}