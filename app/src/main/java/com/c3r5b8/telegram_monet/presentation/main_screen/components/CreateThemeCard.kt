package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            CardButton(R.string.setup_telegram, onTelegramClick)
            CardButton(R.string.setup_telegram_x, onTelegramXClick)
        }
    }
}

@Composable
private fun CardButton(
    @StringRes text: Int,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.padding(end = 8.dp),
        onClick = { onClick() }
    ) {
        Text(
            stringResource(text)
        )
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