package com.ucb.ucbtest.bandeja

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getCountryNameByCode(code: String): String {
    val map = mapOf(
        "+1" to "Estados Unidos",
        "+7" to "Rusia",
        "+20" to "Egipto",
        "+27" to "Sudáfrica",
        "+30" to "Grecia",
        "+31" to "Países Bajos",
        "+32" to "Bélgica",
        "+33" to "Francia",
        "+34" to "España",
        "+36" to "Hungría",
        "+39" to "Italia",
        "+40" to "Rumania",
        "+41" to "Suiza",
        "+43" to "Austria",
        "+44" to "Reino Unido",
        "+45" to "Dinamarca",
        "+46" to "Suecia",
        "+47" to "Noruega",
        "+48" to "Polonia",
        "+49" to "Alemania",
        "+51" to "Perú",
        "+52" to "México",
        "+53" to "Cuba",
        "+54" to "Argentina",
        "+55" to "Brasil",
        "+56" to "Chile",
        "+57" to "Colombia",
        "+58" to "Venezuela",
        "+60" to "Malasia",
        "+61" to "Australia",
        "+62" to "Indonesia",
        "+63" to "Filipinas",
        "+64" to "Nueva Zelanda",
        "+65" to "Singapur",
        "+66" to "Tailandia",
        "+81" to "Japón",
        "+82" to "Corea del Sur",
        "+84" to "Vietnam",
        "+86" to "China",
        "+90" to "Turquía",
        "+91" to "India",
        "+92" to "Pakistán",
        "+93" to "Afganistán",
        "+94" to "Sri Lanka",
        "+95" to "Myanmar",
        "+98" to "Irán",
        "+211" to "Sudán del Sur",
        "+212" to "Marruecos",
        "+213" to "Argelia",
        "+216" to "Túnez",
        "+218" to "Libia",
        "+220" to "Gambia",
        "+221" to "Senegal",
        "+222" to "Mauritania",
        "+223" to "Malí",
        "+224" to "Guinea",
        "+225" to "Costa de Marfil",
        "+226" to "Burkina Faso",
        "+228" to "Togo",
        "+229" to "Benín",
        "+230" to "Mauricio",
        "+231" to "Liberia",
        "+232" to "Sierra Leona",
        "+233" to "Ghana",
        "+234" to "Nigeria",
        "+235" to "Chad",
        "+236" to "República Centroafricana",
        "+237" to "Camerún",
        "+238" to "Cabo Verde",
        "+239" to "Santo Tomé y Príncipe",
        "+240" to "Guinea Ecuatorial",
        "+241" to "Gabón",
        "+242" to "Congo",
        "+243" to "República Democrática del Congo",
        "+244" to "Angola",
        "+245" to "Guinea-Bisáu",
        "+246" to "Territorio Británico del Océano Índico",
        "+247" to "Santa Helena",
        "+248" to "Seychelles",
        "+249" to "Sudán",
        "+250" to "Ruanda",
        "+251" to "Etiopía",
        "+252" to "Somalia",
        "+253" to "Yibuti",
        "+254" to "Kenia",
        "+255" to "Tanzania",
        "+256" to "Uganda",
        "+257" to "Burundi",
        "+258" to "Mozambique",
        "+260" to "Zambia",
        "+261" to "Madagascar",
        "+262" to "Reunión / Mayotte",
        "+263" to "Zimbabue",
        "+264" to "Namibia",
        "+265" to "Malaui",
        "+266" to "Lesoto",
        "+267" to "Botsuana",
        "+268" to "Esuatini",
        "+269" to "Comoras",
        "+290" to "Santa Elena",
        "+291" to "Eritrea",
        "+297" to "Aruba",
        "+298" to "Islas Feroe",
        "+299" to "Groenlandia",
        "+350" to "Gibraltar",
        "+351" to "Portugal",
        "+352" to "Luxemburgo",
        "+353" to "Irlanda",
        "+354" to "Islandia",
        "+355" to "Albania",
        "+356" to "Malta",
        "+357" to "Chipre",
        "+358" to "Finlandia",
        "+359" to "Bulgaria",
        "+370" to "Lituania",
        "+371" to "Letonia",
        "+372" to "Estonia",
        "+373" to "Moldavia",
        "+374" to "Armenia",
        "+375" to "Bielorrusia",
        "+376" to "Andorra",
        "+377" to "Mónaco",
        "+378" to "San Marino",
        "+380" to "Ucrania",
        "+381" to "Serbia",
        "+382" to "Montenegro",
        "+383" to "Kosovo",
        "+385" to "Croacia",
        "+386" to "Eslovenia",
        "+387" to "Bosnia y Herzegovina",
        "+389" to "Macedonia del Norte",
        "+420" to "República Checa",
        "+421" to "Eslovaquia",
        "+423" to "Liechtenstein",
        "+591" to "Bolivia",
        "+593" to "Ecuador",
    )
    return map[code] ?: ""
}

fun String.toFormattedDate(): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = isoFormat.parse(this)
        val outputFormat = SimpleDateFormat("EEE d MMM yy HH:mm", Locale("es", "ES"))
        outputFormat.format(date!!)
            .replaceFirstChar { it.uppercase() }
    } catch (e: Exception) {
        this
    }
}

fun String.timeRemaining(): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")

        val assignedDate = isoFormat.parse(this)
        val now = Date()

        val diffMillis = now.time - assignedDate.time

        val maxMillis = TimeUnit.MINUTES.toMillis(45)

        val remainingMillis = maxMillis - diffMillis

        if (remainingMillis <= 0) {
            "Expirado"
        } else {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) - 239
            String.format("$minutes min.")
        }
    } catch (e: Exception) {
        "Desconocido"
    }
}

fun formatTravelDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "Sin fecha"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("EEE dd MMM yy", Locale("es", "ES"))
        outputFormat.format(date!!)
            .replaceFirstChar { it.uppercase() }
    } catch (e: Exception) {
        "Sin fecha"
    }
}


@Composable
fun BandejaUI(user: User) {
    val viewModel: BandejaViewModel = hiltViewModel()

    val bandejaState by viewModel.bandejaState.collectAsState(BandejaViewModel.BandejaState.Init)
    LaunchedEffect(user.id) {
        viewModel.DoGetByAgent(user.id.toString(), user.accessToken)
    }

    Scaffold { innerPadding ->
        Box(
                modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 5.dp, horizontal = 20.dp),
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
                                        text = "${getCountryNameByCode(it.countryCode)} " + it.countryCode + " " + it.whatsapp,
                                        color = colorResource(id = R.color.text_color_secondary),
                                        style = TextStyle(
                                            fontSize = 10.sp
                                        )
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
                                            formatTravelDate(it.travelDate)
                                        } else {
                                            "Sin fecha"
                                        },
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(10.dp),
                                    )

                                    Text(
                                        it.createdAt.toFormattedDate(),
                                        modifier = Modifier
                                            .weight(0.38f)
                                            .padding(10.dp),
                                    )
                                    if (it.status == "liberado") {
                                        Text(
                                            "TOMAR",
                                            modifier = Modifier
                                                .weight(0.24f)
                                                .padding(10.dp)
                                                .clickable { viewModel.DoTakeRequest(it.id.toString(), user.id.toString(), user.accessToken) },
                                            style = TextStyle(
                                                fontWeight = FontWeight.Bold,
                                                textDecoration = TextDecoration.Underline
                                            )
                                        )
                                    } else if (it.status == "en_progreso") {
                                        Text(
                                            text = "En progreso",
                                            modifier = Modifier
                                                .weight(0.24f)
                                                .padding(10.dp),
                                        )
                                    } else {
                                        Text(
                                            text = it.assignedAt.timeRemaining(),
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
