package no.kristiania.paymentservice.fakeData

import no.kristiania.paymentservice.model.Payment
import java.time.LocalDate

class FakeDataPayments{

    fun getListOfFirstPagePayments(): List<Payment> {
        val paymentList = arrayListOf<Payment>()
        val paymentOne = Payment(
            payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now()
        )

        val paymentTwo = Payment(
            payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now()
        )

        val paymentThree = Payment(
            payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now()
        )

        val paymentFour = Payment(
            payment_id = 1,
            account_number = "93847593847598347",
            amount = "324823 kr",
            date_paid = LocalDate.now()
        )

        paymentList.add(paymentOne)
        paymentList.add(paymentTwo)
        paymentList.add(paymentThree)
        paymentList.add(paymentFour)

        return paymentList


    }

}