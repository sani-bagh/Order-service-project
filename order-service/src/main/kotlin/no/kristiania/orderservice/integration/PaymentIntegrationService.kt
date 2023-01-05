package no.kristiania.orderservice.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Service
class PaymentIntegrationService(@Autowired private val restTemplate: RestTemplate) {

    val paymentUrl = "http://localhost:8080/api/payment/http"


    fun sendHttpCallToPaymentService(message: String) {
        val restTemplate = RestTemplate()
        val response: ResponseEntity<String> = restTemplate.getForEntity("$paymentUrl/$message", ResponseEntity::class)
        println("Response from payment service: $response")
    }
}