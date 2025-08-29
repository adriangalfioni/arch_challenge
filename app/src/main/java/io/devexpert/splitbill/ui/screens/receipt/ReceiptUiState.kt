package io.devexpert.splitbill.ui.screens.receipt

import io.devexpert.splitbill.data.TicketData
import io.devexpert.splitbill.data.TicketItem

data class ReceiptUiState(
    val ticketData: TicketData? = null,
    val selectedQuantities: Map<TicketItem, Int> = emptyMap(),
    val paidQuantities: Map<TicketItem, Int> = emptyMap(),
    val availableItems: List<Pair<TicketItem, Int>> = emptyList(),
    val paidItems: List<Pair<TicketItem, Int>> = emptyList(),
    val selectedTotal: Double = 0.0
)
