package longbowou.getnovissi

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import longbowou.getnovissi.fragments.ProcessingFragment

fun Context.getNofissis(): MutableList<MutableMap<String, String>> {
    val defaultNovissis = assets?.open("novissis.json")?.bufferedReader().use { it?.readText() }
    val savedNovissis = getSharedPreferences(
        ProcessingFragment.NOVISSIS,
        Context.MODE_PRIVATE
    )?.getString(ProcessingFragment.NOVISSIS, defaultNovissis)
    val typeConverter = object : TypeToken<MutableList<MutableMap<String, String>>>() {}.type

    return Gson().fromJson(savedNovissis, typeConverter)
}