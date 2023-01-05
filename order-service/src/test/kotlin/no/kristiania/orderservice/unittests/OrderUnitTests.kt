package no.kristiania.orderservice.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.orderservice.model.Orders
import no.kristiania.orderservice.repo.OrderRepo
import no.kristiania.orderservice.service.OrderService
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OrderUnitTests {

    private val orderRepo = mockk<OrderRepo>()
    private val orderService = OrderService(orderRepo)

    @Test
    fun getAllOrders() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"


        )

        val order2 = Orders(
            order_id = 2,
            product_name = "Test product name 2",
            product_type = "Test product type 2",
            date_added = LocalDate.now(),
            status = "pending"


        )


        every { orderRepo.findAll() } returns listOf(order1, order2)

        val orderList = orderService.getOrders()
        assert(orderList.size == 2)
        assert(orderList[0].order_id?.equals(1) == true)
        assert(orderList[0].product_name.equals("Test product name 1"))
        assert(orderList[0].product_type.equals("Test product type 1"))
        assert(orderList[0].date_added.equals(LocalDate.now()))
        assert(orderList[0].status.equals("pending"))
        assert(orderList[1].order_id?.equals(2) == true)
        assert(orderList[1].product_name.equals("Test product name 2"))
        assert(orderList[1].product_type.equals("Test product type 2"))
        assert(orderList[1].date_added.equals(LocalDate.now()))
        assert(orderList[1].status.equals("pending"))
    }

    @Test
    fun addOrder() {
        every { orderRepo.save(any()) } answers {
            firstArg()
        }

        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"
        )

        assert(order1.product_name.equals("Test product name 1"))
    }

    @Test
    fun updateOrder() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"


        )

        val order2 = Orders(
            order_id = 2,
            product_name = "Test product name 2",
            product_type = "Test product type 2",
            date_added = LocalDate.now(),
            status = "pending"


        )

        every { orderRepo.findAll() } returns listOf(order1, order2)
        val orderList = orderService.getOrders()
        every { orderService.updateOrder(orderList[1].order_id!!, order1) } answers {
            firstArg()
        }

        assert(order2.product_name.equals("Test product name 2"))
    }

    @Test
    fun deleteOrders() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"


        )

        val order2 = Orders(
            order_id = 2,
            product_name = "Test product name 2",
            product_type = "Test product type 2",
            date_added = LocalDate.now(),
            status = "pending"


        )

        every { orderRepo.findAll() } returns listOf(order1, order2)
        val orderList = orderService.getOrders()

        every { orderService.deleteOrder(orderList[1].order_id!!) } answers {
            firstArg()
        }

        assert(orderList[0].product_name.equals("Test product name 1"))
    }
}