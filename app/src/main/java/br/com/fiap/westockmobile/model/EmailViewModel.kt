package br.com.fiap.westockmobile.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class EmailViewModel : ViewModel() {
    var email = mutableStateOf("")

    fun setEmail(newEmail: String) {
        email.value = newEmail
    }
}