package no.kristiania.paymentservice.integrationTests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.paymentservice.fakeData.FakeDataPayments
import no.kristiania.paymentservice.model.Payment
import no.kristiania.paymentservice.repo.PaymentRepo
import no.kristiania.paymentservice.service.PaymentService
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
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import java.time.LocalDate
import java.util.*


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ApiTests {


    private val paymentRepo = mockk<PaymentRepo>()

    val paymentData = FakeDataPayments()
    @Autowired private lateinit var  mockMvc: MockMvc


    private val paymentService= PaymentService(paymentRepo)




    val baseUrl = "http://localhost:8080/api/payment"




    @Test
    @Order(1)
    fun createNewPayment() {
        val paymentPayload = JSONObject()
            .put("account_number", "827364823763")
            .put("amount", "2000 kr")
            .put("date_paid", "2022-10-10")

        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = paymentPayload
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    @Order(3)
    fun getPayment() {

        mockMvc.get("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.account_number", Matchers.containsString("1339047859347")) }
    }

    /*

    @Test
    fun updatePayment() {
        val paymentPayload = JSONObject()
            .put("account_number", "827364823763")
            .put("amount", "2000 kr")
            .put("date_paid", "2022-10-10")

        every { paymentRepo.findAll() } returns paymentData.getListOfFirstPagePayments()

        mockMvc.put("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
            content = paymentPayload
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

     */



    @Test
    fun getListOfPayments() {

        every { paymentRepo.findAll() } returns paymentData.getListOfFirstPagePayments()

        mockMvc.get("$baseUrl?page=0") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { content { paymentData.getListOfFirstPagePayments() } }
            .andExpect {  }


    }

    @Test
    fun getListOfPaymentsWithService() {
        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment2= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment3= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment4= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())

        every { paymentService.getAllPayments(0) } answers {
            listOf(payment1, payment2, payment3, payment4)
            Page.empty()
        }

    }

    /*

    @Test
    fun testPutPaymentEndpoint() {
        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment2= Payment(payment_id = 1,
            account_number = "93847593847597458347",
            amount = "3248323 kr",
            date_paid = LocalDate.now())

        val paymentPayloadNew = JSONObject()
            .put("account_number", "3847593847597458347")
            .put("amount", "3248323 kr")

        every { paymentService.updatePayment(payment1.payment_id!!, payment2) } answers {
            payment2
        }

        mockMvc.put("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
            content = paymentPayloadNew
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

     */




    @Test
    @Order(2)
    fun addPayment() {
        val paymentPayload = Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())

        every { paymentService.addPayment(paymentPayload) } answers {
            val payment = Payment(payment_id = 1,
                account_number = "93847593847598347",
                amount = "324823 kr",
                date_paid = LocalDate.now())
            payment.payment_id
        }

        val paymentPayloadNew = JSONObject()
            .put("account_number", "827364823763")
            .put("amount", "2000 kr")


        mockMvc.post(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = paymentPayloadNew
        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }


    @Test
    fun deletePayment() {
        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())

        val paymentPayloadNew = JSONObject()
            .put("account_number", "3847593847597458347")
            .put("amount", "3248323 kr")

        every { paymentService.deletePayment(payment1.payment_id!!) } answers {
            true
        }

        mockMvc.delete("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
            content = paymentPayloadNew
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }




}
