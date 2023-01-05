package no.kristiania.paymentservice.service

import no.kristiania.paymentservice.model.Payment
import no.kristiania.paymentservice.repo.PaymentRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["payments"])
class PaymentService(@Autowired private val paymentRepo: PaymentRepo) {

    fun getAllPayments(page: Int): Page<Payment> {
        val page = Pageable.ofSize(5).withPage(page)
        return paymentRepo.findAll(page)
    }

    fun getPayments(): List<Payment> {
        return paymentRepo.findAll()
    }

    @CacheEvict(allEntries = true)
    fun addPayment(payment: Payment): Int? {
        return paymentRepo.save(payment).payment_id
    }

    @CacheEvict(key = "#id")
    fun deletePayment(id: Int): Boolean {
        if (paymentRepo.existsById(id)){
            paymentRepo.deleteById(id)
            return true
        }
        return false
    }

    @Cacheable(key = "#id")
    fun getPaymentById(id: Int): Payment? {
        return paymentRepo.findByIdOrNull(id)
    }

    @CachePut(key = "#payment.id")
    fun updatePayment(id: Int, payment: Payment): Payment? {
        return paymentRepo.save(payment)
    }
}