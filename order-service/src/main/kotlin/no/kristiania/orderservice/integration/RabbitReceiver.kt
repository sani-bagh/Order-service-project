package no.kristiania.orderservice.integration

import no.kristiania.orderservice.model.Orders
import no.kristiania.orderservice.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["order_queue"])
class RabbitReceiver(@Autowired private val orderService: OrderService) {
    @RabbitHandler
    fun receive(message: Array<String>) {
        println("Received: $message from payment queue")
        val id = message[0]
        val status = message[1]
        orderService.updateWithMessage(id.toInt(), status)
    }
}