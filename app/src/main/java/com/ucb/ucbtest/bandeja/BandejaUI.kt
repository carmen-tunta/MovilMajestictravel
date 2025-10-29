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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.domain.User
import com.ucb.ucbtest.R

@Composable
fun BandejaUI(user: User) {

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
                    stringResource(id = R.string.login_title),
                    modifier = Modifier.padding(bottom = 22.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.text_color)
                    )
                )
            }


        }
    }
}