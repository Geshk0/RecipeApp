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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AccentOrange
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.FieldAndBarBackground
import com.example.myapplication.ui.theme.OnPrimaryContainer
import com.example.myapplication.ui.theme.PrimaryContainer
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddProductScreen(onBackClick: () -> Unit, onSaveClick: () -> Unit) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("шт") }
    var selectedCategory by remember { mutableStateOf("Овощи") }
    var expiryDate by remember { mutableStateOf("") }

    val categories = listOf("Овощи", "Фрукты", "Молочное", "Мясо", "Крупы")

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
                "Новый продукт",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Поле Название
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            placeholder = { Text("Например, Морковь") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentOrange,
                unfocusedBorderColor = BorderOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Количество и единица
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Поле Количество
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Количество") },
                modifier = Modifier.weight(2f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentOrange,
                    unfocusedBorderColor = BorderOutline,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            // Выпадающий список единиц (в виде OutlinedTextField)
            OutlinedTextField(
                value = selectedUnit,
                onValueChange = {},
                readOnly = true,
                label = { Text(" ") },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = TextSecondaryAndIcons) },
                modifier = Modifier
                    .weight(1f)
                    .clickable { /* TODO: Показать меню выбора */ },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderOutline,
                    unfocusedBorderColor = BorderOutline,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Категория
        Text(
            "Категория",
            fontSize = 14.sp,
            color = TextSecondaryAndIcons
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) PrimaryContainer else Color.White)
                        .border(1.dp, if (isSelected) Color.Transparent else BorderOutline, RoundedCornerShape(20.dp))
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        category,
                        color = if (isSelected) OnPrimaryContainer else TextSecondaryAndIcons,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Срок годности
        OutlinedTextField(
            value = expiryDate,
            onValueChange = { expiryDate = it },
            label = { Text("Срок годности") },
            placeholder = { Text("дд.мм.гггг") },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Календарь",
                    tint = TextSecondaryAndIcons,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "/* TODO: Открыть календарь */", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentOrange,
                unfocusedBorderColor = BorderOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка Сканировать
        Button(
            onClick = { /* TODO: Открыть камеру */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = TextPrimary),
            border = BorderStroke(1.dp, BorderOutline),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                Icons.Default.QrCodeScanner,
                contentDescription = "Сканер"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Сканировать штрих-код",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Прижимаем кнопки

        // Кнопки внизу
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onBackClick() },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = TextPrimary),
                border = BorderStroke(1.dp, BorderOutline),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Отмена",
                    fontSize = 16.sp
                )
            }

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        // TODO: Логика сохранения
                        onSaveClick()
                    }
                },
                modifier = Modifier.weight(2f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Сохранить",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}