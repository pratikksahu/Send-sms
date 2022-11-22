package com.example.sendsms.api.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SmsSent::class], version = 1, exportSchema = false) //add as array if multiple
abstract class SmsSentDatabase : RoomDatabase() {
    abstract fun smsSentDAO(): SmsSentDAO
}