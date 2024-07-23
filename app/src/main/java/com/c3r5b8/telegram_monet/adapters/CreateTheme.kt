package com.c3r5b8.telegram_monet.adapters

import android.content.Context
import android.util.Log
import com.c3r5b8.telegram_monet.common.Constants
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
    isAlterOutColor: Boolean,
    inputFileName: String,
    outputFileName: String,
) {
    val reader = BufferedReader(InputStreamReader(context.assets.open(inputFileName)))
    var themeImport = ""
    val listMain = reader.readLines().toMutableList()
    reader.close()

    if (isGradient)
        listMain.forEachIndexed { index, value ->
            listMain[index] = value.replace("noGradient", "chat_outBubbleGradient")
        }

    if (isAlterOutColor && isTelegram) {
        val inputSecondFileName =
            if (inputFileName == Constants.INPUT_FILE_TELEGRAM_LIGHT) Constants.INPUT_FILE_TELEGRAM_DARK else Constants.INPUT_FILE_TELEGRAM_LIGHT
        val reader2 = BufferedReader(InputStreamReader(context.assets.open(inputSecondFileName)))
        val listSecond = reader2.readLines()
        reader.close()

        ListToReplaceNewThemeTelegram.forEach { value ->
            val index1 = listMain.indexOfFirst { it.startsWith("$value=") }
            val index2 = listMain.indexOfFirst { it.startsWith("$value=") }
            if (index1 < 0 || index2 < 0) {
                Log.d("New Theme", "$value: $index1 $index2")
            } else {
                listMain[index1] = listSecond[index2]
            }
        }
    }

    if (isAlterOutColor && !isTelegram) {
        val inputSecondFileName =
            if (inputFileName == Constants.INPUT_FILE_TELEGRAM_X_LIGHT) Constants.INPUT_FILE_TELEGRAM_X_DARK else Constants.INPUT_FILE_TELEGRAM_X_LIGHT
        val reader2 = BufferedReader(InputStreamReader(context.assets.open(inputSecondFileName)))
        val listSecond = reader2.readLines()
        reader.close()

        ListToReplaceNewThemeTelegramX.forEach { value ->
            val index1 = listMain.indexOfFirst { it.startsWith("$value:") }
            val index2 = listMain.indexOfFirst { it.startsWith("$value:") }
            if (index1 < 0 || index2 < 0) {
                Log.d("New Theme", "$value: $index1 $index2")
            } else {
                listMain[index1] = listSecond[index2]
            }
        }
    }

    listMain.forEach { themeImport += it + "\n" }

    if (isAmoled)
        themeImport = themeImport.replace("n1_900", "n1_1000")

    if (isTelegram && isNicknameColorful)
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
    if (isTelegram && isAvatarGradient) {
        themeImport = themeImport
            .replace("avatar_backgroundBlue=n2_800", "avatar_backgroundBlue=n2_700")
            .replace("avatar_backgroundCyan=n2_800", "avatar_backgroundCyan=n2_700")
            .replace("avatar_backgroundGreen=n2_800", "avatar_backgroundGreen=n2_700")
            .replace("avatar_backgroundOrange=n2_800", "avatar_backgroundOrange=n2_700")
            .replace("avatar_backgroundPink=n2_800", "avatar_backgroundPink=n2_700")
            .replace("avatar_backgroundRed=n2_800", "avatar_backgroundRed=n2_700")
            .replace("avatar_backgroundSaved=n2_800", "avatar_backgroundSaved=n2_700")
            .replace("avatar_backgroundViolet=n2_800", "avatar_backgroundViolet=n2_700")
    }

    val generatedTheme =
        if (isTelegram)
            changeTextTelegram(themeImport, context)
        else
            changeTextTelegramX(themeImport, context)

    File(context.filesDir, outputFileName).writeText(text = generatedTheme)

    shareTheme(context, outputFileName)

}