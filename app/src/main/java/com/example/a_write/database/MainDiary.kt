package com.example.a_write.database

import android.util.Log
import com.example.a_write.HomePreviewDiaryRVAdapter
import com.google.firebase.database.*
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

private lateinit var databaseReference: DatabaseReference
private lateinit var valueEventListener: ValueEventListener
private lateinit var homeRVA: HomePreviewDiaryRVAdapter

fun getHomeDiary(adapter: HomePreviewDiaryRVAdapter) {
    homeRVA = adapter
    databaseReference = FirebaseDatabase.getInstance().getReference("home")

    valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val diaryResultsList = mutableListOf<DiaryResult>()
            for (dataSnapshot in snapshot.children) {
                val diaryResult = dataSnapshot.getValue(DiaryResult::class.java)
                diaryResult?.let { diaryResultsList.add(it) }
            }
            homeRVA.submitList(diaryResultsList)
            Log.d("Data", diaryResultsList.toString())
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Error", error.message)
        }
    }

    databaseReference.addValueEventListener(valueEventListener)
}