package com.c3r5b8.telegram_monet.presentation.main_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.common.Constants
import com.c3r5b8.telegram_monet.presentation.main_screen.components.AboutCard
import com.c3r5b8.telegram_monet.presentation.main_screen.components.CreateThemeCard
import com.c3r5b8.telegram_monet.presentation.main_screen.components.SettingsCard
import com.c3r5b8.telegram_monet.presentation.main_screen.components.TopAppBar

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
) {

    val isAmoled by remember { viewModel.isAmoled }
    val isGradient by remember { viewModel.isGradient }
    val isAvatarGradient by remember { viewModel.isAvatarGradient }
    val isNicknameColorful by remember { viewModel.isNicknameColorful }
    val isAlterOutColor by remember { viewModel.isAlterOutColor }
    val context = LocalContext.current

    MainScreenComponent(
        isAmoled = isAmoled,
        isGradient = isGradient,
        isAvatarGradient = isAvatarGradient,
        isNicknameColorful = isNicknameColorful,
        isAlterOutColor = isAlterOutColor,
        setAmoled = { viewModel.setSettings(Constants.SHARED_IS_AMOLED, it) },
        setGradient = { viewModel.setSettings(Constants.SHARED_USE_GRADIENT, it) },
        setAvatarGradient = { viewModel.setSettings(Constants.SHARED_USE_GRADIENT_AVATARS, it) },
        setNicknameColorful = { viewModel.setSettings(Constants.SHARED_USE_COLORFUL_NICKNAME, it) },
        setUseOldChatStyle = { viewModel.setSettings(Constants.SHARED_USE_OLD_CHAT_STYLE, it) },
        onShareTheme = { isTg, isLight -> viewModel.onShareTheme(context, isTg, isLight) },
    )
}

@Composable
private fun MainScreenComponent(
    isAmoled: Boolean,
    isGradient: Boolean,
    isAvatarGradient: Boolean,
    isNicknameColorful: Boolean,
    isAlterOutColor: Boolean,
    setAmoled: (value: Boolean) -> Unit,
    setGradient: (value: Boolean) -> Unit,
    setAvatarGradient: (value: Boolean) -> Unit,
    setNicknameColorful: (value: Boolean) -> Unit,
    setUseOldChatStyle: (value: Boolean) -> Unit,
    onShareTheme: (isTelegram: Boolean, isLight: Boolean) -> Unit,
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val localContext = LocalContext.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(Constants.URL_ABOUT)
                localContext.startActivity(i)
            }
         }
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {

            item {
                CreateThemeCard(
                    title = R.string.light_theme,
                    description = R.string.light_theme_description,
                    icon = R.drawable.theme_icon_light,
                    onTelegramClick = {
                        onShareTheme(true, true)
                    },
                    onTelegramXClick = {
                        onShareTheme(false, true)
                    }
                )
            }

            item {
                CreateThemeCard(
                    title = R.string.dark_theme,
                    description = R.string.dark_theme_description,
                    icon = R.drawable.theme_icon_dark,
                    onTelegramClick = {
                        onShareTheme(true, false)
                    },
                    onTelegramXClick = {
                        onShareTheme(false, false)
                    }
                )
            }

            item {
                SettingsCard(
                    isAmoled = isAmoled,
                    isGradient = isGradient,
                    isAvatarGradient = isAvatarGradient,
                    isNicknameColorful = isNicknameColorful,
                    isAlterOutColor = isAlterOutColor,
                    setAmoled = setAmoled,
                    setGradient = setGradient,
                    setAvatarGradient = setAvatarGradient,
                    setNicknameColorful = setNicknameColorful,
                    setUseAlterOutColor = setUseOldChatStyle
                )
            }

            item {
                AboutCard()
            }

        }

    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreenComponent(
        isAmoled = true,
        isGradient = false,
        isAvatarGradient = true,
        isNicknameColorful = false,
        isAlterOutColor = true,
        setAmoled = { },
        setGradient = { },
        setAvatarGradient = { },
        setNicknameColorful = { },
        setUseOldChatStyle = {},
        onShareTheme = { _, _ -> },
    )
}