package com.example.myapplication.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Recipe
import com.example.myapplication.ui.theme.AccentOrange
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.Error
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons
import com.example.myapplication.getPortionsText
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
    onStartCookingClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // 1. Верхний тулбар
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Color(0xFFFFE0D6))
        ) {
            // Фото блюда
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Кнопка "Назад"
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }
            // Кнопка "Сердце"
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onFavoriteClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "В избранное",
                    tint = if (isFavorite) AccentOrange else TextSecondaryAndIcons
                )
            }
        }

        // Информация о блюде
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = recipe.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "⏱ ${recipe.time}",
                    color = TextSecondaryAndIcons,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    "🔥 ${recipe.calories}",
                    color = TextSecondaryAndIcons,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    "🍽 ${getPortionsText(recipe.portions)}",
                    color = TextSecondaryAndIcons,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ингредиенты
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    "Ингредиенты",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Тестовый список
                IngredientRow("Макароны", "200 г", true)
                IngredientRow("Сыр", "100 г", true)
                IngredientRow("Масло", "20 г", true)
                IngredientRow("Соль", "по вкусу", false)

                Spacer(modifier = Modifier.height(12.dp))

                // Кнопка "Добавить недостающее"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, BorderOutline, RoundedCornerShape(8.dp))
                        .clickable { /* TODO: Добавить в покупки */ }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Добавить недостающее",
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Добавить",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Прижимаем кнопку

            // Кнопка "Начать готовить"
            Button(
                onClick = { onStartCookingClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Начать готовить",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

// Вспомогательная функция для ингредиента
@Composable
fun IngredientRow(name: String, amount: String, isAvailable: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        // Квадратик с галочкой
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (isAvailable) TextPrimary else Color.Transparent)
                .border(
                    1.dp,
                    if (isAvailable) TextPrimary else BorderOutline,
                    RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isAvailable) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Название ингредиента
        Text(
            text = name,
            fontSize = 16.sp,
            color = if (isAvailable) TextPrimary else Error,
            modifier = Modifier.weight(1f)
        )

        // Количество
        Text(
            text = amount,
            color = TextSecondaryAndIcons,
            fontSize = 16.sp
        )
    }
}