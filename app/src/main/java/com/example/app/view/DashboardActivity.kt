package com.example.app.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.ui.theme.CoffeeBrown
import com.example.app.ui.theme.CoffeeCream
import com.example.app.ui.theme.SoftCream
import com.example.app.ui.theme.White

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

@Composable
fun DashboardBody() {

    val context = LocalContext.current
    val activity = context as Activity

    // Data class for navigation items
    data class NavItem(val label: String, val icon: Int)

    var selectedIndex by remember { mutableStateOf(0) }

    // Navigation list updated for Restaurant Discovery: Explore, Search, Favorites, Profile
    val listNav = listOf(
        NavItem(
            label = "Explore",
            icon = R.drawable.baseline_home_24,
        ),
        NavItem(
            label = "Search",
            icon = R.drawable.baseline_search_24,
        ),
        NavItem(
            label = "Saved",
            icon = R.drawable.baseline_settings_24,
        ),
        NavItem(
            label = "Profile",
            icon = R.drawable.baseline_person_24,
        )
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = CoffeeBrown,
                contentColor = White,
                onClick = {
                    // Logic for adding a new restaurant review
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Review"
                )
            }
        },
        topBar = {
            // Replaced CenterAlignedTopAppBar with a Custom Surface to avoid Experimental errors
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CoffeeBrown,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp) // Adjustment for edge-to-edge
                        .height(64.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { activity.finish() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back",
                            tint = White
                        )
                    }

                    Text(
                        text = "PathVista",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = { /* Action 1 */ }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = "Menu",
                            tint = White
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = White
            ) {
                listNav.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(item.label)
                        },
                        onClick = {
                            selectedIndex = index
                        },
                        selected = selectedIndex == index,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CoffeeBrown,
                            selectedTextColor = CoffeeBrown,
                            indicatorColor = CoffeeCream.copy(alpha = 0.5f),
                            unselectedIconColor = CoffeeBrown.copy(alpha = 0.6f),
                            unselectedTextColor = CoffeeBrown.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SoftCream)
        ) {
            // Routing based on selected index
            when (selectedIndex) {
                0 -> ExploreScreen()
                1 -> SearchScreen()
                2 -> SavedScreen()
                3 -> ProfileScreen()
                else -> ExploreScreen()
            }
        }
    }
}

@Composable
fun ExploreScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Explore: Trending Restaurants", fontWeight = FontWeight.Bold, color = CoffeeBrown)
    }
}

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Search: Find Cuisines & Cafes", fontWeight = FontWeight.Bold, color = CoffeeBrown)
    }
}

@Composable
fun SavedScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Saved: Your Favorite Spots", fontWeight = FontWeight.Bold, color = CoffeeBrown)
    }
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Profile: Your Dining History", fontWeight = FontWeight.Bold, color = CoffeeBrown)
    }
}