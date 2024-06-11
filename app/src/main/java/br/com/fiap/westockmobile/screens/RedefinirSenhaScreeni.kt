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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.ui.theme.Inter
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway
import br.com.fiap.westockmobile.model.EmailViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Composable
fun RedefinirSenhaScreen(navController: NavHostController, usuarioRepository: UsuarioRepository) {
    val emailViewModel: EmailViewModel = viewModel()
    val email = emailViewModel.email.value
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(painter = painterResource(id = R.drawable.bubble2), contentDescription = "Bubble 2", modifier = Modifier.align(Alignment.TopStart).size(150.dp))
        Image(painter = painterResource(id = R.drawable.bubble3), contentDescription = "Right Side Bubble", modifier = Modifier.align(Alignment.CenterEnd).size(180.dp).offset(y = -160.dp))

        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var passwordVisibility by remember { mutableStateOf(false) }
            var confirmPasswordVisibility by remember { mutableStateOf(false) }

            Text("Redefinir Senha", color = Color.Black, fontSize = 40.sp, fontWeight = FontWeight.Bold, fontFamily = Raleway)
            Spacer(modifier = Modifier.height(100.dp))
            CustomTextField1(value = email, onValueChange = {}, placeholder = "Email", enabled = false)
            CustomPasswordField1("Senha", passwordVisibility, onVisibilityChanged = { passwordVisibility = it }, password, onValueChange = { password = it })
            Text(
                text = "Sua senha deve conter:\n- No mínimo, 6 dígitos;\n- Pelo menos uma letra maiúscula;\n- Pelo menos uma letra minúscula;\n- No mínimo um caractere especial (ex: #@&*).",
                fontSize = 10.sp,
                color = Color.Black,
                lineHeight = 12.sp,
                fontFamily = Inter,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            CustomPasswordField1("Repita a senha", confirmPasswordVisibility, onVisibilityChanged = { confirmPasswordVisibility = it }, confirmPassword, onValueChange = { confirmPassword = it })
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(40.dp),
                onClick = {
                    if (password != confirmPassword) {
                        errorMessage = "As senhas não são iguais"
                    } else {
                        coroutineScope.launch {
                            try {
                                val response = usuarioRepository.redefinirSenha(email, password)
                                if (response.isSuccessful) {
                                    navController.navigate("login")
                                } else {
                                    errorMessage = "Erro ao redefinir senha"
                                }
                            } catch (e: IOException) {
                                errorMessage = "Erro de conexão. Verifique sua internet."
                            } catch (e: HttpException) {
                                errorMessage = "Servidor indisponível. Tente novamente mais tarde."
                            } catch (e: Exception) {
                                errorMessage = "Ocorreu um erro inesperado."
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
            ) {
                Text("Salvar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
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
fun CustomTextField1(value: String, onValueChange: (String) -> Unit, placeholder: String, enabled: Boolean) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
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
                    Text(text = placeholder, color = Color(0xFF003399), fontFamily = Poppins, fontSize = 12.sp, fontWeight = FontWeight.Normal)
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

@Composable
fun CustomPasswordField1(placeholder: String, visible: Boolean, onVisibilityChanged: (Boolean) -> Unit, value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (value.isEmpty()) {
                    Text(text = placeholder, color = Color(0xFF003399), fontFamily = Poppins, fontSize = 12.sp, fontWeight = FontWeight.Normal)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    IconButton(onClick = { onVisibilityChanged(!visible) }) {
                        Icon(
                            painter = painterResource(R.drawable.visibleoff),
                            modifier = Modifier.size(15.dp),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
}
