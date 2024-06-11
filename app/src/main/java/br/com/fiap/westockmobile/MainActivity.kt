package br.com.fiap.westockmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.westockmobile.component.Navigate
import br.com.fiap.westockmobile.model.EmailViewModel
import br.com.fiap.westockmobile.model.UsuarioViewModel
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.service.RetrofitInstance
import br.com.fiap.westockmobile.ui.theme.WeStockMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeStockMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val usuarioViewModel: UsuarioViewModel = viewModel()
                    val emailViewModel: EmailViewModel = viewModel()
                    val usuarioRepository = UsuarioRepository(RetrofitInstance.usuarioService)
                    val usuarioId = emailViewModel.email.value?.toLongOrNull() ?: 0L

                    Navigate(usuarioId = usuarioId, usuarioViewModel = usuarioViewModel, usuarioRepository = usuarioRepository)
                }
            }
        }
    }
}
