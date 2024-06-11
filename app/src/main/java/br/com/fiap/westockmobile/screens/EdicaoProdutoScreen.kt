package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.database.repository.ProdutoRepository
import br.com.fiap.westockmobile.model.Produto
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway

@Composable
fun EdicaoProdutoScreen(navController: NavHostController, userId: Long, produtoRepository: ProdutoRepository = ProdutoRepository()) {
    var categoria by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var codigoSku by remember { mutableStateOf("") }
    var dataAquisicao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var tamanho by remember { mutableStateOf("") }
    var imagemUrl by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var dataCadastro by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.titleblack),
                    contentDescription = "WeStock",
                    modifier = Modifier.size(130.dp).offset(y = -50.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.exiticon),
                        contentDescription = "Exit Icon",
                        modifier = Modifier.size(24.dp).clickable { navController.navigate("produtosScreen") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Poppins,
                        color = Color.Black
                    )
                }
            }

            // Title
            Text(
                "Edição do produto",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Raleway,
                modifier = Modifier.align(Alignment.CenterHorizontally).offset(y = -60.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Form Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = -20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)  // Add spacing between rows
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EditTextField(label = "Categoria", value = categoria, onValueChange = { categoria = it }, modifier = Modifier.weight(1f).padding(end = 8.dp))
                    EditTextField(label = "Quantidade (Un)", value = quantidade, onValueChange = { quantidade = it }, modifier = Modifier.weight(1f).padding(start = 8.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EditTextField(label = "Descrição", value = descricao, onValueChange = { descricao = it }, modifier = Modifier.weight(1f).padding(end = 8.dp))
                    EditTextField(label = "Código SKU", value = codigoSku, onValueChange = { codigoSku = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(start = 8.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EditTextField(label = "Data de aquisição", value = dataAquisicao, onValueChange = { dataAquisicao = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(end = 8.dp))
                    EditTextField(label = "Preço", value = preco, onValueChange = { preco = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(start = 8.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EditTextField(label = "Cor", value = cor, onValueChange = { cor = it }, modifier = Modifier.weight(1f).padding(end = 8.dp))
                    EditTextField(label = "Peso (Kg)", value = peso, onValueChange = { peso = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(start = 8.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    EditTextField(label = "Tamanho (Cm)", value = tamanho, onValueChange = { tamanho = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(end = 8.dp))
                    EditTextField(label = "Imagem URL", value = imagemUrl, onValueChange = { imagemUrl = it }, hasIcon = true, modifier = Modifier.weight(1f).padding(start = 8.dp))
                }
                EditTextField(label = "Nome", value = nome, onValueChange = { nome = it }, modifier = Modifier.fillMaxWidth())
                EditTextField(label = "Data de Cadastro", value = dataCadastro, onValueChange = { dataCadastro = it }, modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp),
                    onClick = {
                        val produto = Produto(
                            id = null, // Defina o ID conforme necessário
                            categoria = categoria,
                            quantidade = quantidade.toIntOrNull() ?: 0,
                            descricao = descricao,
                            codigoSku = codigoSku,
                            dataAquisicao = dataAquisicao,
                            preco = preco.toDoubleOrNull() ?: 0.0,
                            cor = cor,
                            peso = peso.toDoubleOrNull() ?: 0.0,
                            tamanho = tamanho.toDoubleOrNull() ?: 0.0,
                            imagem = imagemUrl,
                            imagemUrl = imagemUrl,
                            nome = nome,
                            dataCadastro = dataCadastro
                        )
                        produtoRepository.createProduto(userId, produto) { novoProduto ->
                            if (novoProduto != null) {
                                navController.navigate("produtosScreen")
                            } else {
                                errorMessage = "Erro ao cadastrar produto"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
                ) {
                    Text("Cadastrar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp),
                    onClick = { navController.navigate("produtosScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Voltar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
                }
            }

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, fontSize = 14.sp, fontFamily = Poppins, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fixed Bottom Navigation
            BottomNavigationBar1(navController)
        }
    }
}

@Composable
fun EditTextField(label: String, value: String, onValueChange: (String) -> Unit, hasIcon: Boolean = false, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0FF), shape = RoundedCornerShape(15.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp)  // Make fields thinner
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(color = Color(0xFF003399), fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins),
                    modifier = Modifier.weight(1f)
                )
                if (hasIcon) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.editicon),
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.deleteicon),
                            contentDescription = "Remove Icon",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar1(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp)  // Reduce padding to decrease space
            .offset(y = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavigationIcon2(navController, painterResource(id = R.drawable.perfilicon), "Profile", "perfil")
        BottomNavigationIcon2(navController, painterResource(id = R.drawable.homeicon), "Home", "mainScreen")
        BottomNavigationIcon2(navController, painterResource(id = R.drawable.cloudicon), "Cloud", "produtos")
    }
}

@Composable
fun BottomNavigationIcon2(navController: NavHostController, icon: Painter, contentDescription: String, destination: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(32.dp)
            .clickable { navController.navigate(destination) }
    )
}
