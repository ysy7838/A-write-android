package com.example.a_write.api

import android.os.Parcel
import android.os.Parcelable

data class DiaryResult(
    val diaryId: Int = 0,
    val title: String = "",
    val content: String = "",
    val imgUrl: String? = null,
    val secret: Boolean = false,
    val authorName: String = "",
    val authorProfile: Int = 0,
    var heartby: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(diaryId)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(imgUrl)
        parcel.writeByte(if (secret) 1 else 0)
        parcel.writeString(authorName)
        parcel.writeInt(authorProfile)
        parcel.writeByte(if (heartby) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiaryResult> {
        override fun createFromParcel(parcel: Parcel): DiaryResult {
            return DiaryResult(parcel)
        }

        override fun newArray(size: Int): Array<DiaryResult?> {
            return arrayOfNulls(size)
        }
    }
}