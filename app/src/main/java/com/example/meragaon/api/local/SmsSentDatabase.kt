package com.example.meragaon.api.local

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import retrofit2.Converter


@Database(entities = [SmsSent::class], version = 1, exportSchema = false) //add as array if multiple
abstract class SmsSentDatabase : RoomDatabase() {
    abstract fun smsSentDAO(): SmsSentDAO
}