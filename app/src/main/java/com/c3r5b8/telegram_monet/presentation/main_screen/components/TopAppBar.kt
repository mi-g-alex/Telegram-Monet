package com.c3r5b8.telegram_monet.presentation.main_screen.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.c3r5b8.telegram_monet.R

@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    goHowUse: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { goHowUse() }) {
                Icon(
                    painterResource(R.drawable.how_to_use_icon),
                    stringResource(R.string.how_to_use)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    TopAppBar(
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    ){}
}