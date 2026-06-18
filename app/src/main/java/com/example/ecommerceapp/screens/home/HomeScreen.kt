package com.example.ecommerceapp.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.util.query
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CategoryViewModel
import com.example.ecommerceapp.viewmodels.ProductViewModel
import com.example.ecommerceapp.viewmodels.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,
               onProfileClick : () -> Unit,
               onCartClick : ()-> Unit,
               productViewModel: ProductViewModel = hiltViewModel(),
               categoryViewModel: CategoryViewModel = hiltViewModel(),
               searchViewModel: SearchViewModel = hiltViewModel()
) {
    //provides basic structure app screen
    Scaffold(topBar = { MyTopAppBar(onProfileClick, onCartClick) }, bottomBar = { MyBottomNavBar(navController) }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(5.dp)) {
            //Search Section
            // Spacer(Modifier.height(12.dp))
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current


            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = {
                    searchViewModel.searchProducts(searchQuery.value)
                    focusManager.clearFocus()
                })


            if(searchQuery.value.isNotBlank()){
                SearchResultsSection(navController)
            }


            //Categories Section
            SectionTitle("Categories", "See All") {
                navController.navigate(Screens.CategoryList.route)
            }

            val categoriesState = categoryViewModel.categories.collectAsState()
            val categories= categoriesState.value

            Log.i("Categories in VM = ", categories.toString())

            val selectedCategory = remember { mutableStateOf(0) }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(categories.size) {
                    CategoryChip(
                        categories[it].iconURL,
                        text = categories[it].name,
                        isSelected = selectedCategory.value == it,
                        onClick = {
                            selectedCategory.value = categories[it].id
                            Log.i("Malvika", "selected value = $it")
                            navController.navigate(
                                Screens.ProductList.createRoute(categories[it].id.toString()))
                        })
                }

            }

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))


            //featured products section
            SectionTitle("Featured Products", "See All") {
                navController.navigate(Screens.CategoryList.route)
            }

            val allProductListState = productViewModel.allProducts.collectAsState()
            val productsList = allProductListState.value

            Log.i("productsList in VM = ", productsList.toString())

            /*val productsList = listOf<Products>(
                Products(
                    "1",
                    50000.0, "Smartphone", "https://cdn-icons-png.flaticon.com/512/15/15874.png", ""
                ),
                Products(
                    "1",
                    100000.0, "Laptop", "https://cdn-icons-png.flaticon.com/512/179/179386.png", ""
                ),
                Products(
                    "1",
                    200.0, "Bottle", "https://cdn-icons-png.flaticon.com/512/3105/3105807.png", ""
                ),
                Products(
                    "1",
                    5000.0, "Cooler", "https://cdn-icons-png.flaticon.com/512/3659/3659899.png", ""
                )
            )*/


            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(productsList){ product ->
                    FeaturedProductCard(product, {
                        navController.navigate(Screens.ProductDetails.createRoute(product.id))
                    })
                }
            }
        }
    }

}