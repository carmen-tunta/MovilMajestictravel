package com.ucb.ucbtest.bandeja

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.zIndex


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
                    .zIndex(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                Row {
                    Text(
                        text = "Bandeja de Reservas",
                        modifier = Modifier.padding(bottom = 22.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.text_color)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Recargar",
                        tint = colorResource(id = R.color.text_color_secondary),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { viewModel.DoGetByAgent(user.id.toString(), user.accessToken) }
                    )
                }

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
                        val (liberadas, recibidos) = bandejas.partition { it.status.equals("liberado", ignoreCase = true) }

                        (recibidos + liberadas).map {
                            Text("")
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.border_table_color),
                                        shape = RoundedCornerShape(12.dp))
                                    .padding(
                                        vertical = 8.dp,
                                        horizontal = 5.dp,
                                    ),
                            ) {
                                val borderColor = colorResource(id = R.color.border_table_color)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .drawBehind {
                                            val strokeWidth = 1.dp.toPx()
                                            val y = size.height - strokeWidth / 2
                                            drawLine(
                                                color = borderColor,
                                                start = Offset(0f, y),
                                                end = Offset(size.width, y),
                                                strokeWidth = strokeWidth)
                                        }
                                        .padding(
                                            vertical = 8.dp,
                                            horizontal = 11.dp,
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = it.passengerName,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )
                                    Text(
                                        text = it.countryCode + " " + it.whatsapp,
                                        color = colorResource(id = R.color.text_color_secondary),
                                        )
                                }

                                it.services.map {
                                    Text(
                                        text = it.serviceName,
                                        color = colorResource(id = R.color.text_color_secondary),
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                }

                                Text("")
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 5.dp,
                                            vertical = 3.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        "Fecha probable de viaje",
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(horizontal = 8.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        "Fecha solicitada",
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(horizontal = 8.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        "Vence en",
                                        modifier = Modifier
                                            .weight(0.24f)
                                            .padding(horizontal = 8.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = if (it.travelDate != null) {
                                            it.travelDate.toString()
                                        } else {
                                            "Sin fecha"
                                        },
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(10.dp),
                                    )

                                    Text(
                                        it.createdAt,
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(10.dp),
                                    )
                                    if (it.status == "liberado") {
                                        Text(
                                            "TOMAR",
                                            modifier = Modifier
                                                .weight(0.24f)
                                                .padding(10.dp),
                                            style = TextStyle(
                                                fontWeight = FontWeight.Bold,
                                                textDecoration = TextDecoration.Underline
                                            )
                                        )
                                    } else {
                                        Text(
                                            text = it.status,
                                            modifier = Modifier
                                                .weight(0.24f)
                                                .padding(10.dp),
                                        )
                                    }


                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
