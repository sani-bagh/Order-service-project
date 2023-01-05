package no.kristiania.shippingservice.integration

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["shipping_queue"])
class RabbitReceiver(@Autowired private val rabbitSender: RabbitSender) {
    @RabbitHandler
    fun receive(message: String) {
        println("Received: $message from payment queue")
        val messageToOrder = arrayOf(message, "shippingcompleted")
        rabbitSender.sendMessage(messageToOrder)
    }
}