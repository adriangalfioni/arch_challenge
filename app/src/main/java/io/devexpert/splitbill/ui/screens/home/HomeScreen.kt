package io.devexpert.splitbill.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.devexpert.splitbill.R
import io.devexpert.splitbill.di.AppModule
import io.devexpert.splitbill.ui.viewModelWithParam
import java.io.File

// El Composable principal de la pantalla de inicio
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onTicketProcessed: () -> Unit
) {
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModelWithParam {
        AppModule.provideHomeViewModel()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.ticketProcessed) {
        if (uiState.ticketProcessed) onTicketProcessed()
    }

    // Launcher para capturar foto con la cámara (alta resolución)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && uiState.photoUri != null) {
            val inputStream = context.contentResolver.openInputStream(uiState.photoUri!!)
            viewModel.onAction(HomeActions.OnPictureTaken(inputStream))
        }
    }

    Scaffold { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = if (uiState.scansLeft > 0)
                        stringResource(R.string.scans_remaining, uiState.scansLeft)
                    else
                        stringResource(R.string.no_scans_remaining),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(
                    onClick = {
                        if (uiState.isButtonEnabled && !uiState.isProcessing) {
                            // Crear archivo temporal para la foto
                            val photoFile = File.createTempFile("ticket_", ".jpg", context.cacheDir)
                            val uri = FileProvider.getUriForFile(
                                context,
                                "io.devexpert.splitbill.fileprovider",
                                photoFile
                            )
                            viewModel.onAction(HomeActions.OnPhotoUri(uri))
                            cameraLauncher.launch(uri)
                        }
                    },
                    enabled = uiState.isButtonEnabled && !uiState.isProcessing,
                    modifier = Modifier.size(width = 320.dp, height = 64.dp),
                    shape = ButtonDefaults.shape
                ) {
                    Text(
                        text = if (uiState.isProcessing)
                            stringResource(R.string.processing)
                        else
                            stringResource(R.string.scan_ticket),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (uiState.isProcessing) { Text(text = stringResource(R.string.photo_captured_processing), fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp)) }
                uiState.errorMessage?.let { Text(text = it, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp)) }
            }
        }
    }
} 