package no.kristiania.orderservice.integrationtest

import io.mockk.every
import io.mockk.mockk
import no.kristiania.orderservice.fakedata.FakeOrderData
import no.kristiania.orderservice.model.Orders
import no.kristiania.orderservice.repo.OrderRepo
import no.kristiania.orderservice.service.OrderService
import org.hamcrest.Matchers
import org.json.JSONObject
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import java.time.LocalDate


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ApiTests {

    @Autowired
    private lateinit var  mockMvc: MockMvc



    private val orderRepo = mockk<OrderRepo>()
    private val orderService = OrderService(orderRepo)
    val orderData = FakeOrderData()

    val baseUrl = "http://localhost:8080/api/order"




    //Create new order test runs only when server is up
    @Test
    @Order(1)
    fun createNewOrder() {
        val orderPayload = JSONObject()
            .put("product_name", "product 1")
            .put("product_type", "type 1")
            .put("status", "pending")

        mockMvc.post("$baseUrl") {
            contentType = MediaType.APPLICATION_JSON
            content = orderPayload
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

    //add order runs when servers are up
    @Test
    @Order(2)
    fun addOrder() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"
        )

        val orderPayload = JSONObject()
            .put("product_name", "Test product 1")
            .put("product_type", "type 1")
            .put("status", "pending")

        every { orderService.createOrder(order1) } answers {
            val order = Orders(
                order_id = 1,
                product_name = "Test product name 1",
                product_type = "Test product type 1",
                date_added = LocalDate.now(),
                status = "pending"
            )
            order.order_id
        }

        mockMvc.post(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = orderPayload
        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }



    @Test
    @Order(4)
    fun getOrder() {
        mockMvc.get("$baseUrl/1"){
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.product_name", Matchers.containsString("product 1")) }
            .andExpect { jsonPath("$.product_type", Matchers.containsString("type 1")) }
            .andExpect { jsonPath("$.status", Matchers.containsString("pending")) }
    }


    @Test
    @Order(3)
    fun shouldGetOrderByIDWithService() {
        val order1 = Orders(1, "Test product name 1", "Test product type 1", LocalDate.now(), "pending")

        every { orderService.getOrderById(order1.order_id!!) } returns order1

        mockMvc.get("$baseUrl/${order1.order_id}"){

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    /*
    @Test
    fun updateOrder() {

        val orderPayload = JSONObject()
            .put("product_name", "Test product 1")
            .put("product_type", "type 1")
            .put("status", "pending")

        mockMvc.put("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"order_id\": \"1\",\n" +
                    "    \"product_name\": \"Test product name 2\",\n" +
                    "    \"product_type\": \"Test product type 2\",\n" +
                    "    \"status\": pending\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

     */

    @Test
    fun deleteOrder() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"


        )

        val orderPayload = JSONObject()
            .put("product_name", "Test product 1")
            .put("product_type", "type 1")
            .put("status", "pending")

        every { orderService.deleteOrder(order1.order_id!!) } answers {
            true
        }


        mockMvc.delete("$baseUrl/${order1.order_id}") {
            contentType = MediaType.APPLICATION_JSON
            content = orderPayload
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }



    @Test
    fun getListOfOrders() {
        every { orderRepo.findAll() } returns orderData.getListOfFirstPageOrders()

        mockMvc.get("$baseUrl?page=0") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { content { orderData.getListOfFirstPageOrders() } }
    }




/*


    @Test
    fun testPutOrderEndpoint() {
        val order1 = Orders(
            order_id = 1,
            product_name = "Test product name 1",
            product_type = "Test product type 1",
            date_added = LocalDate.now(),
            status = "pending"


        )

        val order2 = Orders(
            order_id = 1,
            product_name = "Test product name 2",
            product_type = "Test product type 2",
            date_added = LocalDate.now(),
            status = "pending"


        )


        val orderPayloadNew = JSONObject()
            .put("product_name", "Test product name 2")
            .put("product_type", "Test product type 2")
            .put("status", "pending")

        every { orderService.updateOrder(order1.order_id!!, order2) } answers {
            order2
        }

        mockMvc.put("$baseUrl/${order1.order_id}") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"order_id\": \"1\",\n" +
                    "    \"product_name\": \"Test product name 2\",\n" +
                    "    \"product_type\": \"Test product type 2\"\n" +
                    "}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

 */


}