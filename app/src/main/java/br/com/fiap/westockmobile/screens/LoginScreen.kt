package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.service.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val usuarioRepository = remember { UsuarioRepository(RetrofitInstance.usuarioService) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background bubbles resized to 40% larger
        Image(painter = painterResource(id = R.drawable.bubble2), contentDescription = "Background Bubble", modifier = Modifier.align(Alignment.TopStart).size(230.dp)) // Assuming original size was 120.dp
        Image(painter = painterResource(id = R.drawable.bubble3), contentDescription = "Right Side Bubble", modifier = Modifier.align(Alignment.CenterEnd).size(120.dp).offset(y = -100.dp))
        Image(painter = painterResource(id = R.drawable.bubble1), contentDescription = "Dark Blue Bubble", modifier = Modifier.align(Alignment.TopStart).size(190.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.Start // Align text to the start (left)
        ) {
            Image(painter = painterResource(id = R.drawable.titlelogin), modifier = Modifier.size(120.dp).offset(y = -20.dp), contentDescription = "Titulo")
            Image(painter = painterResource(id = R.drawable.welcometitle), modifier = Modifier.size(100.dp).offset(x = 2.dp, y = -60.dp), alignment = Alignment.TopStart, contentDescription = "Subtitulo")
            Image(painter = painterResource(id = R.drawable.heart), modifier = Modifier.size(10.dp).offset(x = 106.dp, y = -159.dp), alignment = Alignment.TopStart, contentDescription = "Coração")
            Text("LOGIN", fontSize = 36.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(7.dp))
            TextFieldWithoutBorder(value = email, onValueChange = { email = it }, placeHolderText = "Email")
            Spacer(modifier = Modifier.height(7.dp))
            TextFieldWithoutBorder(value = senha, onValueChange = { senha = it }, placeHolderText = "Senha", isPassword = true)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Esqueceu sua senha?",
                modifier = Modifier.clickable { navController.navigate("redefinirSenhaEmail") }.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Image(painter = painterResource(id = R.drawable.socialicons), modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally).offset(y = -25.dp), contentDescription = "Social Login Icons")
            ButtonComponent(text = "Entrar", onClick = {
                coroutineScope.launch {
                    try {
                        val response = usuarioRepository.login(email, senha)
                        if (response.isSuccessful) {
                            navController.navigate("mainScreen")
                        } else {
                            errorMessage = "Login falhou. Verifique suas credenciais."
                        }
                    } catch (e: IOException) {
                        errorMessage = "Erro ao conectar ao servidor. Tente novamente mais tarde."
                    } catch (e: Exception) {
                        errorMessage = "Ocorreu um erro inesperado. Tente novamente mais tarde."
                    }
                }
            }, colord = 0xFF689AFF)
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ButtonComponent(text = "Cadastre-se", onClick = { navController.navigate("criarConta") }, colord = 0xFF003399)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithoutBorder(value: String, onValueChange: (String) -> Unit, placeHolderText: String, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeHolderText, color = Color.Gray, fontSize = 12.sp)
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, fontFamily = FontFamily.SansSerif),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent, // Transparent border
            focusedBorderColor = Color.Transparent   // Transparent border even when focused
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ButtonComponent(text: String, onClick: () -> Unit, colord: Long) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)  // Centraliza o conteúdo horizontalmente
            .height(40.dp)
            .offset(y = -25.dp),  // Move o botão 25.dp para cima
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(colord))  // Define a cor de fundo usando um valor hexadecimal
    ) {
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}
