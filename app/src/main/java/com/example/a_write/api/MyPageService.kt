package com.example.a_write.api

import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface MyPageDiaryListener {
    fun onDataLoaded(diaries: List<MyPageDiary>)
}

interface MyPageCalenderDiaryListener {
    fun onDataLoaded(diaries: MyPageDiary)
}

interface MyPageUserInfoListener {
    fun onDataLoaded(data: UserInfo)
}

interface PasswordMatchListener {
    fun onDataLoaded(data: Boolean)
}

class MyPageService {
    private val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
    fun getMyPageDiaryList(listener: MyPageDiaryListener) {
        myPageService.getMyPageDiaries().enqueue(object : Callback<List<MyPageDiary>> {
            override fun onResponse(
                call: Call<List<MyPageDiary>>,
                response: Response<List<MyPageDiary>>
            ) {
                if (response.isSuccessful) {
                    val diaries = response.body()
                    if (diaries != null) {
                        listener.onDataLoaded(diaries)
                    } else {
                        Log.d("getDiary ERROR", "일기 데이터가 없습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<List<MyPageDiary>>, t: Throwable) {
                Log.d("getDiary ERROR", "$t")
            }
        })
    }

    fun getCalenderDiaryDetail(listener: MyPageCalenderDiaryListener, date: String) {
        myPageService.getCalenderDiary(date).enqueue(object : Callback<MyPageDiary> {
            override fun onResponse(call: Call<MyPageDiary>, response: Response<MyPageDiary>) {
                if (response.isSuccessful) {
                    val diary = response.body()
                    if (diary != null) {
                        listener.onDataLoaded(diary)
                    } else {
                        Log.d("getDiary ERROR", "일기 데이터가 없습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<MyPageDiary>, t: Throwable) {
                Log.d("getDiary ERROR", "$t")
            }
        })
    }

    fun getUserInfo(listener: MyPageUserInfoListener) {
         myPageService.getUserInformation().enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        listener.onDataLoaded(data)
                    } else {
                        Log.d("getDiary ERROR", "일기 데이터가 없습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("getDiary ERROR", "$t")
            }
        })
    }

    fun patchProfile(profileData: JsonObject) {
        myPageService.updateProfile(profileData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("patchProfile 성공")
                } else {
                    println("patchProfile 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("patchProfile ERROR", "$t")
            }
        })
    }

    fun patchNickname(profileData: JsonObject) {
        myPageService.updateNickname(profileData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("patchNickname 성공")
                } else {
                    println("patchNickname 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("patchNickname ERROR", "$t")
            }
        })
    }

    fun passwordsMatch(listener : PasswordMatchListener, profileData: JsonObject) {
        myPageService.postWithdrawal(profileData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("passwordsMatch 성공")
                    listener.onDataLoaded(true)
                } else {
                    println("passwordsMatch 실패")
                    listener.onDataLoaded(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("passwordsMatch ERROR", "$t")
            }
        })
    }

    fun deleteUser() {
        myPageService.deleteWithdrawal().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("deleteUser 성공")
                } else {
                    println("deleteUser 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteUser ERROR", "$t")
            }
        })
    }

    fun deleteDiary(diaryId: Int) {
        myPageService.deleteDiary(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("deleteDiary 성공")
                } else {
                    println("deleteDiary 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteDiary ERROR", "$t")
            }
        })
    }

}