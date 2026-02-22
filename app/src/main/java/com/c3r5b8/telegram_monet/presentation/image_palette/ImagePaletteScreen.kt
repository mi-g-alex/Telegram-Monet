package com.c3r5b8.telegram_monet.presentation.image_palette

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.common.ColorScheme
import com.c3r5b8.telegram_monet.presentation.components.LabeledPaletteCircle
import com.c3r5b8.telegram_monet.presentation.components.PaletteCircle
import com.c3r5b8.telegram_monet.presentation.components.SimpleColorCircle
import com.c3r5b8.telegram_monet.presentation.components.TonalPaletteStrips
import com.c3r5b8.telegram_monet.presentation.components.getTonalPalettes
import com.c3r5b8.telegram_monet.presentation.components.paletteExampleTriple
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private fun parseHexColor(hex: String): Int? {
	val clean = hex.trimStart('#')
	return when (clean.length) {
		6 -> clean.toLongOrNull(16)?.let { (0xFF000000L or it).toInt() }
		8 -> clean.toLongOrNull(16)?.toInt()
		else -> null
	}
}

@Composable
fun ImagePaletteScreen(
	onBack: () -> Unit,
	paletteId: Int = -1,
	viewModel: ImagePaletteViewModel = koinViewModel(parameters = { parametersOf(paletteId) }),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val imageBitmap by viewModel.imageBitmap.collectAsStateWithLifecycle()
	val extractedColors by viewModel.extractedColors.collectAsStateWithLifecycle()
	val selectedColorIndex by viewModel.selectedColorIndex.collectAsStateWithLifecycle()
	val manualColor by viewModel.manualColor.collectAsStateWithLifecycle()
	val selectedScheme by viewModel.selectedScheme.collectAsStateWithLifecycle()
	val initialName by viewModel.initialName.collectAsStateWithLifecycle()

	var paletteName by rememberSaveable { mutableStateOf("") }
	var showColorPicker by remember { mutableStateOf(false) }

	LaunchedEffect(initialName) {
		initialName?.let { paletteName = it }
	}

	val selectedArgb: Int? = manualColor
		?: selectedColorIndex?.let { extractedColors.getOrNull(it) }

	val imagePicker = rememberLauncherForActivityResult(PickVisualMedia()) { uri: Uri? ->
		uri?.let { viewModel.processImage(it) }
	}

	if (showColorPicker) {
		ColorPickerDialog(
			onDismiss = { showColorPicker = false },
			onApply = { argb ->
				viewModel.setManualColor(argb)
				showColorPicker = false
			}
		)
	}

	val screenTitle = if (viewModel.isEditMode)
		stringResource(R.string.image_palette_edit_title)
	else
		stringResource(R.string.image_palette_title)

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(screenTitle) },
				navigationIcon = {
					IconButton(onClick = onBack) {
						Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
					}
				},
			)
		},
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.verticalScroll(rememberScrollState())
				.padding(horizontal = 16.dp, vertical = 8.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			SourceCard(
				isEditMode = viewModel.isEditMode,
				manualColor = manualColor,
				uiState = uiState,
				imageBitmap = imageBitmap,
				onPickImage = { imagePicker.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
				onPickColor = { showColorPicker = true },
				onReset = { viewModel.reset() },
			)

			if (!viewModel.isEditMode &&
				uiState is ImagePaletteUiState.ColorsReady &&
				extractedColors.isNotEmpty()
			) {
				ExtractedColorsCard(
					extractedColors = extractedColors,
					selectedColorIndex = selectedColorIndex,
					onSelectColor = { viewModel.selectColor(it) },
				)
			}

			if (selectedArgb != null) {
				TonalPaletteCard(selectedArgb = selectedArgb, selectedScheme = selectedScheme)
				SchemeSelectorCard(
					selectedArgb = selectedArgb,
					selectedScheme = selectedScheme,
					onSelectScheme = { viewModel.setScheme(it) },
				)
				SaveCard(
					paletteName = paletteName,
					onNameChange = { if (it.length < 11) paletteName = it },
					onSave = { viewModel.savePalette(paletteName); onBack() },
				)
			}
		}
	}
}

@Composable
private fun SourceCard(
	isEditMode: Boolean,
	manualColor: Int?,
	uiState: ImagePaletteUiState,
	imageBitmap: Bitmap?,
	onPickImage: () -> Unit,
	onPickColor: () -> Unit,
	onReset: () -> Unit,
) {
	ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		) {
			when {
				isEditMode && manualColor != null -> {
					SimpleColorCircle(argb = manualColor, size = 64.dp)
				}

				uiState is ImagePaletteUiState.Loading -> {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(80.dp),
						contentAlignment = Alignment.Center
					) { CircularProgressIndicator() }
				}

				imageBitmap != null -> {
					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Image(
							bitmap = imageBitmap.asImageBitmap(),
							contentDescription = null,
							modifier = Modifier
								.size(80.dp)
								.clip(RoundedCornerShape(12.dp)),
							contentScale = ContentScale.Crop
						)
						Spacer(modifier = Modifier.weight(1f))
						TextButton(onClick = onReset) {
							Text(stringResource(R.string.image_palette_change))
						}
					}
				}

				manualColor != null -> {
					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
					) {
						SimpleColorCircle(argb = manualColor, size = 64.dp)
						Spacer(modifier = Modifier.weight(1f))
						TextButton(onClick = onReset) {
							Text(stringResource(R.string.image_palette_change))
						}
					}
				}

				else -> {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(8.dp)
					) {
						Button(
							onClick = onPickImage,
							modifier = Modifier.weight(1f)
						) { Text(stringResource(R.string.image_palette_choose)) }
						Button(
							onClick = onPickColor,
							modifier = Modifier.weight(1f)
						) { Text(stringResource(R.string.image_palette_choose_color)) }
					}
				}
			}
		}
	}
}

@Composable
private fun ExtractedColorsCard(
	extractedColors: List<Int>,
	selectedColorIndex: Int?,
	onSelectColor: (Int) -> Unit,
) {
	ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		) {
			Text(stringResource(R.string.image_palette_extracted_colors), fontSize = 16.sp)
			Spacer(modifier = Modifier.height(12.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				extractedColors.forEachIndexed { index, argb ->
					SimpleColorCircle(
						argb = argb,
						isSelected = selectedColorIndex == index,
						onClick = { onSelectColor(index) },
					)
				}
			}
		}
	}
}

@Composable
private fun TonalPaletteCard(selectedArgb: Int, selectedScheme: ColorScheme) {
	val palettes = remember(selectedArgb, selectedScheme) {
		getTonalPalettes(selectedArgb, selectedScheme)
	}
	ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(6.dp)
		) {
			Text(stringResource(R.string.image_palette_tonal_palette), fontSize = 16.sp)
			Spacer(modifier = Modifier.height(6.dp))
			TonalPaletteStrips(palettes = palettes)
		}
	}
}

@Composable
private fun SchemeSelectorCard(
	selectedArgb: Int,
	selectedScheme: ColorScheme,
	onSelectScheme: (ColorScheme) -> Unit,
) {
	ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text(stringResource(R.string.scheme_selector_title), fontSize = 16.sp)
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(12.dp),
			) {
				ColorScheme.entries.forEach { scheme ->
					val (a1, a2, a3) = remember(selectedArgb, scheme) {
						paletteExampleTriple(selectedArgb, scheme)
					}
					ColorSchemeCard(
						topColor = a1,
						bottomLeftColor = a2,
						bottomRightColor = a3,
						label = stringResource(scheme.resName),
						isSelected = selectedScheme == scheme,
						onClick = { onSelectScheme(scheme) },
					)
				}
			}
		}
	}
}

@Composable
fun ColorSchemeCard(
	topColor: Color,
	bottomLeftColor: Color,
	bottomRightColor: Color,
	label: String,
	isSelected: Boolean = false,
	circleSize: Dp = 48.dp,
	onClick: (() -> Unit)? = null,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.then(
				onClick?.let {
					Modifier.clickable(onClick = it)
				} ?: Modifier
			),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		PaletteCircle(
			topColor = topColor,
			bottomLeftColor = bottomLeftColor,
			bottomRightColor = bottomRightColor,
			circleSize = circleSize,
			isSelected = isSelected,
		)
		Text(label)
	}
}

@Composable
private fun SaveCard(
	paletteName: String,
	onNameChange: (String) -> Unit,
	onSave: () -> Unit,
) {
	ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
		) {
			Text(stringResource(R.string.image_palette_name), fontSize = 16.sp)
			OutlinedTextField(
				value = paletteName,
				onValueChange = onNameChange,
				label = { Text(stringResource(R.string.image_palette_name_hint)) },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true
			)
			Button(
				onClick = onSave,
				enabled = paletteName.isNotBlank(),
				modifier = Modifier.fillMaxWidth()
			) { Text(stringResource(R.string.image_palette_save)) }
		}
	}
}

@Composable
private fun ColorPickerDialog(
	onDismiss: () -> Unit,
	onApply: (Int) -> Unit,
) {
	val controller = rememberColorPickerController()
	var pickedColor by remember { mutableStateOf(Color(0xFF6650A4L.toInt())) }
	var hexInput by remember { mutableStateOf("") }

	Dialog(onDismissRequest = onDismiss) {
		ElevatedCard(shape = RoundedCornerShape(24.dp)) {
			Column(
				modifier = Modifier.padding(20.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				Text(stringResource(R.string.image_palette_choose_color), fontSize = 18.sp)
				HsvColorPicker(
					modifier = Modifier.size(280.dp),
					controller = controller,
					onColorChanged = { envelope ->
						pickedColor = envelope.color
						if (envelope.fromUser) {
							hexInput = Integer.toHexString(envelope.color.toArgb() and 0xFFFFFF)
								.uppercase().padStart(6, '0')
						}
					}
				)
				OutlinedTextField(
					value = hexInput,
					onValueChange = { new ->
						hexInput = new.take(6)
						parseHexColor(new)?.let { argb ->
							pickedColor = Color(argb)
							controller.selectByColor(Color(argb), fromUser = false)
						}
					},
					label = { Text("RRGGBB") },
					placeholder = { Text("6650A4") },
					leadingIcon = { Text("#", fontSize = 16.sp) },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true,
				)
				val pickedArgb = pickedColor.toArgb()
				SimpleColorCircle(argb = pickedArgb)
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
					TextButton(onClick = onDismiss) { Text(stringResource(R.string.palette_card_delete_cancel)) }
					Spacer(modifier = Modifier.width(8.dp))
					Button(onClick = { onApply(pickedColor.toArgb()) }) {
						Text(stringResource(R.string.image_palette_manual_apply))
					}
				}
			}
		}
	}
}
