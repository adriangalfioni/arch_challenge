package io.devexpert.splitbill.ui.screens.home

import android.net.Uri
import java.io.InputStream

sealed interface HomeActions {
    data class OnPhotoUri(val uri: Uri?) : HomeActions
    data class OnPictureTaken(val inputStream: InputStream?) : HomeActions
}