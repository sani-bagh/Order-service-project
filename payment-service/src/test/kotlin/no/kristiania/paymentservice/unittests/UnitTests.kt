package no.kristiania.paymentservice.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.paymentservice.model.Payment
import no.kristiania.paymentservice.repo.PaymentRepo
import no.kristiania.paymentservice.service.PaymentService
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import java.time.LocalDate

class UnitTests {

    private val paymentRepo = mockk<PaymentRepo>()
    private val paymentService = PaymentService(paymentRepo)

    @Test
    fun getAllPayments() {
        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment2= Payment(payment_id = 2,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())

        every { paymentRepo.findAll() } returns listOf(payment1, payment2)

        val paymentList = paymentService.getPayments()
        assert(paymentList.size == 2)
        assert(paymentList[0].payment_id?.equals(1) == true)
        assert(paymentList[0].account_number.equals("93847593847598347"))
        assert(paymentList[0].amount.equals("324823 kr"))
        assert(paymentList[0].date_paid.equals(LocalDate.now()))
        assert(paymentList[1].payment_id?.equals(2) == true)
        assert(paymentList[1].account_number.equals("93847593847598347"))
        assert(paymentList[1].amount.equals("324823 kr"))
        assert(paymentList[1].date_paid.equals(LocalDate.now()))
    }

    @Test
    fun addPayment() {
        every { paymentRepo.save(any()) } answers {
            firstArg()
        }

        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        assert(payment1.account_number.equals("93847593847598347"))
    }

    @Test
    fun updatePayment() {
        val payment1= Payment(payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment2= Payment(payment_id = 2,
            account_number = "938475876793847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())

        every { paymentRepo.findAll() } returns listOf(payment1)
        val paymentList = paymentService.getPayments()
        every { paymentService.updatePayment(paymentList[0].payment_id!!, payment2) } answers {
            firstArg()
        }

        assert(payment2.account_number.equals("938475876793847598347"))
    }



    @Test
    fun deletePayment() {
        val payment1= Payment(payment_id = 1,
            account_number = "100000000000000000",
            amount = "324823 kr",
            date_paid = LocalDate.now())
        val payment2= Payment(payment_id = 2,
            account_number = "938475876793847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now())


        every { paymentRepo.findAll() } returns listOf(payment1, payment2)
        val paymentList = paymentService.getPayments()

        every { paymentService.deletePayment(paymentList[1].payment_id!!) } answers {
            firstArg()
        }

        assert(paymentList[0].account_number.equals("100000000000000000"))
    }



}