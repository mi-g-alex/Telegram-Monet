package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.presentation.components.bounceClick

@Composable
fun SettingsCard(
    isAmoled: Boolean,
    isGradient: Boolean,
    isAvatarGradient: Boolean,
    isNicknameColorful: Boolean,
    isAlterOutColor: Boolean,
    setAmoled: (value: Boolean) -> Unit,
    setGradient: (value: Boolean) -> Unit,
    setAvatarGradient: (value: Boolean) -> Unit,
    setNicknameColorful: (value: Boolean) -> Unit,
    setUseAlterOutColor: (value: Boolean) -> Unit,
) {

    BasicCard(
        title = R.string.settings_card_title,
        icon = R.drawable.icon_settings,
        description = R.string.settings_card_title
    ) {

        SettingItem(
            stringResource(R.string.settings_card_switch_amoled),
            isAmoled,
            setAmoled
        )

        SettingItem(
            stringResource(R.string.settings_card_use_gradient),
            isGradient,
            setGradient
        )

        SettingItem(
            stringResource(R.string.settings_card_use_gradient_avatars),
            isAvatarGradient,
            setAvatarGradient
        )

        SettingItem(
            stringResource(R.string.settings_card_monet_nick),
            isNicknameColorful,
            setNicknameColorful
        )

        SettingItem(
            stringResource(R.string.settings_card_chat_old_style),
            isAlterOutColor,
            setUseAlterOutColor
        )
    }
}

@Composable
private fun SettingItem(
    text: String,
    isChecked: Boolean,
    onClick: (state: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(0.9f) {
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
}

@Preview(locale = "ru")
@Composable
private fun SettingsPreview() {

    var isAmoled by remember { mutableStateOf(false) }
    var isGradient by remember { mutableStateOf(true) }
    var isAvatarGradient by remember { mutableStateOf(false) }
    var isNicknameColorful by remember { mutableStateOf(true) }
    var isOldChatStyle by remember { mutableStateOf(true) }

    SettingsCard(
        isAmoled = isAmoled,
        isGradient = isGradient,
        isAvatarGradient = isAvatarGradient,
        isNicknameColorful = isNicknameColorful,
        isAlterOutColor = isOldChatStyle,
        setAmoled = { isAmoled = it },
        setGradient = { isGradient = it },
        setAvatarGradient = { isAvatarGradient = it },
        setNicknameColorful = { isNicknameColorful = it },
        setUseAlterOutColor = { isOldChatStyle = it }
    )
}

@Preview
@Composable
private fun LongTextPreview() {
    SettingItem(
        "TEST VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY LONG TEXT",
        false,
    ) { }
}