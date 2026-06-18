package com.example.ecommerceapp.screens.home

import android.R
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.ui.theme.PrimaryColor

@Composable
fun FeaturedProductCard(product: Products, onProductClick: () -> Unit) {
    Card(
        onClick = onProductClick,
        modifier = Modifier.width(280.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box{
            DiscountBadge(5, modifier = Modifier.align(Alignment.TopStart)
                .padding(8.dp)
                .zIndex(2f))

            Column(modifier = Modifier.padding(16.dp).zIndex(1f)){
                Image(painter = rememberAsyncImagePainter(product.imageUrl), contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(150.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${product.price}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(R.drawable.star_on), contentDescription = "",
                        tint = PrimaryColor, modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "${product.id}",
                        style = MaterialTheme.typography.bodySmall,
                      )

                }
            }
        }

    }
}