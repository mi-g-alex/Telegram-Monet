package com.c3r5b8.telegram_monet.presentation.how_to_use

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.common.Constants

@Composable
fun HowToUseScreen(
    goBack: () -> Unit
) {
    var goBackClicked by remember { mutableStateOf(false) }

    var isError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.how_to_use)) },
                navigationIcon = {
                    IconButton(onClick = { goBackClicked = true; goBack() }, enabled = !goBackClicked) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                }
            )
        }
    ) { pad ->
        Box(Modifier.fillMaxSize().padding(pad).padding(16.dp), contentAlignment = Alignment.Center) {
            if(!isError) CircularProgressIndicator()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.URL_GIF)
                    .decoderFactory(GifDecoder.Factory())
                    .build(),
                contentDescription = stringResource(R.string.how_to_use),
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillHeight,
                onError = {
                    isError = true
                }
            )
            if(isError) {
                Text(
                    stringResource(R.string.something_went_wrong)
                )
            }
        }
    }
}