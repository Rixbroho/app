package com.example.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
// Import your project's R file, not the androidx.core one
import com.example.app.R
import com.example.app.repository.UserRepoImpl
import com.example.app.ui.theme.Black
import com.example.app.ui.theme.CoffeeBrown
import com.example.app.ui.theme.CoffeeCream
import com.example.app.ui.theme.CoffeeLight
import com.example.app.ui.theme.SoftCream
import com.example.app.ui.theme.White
import com.example.app.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity
    val snackbarHostState = remember { SnackbarHostState() }

    // Initialization using the project structure
    val repo = remember { UserRepoImpl() }
    val viewModel = remember { UserViewModel(repo) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SoftCream)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Brand Icon
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(20.dp),
                color = CoffeeBrown.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu_24),
                        contentDescription = "Logo",
                        modifier = Modifier.size(45.dp),
                        tint = CoffeeBrown
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Welcome Back",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = CoffeeBrown,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                "Sign in to explore the best flavors around you.",
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            )

            // Social Login Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialMediaCard(
                    Modifier.weight(1f),
                    R.drawable.face, // Ensure face.png exists in drawables
                    "Facebook"
                )
                SocialMediaCard(
                    Modifier.weight(1f),
                    R.drawable.gmail, // Ensure gmail.png exists in drawables
                    "Google"
                )
            }

            // Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeCream)
                Text(
                    "OR CONTINUE WITH",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = CoffeeCream)
            }

            // Input Fields
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(painterResource(id = R.drawable.baseline_email_24), contentDescription = null, tint = CoffeeBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CoffeeBrown,
                    unfocusedBorderColor = CoffeeCream,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(painterResource(id = R.drawable.baseline_lock_24), contentDescription = null, tint = CoffeeBrown) },
                trailingIcon = {
                    IconButton(onClick = { visibility = !visibility }) {
                        Icon(
                            painter = if (visibility)
                                painterResource(id = R.drawable.baseline_visibility_off_24)
                            else
                                painterResource(id = R.drawable.baseline_visibility_24),
                            contentDescription = null,
                            tint = CoffeeBrown
                        )
                    }
                },
                visualTransformation = if (!visibility) PasswordVisualTransformation() else VisualTransformation.None,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CoffeeBrown,
                    unfocusedBorderColor = CoffeeCream,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Text(
                "Forgot Password?",
                color = CoffeeBrown,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clickable {
                        context.startActivity(Intent(context, ForgetPasswordActivity::class.java))
                    },
                style = TextStyle(textAlign = TextAlign.End, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("Please fill all fields") }
                        return@Button
                    }
                    viewModel.login(email, password) { success, message ->
                        if (success) {
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, DashboardActivity::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        } else {
                            coroutineScope.launch { snackbarHostState.showSnackbar(message ?: "Login failed") }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown)
            ) {
                Text("Log In", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                buildAnnotatedString {
                    append("New to PathVista? ")
                    withStyle(SpanStyle(color = CoffeeBrown, fontWeight = FontWeight.Bold)) {
                        append("Create Account")
                    }
                },
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, RegistrationActivity::class.java))
                    activity.finish()
                },
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SocialMediaCard(modifier: Modifier, image: Int, label: String) {
    Card(
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = CoffeeBrown, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginBody()
}