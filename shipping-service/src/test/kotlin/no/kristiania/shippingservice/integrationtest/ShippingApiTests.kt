package no.kristiania.shippingservice.integrationtest

import io.mockk.every
import io.mockk.mockk
import no.kristiania.shippingservice.fakedata.FakeShippingData
import no.kristiania.shippingservice.model.Shipping
import no.kristiania.shippingservice.repo.ShippingRepo
import no.kristiania.shippingservice.service.ShippingService
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
class ShippingApiTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val shippingRepo = mockk<ShippingRepo>()
    private val shippingService = ShippingService(shippingRepo)
    val shippingData = FakeShippingData()


    val baseUrl = "http://localhost:8080/api/shipping"

    @Test
    @Order(1)
    fun createNewShipping() {
        val shippingPayload = JSONObject()
            .put("tracking_no", "390487593475NO")

        mockMvc.post("$baseUrl") {
            contentType = MediaType.APPLICATION_JSON
            content = shippingPayload
        }

            .andExpect { status { isOk() } }
            .andReturn()

    }

    @Test
    @Order(2)
    fun getShipping() {
        mockMvc.get("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.tracking_no", Matchers.containsString("1339047859347NO")) }
    }


    @Test
    @Order(3)
    fun getShippingByIdWithService() {
        val shipping1 = Shipping(
            shipment_id = 1,
            tracking_no = "3984759384758NO",
            date_shipped = LocalDate.now()
        )

        every { shippingService.getShippingById(shipping1.shipment_id!!) } returns shipping1

        mockMvc.get("$baseUrl/${shipping1.shipment_id}") {

        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    /*
    @Test
    fun updateShipping() {
        val shipping1 = Shipping(
            shipment_id = 1,
            tracking_no = "3984759384758NO",
            date_shipped = LocalDate.now()
        )

        mockMvc.put("$baseUrl/${shipping1.shipment_id}") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"tracking_no\": \"newtrackingno\"\n" +
                    "}"
        }

            .andExpect { status { isOk() } }
            .andReturn()
    }

     */

    @Test
    fun deleteShipping() {
        val shipping1 = Shipping(
            shipment_id = 1,
            tracking_no = "3984759384758NO",
            date_shipped = LocalDate.now()
        )

        val shippingPayload = JSONObject()
            .put("tracking_no", "390487593475NO")

        every { shippingService.deleteShipment(shipping1.shipment_id!!) } answers {
            true
        }

        mockMvc.delete("$baseUrl/${shipping1.shipment_id}") {
            contentType = MediaType.APPLICATION_JSON
            content = shippingPayload
        }

            .andExpect { status { isOk() } }
            .andReturn()
    }


    @Test
    @Order(4)
    fun getListOfShipments() {
        every { shippingRepo.findAll() } returns shippingData.getListOfShipping()

        mockMvc.get("$baseUrl?page=0") {
            contentType = MediaType.APPLICATION_JSON
        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { content { shippingData.getListOfShipping() } }
    }

    /*
    @Test
    fun testPutOrderEndpoint() {
        val shipping1 = Shipping(
            shipment_id = 1,
            tracking_no = "3984759384758NO",
            date_shipped = LocalDate.now()
        )

        val shipping2 = Shipping(
            shipment_id = 2,
            tracking_no = "3984759384758AZ",
            date_shipped = LocalDate.now()
        )

        every { shippingService.updateShipping(shipping1.shipment_id!!, shipping2) } answers {
            shipping2
        }

        mockMvc.put("$baseUrl/${shipping1.shipment_id}") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"tracking_no\": \"9348759834NO\"\n" +
                    "}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

     */

}