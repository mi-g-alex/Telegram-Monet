package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreen

@Composable
fun SettingsCard(
	isAmoled: Boolean,
	isGradient: Boolean,
	isAvatarGradient: Boolean,
	isNicknameColorful: Boolean,
	isAlterOutColor: Boolean,
	isUseDivider: Boolean,
	setAmoled: (value: Boolean) -> Unit,
	setGradient: (value: Boolean) -> Unit,
	setAvatarGradient: (value: Boolean) -> Unit,
	setNicknameColorful: (value: Boolean) -> Unit,
	setUseAlterOutColor: (value: Boolean) -> Unit,
	setUseDivider: (value: Boolean) -> Unit,
) {

	BasicCard(
		title = R.string.settings_card_title,
		icon = R.drawable.icon_settings,
		description = R.string.settings_card_title,
	) {

		SettingItem(
			stringResource(R.string.settings_card_switch_amoled),
			isAmoled,
			setAmoled,
		)

		SettingItem(
			stringResource(R.string.settings_card_use_gradient),
			isGradient,
			setGradient,
		)

		SettingItem(
			stringResource(R.string.settings_card_use_gradient_avatars),
			isAvatarGradient,
			setAvatarGradient,
		)

		SettingItem(
			stringResource(R.string.settings_card_monet_nick),
			isNicknameColorful,
			setNicknameColorful,
		)

		SettingItem(
			stringResource(R.string.settings_card_chat_old_style),
			isAlterOutColor,
			setUseAlterOutColor,
		)

		SettingItem(
			stringResource(R.string.settings_card_use_divider),
			isUseDivider,
			setUseDivider,
			addDivider = false,
		)
	}
}

@Composable
private fun SettingItem(
	text: String,
	isChecked: Boolean,
	onClick: (state: Boolean) -> Unit,
	addDivider: Boolean = true,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable {
				onClick(!isChecked)
			},
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceEvenly
	) {
		Text(
			text = text,
			modifier = Modifier.weight(1f)
		)

		Switch(
			checked = isChecked,
			onCheckedChange = { state -> onClick(state) }
		)
	}
	if (addDivider) {
		HorizontalDivider(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 2.dp)
		)
	}
}

@Preview(locale = "ru")
@Composable
private fun SettingsPreview() {

	var isAmoled by remember { mutableStateOf(false) }
	var isGradient by remember { mutableStateOf(true) }
	var isAvatarGradient by remember { mutableStateOf(false) }
	var isNicknameColorful by remember { mutableStateOf(true) }
	var isOldChatStyle by remember { mutableStateOf(true) }
	var isUseDivider by remember { mutableStateOf(true) }

	SettingsCard(
		isAmoled = isAmoled,
		isGradient = isGradient,
		isAvatarGradient = isAvatarGradient,
		isNicknameColorful = isNicknameColorful,
		isAlterOutColor = isOldChatStyle,
		isUseDivider = isUseDivider,
		setAmoled = { isAmoled = it },
		setGradient = { isGradient = it },
		setAvatarGradient = { isAvatarGradient = it },
		setNicknameColorful = { isNicknameColorful = it },
		setUseAlterOutColor = { isOldChatStyle = it },
		setUseDivider = { isUseDivider = it },
	)
}

@Preview
@Composable
private fun LongTextPreview() {
	SettingItem(
		"TEST VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY LONG TEXT",
		false,
		{},
	)
}
