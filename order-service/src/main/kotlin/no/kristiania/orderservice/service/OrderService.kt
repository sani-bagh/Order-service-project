package no.kristiania.orderservice.service

import no.kristiania.orderservice.model.Orders
import no.kristiania.orderservice.repo.OrderRepo
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
@CacheConfig(cacheNames = ["orders"])
class OrderService(@Autowired private val orderRepo: OrderRepo) {

    fun getAllOrders(page: Int): Page<Orders> {
        val page = Pageable.ofSize(3).withPage(page)
        return orderRepo.findAll(page)
    }

    fun getOrders(): List<Orders> {
        return orderRepo.findAll()
    }

    @CacheEvict(allEntries = true)
    fun createOrder(orders: Orders): Int? {
        return orderRepo.save(orders).order_id
    }

    @CacheEvict(key = "#id")
    fun deleteOrder(id: Int): Boolean {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id)
            return true
        }
        return false
    }

    @Cacheable(key = "#id")
    fun getOrderById(id: Int): Orders? {
        return orderRepo.findByIdOrNull(id)
    }

    @CachePut(key = "#orders.id")
    fun updateOrder(id: Int, orders: Orders): Orders? {
        if(orderRepo.existsById(id)){
            orders.order_id = id
            return orderRepo.save(orders)
        }
        return null
    }

    fun updateWithMessage(id: Int, status: String) {
        var order = getOrderById(id)
        if(order != null){
            order.order_id = id
            order.status = status
            orderRepo.save(order)
        }
    }

}