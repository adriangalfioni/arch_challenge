package io.devexpert.splitbill.ui.screens.home

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.devexpert.splitbill.data.ScanCounterRepository
import io.devexpert.splitbill.data.TicketRepository
import io.devexpert.splitbill.domain.usecases.DecrementScanCounterUseCase
import io.devexpert.splitbill.domain.usecases.GetScansRemainingUseCase
import io.devexpert.splitbill.domain.usecases.InitializeScanCounterUseCase
import io.devexpert.splitbill.domain.usecases.ProcessTicketUseCase
import io.devexpert.splitbill.ui.ImageConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class HomeViewModel(
    ticketRepository: TicketRepository,
    scanCounterRepository: ScanCounterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Casos de uso
    private val processTicketUseCase = ProcessTicketUseCase(ticketRepository)
    private val initializeScanCounterUseCase = InitializeScanCounterUseCase(scanCounterRepository)
    private val getScansRemainingUseCase = GetScansRemainingUseCase(scanCounterRepository)
    private val decrementScanCounterUseCase = DecrementScanCounterUseCase(scanCounterRepository)

    init {
        viewModelScope.launch {
            observeScansRemaining()
            initializeScanCounterUseCase()
        }
    }

    fun onAction(action: HomeActions) {
        when (action) {
            is HomeActions.OnPhotoUri ->  _uiState.update { it.copy(photoUri = action.uri) }
            is HomeActions.OnPictureTaken -> onPictureTaken(action.inputStream)
        }
    }

    private fun onPictureTaken(inputStream: InputStream?) {
        val bitmap = inputStream?.use { BitmapFactory.decodeStream(it) }
        if (bitmap != null) {
            _uiState.update { it.copy(isProcessing = true, errorMessage = null) }
            // Convertir Bitmap a ByteArray y procesar con IA
            viewModelScope.launch {
                try {
                    val imageBytes = ImageConverter.toResizedByteArray(bitmap)
                    processTicketUseCase(imageBytes)
                    // Decrementar el contador solo si el procesamiento fue exitoso
                    decrementScanCounterUseCase()
                    _uiState.update { it.copy(isProcessing = false, ticketProcessed = true) }
                } catch (_: Exception) {
                    _uiState.update { it.copy(isProcessing = false, errorMessage = "R.string.error_processing_ticket") }
                    //errorMessage = context.getString(R.string.error_processing_ticket, error.message ?: "")
                }
            }
        } else {
            _uiState.update { it.copy(isProcessing = false, errorMessage = "R.string.could_not_read_image") }
        }
    }

    private suspend fun observeScansRemaining() {
        getScansRemainingUseCase()
            .collect { scans ->
                _uiState.update { it.copy(scansLeft = scans, isButtonEnabled = scans > 0) }
            }
    }
}