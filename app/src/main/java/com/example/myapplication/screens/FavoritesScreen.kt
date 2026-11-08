package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Recipe
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.OnPrimaryContainer
import com.example.myapplication.ui.theme.PrimaryContainer
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun FavoritesScreen(
    onRecipeClick: (Recipe) -> Unit,
    favoriteRecipes: List<Recipe>,
) {

    var selectedSort by remember { mutableStateOf("По дате") }
    val sortOptions = listOf("По дате", "По времени", "Калории")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(top = 16.dp) // Отступ для топ бара
    ) {
        // Заголовок и кнопка сортировки
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Избранное",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Сортировка",
                tint = TextPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Открыть меню сортировки */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Фильтры сортировки
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(sortOptions) { option ->
                val isSelected = option == selectedSort
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) PrimaryContainer else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else BorderOutline,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedSort = option }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) OnPrimaryContainer else TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Сетка карточек (2 колонки)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(favoriteRecipes) { recipe ->
                FavoriteRecipeCard(recipe, onClick = { onRecipeClick(recipe) })
            }
        }
    }
}

// Карточка для сетки
@Composable
fun FavoriteRecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BorderOutline, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        // Фото рецепта
        Image(
            painter = painterResource(id = recipe.imageRes),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        // Название и время
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = recipe.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 2, // Обрезает, если длинное
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "⏱ ${recipe.time}",
                fontSize = 12.sp,
                color = TextSecondaryAndIcons
            )
        }
    }
}