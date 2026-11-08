package com.example.myapplication.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.myapplication.ui.theme.PrimaryContainer
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("DefaultLocale")
@Composable
fun CookingStepsScreen(recipeTitle: String, stepsImage: List<Int>, onBackClick: () -> Unit, onFinishClick: () -> Unit) {

    // Состояние текущего шага (от 1 до 5)
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 5

    // Таймер
    var timeLeft by remember { mutableIntStateOf(300) } // 300 секунд = 5 минут
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning && timeLeft > 0) {
            while (timeLeft > 0 && isTimerRunning) {
                delay(1000L.milliseconds)
                timeLeft--
            }
            if (timeLeft == 0) isTimerRunning = false
        }
    }

    // Формат секунд в виде 05:00
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Нижний тулбар
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recipeTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        // Верхний бар
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            LinearProgressIndicator(
                progress = { currentStep.toFloat() / totalSteps },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = PrimaryContainer,
                trackColor = BorderOutline
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Шаг $currentStep из $totalSteps",
                color = TextSecondaryAndIcons,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Основной контент
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            // Фото шага (есть ли картинка для этого шага)
            val currentImageRes = if (stepsImage.isNotEmpty() && currentStep <= stepsImage.size) {
                stepsImage[currentStep - 1]
            } else {
                0
            }

            if (currentImageRes != 0) {
                // Если картинка есть - показываем её
                Image(
                    painter = painterResource(id = currentImageRes),
                    contentDescription = "Фото шага $currentStep",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            } else {
                // Заглушка
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFE0D6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Тут будет фото шага", color = TextSecondaryAndIcons)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Заголовок и текст
            Text(
                text = "$currentStep. Обжарь лук",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Разогрей масло на среднем огне, добавь нарезанный лук и готовь 5-7 минут, помешивая.",
                fontSize = 16.sp,
                color = TextSecondaryAndIcons
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Таймер
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(FieldAndBarBackground)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏱", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(formattedTime, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        if (timeLeft > 0) isTimerRunning = !isTimerRunning
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isTimerRunning) Color(0xFFB3261E) else Color(0xFF4A4A4A)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        if (isTimerRunning) "⏸ Пауза" else "▶ Старт",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Советы
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFF3E0))
                    .border(1.dp, Color(0xFFFFE0B2), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    "💡 Советы",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFE65100)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "Не пережарить лук, он должен стать прозрачным, не коричневым.",
                    fontSize = 14.sp,
                    color = TextSecondaryAndIcons
                )
            }
        }

        // Кнопки внизу
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Кнопка Назад
            if (currentStep > 1) {
                Button(
                    onClick = { currentStep-- },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = TextPrimary),
                    border = BorderStroke(1.dp, BorderOutline),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("← Назад", fontSize = 16.sp)
                }
            }

            // Кнопка Далее
            Button(
                onClick = {
                    if (currentStep < totalSteps) currentStep++
                    else onFinishClick()
                },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    if (currentStep < totalSteps) "Далее →" else "Завершить ✓",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}