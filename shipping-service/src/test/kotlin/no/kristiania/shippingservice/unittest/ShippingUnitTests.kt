package no.kristiania.shippingservice.unittest

import io.mockk.every
import io.mockk.mockk
import no.kristiania.shippingservice.model.Shipping
import no.kristiania.shippingservice.repo.ShippingRepo
import no.kristiania.shippingservice.service.ShippingService
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ShippingUnitTests {

    private val shippingRepo = mockk<ShippingRepo>()
    private val shippingService = ShippingService(shippingRepo)


    @Test
    fun getAllShipments() {
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


        every { shippingRepo.findAll() } returns listOf(shipping1, shipping2)

        val shippingList = shippingService.getShipments()
        assert(shippingList.size == 2)
        assert(shippingList[0].shipment_id?.equals(1) == true)
        assert(shippingList[0].tracking_no.equals("3984759384758NO"))
        assert(shippingList[0].date_shipped.equals(LocalDate.now()))
        assert(shippingList[1].shipment_id?.equals(2) == true)
        assert(shippingList[1].tracking_no.equals("3984759384758AZ"))
        assert(shippingList[1].date_shipped.equals(LocalDate.now()))
    }

    @Test
    fun addShipping() {
        every { shippingRepo.save(any()) } answers {
            firstArg()
        }

        val shipping1 = Shipping(
            shipment_id = 1,
            tracking_no = "3984759384758NO",
            date_shipped = LocalDate.now()
        )

        assert(shipping1.tracking_no.equals("3984759384758NO"))
    }

    @Test
    fun updateShipping() {
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

        every { shippingRepo.findAll() } returns listOf(shipping1, shipping2)
        val shippingList = shippingService.getShipments()
        every { shippingService.updateShipping(shippingList[1].shipment_id!!, shipping1) } answers {
            firstArg()
        }

        assert(shipping2.tracking_no.equals("3984759384758AZ"))
    }

    @Test
    fun deleteShipments() {
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

        every { shippingRepo.findAll() } returns listOf(shipping1, shipping2)
        val shippingList = shippingService.getShipments()

        every { shippingService.deleteShipment(shippingList[1].shipment_id!!) } answers {
            firstArg()
        }

        assert(shippingList[0].tracking_no.equals("3984759384758NO"))
    }
}