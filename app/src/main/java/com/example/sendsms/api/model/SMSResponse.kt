package com.example.sendsms.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Keep
@Parcelize
data class SMSResponse(
    @SerializedName("account_sid")
    val accountSid: String?,
    @SerializedName("api_version")
    val apiVersion: String?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("date_created")
    val dateCreated: String?,
    @SerializedName("date_sent")
    val dateSent: String?,
    @SerializedName("date_updated")
    val dateUpdated: String?,
    @SerializedName("direction")
    val direction: String?,
    @SerializedName("error_code")
    val errorCode: String?,
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("messaging_service_sid")
    val messagingServiceSid: String?,
    @SerializedName("num_media")
    val numMedia: String?,
    @SerializedName("num_segments")
    val numSegments: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_unit")
    val priceUnit: String?,
    @SerializedName("sid")
    val sid: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("subresource_uris")
    val subresourceUris: SubresourceUris?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("uri")
    val uri: String?
) : Parcelable {
    @Keep
    @Parcelize
    data class SubresourceUris(
        @SerializedName("media")
        val media: String?
    ) : Parcelable
}