package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.model.EmailViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Composable
fun RedefinirSenhaEmailScreen(navController: NavHostController, usuarioRepository: UsuarioRepository) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val emailViewModel: EmailViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(painter = painterResource(id = R.drawable.bubble2), contentDescription = "Bubble 2", modifier = Modifier.align(Alignment.TopStart).size(150.dp))
        Image(painter = painterResource(id = R.drawable.bubble3), contentDescription = "Right Side Bubble", modifier = Modifier.align(Alignment.CenterEnd).size(180.dp).offset(y = -160.dp))

        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Redefinir Senha", color = Color.Black, fontSize = 40.sp, fontWeight = FontWeight.Bold, fontFamily = Raleway)
            Spacer(modifier = Modifier.height(180.dp))
            Text("Digite seu e-mail para recuperação da senha:", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins)
            Spacer(modifier = Modifier.height(4.dp))
            CustomTextField2(value = email, onValueChange = { email = it }, placeholder = "Email")
            Spacer(modifier = Modifier.height(150.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(40.dp),
                onClick = {
                    coroutineScope.launch {
                        try {
                            val response = usuarioRepository.validateEmail(email)
                            if (response.isSuccessful && response.body() == true) {
                                emailViewModel.setEmail(email)
                                navController.navigate("redefinirSenha")
                            } else {
                                errorMessage = "Email inválido"
                            }
                        } catch (e: IOException) {
                            errorMessage = "Erro de conexão. Verifique sua internet."
                        } catch (e: HttpException) {
                            errorMessage = "Servidor indisponível. Tente novamente mais tarde."
                        } catch (e: Exception) {
                            errorMessage = "Ocorreu um erro inesperado."
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
            ) {
                Text("Enviar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
            }
            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, fontSize = 14.sp, fontFamily = Poppins)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(40.dp),
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cancelar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
            }
        }
    }
}

@Composable
fun CustomTextField2(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (value.isEmpty()) {
                    Text(text = placeholder, color = Color(0xFF003399), fontFamily = Poppins, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
}
