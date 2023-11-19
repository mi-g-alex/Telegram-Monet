package com.c3r5b8.telegram_monet

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ResolveInfoFlags
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.FileProvider
import com.c3r5b8.telegram_monet.BuildConfig.APPLICATION_ID
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set switches
        getData()

        //Buttons Telegram
        val buttonTelegramDark: Button = findViewById(R.id.setup_dark_button)
        val buttonTelegramLight: Button = findViewById(R.id.setup_light_button)
        val useGradient: SwitchCompat = findViewById(R.id.switchGradient)
        val useGradientAvatars: SwitchCompat = findViewById(R.id.switchGradientAvatars)
        val useColorNick: SwitchCompat = findViewById(R.id.switchColorsOfNickNames)

        //Buttons TelegramX
        val buttonTelegramXDark: Button = findViewById(R.id.setup_x_dark_button)
        val buttonTelegramXLight: Button = findViewById(R.id.setup_x_light_button)

        //Buttons About Card
        val buttonAboutGithub: Button = findViewById(R.id.button_github)
        val buttonAboutTg: Button = findViewById(R.id.button_tg)
        val descriptionTitle: TextView = findViewById(R.id.description_title)

        descriptionTitle.text = getString(R.string.about_card_title, BuildConfig.VERSION_NAME)

        //Create telegram Dark theme
        buttonTelegramDark.setOnClickListener {
            val darkMonetFile = "monet_dark.attheme"
            var darkThemeImport = application.assets.open(darkMonetFile).bufferedReader().readText()
            val isAmoledMode: SwitchCompat = findViewById(R.id.switchAmoledPhone)
            var fileName = "Dark Theme.attheme"

            if (isAmoledMode.isChecked) {
                darkThemeImport = darkThemeImport.replace("n1_900", "n1_1000")
                fileName = "Amoled Theme.attheme"
            }

            if (useGradient.isChecked) {
                darkThemeImport = darkThemeImport.replace("noGradient", "chat_outBubbleGradient")
            }

            if (useColorNick.isChecked) {
                darkThemeImport = darkThemeImport.replace(
                    "\nend",
                    "\navatar_nameInMessageBlue=a1_400\n" +
                            "avatar_nameInMessageCyan=a1_400\n" +
                            "avatar_nameInMessageGreen=a1_400\n" +
                            "avatar_nameInMessageOrange=a1_400\n" +
                            "avatar_nameInMessagePink=a1_400\n" +
                            "avatar_nameInMessageRed=a1_400\n" +
                            "avatar_nameInMessageViolet=a1_400\nend"
                )
            }

            if (useGradientAvatars.isChecked) {
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundBlue=n2_800",
                    "avatar_backgroundBlue=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundCyan=n2_800",
                    "avatar_backgroundCyan=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundGreen=n2_800",
                    "avatar_backgroundGreen=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundOrange=n2_800",
                    "avatar_backgroundOrange=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundPink=n2_800",
                    "avatar_backgroundPink=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundRed=n2_800",
                    "avatar_backgroundRed=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundSaved=n2_800",
                    "avatar_backgroundSaved=n2_700"
                )
                darkThemeImport = darkThemeImport.replace(
                    "avatar_backgroundViolet=n2_800",
                    "avatar_backgroundViolet=n2_700"
                )
            }

            val themeString = changeTextTelegram(darkThemeImport, applicationContext)

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.dark_theme)

            shareTheme(themeName, fileName)
        }

        //Create telegram Light theme
        buttonTelegramLight.setOnClickListener {
            val lightMonetFile = "monet_light.attheme"
            var lightThemeImport =
                application.assets.open(lightMonetFile).bufferedReader().readText()

            val fileName = "Light Theme.attheme"

            if (useGradient.isChecked) {
                lightThemeImport = lightThemeImport.replace("noGradient", "chat_outBubbleGradient")
            }

            if (useColorNick.isChecked) {
                lightThemeImport = lightThemeImport.replace(
                    "\nend",
                    "\navatar_nameInMessageBlue=a1_400\n" +
                            "avatar_nameInMessageCyan=a1_400\n" +
                            "avatar_nameInMessageGreen=a1_400\n" +
                            "avatar_nameInMessageOrange=a1_400\n" +
                            "avatar_nameInMessagePink=a1_400\n" +
                            "avatar_nameInMessageRed=a1_400\n" +
                            "avatar_nameInMessageViolet=a1_400\nend"
                )
            }

            if (useGradientAvatars.isChecked) {
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundBlue=a1_600",
                    "avatar_backgroundBlue=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundCyan=a1_600",
                    "avatar_backgroundCyan=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundGreen=a1_600",
                    "avatar_backgroundGreen=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundOrange=a1_600",
                    "avatar_backgroundOrange=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundPink=a1_600",
                    "avatar_backgroundPink=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundRed=a1_600",
                    "avatar_backgroundRed=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundSaved=a1_600",
                    "avatar_backgroundSaved=a1_500"
                )
                lightThemeImport = lightThemeImport.replace(
                    "avatar_backgroundViolet=a1_600",
                    "avatar_backgroundViolet=a1_500"
                )
            }

            val themeString = changeTextTelegram(lightThemeImport, applicationContext)

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.light_theme)

            shareTheme(themeName, fileName)
        }

        //Create telegramX Dark theme
        buttonTelegramXDark.setOnClickListener {
            val darkMonetFile = "monet_x_dark.tgx-theme"
            var darkThemeImport = application.assets.open(darkMonetFile).bufferedReader().readText()
            val isAmoledMode: SwitchCompat = findViewById(R.id.switchAmoledPhone)
            var fileName = "Dark Theme.tgx-theme"

            if (isAmoledMode.isChecked) {
                darkThemeImport = darkThemeImport.replace("n1_900", "n1_1000")
                fileName = "Amoled Theme.tgx-theme"
            }

            val themeString = changeTextTelegramX(darkThemeImport, applicationContext)

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.dark_theme)

            shareTheme(themeName, fileName)
        }

        //Create telegramX Light theme
        buttonTelegramXLight.setOnClickListener {
            val lightMonetFile = "monet_x_light.tgx-theme"
            val lightThemeImport =
                application.assets.open(lightMonetFile).bufferedReader().readText()
            val themeString = changeTextTelegramX(lightThemeImport, applicationContext)
            val fileName = "Light Theme.tgx-theme"

            File(applicationContext.cacheDir, fileName).writeText(text = themeString)

            val themeName: String = resources.getString(R.string.light_theme)

            shareTheme(themeName, fileName)
        }

        //Button - go to GitHub
        buttonAboutGithub.setOnClickListener {
            openLink("https://github.com/mi-g-alex/Telegram-Monet")
        }

        //Button - go to Telegram
        buttonAboutTg.setOnClickListener {
            openLink("https://t.me/tgmonet")
        }
    }

    //Open links fun
    private fun openLink(link: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        startActivity(i)
    }

    //Share theme fun
    private fun shareTheme(theme: String, file_name: String) {
        val file = File(applicationContext.cacheDir, file_name)
        val uri = FileProvider.getUriForFile(
            applicationContext,
            "$APPLICATION_ID.provider",
            file
        )
        val intent = Intent("org.telegram.messenger.IMPORT_THEME")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.type = "*/*"

        val flags = PackageManager.MATCH_DEFAULT_ONLY
        val resolveInfos: List<ResolveInfo>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            resolveInfos =
                packageManager.queryIntentActivities(intent, ResolveInfoFlags.of(flags.toLong()))
        } else {
            resolveInfos = packageManager.queryIntentActivities(intent, flags)
        }
        if (!resolveInfos.isEmpty()) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.fast_import_choose_method)
                .setMessage(R.string.fast_import_description)
                .setPositiveButton(R.string.fast_import_method_fast_import) { dialog, which ->
                    resolveInfos.forEach {
                        grantUriPermission(
                            it.activityInfo.packageName,
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }

                    val runIntent = Intent("org.telegram.messenger.IMPORT_THEME")
                    runIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    runIntent.type = "*/*"
                    runIntent.putExtra(Intent.EXTRA_STREAM, uri)
                    runIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(Intent.createChooser(runIntent, theme))
                }
                .setNegativeButton(R.string.fast_import_method_regular) { dialog, which ->
                    shareThemeRegular(
                        theme,
                        uri
                    )
                }
                .show()

            return
        }

        shareThemeRegular(theme, uri)
    }

    private fun shareThemeRegular(theme: String, uri: Uri) {
        intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(intent, theme))
    }

    private fun putData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("switchSettings", MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

        val useGradient: SwitchCompat = findViewById(R.id.switchGradient)
        val useGradientAvatars: SwitchCompat = findViewById(R.id.switchGradientAvatars)
        val isAmoledMode: SwitchCompat = findViewById(R.id.switchAmoledPhone)
        val useColorNick: SwitchCompat = findViewById(R.id.switchColorsOfNickNames)


        sharedPreferencesEditor.putBoolean("useGradient", useGradient.isChecked)
        sharedPreferencesEditor.putBoolean("useGradientAvatars", useGradientAvatars.isChecked)
        sharedPreferencesEditor.putBoolean("isAmoledMode", isAmoledMode.isChecked)
        sharedPreferencesEditor.putBoolean("useColorNick", useColorNick.isChecked)
        sharedPreferencesEditor.apply()
    }

    private fun getData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("switchSettings", MODE_PRIVATE)

        val useGradient: SwitchCompat = findViewById(R.id.switchGradient)
        val useGradientAvatars: SwitchCompat = findViewById(R.id.switchGradientAvatars)
        val isAmoledMode: SwitchCompat = findViewById(R.id.switchAmoledPhone)
        val useColorNick: SwitchCompat = findViewById(R.id.switchColorsOfNickNames)

        useGradient.isChecked = sharedPreferences.getBoolean("useGradient", false)
        useGradientAvatars.isChecked = sharedPreferences.getBoolean("useGradientAvatars", false)
        isAmoledMode.isChecked = sharedPreferences.getBoolean("isAmoledMode", false)
        useColorNick.isChecked = sharedPreferences.getBoolean("useColorNick", true)
    }

    override fun onPause() {
        super.onPause()
        putData()
    }

}