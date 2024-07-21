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
    setAmoled: (value: Boolean) -> Unit,
    setGradient: (value: Boolean) -> Unit,
    setAvatarGradient: (value: Boolean) -> Unit,
    setNicknameColorful: (value: Boolean) -> Unit,
) {

    BasicCard(
        title = R.string.settings_card_title,
        icon = R.drawable.icon_settings,
        description = R.string.settings_card_title
    ) {

        SettingItem(
            R.string.settings_card_switch_amoled,
            isAmoled,
            setAmoled
        )

        SettingItem(
            R.string.settings_card_use_gradient,
            isGradient,
            setGradient
        )

        SettingItem(
            R.string.settings_card_use_gradient_avatars,
            isAvatarGradient,
            setAvatarGradient
        )

        SettingItem(
            R.string.settings_card_monet_nick,
            isNicknameColorful,
            setNicknameColorful
        )
    }
}

@Composable
private fun SettingItem(
    @StringRes text: Int,
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(text)
        )

        Switch(
            checked = isChecked,
            onCheckedChange = { state -> onClick(state) }
        )
    }
}

@Preview
@Composable
private fun SettingsPreview() {

    var isAmoled by remember { mutableStateOf(false) }
    var isGradient by remember { mutableStateOf(true) }
    var isAvatarGradient by remember { mutableStateOf(false) }
    var isNicknameColorful by remember { mutableStateOf(true) }

    SettingsCard(
        isAmoled = isAmoled,
        isGradient = isGradient,
        isAvatarGradient = isAvatarGradient,
        isNicknameColorful = isNicknameColorful,
        setAmoled = { isAmoled = it},
        setGradient = { isGradient = it},
        setAvatarGradient = { isAvatarGradient = it},
        setNicknameColorful = { isNicknameColorful = it}
    )
}