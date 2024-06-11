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
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.model.Usuario
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.service.RetrofitInstance
import br.com.fiap.westockmobile.ui.theme.Inter
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun CriarContaScreen(navController: NavHostController) {
    val usuarioRepository = remember { UsuarioRepository(RetrofitInstance.usuarioService) }
    val coroutineScope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var repetirSenha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(painter = painterResource(id = R.drawable.bubble2), contentDescription = "Bubble 2", modifier = Modifier.align(Alignment.TopStart).size(150.dp))
        Image(painter = painterResource(id = R.drawable.bubble3), contentDescription = "Right Side Bubble", modifier = Modifier.align(Alignment.CenterEnd).size(180.dp).offset(y = -160.dp))
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Criar conta", color = Color.Black, fontSize = 40.sp, fontWeight = FontWeight.Bold, fontFamily = Raleway)
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(value = nome, onValueChange = { nome = it }, placeholder = "Nome")
            CustomTextField(value = sobrenome, onValueChange = { sobrenome = it }, placeholder = "Sobrenome")
            CustomTextField(value = email, onValueChange = { email = it }, placeholder = "Email")
            CustomTextField(value = celular, onValueChange = { celular = it }, placeholder = "Celular")
            CustomPasswordField(value = senha, onValueChange = { senha = it }, placeholder = "Senha", visible = passwordVisibility, onVisibilityChanged = { passwordVisibility = it })

            Text(
                text = "Sua senha deve conter:\n- No mínimo, 6 dígitos;\n- Pelo menos uma letra maiúscula;\n- Pelo menos uma letra minúscula;\n- No mínimo um caractere especial (ex: #@&*).",
                fontSize = 10.sp,
                color = Color.Black,
                lineHeight = 12.sp,
                fontFamily = Inter,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomPasswordField(value = repetirSenha, onValueChange = { repetirSenha = it }, placeholder = "Repita a senha", visible = confirmPasswordVisibility, onVisibilityChanged = { confirmPasswordVisibility = it })
            Spacer(modifier = Modifier.height(4.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(40.dp),
                onClick = {
                    coroutineScope.launch {
                        if (senha != repetirSenha) {
                            errorMessage = "As senhas não coincidem."
                        } else {
                            val newUsuario = Usuario(nome = nome, sobrenome = sobrenome, email = email, celular = celular, senha = senha)
                            try {
                                val response = usuarioRepository.createUsuario(newUsuario)
                                if (response.isSuccessful) {
                                    navController.navigate("login")
                                } else {
                                    if (response.code() == 409) {
                                        errorMessage = "Este e-mail já está em uso."
                                    } else {
                                        errorMessage = "Falha ao criar conta. Tente novamente."
                                    }
                                }
                            } catch (e: HttpException) {
                                if (e.code() == 409) {
                                    errorMessage = "Este e-mail já está em uso."
                                } else {
                                    errorMessage = "Erro ao conectar ao servidor. Tente novamente mais tarde."
                                }
                            } catch (e: Exception) {
                                errorMessage = "Erro ao conectar ao servidor. Tente novamente mais tarde."
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
            ) {
                Text("Salvar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
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
fun CustomTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
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
fun CustomPasswordField(value: String, onValueChange: (String) -> Unit, placeholder: String, visible: Boolean, onVisibilityChanged: (Boolean) -> Unit) {
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
