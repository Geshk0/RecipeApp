package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.Error
import com.example.myapplication.ui.theme.FieldAndBarBackground
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons

@Composable
fun ProfileScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Верхний тулбар
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Кнопка Назад
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(FieldAndBarBackground)
                    .border(1.dp, BorderOutline, CircleShape)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = TextPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                "Профиль",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Данные пользователя
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Аватарка
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(FieldAndBarBackground)
                    .border(1.dp, BorderOutline, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Аватар",
                    tint = TextPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Иван Иванов", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("ivan@mail.ru", fontSize = 14.sp, color = TextSecondaryAndIcons)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Секция Мои предпочтения
        Text(
            "МОИ ПРЕДПОЧТЕНИЯ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondaryAndIcons
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
        ) {
            ProfileRow(
                icon = "🍽",
                title = "Диета",
                value = "Обычная",
                showArrow = true
            )

            HorizontalDivider(color = BorderOutline)

            ProfileRow(
                icon = "⚠",
                title = "Аллергии",
                value = "Нет",
                showArrow = true
            )

            HorizontalDivider(color = BorderOutline)

            ProfileRow(
                icon = "👥",
                title = "Порции по умолчанию",
                value = "2",
                showArrow = false
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Секция Данные
        Text(
            "ДАННЫЕ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondaryAndIcons
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
        ) {
            ProfileRow(
                icon = "📤",
                title = "Экспорт списка",
                value = "",
                showArrow = true
            )

            HorizontalDivider(color = BorderOutline)

            ProfileRow(
                icon = "🗑",
                title = "Очистить историю",
                value = "",
                showArrow = true,
                textColor = Error
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Прижимаем кнопку

        // Кнопка Выйти
        Button(
            onClick = { /* TODO: Выход */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Error),
            border = BorderStroke(1.dp, Error),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Выйти", fontSize = 16.sp)
        }
    }
}

// Вспомогательная функция для строк в профиле
@Composable
fun ProfileRow(
    icon: String,
    title: String,
    value: String,
    showArrow: Boolean,
    textColor: Color = TextPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Действие */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 16.sp, color = textColor, modifier = Modifier.weight(1f))
        Text(value, fontSize = 16.sp, color = TextSecondaryAndIcons)
        if (showArrow) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = TextSecondaryAndIcons,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}