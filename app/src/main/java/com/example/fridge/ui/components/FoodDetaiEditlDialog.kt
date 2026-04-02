package com.example.fridge.ui.components

import android.R.attr.contentDescription
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fridge.data.model.FoodItem

@Composable
fun FoodDetaildEditDialog(
    food: FoodItem,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, Long) -> Unit
) {
    var nameInput by remember { mutableStateOf(food.name)}
    var quantityInput by remember { mutableStateOf(food.quantity.toString()) }
    var unitInput by remember { mutableStateOf(food.unit) }
    var typeInput by remember { mutableStateOf(food.type)}
    var expiryInput by remember { mutableStateOf(food.expiry) }
    var isEditing by remember { mutableStateOf(false) }
    val unitOption = listOf("Kg", "Hộp", "Quả", "Lít", "Bó", "Gói")
    val typeOption = listOf("Thịt cá", "Rau củ", "Trái cây", "Sữa/Đồ uống", "Gia vị", "Khác")

    var imageInput by remember {
        mutableStateOf(
            if (food.image.isNotEmpty()) Uri.parse(food.image) else null
        )
    }

    val photoPickerLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) imageInput = uri
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (isEditing) "Sửa món ăn" else "Chi tiết món ăn")
        },

        confirmButton = {
            Button(
                onClick = {
                    if (isEditing) {
                        val imageString = imageInput?.toString() ?: ""
                        onSave(nameInput, imageString, quantityInput, unitInput, typeInput, expiryInput)
                        onDismiss()
                    } else {
                        isEditing = true
                    }
                }
            ) {
                Text(if (isEditing) "Lưu" else "Sửa")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Đóng")
            }
        },

        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable(enabled = isEditing) {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageInput != null) {
                        AsyncImage(
                            model = imageInput,
                            contentDescription = "Ảnh món ăn",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = "Chọn ảnh",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Tên món") },
                    enabled = isEditing
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        label = { Text("Số lượng") },
                        enabled = isEditing,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        DropdownField(
                            label = "Đơn vị",
                            options = unitOption,
                            selectedOption = unitInput,
                            onOptionSelected = { unitInput = it },
                            enabled = isEditing
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                DropdownField(
                    label = "Phân loại",
                    options = typeOption,
                    selectedOption = typeInput,
                    onOptionSelected = { typeInput = it },
                    enabled = isEditing
                )

                DatePickerField(
                    label = "Hạn sử dụng",
                    selectedDateMillis = food.expiry,
                    enabled = isEditing,
                    onDateSelected = { newDate ->
                        expiryInput = newDate
                    }
                )
            }
        },
    )
}