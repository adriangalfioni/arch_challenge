package io.devexpert.splitbill.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.jvm.java

@Composable
inline fun <reified VM : ViewModel> viewModelWithParam(
    crossinline creator: () -> VM
): VM {
    val factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(VM::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return creator() as T
                }
                throw kotlin.IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
    return viewModel(factory = factory)
}