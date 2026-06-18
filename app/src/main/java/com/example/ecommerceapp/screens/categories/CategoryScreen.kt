package com.example.ecommerceapp.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CategoryViewModel

@Composable
fun CategoryScreen(navController: NavController,
                   onCartClick  : () -> Unit,
                   onProfileClick : () -> Unit,
                   categoryViewModel: CategoryViewModel = hiltViewModel()
){

    val categoriesState = categoryViewModel.categories.collectAsState()
    val categories = categoriesState.value

    Column() {
        if(categories.isEmpty()){
            Box(modifier = Modifier.fillMaxSize().padding(16.dp),
               contentAlignment = Alignment.Center){
                Text("Cart is Empty", style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp))
            }
        }
        else{
            Text("Your Categories", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp, vertical = 8.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {


                items(categories){ category ->
                    CategoryItem(category, onCategoryClick = {
                        navController.navigate(Screens.ProductList.createRoute(category.id.toString()))
                    })

                }
            }
        }
    }

}