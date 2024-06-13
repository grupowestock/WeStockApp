package br.com.fiap.westockmobile.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.westockmobile.model.UsuarioViewModel
import br.com.fiap.westockmobile.model.UsuarioViewModelFactory
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.screens.*

@Composable
fun Navigate(usuarioId: Long, usuarioRepository: UsuarioRepository) {
    val navController = rememberNavController()

    // Crie o ViewModel usando a fábrica
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(usuarioRepository)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("criarConta") { CriarContaScreen(navController) }
        composable("mainScreen") { MainScreen(navController, usuarioId, usuarioViewModel) }
        composable("menuScreen") { MenuScreen(navController) }
        composable("redefinirSenhaEmail") { RedefinirSenhaEmailScreen(navController, usuarioRepository) }
        composable("redefinirSenha") { RedefinirSenhaScreen(navController, usuarioRepository) }
        composable("perfil") { PerfilScreen(navController) }
        composable("produtos") { ProdutosScreen(navController, usuarioId) }
        composable("edicaoProduto") { EdicaoProdutoScreen(navController, usuarioId) }
        composable("whatsScreen") { WhatsScreen() }
    }
}
