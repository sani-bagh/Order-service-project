package no.kristiania.paymentservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Payment(
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val payment_id: Int? = 0,
    val account_number: String,
    val amount: String,
    val date_paid: LocalDate = LocalDate.now()
) {

    override fun toString(): String {
        return "Payment(payment_id=$payment_id, account_number=$account_number, amount=$amount, date_paid=$date_paid)"
    }
}