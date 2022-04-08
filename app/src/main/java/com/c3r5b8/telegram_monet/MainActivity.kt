package com.c3r5b8.telegram_monet

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat //for import colors
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        val buttonDark: ExtendedFloatingActionButton = findViewById(R.id.button_dark)
        val buttonLight: ExtendedFloatingActionButton = findViewById(R.id.button_light)
        val buttonGitHub : ExtendedFloatingActionButton = findViewById(R.id.button_github)
        val buttonTelegram : ExtendedFloatingActionButton = findViewById(R.id.button_tg)
        buttonDark.setOnClickListener {
            val darkMonetFile = "monet_dark.attheme"
            val outputFile = "MonetDark.attheme"
            val theme : String = resources.getString(R.string.dark_theme)
            shareTheme(darkMonetFile, outputFile, theme)
        }
        buttonLight.setOnClickListener {
            val lightMonetFile = "monet_light.attheme"
            val outputFile = "MonetLight.attheme"
            val theme : String = resources.getString(R.string.light_theme)
            shareTheme(lightMonetFile, outputFile, theme)
        }

        buttonGitHub.setOnClickListener{
            openLink("https://github.com/c3r5b8/Telegram-Monet")
        }

        buttonTelegram.setOnClickListener{
            openLink("https://t.me/tgmonet")
        }
    }
    private fun openLink(link: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        startActivity(i)
    }


    private fun shareTheme(MonetFile: String, outputFile : String, theme : String) {
        val a1_0 = ContextCompat.getColor(applicationContext, R.color.system_accent1_0)
        val a1_10 = ContextCompat.getColor(applicationContext, R.color.system_accent1_10)
        val a1_50 = ContextCompat.getColor(applicationContext, R.color.system_accent1_50)
        val a1_100 = ContextCompat.getColor(applicationContext, R.color.system_accent1_100)
        val a1_200 = ContextCompat.getColor(applicationContext, R.color.system_accent1_200)
        val a1_300 = ContextCompat.getColor(applicationContext, R.color.system_accent1_300)
        val a1_400 = ContextCompat.getColor(applicationContext, R.color.system_accent1_400)
        val a1_500 = ContextCompat.getColor(applicationContext, R.color.system_accent1_500)
        val a1_600 = ContextCompat.getColor(applicationContext, R.color.system_accent1_600)
        val a1_700 = ContextCompat.getColor(applicationContext, R.color.system_accent1_700)
        val a1_800 = ContextCompat.getColor(applicationContext, R.color.system_accent1_800)
        val a1_900 = ContextCompat.getColor(applicationContext, R.color.system_accent1_900)
        val a1_1000 = ContextCompat.getColor(applicationContext, R.color.system_accent1_1000)
        val a2_0 = ContextCompat.getColor(applicationContext, R.color.system_accent2_0)
        val a2_10 = ContextCompat.getColor(applicationContext, R.color.system_accent2_10)
        val a2_50 = ContextCompat.getColor(applicationContext, R.color.system_accent2_50)
        val a2_100 = ContextCompat.getColor(applicationContext, R.color.system_accent2_100)
        val a2_200 = ContextCompat.getColor(applicationContext, R.color.system_accent2_200)
        val a2_300 = ContextCompat.getColor(applicationContext, R.color.system_accent2_300)
        val a2_400 = ContextCompat.getColor(applicationContext, R.color.system_accent2_400)
        val a2_500 = ContextCompat.getColor(applicationContext, R.color.system_accent2_500)
        val a2_600 = ContextCompat.getColor(applicationContext, R.color.system_accent2_600)
        val a2_700 = ContextCompat.getColor(applicationContext, R.color.system_accent2_700)
        val a2_800 = ContextCompat.getColor(applicationContext, R.color.system_accent2_800)
        val a2_900 = ContextCompat.getColor(applicationContext, R.color.system_accent2_900)
        val a2_1000 = ContextCompat.getColor(applicationContext, R.color.system_accent2_1000)
        val a3_0 = ContextCompat.getColor(applicationContext, R.color.system_accent3_0)
        val a3_10 = ContextCompat.getColor(applicationContext, R.color.system_accent3_10)
        val a3_50 = ContextCompat.getColor(applicationContext, R.color.system_accent3_50)
        val a3_100 = ContextCompat.getColor(applicationContext, R.color.system_accent3_100)
        val a3_200 = ContextCompat.getColor(applicationContext, R.color.system_accent3_200)
        val a3_300 = ContextCompat.getColor(applicationContext, R.color.system_accent3_300)
        val a3_400 = ContextCompat.getColor(applicationContext, R.color.system_accent3_400)
        val a3_500 = ContextCompat.getColor(applicationContext, R.color.system_accent3_500)
        val a3_600 = ContextCompat.getColor(applicationContext, R.color.system_accent3_600)
        val a3_700 = ContextCompat.getColor(applicationContext, R.color.system_accent3_700)
        val a3_800 = ContextCompat.getColor(applicationContext, R.color.system_accent3_800)
        val a3_900 = ContextCompat.getColor(applicationContext, R.color.system_accent3_900)
        val a3_1000 = ContextCompat.getColor(applicationContext, R.color.system_accent3_1000)
        val n1_0 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_0)
        val n1_10 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_10)
        val n1_50 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_50)
        val n1_100 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_100)
        val n1_200 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_200)
        val n1_300 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_300)
        val n1_400 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_400)
        val n1_500 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_500)
        val n1_600 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_600)
        val n1_700 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_700)
        val n1_800 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_800)
        val n1_900 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_900)
        val n1_1000 = ContextCompat.getColor(applicationContext, R.color.system_neutral1_1000)
        val n2_0 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_0)
        val n2_10 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_10)
        val n2_50 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_50)
        val n2_100 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_100)
        val n2_200 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_200)
        val n2_300 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_300)
        val n2_400 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_400)
        val n2_500 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_500)
        val n2_600 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_600)
        val n2_700 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_700)
        val n2_800 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_800)
        val n2_900 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_900)
        val n2_1000 = ContextCompat.getColor(applicationContext, R.color.system_neutral2_1000)
        val darkThemeImport = application.assets.open(MonetFile).bufferedReader().readLines().joinToString().replace(", ", "\n").replace("a1_0", a1_0.toString() ).replace("a1_500", a1_500.toString() ).replace("a1_50", a1_50.toString() ).replace("a1_200", a1_200.toString() ).replace("a1_300", a1_300.toString() ).replace("a1_400", a1_400.toString() ).replace("a1_600", a1_600.toString() ).replace("a1_700", a1_700.toString() ).replace("a1_800", a1_800.toString() ).replace("a1_900", a1_900.toString() ).replace("a1_1000", a1_1000.toString() ).replace("a1_100", a1_100.toString() ).replace("a1_10", a1_10.toString() ).replace("a2_0", a2_0.toString() ).replace("a2_200", a2_200.toString() ).replace("a2_300", a2_300.toString() ).replace("a2_400", a2_400.toString() ).replace("a2_500", a2_500.toString() ).replace("a2_50", a2_50.toString() ).replace("a2_600", a2_600.toString() ).replace("a2_700", a2_700.toString() ).replace("a2_800", a2_800.toString() ).replace("a2_900", a2_900.toString() ).replace("a2_1000", a2_1000.toString() ).replace("a2_100", a2_100.toString() ).replace("a2_10", a2_10.toString() ).replace("a3_0", a3_0.toString() ).replace("a3_200", a3_200.toString() ).replace("a3_300", a3_300.toString() ).replace("a3_400", a3_400.toString() ).replace("a3_500", a3_500.toString() ).replace("a3_50", a3_50.toString() ).replace("a3_600", a3_600.toString() ).replace("a3_700", a3_700.toString() ).replace("a3_800", a3_800.toString() ).replace("a3_900", a3_900.toString() ).replace("a3_1000", a3_1000.toString() ).replace("a3_100", a3_100.toString() ).replace("a3_10", a3_10.toString() ).replace("n1_0", n1_0.toString() ).replace("n1_200", n1_200.toString() ).replace("n1_300", n1_300.toString() ).replace("n1_400", n1_400.toString() ).replace("n1_500", n1_500.toString() ).replace("n1_600", n1_600.toString() ).replace("n1_700", n1_700.toString() ).replace("n1_800", n1_800.toString() ).replace("n1_900", n1_900.toString() ).replace("n1_1000", n1_1000.toString() ).replace("n1_50", n1_50.toString() ).replace("n1_100", n1_100.toString() ).replace("n1_10", n1_10.toString() ).replace("n2_0", n2_0.toString() ).replace("n2_200", n2_200.toString() ).replace("n2_300", n2_300.toString() ).replace("n2_400", n2_400.toString() ).replace("n2_500", n2_500.toString() ).replace("n2_600", n2_600.toString() ).replace("n2_700", n2_700.toString() ).replace("n2_800", n2_800.toString() ).replace("n2_900", n2_900.toString() ).replace("n2_1000", n2_1000.toString() ).replace("n2_50", n2_50.toString() ).replace("n2_100", n2_100.toString() ).replace("n2_10", n2_10.toString() )
        File(applicationContext.cacheDir, outputFile).writeText(text = darkThemeImport)
        val file = File(applicationContext.cacheDir, outputFile)
        val uri = FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "document/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(intent, theme))
    }
}
