package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c3r5b8.telegram_monet.R

@Composable
fun CreateThemeCard(
	@StringRes title: Int,
	@StringRes description: Int,
	@DrawableRes icon: Int,
	onTelegramClick: () -> Unit,
	onTelegramXClick: () -> Unit,
	onTelegramLongClick: () -> Unit = {},
	onTelegramXLongClick: () -> Unit = {},
) {
	BasicCard(
		title = title,
		icon = icon,
		description = description
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 12.dp),
			text = stringResource(description),
			fontSize = 14.sp
		)

		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			CardButton(R.string.setup_telegram, onTelegramClick, onTelegramLongClick)
			CardButton(R.string.setup_telegram_x, onTelegramXClick, onTelegramXLongClick)
		}

		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 8.dp),
			text = stringResource(R.string.long_press_to_save_hint, "Downloads/TgMonet"),
			fontSize = 12.sp,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardButton(
	@StringRes text: Int,
	onClick: () -> Unit,
	onLongClick: (() -> Unit)? = null,
) {
	val haptic = LocalHapticFeedback.current
	Surface(
		modifier = Modifier
			.padding(end = 8.dp)
			.combinedClickable(
				onClick = onClick,
				onLongClick = onLongClick?.let { lc ->
					{
						haptic.performHapticFeedback(HapticFeedbackType.LongPress)
						lc()
					}
				},
			),
		shape = ButtonDefaults.shape,
		color = MaterialTheme.colorScheme.primary,
		contentColor = MaterialTheme.colorScheme.onPrimary,
	) {
		Box(
			modifier = Modifier.padding(ButtonDefaults.ContentPadding),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = stringResource(text),
				style = MaterialTheme.typography.labelLarge,
			)
		}
	}
}


@Preview
@Composable
private fun CreateCardPreview1() {
	CreateThemeCard(
		title = R.string.light_theme,
		description = R.string.light_theme_description,
		icon = R.drawable.theme_icon_light,
		onTelegramClick = {},
		onTelegramXClick = {},
	)
}

@Preview
@Composable
private fun CreateCardPreview2() {
	CreateThemeCard(
		title = R.string.dark_theme,
		description = R.string.dark_theme_description,
		icon = R.drawable.theme_icon_dark,
		onTelegramClick = {},
		onTelegramXClick = {},
	)
}
