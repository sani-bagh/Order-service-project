package no.kristiania.shippingservice.controller

import no.kristiania.shippingservice.integration.RabbitSender
import no.kristiania.shippingservice.model.Shipping
import no.kristiania.shippingservice.service.ShippingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api/shipping")
class ShippingController(@Autowired private val shippingService: ShippingService,
                         @Autowired private val rabbitSender: RabbitSender) {

    @GetMapping("/{id}")
    fun getShippingById(@PathVariable id: Int): ResponseEntity<Shipping> {
        return ResponseEntity.ok(shippingService.getShippingById(id))
    }

    @GetMapping
    fun getPayments(@PathParam("page") page: Int): ResponseEntity<List<Shipping>> {
        return ResponseEntity.ok(shippingService.getAllShipments(page).toList())
    }

    @PostMapping
    fun addShipping(@RequestBody shipping: Shipping): ResponseEntity<Int> {
        //rabbitSender.sendMessage("Message to order_queue. The shipment is complete")
        return ResponseEntity.ok(shippingService.addShipping(shipping))
    }

    @PutMapping("/{id}")
    fun updateShipping(@PathVariable id: Int, @RequestBody shipping: Shipping): ResponseEntity<Shipping> {
        return ResponseEntity.ok(shippingService.updateShipping(id, shipping))
    }

    @DeleteMapping("/{id}")
    fun deleteShipping(@PathVariable id: Int){
        id.let {
            if (!shippingService.deleteShipment(it)) throw ObjectNotFoundException()
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Shipping with given id not exist")
    class ObjectNotFoundException: RuntimeException()
}