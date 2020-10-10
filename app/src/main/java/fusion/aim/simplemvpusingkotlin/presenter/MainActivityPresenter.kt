package fusion.aim.simplemvpusingkotlin.presenter

import android.content.Context

import fusion.aim.simplemvpusingkotlin.models.User
import fusion.aim.simplemvpusingkotlin.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(val context: Context,val mainActivityResponse: MainActivityResponse) {
    interface MainActivityResponse{
        fun success(users:List<User>)
        fun fail(fail:String)
        fun error(error:String)
    }

    fun getResponse(){
       val servicesApiInterface=ApiClient.build()
        val userList=servicesApiInterface?.getUsers(1)
        userList?.enqueue( object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                if(response?.body() != null){
                    mainActivityResponse.success(response.body()!!)
                }

            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    mainActivityResponse.fail(t?.message!!)
            }
        })
    }
}