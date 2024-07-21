package com.c3r5b8.telegram_monet.presentation.main_screen.components

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c3r5b8.telegram_monet.R
import com.c3r5b8.telegram_monet.common.Constants

@Composable
fun AboutCard(

) {

    val localContext = LocalContext.current

    val openLink: (link: String) -> Unit =  { link ->
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        localContext.startActivity(i)
    }

    BasicCard(
        title = R.string.about_card_title,
        icon = R.drawable.about_icon,
        description = R.string.about_card_description
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            text = stringResource(R.string.about_card_description),
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CardButton(R.string.about_card_telegram) {
                openLink(Constants.URL_TELEGRAM)
            }
            CardButton(R.string.about_card_github) {
                openLink(Constants.URL_GITHUB)
            }
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
private fun AboutCardPreview() {

    AboutCard(
    )
}