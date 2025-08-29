package io.devexpert.splitbill

import io.devexpert.splitbill.data.TicketData
import io.devexpert.splitbill.data.TicketDataSource
import io.devexpert.splitbill.data.TicketItem
import io.devexpert.splitbill.data.TicketRepository
import io.devexpert.splitbill.domain.usecases.ProcessTicketUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

// Fake DataSource que devuelve datos de prueba
class FakeTicketDataSource : TicketDataSource {
    override suspend fun processTicket(imageBytes: ByteArray): TicketData {
        return TicketData(
            items = listOf(
                TicketItem(name = "Pizza", quantity = 2, price = 10.0),
                TicketItem(name = "Coca-Cola", quantity = 1, price = 3.0)
            ),
            total = 23.0
        )
    }
}

class ProcessTicketUseCaseTest {

    @Test
    fun `cuando se procesa un ticket debe devolver los datos esperados`() = runTest {
        // Arrange
        val fakeDataSource = FakeTicketDataSource()
        val repository = TicketRepository(fakeDataSource)
        val useCase = ProcessTicketUseCase(repository)

        // Act
        val result = useCase.invoke(byteArrayOf(1, 2, 3)) // no importa el contenido

        // Assert
        assertEquals(2, result.items.size)
        assertEquals("Pizza", result.items[0].name)
        assertEquals(2, result.items[0].quantity)
        assertEquals(10.0, result.items[0].price, 0.0)
        assertEquals(23.0, result.total, 0.0)
    }
}
