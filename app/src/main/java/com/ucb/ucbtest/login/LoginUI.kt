package com.ucb.ucbtest.login

import android.print.PrintAttributes.Margins
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucb.ucbtest.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI(onSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()

    val loginState by viewModel.loginState.collectAsState(LoginViewModel.LoginState.Init)
    var userData by remember { mutableStateOf<String?>(null) } // 👈 aquí guardaremos lo que mostraremos

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "PeruAndesTravel Logo",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(horizontal = 15.dp, vertical = 25.dp)
                    .width(160.dp)
            )


            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(id = R.string.login_title),
                    modifier = Modifier.padding(bottom = 22.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.primary)
                    )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.primary),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = colorResource(id = R.color.primary),
                        focusedLabelColor = colorResource(id = R.color.primary)
                    )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(280.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.primary),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = colorResource(id = R.color.primary),
                        focusedLabelColor = colorResource(id = R.color.primary)
                    ),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña")
                        }
                    }
                )

                Box(
                    modifier = Modifier
                        .height(70.dp)
                        .padding(vertical = 10.dp)
                ) {
                    Button(
                        modifier = Modifier.width(280.dp).height(50.dp),
                        onClick = { viewModel.DoLogin(username, password) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.primary),
                            contentColor = Color.White
                        )
                    ) { Text("Acceder al sistema") }
                }

                // 👇 Aquí mostramos los resultados del estado
                when (loginState) {
                    is LoginViewModel.LoginState.Init -> {
                        userData = null
                    }
                    is LoginViewModel.LoginState.Loading -> {
                        userData = "Cargando..."
                    }
                    is LoginViewModel.LoginState.Error -> {
                        val msg = (loginState as LoginViewModel.LoginState.Error).message
                        userData = "Error: $msg"
                    }
                    is LoginViewModel.LoginState.Successful -> {
                        val user = (loginState as LoginViewModel.LoginState.Successful).user
                        userData = "Bienvenido ${user.username}\nEmail: ${user.accessToken}"
                    }
                }

                userData?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(2f))
            }
        }
    }
}
