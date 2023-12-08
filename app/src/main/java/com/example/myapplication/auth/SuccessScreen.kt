package com.example.myapplication.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.DestinationScreen
import com.example.myapplication.IgViewModel
import com.example.myapplication.R

@Composable
fun SuccessScreen(navController: NavController, vm: IgViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.lu),
            contentDescription = null
        )
    }
}
@Composable
fun SecondMain(navController: NavController,vm: IgViewModel){
    Column(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.lu), contentDescription =null )

        Button(onClick = {
            navController.navigate(DestinationScreen.GetData.route)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Get User Data")
        }
        OutlinedButton(onClick = {
            navController.navigate(DestinationScreen.Add.route)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Add User Data")
        }
    }

}