package com.example.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.model.UserModel
import com.example.app.repository.UserRepoImpl
import com.example.app.ui.theme.CoffeeBrown
import com.example.app.ui.theme.CoffeeCream
import com.example.app.ui.theme.SoftCream
import com.example.app.ui.theme.White
import kotlinx.coroutines.launch

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }
}

@Composable
fun RegistrationBody() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as Activity
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Using Repo implementation directly
    val repo = remember { UserRepoImpl() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SoftCream)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Join PathVista",
                style = TextStyle(
                    fontSize = 32.sp,
                    color = CoffeeBrown,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                "Create an account to start your journey.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomOutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "First Name",
                    modifier = Modifier.weight(1f)
                )
                CustomOutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Last Name",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email Address",
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            var visibility by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password (min 6 characters)") },
                visualTransformation = if (!visibility) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { visibility = !visibility }) {
                        Icon(
                            painter = if (visibility)
                                painterResource(R.drawable.baseline_visibility_off_24)
                            else
                                painterResource(R.drawable.baseline_visibility_24),
                            contentDescription = null,
                            tint = CoffeeBrown
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = White,
                    focusedContainerColor = White,
                    focusedIndicatorColor = CoffeeBrown,
                    unfocusedIndicatorColor = CoffeeCream,
                    cursorColor = CoffeeBrown
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.length < 6) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields correctly.")
                        }
                        return@Button
                    }

                    // Network call to register
                    repo.register(email, password) { success, message, userId ->
                        if (success) {
                            val newUser = UserModel(
                                userId = userId,
                                email = email,
                                firstName = firstName,
                                lastName = lastName
                            )

                            repo.addUserToDatabase(userId, newUser) { dbSuccess, dbMessage ->
                                if (dbSuccess) {
                                    Toast.makeText(context, "Welcome aboard!", Toast.LENGTH_SHORT).show()
                                    activity.finish()
                                } else {
                                    // FIXED: Using coroutineScope to show snackbar from callback
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(dbMessage ?: "Database error occurred")
                                    }
                                }
                            }
                        } else {
                            // FIXED: Using coroutineScope to show snackbar from callback
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message ?: "Registration failed")
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown)
            ) {
                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(SpanStyle(color = CoffeeBrown, fontWeight = FontWeight.Bold)) {
                        append("Log In")
                    }
                },
                modifier = Modifier.clickable {
                    activity.finish()
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = White,
            focusedContainerColor = White,
            focusedIndicatorColor = CoffeeBrown,
            unfocusedIndicatorColor = CoffeeCream,
            cursorColor = CoffeeBrown
        ),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationPreview() {
    RegistrationBody()
}