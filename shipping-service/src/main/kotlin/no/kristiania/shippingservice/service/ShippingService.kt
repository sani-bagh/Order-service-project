package no.kristiania.shippingservice.service

import no.kristiania.shippingservice.model.Shipping
import no.kristiania.shippingservice.repo.ShippingRepo
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
@CacheConfig(cacheNames = ["shipments"])
class ShippingService(@Autowired private val shippingRepo: ShippingRepo) {

    fun getAllShipments(page: Int): Page<Shipping> {
        val page = Pageable.ofSize(3).withPage(page)
        return shippingRepo.findAll(page)
    }

    fun getShipments(): List<Shipping> {
        return shippingRepo.findAll()
    }

    @CacheEvict(allEntries = true)
    fun addShipping(shipping: Shipping): Int? {
        return shippingRepo.save(shipping).shipment_id
    }

    @CacheEvict(key = "#id")
    fun deleteShipment(id: Int): Boolean {
        if (shippingRepo.existsById(id)) {
            shippingRepo.deleteById(id)
            return true
        }
        return false
    }

    @Cacheable(key = "#id")
    fun getShippingById(id: Int): Shipping? {
        return shippingRepo.findByIdOrNull(id)
    }

    @CachePut(key = "#shipping.id")
    fun updateShipping(id: Int, shipping: Shipping): Shipping? {
        return shippingRepo.save(shipping)
    }
}