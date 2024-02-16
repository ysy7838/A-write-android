package com.example.a_write.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface HomeDataListener {
    fun onDataLoaded(diaries: List<DiaryResult>)
}

interface HeartDataListener {
    fun onDataLoaded(diaries: List<DiaryResult>)
}

class DiaryService {
    private val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
    fun getHomeList(listener: HomeDataListener) {
        diaryService.getHomeDiaries().enqueue(object : Callback<List<DiaryResult>> {
            override fun onResponse(call: Call<List<DiaryResult>>, response: Response<List<DiaryResult>>) {
                if (response.isSuccessful) {
                    val diaries: List<DiaryResult>? = response.body()
                    Log.d("getDiary", diaries.toString())

                    if (diaries != null) {
                        listener.onDataLoaded(diaries)
                    }
                }
            }

            override fun onFailure(call: Call<List<DiaryResult>>, t: Throwable) {
                Log.d("getDiary ERROR", "$t")
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
                }
            }

            override fun onFailure(call: Call<List<DiaryResult>>, t: Throwable) {
                Log.d("getDiary ERROR", "$t")
            }
        })
    }

    fun postDiaryHeart(diaryId: Int) {
        diaryService.postHeart(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("postDiaryHeart 성공")
                } else {
                    println("postDiaryHeart 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("postDiaryHeart ERROR", "$t")
            }
        })
    }

    fun deleteDiaryHeart(diaryId: Int) {
        diaryService.deleteHeart(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("deleteDiaryHeart 성공")
                } else {
                    println("deleteDiaryHeart 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteDiaryHeart ERROR", "$t")
            }
        })
    }
}