package com.plcoding.bottomnavwithbadges

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plcoding.bottomnavwithbadges.ui.theme.TestTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(modifier = Modifier.height(60.dp), items = listOf(
                            BottomNavItem(
                                name = "Home",
                                route = "Home",
                                icon = Icons.Default.Home
                            ),
                            BottomNavItem(
                                name = "Favourites",
                                route = "Favourites",
                                icon = Icons.Default.Favorite
                            ),
                        ), navController = navController, onItemClick = {
                            navController.navigate(it.route)
                        })
                    },

                    ) {
                    Navigation(navController = navController)
                }
            }

        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("Favourites") {
            FavouritesScreen()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier, containerColor = Color.DarkGray, tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.Red,
                    unselectedIconColor = Color.Gray,
                ),
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon, contentDescription = item.name
                        )

                        if (selected) {
                            Text(
                                text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp
                            )
                        }
                    }
                })
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(12) { i ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Yellow)

                ) {
                    Text(text = "Item $i")
                }
            }
        })
    }
}

@Composable
fun FavouritesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Favourites")
    }
}