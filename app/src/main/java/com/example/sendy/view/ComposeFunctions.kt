//package com.example.sendy.view
//
//import android.annotation.SuppressLint
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import kotlinx.coroutines.delay
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextRange
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.sendy.R
//import com.example.sendy.validator.PhoneNumberValidator
//
//class ComposeFunctions {
//
//
//    @Composable
//    fun RunMyApp() {
//        var shouldShowSplash by remember { mutableStateOf(true) }
//
//        LaunchedEffect(Unit) {
//            delay(3000)
//            shouldShowSplash = false
//        }
//
//        if(shouldShowSplash){
//            SplashScreen()
//        } else {
//            LoginScreen()
//        }
//    }
//
//    //@Preview(showBackground = true)
//    @Composable
//    fun SplashScreen(){
//        Box (
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Добро пожаловать !",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 16.dp)
//            )
//            Image(
//                painter = painterResource(id = R.drawable.load_image),
//                contentDescription = "Экран загрузки",
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//                    .border(2.dp, Color.Gray, CircleShape)
//            )
//            Text(
//                text = "Идет загрузка..",
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 16.dp)
//            )
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Preview(showBackground = true)
//    @Composable
//    fun LoginScreen(){
//        var rawPhoneNumber by remember { mutableStateOf(TextFieldValue()) }
//        val focusManager = LocalFocusManager.current
//        var phoneNumberValidator = remember { PhoneNumberValidator() }
//        var isPhoneNumberValid by remember { mutableStateOf(false) }
//
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Введите номер телефона",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 16.dp)
//            )
//
//            BasicTextField(
//                value = rawPhoneNumber,
//                onValueChange = { newLine ->
//                    var resultFormat = phoneNumberValidator.formatPhone(newLine.text)
//                    rawPhoneNumber = TextFieldValue(
//                        text = resultFormat[0],
//                        selection = TextRange(resultFormat[1].toInt())
//                    )
//                },
//                decorationBox = { innerTextField ->
//                    TextFieldDefaults.DecorationBox(
//                        innerTextField = innerTextField,
//                        placeholder = { Text("+7 (XXX) XXX-XX-XX") },
//                        enabled = true,
//                        singleLine = true,
//                        value = rawPhoneNumber.text,
//                        visualTransformation =  VisualTransformation.None,
//                        interactionSource =  remember { MutableInteractionSource() }
//                    )
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//            )
//
//            Button(
//                onClick = {(println("Кликнул"))},
//                enabled = isPhoneNumberValid,
//                modifier = Modifier
//                    .size(200.dp,50.dp)
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 16.dp)
//            ) {
//                Text(
//                    text = "Отправить СМС"
//                )
//            }
//
//            LaunchedEffect(rawPhoneNumber) {
//                if(phoneNumberValidator.isValid(rawPhoneNumber.text)){
//                    isPhoneNumberValid = true
//                    focusManager.clearFocus()
//                } else {
//                    isPhoneNumberValid = false
//                }
//            }
//        }
//    }
//}