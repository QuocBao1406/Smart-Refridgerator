package com.example.fridge.ui.components

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
import kotlinx.coroutines.selects.select

@Composable
fun AddFoodDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, Long) -> Unit
) {
    var nameInput by remember { mutableStateOf("")}
    var quantityInput by remember { mutableStateOf("") }
    var expiryInput by remember { mutableStateOf(System.currentTimeMillis() + 86400000*3) }

    var unitInput by remember { mutableStateOf("Kg") }
    var typeInput by remember { mutableStateOf("Thịt")}

    val unitOption = listOf("Kg", "g", "Hộp", "Quả", "Lít", "Bó", "Gói")
    val typeOption = listOf("Thịt cá", "Rau củ", "Trái cây", "Sữa/Đồ uống", "Gia vị", "Khác")

    var image by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            image = uri
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,

        confirmButton = {
            Button(
                onClick = {
                    val imageString = image?.toString() ?: ""
                    onSave(nameInput, imageString, quantityInput, unitInput, typeInput, expiryInput)
                }
            ) {
                Text("Lưu")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        },

        title = { Text("thêm đồ ăn mới") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (image != null) {
                        AsyncImage(
                            model = image,
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
                    label = { Text("Tên món ăn:") }
                )

                Spacer(modifier = Modifier. height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        label = { Text("Số lượng") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        DropdownField(
                            label = "Đơn vị",
                            options= unitOption,
                            selectedOption = unitInput,
                            onOptionSelected = { unitInput = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                DropdownField(
                    label = "Phân Loại",
                    options = typeOption,
                    selectedOption = typeInput,
                    onOptionSelected = { typeInput = it }
                )

                DatePickerField(
                    label = "Hạn sử dụng",
                    selectedDateMillis = expiryInput,
                    onDateSelected = { newDate ->
                        expiryInput = newDate
                    }
                )
            }
        }
    )
}