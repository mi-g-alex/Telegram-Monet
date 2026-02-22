package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.common.ColorScheme
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity
import com.c3r5b8.telegram_monet.presentation.components.LabeledPaletteCircle
import com.c3r5b8.telegram_monet.presentation.components.PaletteCircle
import com.c3r5b8.telegram_monet.presentation.components.TonalPaletteStrips
import com.c3r5b8.telegram_monet.presentation.components.getTonalPalettes
import com.c3r5b8.telegram_monet.presentation.components.paletteExampleTriple
import kotlinx.coroutines.launch

@Composable
fun PaletteCard(
    allPalettes: List<PaletteEntity>,
    selectedPaletteId: Int,
    onSelectPalette: (Int) -> Unit,
    onDeletePalette: (PaletteEntity) -> Unit,
    onEditPalette: (PaletteEntity) -> Unit,
    onOpenImagePalette: () -> Unit,
) {
    val context = LocalContext.current

    val systemA1 = remember(context) { Color(ContextCompat.getColor(context, R.color.system_accent1_600)) }
    val systemA2 = remember(context) { Color(ContextCompat.getColor(context, R.color.system_accent2_600)) }
    val systemA3 = remember(context) { Color(ContextCompat.getColor(context, R.color.system_accent3_600)) }
    val systemSeedArgb = remember(context) { ContextCompat.getColor(context, R.color.system_accent1_500) }

    var previewIsSystem by remember { mutableStateOf(false) }
    var previewEntity by remember { mutableStateOf<PaletteEntity?>(null) }
    val isShowingPreview = previewIsSystem || previewEntity != null

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    fun dismissPreview() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            previewIsSystem = false
            previewEntity = null
        }
    }

    var paletteToDelete by remember { mutableStateOf<PaletteEntity?>(null) }

    if (paletteToDelete != null) {
        AlertDialog(
            onDismissRequest = { paletteToDelete = null },
            title = { Text(stringResource(R.string.palette_card_delete_title)) },
            text = { Text(paletteToDelete!!.name) },
            confirmButton = {
                TextButton(onClick = {
                    onDeletePalette(paletteToDelete!!)
                    paletteToDelete = null
                    dismissPreview()
                }) {
                    Text(stringResource(R.string.palette_card_delete_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { paletteToDelete = null }) {
                    Text(stringResource(R.string.palette_card_delete_cancel))
                }
            }
        )
    }

    if (isShowingPreview) {
        val previewArgb = if (previewIsSystem) systemSeedArgb else previewEntity!!.argbColor
        val previewName = if (previewIsSystem)
            stringResource(R.string.palette_card_system_label)
        else
            previewEntity!!.name
        val previewScheme = if (previewIsSystem)
            ColorScheme.TONAL_SPOT
        else
            ColorScheme.fromName(previewEntity!!.scheme)

        ModalBottomSheet(
            onDismissRequest = { previewIsSystem = false; previewEntity = null },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val (a1, a2, a3) = remember(previewArgb, previewScheme) { paletteExampleTriple(previewArgb, previewScheme) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PaletteCircle(
                        topColor = a1,
                        bottomLeftColor = a2,
                        bottomRightColor = a3,
                        circleSize = 64.dp,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(previewName, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                        if (!previewIsSystem) {
                            Text(
                                text = stringResource(previewScheme.resName),
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                val palettes = remember(previewArgb, previewScheme) { getTonalPalettes(previewArgb, previewScheme) }
                TonalPaletteStrips(palettes = palettes)

				Column(
					modifier = Modifier.fillMaxWidth().wrapContentHeight(),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					if (previewEntity != null) {
						Row(
							modifier = Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.spacedBy(8.dp)
						) {
							OutlinedButton(
								onClick = { paletteToDelete = previewEntity },
								modifier = Modifier.weight(1f)
							) {
								Text(stringResource(R.string.palette_card_delete_confirm))
							}
							OutlinedButton(
								onClick = {
									onEditPalette(previewEntity!!)
									dismissPreview()
								},
								modifier = Modifier.weight(1f),
							) {
								Text(stringResource(R.string.palette_card_edit))
							}
						}
					}
					Button(
						onClick = {
							onSelectPalette(if (previewIsSystem) -1 else previewEntity!!.id)
							dismissPreview()
						},
						modifier = Modifier.fillMaxWidth()
					) {
						Text(stringResource(R.string.palette_card_apply))
					}
				}
            }
        }
    }

    // Main card
    BasicCard(
        title = R.string.palette_card_title,
        description = R.string.palette_card_title,
        icon = R.drawable.palette_icon,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // System color circle
            LabeledPaletteCircle(
                topColor = systemA1,
                bottomLeftColor = systemA2,
                bottomRightColor = systemA3,
                isSelected = selectedPaletteId == -1,
                onClick = { previewIsSystem = true },
                label = stringResource(R.string.palette_card_system_label),
            )

            // Saved palette circles
            allPalettes.forEach { palette ->
                val paletteScheme = remember(palette.scheme) { ColorScheme.fromName(palette.scheme) }
                val (a1, a2, a3) = remember(palette.argbColor, paletteScheme) {
                    paletteExampleTriple(palette.argbColor, paletteScheme)
                }
                LabeledPaletteCircle(
                    topColor = a1,
                    bottomLeftColor = a2,
                    bottomRightColor = a3,
                    isSelected = selectedPaletteId == palette.id,
                    onClick = { previewEntity = palette },
                    label = palette.name,
                )
            }

            // Add button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .combinedClickable(onClick = onOpenImagePalette),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.palette_card_add_label),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
                Text(
                    text = stringResource(R.string.palette_card_add_label),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
