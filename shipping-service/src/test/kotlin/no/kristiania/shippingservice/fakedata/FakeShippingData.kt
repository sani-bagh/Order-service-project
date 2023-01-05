package no.kristiania.shippingservice.fakedata

import no.kristiania.shippingservice.model.Shipping
import java.time.LocalDate

class FakeShippingData {

    fun getListOfShipping(): List<Shipping> {
        val shippingList = arrayListOf<Shipping>()

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

        val shipping3 = Shipping(
            shipment_id = 3,
            tracking_no = "3984759384758TR",
            date_shipped = LocalDate.now()
        )

        shippingList.add(shipping1)
        shippingList.add(shipping2)
        shippingList.add(shipping3)

        return shippingList
    }
}