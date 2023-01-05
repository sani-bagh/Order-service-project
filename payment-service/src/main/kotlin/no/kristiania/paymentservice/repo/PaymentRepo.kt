package no.kristiania.paymentservice.repo

import no.kristiania.paymentservice.model.Payment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepo: PagingAndSortingRepository<Payment, Int> {

    override fun findAll(pageable: Pageable): Page<Payment>

    override fun findAll(): List<Payment>
}