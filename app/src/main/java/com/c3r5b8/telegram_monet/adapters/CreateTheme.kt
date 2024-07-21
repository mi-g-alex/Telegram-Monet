package com.c3r5b8.telegram_monet.adapters

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun createTheme(
    context: Context,
    isTelegram: Boolean,
    isAmoled: Boolean,
    isGradient: Boolean,
    isAvatarGradient: Boolean,
    isNicknameColorful: Boolean,
    inputFileName: String,
    outputFileName: String,
) {
    val reader = BufferedReader(InputStreamReader(context.assets.open(inputFileName)))
    var themeImport = ""
    reader.readLines().forEach { themeImport += it + "\n" }
    reader.close()


    if (isAmoled)
        themeImport = themeImport.replace("n1_900", "n1_1000")
    if (isGradient)
        themeImport = themeImport.replace("noGradient", "chat_outBubbleGradient")
    if (isNicknameColorful)
        themeImport = themeImport.replace(
            "\nend",
            "\navatar_nameInMessageBlue=a1_400\n" +
                    "avatar_nameInMessageCyan=a1_400\n" +
                    "avatar_nameInMessageGreen=a1_400\n" +
                    "avatar_nameInMessageOrange=a1_400\n" +
                    "avatar_nameInMessagePink=a1_400\n" +
                    "avatar_nameInMessageRed=a1_400\n" +
                    "avatar_nameInMessageViolet=a1_400\nend"
        )
    if (isAvatarGradient) {
        themeImport = themeImport.replace("avatar_backgroundBlue=n2_800", "avatar_backgroundBlue=n2_700")
        themeImport = themeImport.replace("avatar_backgroundCyan=n2_800", "avatar_backgroundCyan=n2_700")
        themeImport = themeImport.replace("avatar_backgroundGreen=n2_800", "avatar_backgroundGreen=n2_700")
        themeImport = themeImport.replace("avatar_backgroundOrange=n2_800", "avatar_backgroundOrange=n2_700")
        themeImport = themeImport.replace("avatar_backgroundPink=n2_800", "avatar_backgroundPink=n2_700")
        themeImport = themeImport.replace("avatar_backgroundRed=n2_800", "avatar_backgroundRed=n2_700")
        themeImport = themeImport.replace("avatar_backgroundSaved=n2_800", "avatar_backgroundSaved=n2_700")
        themeImport = themeImport.replace("avatar_backgroundViolet=n2_800", "avatar_backgroundViolet=n2_700")
    }

    val generatedTheme =
        if (isTelegram)
            changeTextTelegram(themeImport, context)
        else
            changeTextTelegramX(themeImport, context)

    File(context.filesDir, outputFileName).writeText(text = generatedTheme)

    shareTheme(context, outputFileName)

}