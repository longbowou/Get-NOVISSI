package longbowou.getnovissi

import android.content.Context
import android.view.MotionEvent
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun Context.getNovissis(): MutableList<MutableMap<String, String>> {
    val novissisJsonFile = File(filesDir, "novissis.json")
    val savedNovissis = if (novissisJsonFile.exists()) {
        novissisJsonFile.readText()
    } else {
        assets?.open("novissis.json")?.bufferedReader().use { it?.readText() }
    }

    val typeConverter = object : TypeToken<MutableList<MutableMap<String, String>>>() {}.type
    return Gson().fromJson(savedNovissis, typeConverter)
}

fun Context.saveNovissis(novissis: MutableList<MutableMap<String, String>>) {
    val novissisJson = Gson().toJson(novissis)
    val novissisJsonFile = File(filesDir, "novissis.json")
    novissisJsonFile.writeText(novissisJson)
}

fun TextInputEditText.onRightDrawableClicked(onClicked: (view: TextInputEditText) -> Unit) {
    setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is TextInputEditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}