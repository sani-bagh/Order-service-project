package no.kristiania.shippingservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Shipping(
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val shipment_id: Int? = 0,
    val tracking_no: String,
    val date_shipped: LocalDate = LocalDate.now()
) {

    override fun toString(): String {
        return "Shipping(shipment_id=$shipment_id, tracking_no='$tracking_no', date_shipped=$date_shipped)"
    }
}