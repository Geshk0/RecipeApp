package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.Recipe
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.FieldAndBarBackground
import com.example.myapplication.ui.theme.OnPrimaryContainer
import com.example.myapplication.ui.theme.PrimaryContainer
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons
import com.example.myapplication.getPortionsText
import androidx.compose.foundation.Image

@Composable
fun RecipesScreen(
    onRecipeClick: (Recipe) -> Unit,
    onSettingsClick: () -> Unit,
    favoriteRecipes: List<Recipe>
) {
    // Тестовые данные для рецептов
    val recipes = remember {
        mutableStateListOf(
            Recipe(
                "Макароны с сыром",
                "15 мин",
                "350 ккал",
                2,
                true,
                imageRes = R.drawable.macaroni,
                stepsImages = listOf(
                    R.drawable.macaroni
                )
            ),
            Recipe(
                "Омлет с сыром",
                "10 мин",
                "280 ккал",
                2,
                true,
                imageRes = R.drawable.omlet,
            ),
            Recipe(
                "Сырники",
                "20 мин",
                "420 ккал",
                2,
                false,
                imageRes = R.drawable.sirniki,
            ),
            Recipe(
                "Паста с чесноком",
                "25 мин",
                "390 ккал",
                1,
                true,
                imageRes = R.drawable.pasta,
            )
        )
    }

    // Состояние для выбранного фильтра
    var selectedFilter by remember { mutableStateOf("Все") }
    val filters = listOf("Все", "Без плиты", "15 мин", "До 200 ₽", "Микроволновка")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Верхний тулбар
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Текст
            Text(
                text = "Можно приготовить",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            // Иконка
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(FieldAndBarBackground)
                    .border(1.dp, BorderOutline, CircleShape)
                    .clickable { onSettingsClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Settings,
                    contentDescription = "Настройки",
                    tint = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Фильтры
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                val isSelected = filter == selectedFilter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) PrimaryContainer else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else BorderOutline,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedFilter = filter }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = filter,
                        color = if (isSelected) OnPrimaryContainer else TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список рецептов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe, onClick = { onRecipeClick(recipe) })
            }
        }
    }
}

// Карточка рецепта
@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            1.dp,
            BorderOutline
        )
    ) {
        Column {
            // Заглушка вместо картинки
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )


            // Информация о рецепте
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Время, калории и порции
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "⏱ ${recipe.time}",
                        color = TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "🔥 ${recipe.calories}",
                        color = TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "🍽 ${getPortionsText(recipe.portions)}",
                        color = TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // "Всё есть" / "Не хватает"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (recipe.isAvailable) Color(0xFF4CAF50) else Color(0xFFFF9800))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (recipe.isAvailable) "Всё есть" else "Не хватает 1", // Затычка, до появления БД
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}