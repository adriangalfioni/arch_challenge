package io.devexpert.splitbill.ui.screens.home

import android.net.Uri

data class HomeUiState(
    val scansLeft: Int = 0,
    val isButtonEnabled: Boolean = scansLeft > 0,
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val ticketProcessed: Boolean = false,
    val photoUri: Uri? = null
)
