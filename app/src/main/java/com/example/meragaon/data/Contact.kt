package com.example.meragaon.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.annotation.RawRes
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
@Keep
data class Contact(
    @SerializedName("contacts")
    val contact: MutableList<Contacts>?
): Parcelable {
    @Parcelize
    @Keep
    data class Contacts(
        @SerializedName("fname")
        val fname: String?,
        @SerializedName("lname")
        val lname: String?,
        @SerializedName("number")
        val number: String?
    ) : Parcelable
}