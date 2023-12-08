package com.example.myapplication

import android.app.usage.UsageEvents.Event
import android.content.Context
import android.os.Message
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myapplication.auth.SuccessScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class IgViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<com.example.myapplication.Event<String>?>(null)

    fun onSignup(email: String, pass: String) {
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                    handleException(it.exception, "signup successful")
                }
                else {
                    handleException(it.exception, "signup failed")
                }
                inProgress.value = false
            }
    }


    fun login(email: String, pass: String) {
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                    handleException(it.exception, "login successful")
                } else {
                    handleException(it.exception, "login failed")
                }
                inProgress.value = false
            }
    }
    fun saveData(userData: UserData,context: Context)= CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef= Firebase.firestore
            .collection("user").document(userData.uid)
        try {
            fireStoreRef.set(userData).addOnSuccessListener {
                Toast.makeText(context,"Successfully saved data",Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    fun retrieveData(
        userID:String,
        context: Context,
        data: (UserData) ->Unit
    )= CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef=Firebase.firestore.collection("user").document(userID)
        try {
          fireStoreRef.get().addOnSuccessListener {
              if(it.exists()){
                  val userData=it.toObject<UserData>()!!
                  data(userData)
              }
              else{
                  Toast.makeText(context,"No User Data Found",Toast.LENGTH_LONG).show()
              }
          }
        }catch (e:Exception){
         Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteData(
        userID: String,
        context: Context,
        navController: NavController
    )=CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef=Firebase.firestore.collection("user").document(userID)
        try {
         fireStoreRef.delete().addOnSuccessListener {
             Toast.makeText(context,"Successfully deleted data",Toast.LENGTH_LONG).show()
             navController.popBackStack()
         }
        }
        catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
    }
}