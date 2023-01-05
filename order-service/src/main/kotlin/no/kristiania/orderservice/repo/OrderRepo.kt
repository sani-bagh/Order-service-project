package no.kristiania.orderservice.repo

import no.kristiania.orderservice.model.Orders
import org.springframework.data.domain.Page
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
interface OrderRepo: PagingAndSortingRepository<Orders, Int> {

    override fun findAll(pageable: Pageable): Page<Orders>

    override fun findAll(): List<Orders>
}