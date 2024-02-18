package com.example.a_write.api

import android.content.Context
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

interface HomeDataListener {
    fun onDataLoaded(diaries: List<DiaryResult>)
}

interface HeartDataListener {
    fun onDataLoaded(diaries: List<DiaryResult>)
}

class DiaryService(private val context: Context) {
    private val diaryService = getUserRetrofit(context).create(DiaryRetrofitInterface::class.java)
    fun getHomeList(listener: HomeDataListener) {
        diaryService.getHomeDiaries().enqueue(object : Callback<List<DiaryResult>> {
            override fun onResponse(
                call: Call<List<DiaryResult>>,
                response: Response<List<DiaryResult>>
            ) {
                if (response.isSuccessful) {
                    val diaries: List<DiaryResult>? = response.body()
                    if (diaries != null) {
                        listener.onDataLoaded(diaries)
                    }
                    Log.d("API getHomeList", diaries.toString())
                }
            }

            override fun onFailure(call: Call<List<DiaryResult>>, t: Throwable) {
                Log.d("API getHomeList ERROR", "$t")
            }
        })
    }

    fun getHeartList(listener: HeartDataListener) {
        diaryService.getHeartDiaries().enqueue(object : Callback<List<DiaryResult>> {
            override fun onResponse(
                call: Call<List<DiaryResult>>,
                response: Response<List<DiaryResult>>
            ) {
                if (response.isSuccessful) {
                    val diaries: List<DiaryResult>? = response.body()
                    if (diaries != null) {
                        listener.onDataLoaded(diaries)
                    }
                    Log.d("API getHeartList", diaries.toString())
                }
            }

            override fun onFailure(call: Call<List<DiaryResult>>, t: Throwable) {
                Log.d("API getHeartList ERROR", "$t")
            }
        })
    }

    fun postDiaryHeart(diaryId: Int) {
        diaryService.postHeart(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API postDiaryHeart", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API postDiaryHeart ERROR", "$t")
            }
        })
    }

    fun deleteDiaryHeart(diaryId: Int) {
        diaryService.deleteHeart(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API deleteDiaryHeart", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API deleteDiaryHeart ERROR", "$t")
            }
        })
    }

    fun postDiary(diaryData: Map<String, Any>, imageFile: File) {
        val title = diaryData["title"].toString()
        val content = diaryData["content"].toString()
        val secret = diaryData["secret"].toString()
        val theme = diaryData["theme"].toString()
        val date = diaryData["date"].toString()

        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentRequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val secretRequestBody = secret.toRequestBody("text/plain".toMediaTypeOrNull())
        val themeRequestBody = theme.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateRequestBody = date.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("imgUrl", imageFile.name, imageRequestBody)

        diaryService.postDiary(titleRequestBody, contentRequestBody, imagePart, secretRequestBody, themeRequestBody, dateRequestBody).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API postDiary", "Code: ${response.code()}")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API postDiary ERROR", "$t")
            }
        })
    }

}