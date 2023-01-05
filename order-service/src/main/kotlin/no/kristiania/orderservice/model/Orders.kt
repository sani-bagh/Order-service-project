package no.kristiania.orderservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Orders(
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    var order_id: Int? = 0,
    val product_name: String,
    val product_type: String,
    val date_added: LocalDate = LocalDate.now(),
    var status: String = "pending"

) {

    override fun toString(): String {
        return "Orders(order_id=$order_id, product_name='$product_name', product_type='$product_type', date_added=$date_added, status='$status')"
    }
}