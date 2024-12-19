package com.example.diyetkitabi.util

import android.content.Context
import android.content.SharedPreferences


class PrivateSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: PrivateSharedPreferences? =
            null //singleton tek bir instanmce (nesne) oluşturuyor yoksa yeni oluşturuyor

        private val lock = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(lock) {  //invoke nesne oluşturulurkenb çalışacak bir fonksiyon sharedPreferences nesnesi oluşturulurken istiyor context
                instance ?: PrivateSharedPreferencesCreate(context).also {
                    instance = it //boş olanı instanceyi databaseye eşitle
                }
            }

        private fun PrivateSharedPreferencesCreate(context: Context): PrivateSharedPreferences {
            sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }

    }

    fun saveTime(time: Long) {
        sharedPreferences?.edit()?.putLong(TIME, time)?.apply()
    }

    fun getTime() = sharedPreferences?.getLong(TIME, 0)

}