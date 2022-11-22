package com.example.sendsms.api.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "sms_history") //room ; to create sqlite objects //one table
data class SmsSent  // You may add getters and setters according to your requirement
    (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("time")
    val time: String? = null,
):Parcelable
