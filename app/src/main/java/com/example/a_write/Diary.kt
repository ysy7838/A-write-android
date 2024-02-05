package com.example.a_write
import android.os.Parcel
import android.os.Parcelable

data class Diary(
    val title: String,
    val content: String,
    val img: String,
    val theme: Int,
    val user: String,
    var profile: Int,
    val date: String,
    var isSaved: Boolean,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ?: 0,
        parcel.readString() ?: "",
        parcel.readInt() ?: 1,
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Diary> {
        override fun createFromParcel(parcel: Parcel): Diary {
            return Diary(parcel)
        }

        override fun newArray(size: Int): Array<Diary?> {
            return arrayOfNulls(size)
        }
    }
}
