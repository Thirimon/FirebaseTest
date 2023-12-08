package com.example.myapplication.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.IgViewModel
import com.example.myapplication.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(
    navController: NavController,
    v:IgViewModel
){
   var userID: String by remember{ mutableStateOf("") }
    var name: String by remember {
        mutableStateOf("")
    }
    var profession by remember{ mutableStateOf("") }
    var age:String by remember{ mutableStateOf("") }
    var ageInt:Int by remember {
        mutableStateOf(0)
    }
    val context= LocalContext.current
    Column (
        modifier=Modifier.fillMaxSize()
    ){
       Row (
           modifier= Modifier
               .padding(top = 15.dp)
               .fillMaxWidth(),
           horizontalArrangement = Arrangement.Start
       ){
           IconButton(onClick = { navController.popBackStack() }) {
               Icon(imageVector= Icons.Filled.ArrowBack,contentDescription = null)
           }
           Column(
               modifier= Modifier
                   .padding(end = 10.dp, bottom = 30.dp)
                   .fillMaxSize(),
               horizontalAlignment = Alignment.Start,
               verticalArrangement = Arrangement.Center
           ) {
               Row(
                   modifier=Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   OutlinedTextField(modifier = Modifier.fillMaxWidth(0.6f),value = userID, onValueChange ={userID=it},
                       label = {Text(text="User ID")})
                   Button(modifier = Modifier
                       .padding(start = 10.dp)
                       .width(200.dp),
                       onClick = {
                           v.retrieveData(
                               userID=userID,
                               context=context
                           ){
                               data ->
                               name=data.name
                               profession=data.prof
                               age=data.age.toString()
                               ageInt=age.toInt()
                           }
                       }) {
                      Text(text = "Get Data")
                   }
               }
               //Name
               OutlinedTextField(
                   modifier = Modifier.fillMaxWidth(),
                   value = name,
                   onValueChange = { name = it },
                   label = { Text(text = "Name") })
               //Profession
               OutlinedTextField(
                   modifier = Modifier.fillMaxWidth(),
                   value = profession,
                   onValueChange = { profession = it },
                   label = { Text(text = "Profession") })
               //Age
               OutlinedTextField(
                   modifier = Modifier.fillMaxWidth(),
                   value = age,
                   onValueChange = {
                       age = it
                       if(age.isNotEmpty()){
                           ageInt=age.toInt()
                       }            },
                   label = { Text(text = "Age") },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
               Button(modifier= Modifier
                   .padding(top = 50.dp)
                   .fillMaxWidth(),
                   onClick = {
                       val userData= UserData(
                         uid=userID,
                           name=name,
                           prof=profession,
                           age=ageInt)
                       v.saveData(userData = userData,context=context)
                   }) {
                   Text(text = "Save")
               }
               //delete Button
               Button(modifier = Modifier
                   .padding(top = 20.dp)
                   .fillMaxWidth(),
                   onClick = {
                       v.deleteData(userID=userID, context = context,navController=navController)
                   }) {
                   Text(text = "Delete")
               }
           }
       } 
    }
   }
