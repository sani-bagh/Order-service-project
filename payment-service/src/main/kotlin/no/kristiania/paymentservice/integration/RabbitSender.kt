package no.kristiania.paymentservice.integration

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RabbitSender(@Autowired private val rabbitTemplate: RabbitTemplate) {

    fun sendMessageToOrderQueue(message: Array<String>) {
        rabbitTemplate.convertAndSend("order_queue", message)
    }

    fun sendMessageToShippingQueue(message: String) {
        rabbitTemplate.convertAndSend("shipping_queue", message)
    }
}