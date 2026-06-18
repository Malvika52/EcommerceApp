package com.example.ecommerceapp.screens.home

import android.icu.text.CaseMap
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ecommerceapp.screens.navigation.Screens

@Composable
fun MyBottomNavBar(navController: NavController){

    val currentRoute = ""
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, Screens.HomeScreen.route),
        BottomNavItem("Categories", Icons.Default.Search, Screens.CategoryList.route),
        BottomNavItem("Wishlist", Icons.Default.Favorite, Screens.Cart.route, badgeCount = 5),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, Screens.Cart.route, badgeCount = 3),
        BottomNavItem("Profile", Icons.Default.Person, Screens.Profile.route),
    )


    NavigationBar(modifier = Modifier.height(82.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp) {

        //converting current navbackstackentry into a state
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //getting current route of the destination
        val currentRoute = navBackStackEntry?.destination?.route



        items.forEach {
            NavigationBarItem(
                icon = {
                    if (it.badgeCount > 0) {
                        BadgedBox(badge = { Badge { Text(it.badgeCount.toString()) } }) {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {Text(it.title, fontSize = 10.sp)},
                selected = currentRoute == it.route,
                onClick = {
                    //navigating between the screens
                    navController.navigate(it.route){

                        //remove all the destiantion from backstack screens except start destination
                        //if already in top, then avoid unnecessary recompositions
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon  : ImageVector,
    val route : String,
    val badgeCount : Int = 0
)