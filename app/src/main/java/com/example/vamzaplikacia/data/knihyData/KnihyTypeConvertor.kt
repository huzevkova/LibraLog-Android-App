package com.example.vamzaplikacia.data.knihyData

import android.net.Uri
import androidx.room.TypeConverter
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class Converters {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(millis: Long?): Calendar? {
        return millis?.let {
            Calendar.getInstance().apply { timeInMillis = it }
        }
    }

    @TypeConverter
    fun fromVlastnostiList(vlastnosti: MutableList<Vlastnosti>?): String? {
        val gson = Gson()
        return gson.toJson(vlastnosti)
    }

    @TypeConverter
    fun toVlastnostiList(data: String?): MutableList<Vlastnosti>? {
        if (data == null) {
            return mutableListOf()
        }
        val listType = object : TypeToken<MutableList<Vlastnosti>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun fromZanreList(zanre: MutableList<Zanre>?): String? {
        val gson = Gson()
        return gson.toJson(zanre)
    }

    @TypeConverter
    fun toZanreList(data: String?): MutableList<Zanre>? {
        if (data == null) {
            return mutableListOf()
        }
        val listType = object : TypeToken<MutableList<Zanre>>() {}.type
        return Gson().fromJson(data, listType)
    }
}