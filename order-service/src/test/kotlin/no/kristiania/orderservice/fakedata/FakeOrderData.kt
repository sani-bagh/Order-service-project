package no.kristiania.orderservice.fakedata

import no.kristiania.orderservice.model.Orders
import java.time.LocalDate

class FakeOrderData {

    fun getListOfFirstPageOrders(): List<Orders> {
        val orderList = arrayListOf<Orders>()

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

        val order3 = Orders(
            order_id = 3,
            product_name = "Test product name 3",
            product_type = "Test product type 3",
            date_added = LocalDate.now(),
            status = "pending"


        )

        orderList.add(order1)
        orderList.add(order2)
        orderList.add(order3)

        return orderList
    }
}