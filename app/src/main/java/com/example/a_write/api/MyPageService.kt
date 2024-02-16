package com.example.a_write.api

import android.content.Context
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
    fun onUserDataLoaded(data: UserInfo)
}

interface PasswordMatchListener {
    fun onDataLoaded(data: Boolean)
}

class MyPageService(private val context: Context) {
    private val myPageService = getUserRetrofit(context).create(MyPageRetrofitInterface::class.java)

    fun getMyPageDiaryList(listener: MyPageDiaryListener) {
        myPageService.getMyPageDiaries().enqueue(object : Callback<List<MyPageDiary>> {
            override fun onResponse(
                call: Call<List<MyPageDiary>>,
                response: Response<List<MyPageDiary>>
            ) {
                if (response.isSuccessful) {
                    val diaries = response.body()
                    Log.d("API getMyPageDiaryList", diaries.toString())
                    if (diaries != null) {
                        listener.onDataLoaded(diaries)
                    }
                }
                Log.d("API getMyPageDiaryList", response.toString())
            }

            override fun onFailure(call: Call<List<MyPageDiary>>, t: Throwable) {
                Log.d("API getMyPageDiaryList ERROR", "$t")
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
                    }
                }
                Log.d("API getCalenderDiaryDetail", response.toString())
            }

            override fun onFailure(call: Call<MyPageDiary>, t: Throwable) {
                Log.d("API getCalenderDiaryDetail ERROR", "$t")
            }
        })
    }

    fun getCalenderBool(date: String): Boolean {
        var isSuccess = false
        myPageService.getCalenderDiary(date).enqueue(object : Callback<MyPageDiary> {
            override fun onResponse(call: Call<MyPageDiary>, response: Response<MyPageDiary>) {
                if (response.isSuccessful) {
                    isSuccess = true
                }
                Log.d("API getCalenderDiaryDetail", response.toString())
            }

            override fun onFailure(call: Call<MyPageDiary>, t: Throwable) {
                isSuccess = false
                Log.d("API getCalenderDiaryDetail ERROR", "$t")
            }
        })
        return isSuccess
    }

    fun getUserInfo(listener: MyPageUserInfoListener) {
        myPageService.getUserInformation().enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        listener.onUserDataLoaded(data)
                        Log.d("API getUserInfo", data.toString())
                    } else {
                        Log.d("API getUserInfo null", "사용자 정보가 없습니다.")
                    }
                }
                Log.d("API getUserInfo", response.toString())
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("API getUserInfo ERROR", "$t")
            }
        })
    }

    fun patchProfile(profileData: JsonObject) {
        myPageService.updateProfile(profileData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API patchProfile", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API patchProfile ERROR", "$t")
            }
        })
    }

    fun patchNickname(nicknameData: JsonObject) {
        myPageService.updateNickname(nicknameData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API patchNickname", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API patchNickname ERROR", "$t")
            }
        })
    }

    fun passwordsMatch(listener: PasswordMatchListener, profileData: JsonObject) {
        myPageService.postWithdrawal(profileData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    listener.onDataLoaded(true)
                } else {
                    listener.onDataLoaded(false)
                }
                Log.d("API passwordsMatch", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API passwordsMatch ERROR", "$t")
            }
        })
    }

    fun deleteUser() {
        myPageService.deleteWithdrawal().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API deleteUser", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API deleteUser ERROR", "$t")
            }
        })
    }

    fun deleteDiary(diaryId: Int) {
        myPageService.deleteDiary(diaryId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API deleteDiary", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("API deleteDiary ERROR", "$t")
            }
        })
    }

}