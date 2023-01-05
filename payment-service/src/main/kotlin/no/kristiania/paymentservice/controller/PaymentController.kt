package no.kristiania.paymentservice.controller

import no.kristiania.paymentservice.integration.RabbitSender
import no.kristiania.paymentservice.model.Payment
import no.kristiania.paymentservice.service.PaymentService
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api/payment")
class PaymentController(@Autowired private val paymentService: PaymentService,
                        @Autowired private val rabbitSender: RabbitSender) {

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Int): ResponseEntity<Payment> {
        return ResponseEntity.ok(paymentService.getPaymentById(id))
    }

    @GetMapping()
    fun getPayments(@PathParam("page") page: Int): ResponseEntity<List<Payment>> {
        return ResponseEntity.ok(paymentService.getAllPayments(page).toList())
    }

    @PostMapping()
    fun addPayment(@RequestBody payment: Payment): ResponseEntity<Int> {
       // rabbitSender.sendMessageToOrderQueue("The payment has been updated")
        rabbitSender.sendMessageToShippingQueue("The order can be shipped")
        return ResponseEntity.ok(paymentService.addPayment(payment))
    }

    @PutMapping("/{id}")
    fun updatePayment(@PathVariable id: Int, @RequestBody payment: Payment): ResponseEntity<Payment> {
        return ResponseEntity.ok(paymentService.updatePayment(id, payment))
    }

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable("id") id: Int) {
        id.let {
            if (!paymentService.deletePayment(it)) throw ObjectNotFoundException()
        }
    }

    @GetMapping("/http/{message}")
    fun responseToPaymentService(@PathVariable message: String): ResponseEntity<String> {
        println(message)
        val messageOrder = arrayOf(message, "paymentcompleted")
        rabbitSender.sendMessageToOrderQueue(messageOrder)
        rabbitSender.sendMessageToShippingQueue(message)
        return ResponseEntity.ok("Hello from payment service, thank you for your request")
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Payment with given id not exist")
    class ObjectNotFoundException: RuntimeException()

}