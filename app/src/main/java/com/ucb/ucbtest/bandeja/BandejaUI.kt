package com.ucb.ucbtest.bandeja

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.domain.User
import com.ucb.ucbtest.R
import com.ucb.ucbtest.login.LoginViewModel
import androidx.compose.runtime.getValue


@Composable
fun BandejaUI(user: User) {
    val viewModel: BandejaViewModel = hiltViewModel()

    val bandejaState by viewModel.bandejaState.collectAsState(BandejaViewModel.BandejaState.Init)

    // Lanza la carga inicial al ingresar a la pantalla
    LaunchedEffect(user.id) {
        viewModel.DoGetByAgent(user.id.toString(), user.accessToken)
    }

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 70.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = user.username,
                    modifier = Modifier.padding(bottom = 7.dp),
                    style = TextStyle(
                        fontSize = 17.sp,
                        color = colorResource(id = R.color.text_color)
                    )
                )
                Text(
                    text = "Reservas",
                    modifier = Modifier.padding(bottom = 22.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.text_color_secondary)
                    )
                )
                Text(
                    text = "Bandeja de Reservas",
                    modifier = Modifier.padding(bottom = 22.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.text_color)
                    )
                )

                when (bandejaState) {
                    is BandejaViewModel.BandejaState.Init -> {
                        Text("Esperando datos...")
                    }
                    is BandejaViewModel.BandejaState.Loading -> {
                        Text("Cargando...")
                    }
                    is BandejaViewModel.BandejaState.Error -> {
                        val msg = (bandejaState as BandejaViewModel.BandejaState.Error).message
                        Text("Error: $msg", color = Color.Red)
                    }
                    is BandejaViewModel.BandejaState.Successful -> {
                        val bandejas = (bandejaState as BandejaViewModel.BandejaState.Successful).bandejas
                        Text(
                            bandejas.toString()
                        )
                    }
                }
            }
        }
    }
}
