package no.kristiania.orderservice.controller

import no.kristiania.orderservice.integration.PaymentIntegrationService
import no.kristiania.orderservice.integration.RabbitSender
import no.kristiania.orderservice.model.Orders
import no.kristiania.orderservice.service.OrderService
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api/order")
class OrderController(@Autowired private val orderService: OrderService,
                      @Autowired private val rabbitSender: RabbitSender,
                      @Autowired private val paymentIntegrationService: PaymentIntegrationService) {

    @GetMapping("/{id}")
    fun getOrdersByID(@PathVariable id: Int): ResponseEntity<Orders> {
        return ResponseEntity.ok(orderService.getOrderById(id))
    }

    @GetMapping()
    fun getOrders(@PathParam("page") page: Int): ResponseEntity<List<Orders>> {
        return ResponseEntity.ok(orderService.getAllOrders(page).toList())
    }

    @PostMapping()
    fun createOrder(@RequestBody orders: Orders): ResponseEntity<Int> {
        rabbitSender.sendMessage("The order has been registered")
        val tempRes = orderService.createOrder(orders)
        val result = ResponseEntity.ok(tempRes)
        val stringMessage = tempRes.toString()
        paymentIntegrationService.sendHttpCallToPaymentService(stringMessage)
        return result
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: Int, @RequestBody orders: Orders): ResponseEntity<Orders> {
        return ResponseEntity.ok(orderService.updateOrder(id, orders))
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: Int?) {
        id?.let {
            if (!orderService.deleteOrder(it)) throw ObjectNotFoundException()
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order with given id not exist")
    class ObjectNotFoundException: RuntimeException()


}