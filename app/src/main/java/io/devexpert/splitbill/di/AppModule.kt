package io.devexpert.splitbill.di

import android.content.Context
import io.devexpert.splitbill.BuildConfig
import io.devexpert.splitbill.MockTicketDataSource
import io.devexpert.splitbill.data.DataStoreScanCounterDataSource
import io.devexpert.splitbill.data.MLKitTicketDataSource
import io.devexpert.splitbill.data.ScanCounterDataSource
import io.devexpert.splitbill.data.ScanCounterRepository
import io.devexpert.splitbill.data.TicketRepository
import io.devexpert.splitbill.domain.usecases.DecrementScanCounterUseCase
import io.devexpert.splitbill.domain.usecases.GetScansRemainingUseCase
import io.devexpert.splitbill.domain.usecases.GetTicketDataUseCase
import io.devexpert.splitbill.domain.usecases.InitializeScanCounterUseCase
import io.devexpert.splitbill.domain.usecases.ProcessTicketUseCase
import io.devexpert.splitbill.ui.screens.home.HomeViewModel
import io.devexpert.splitbill.ui.screens.receipt.ReceiptViewModel

object AppModule {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context
    }

    // Data sources
    private val scanCounterDataSource: ScanCounterDataSource by lazy {
        DataStoreScanCounterDataSource(appContext)
    }
    private val ticketDataSource by lazy {
        if (BuildConfig.DEBUG) {
            MockTicketDataSource()
        } else {
            MLKitTicketDataSource()
        }
    }
    // Repositories
    private val scanCounterRepository: ScanCounterRepository by lazy {
        ScanCounterRepository(scanCounterDataSource)
    }
    private val ticketRepository by lazy { TicketRepository(ticketDataSource) }

    // Use cases
    private val processTicketUseCase by lazy { ProcessTicketUseCase(ticketRepository) }
    private val initializeScanCounterUseCase by lazy { InitializeScanCounterUseCase(scanCounterRepository) }
    private val getScansRemainingUseCase by lazy { GetScansRemainingUseCase(scanCounterRepository) }
    private val decrementScanCounterUseCase by lazy { DecrementScanCounterUseCase(scanCounterRepository) }

    private val getTicketDataUseCase by lazy { GetTicketDataUseCase(ticketRepository) }

    // ViewModels
    fun provideHomeViewModel(): HomeViewModel =
        HomeViewModel(processTicketUseCase, initializeScanCounterUseCase, getScansRemainingUseCase, decrementScanCounterUseCase)

    fun provideReceiptViewModel(): ReceiptViewModel =
        ReceiptViewModel(getTicketDataUseCase)




}