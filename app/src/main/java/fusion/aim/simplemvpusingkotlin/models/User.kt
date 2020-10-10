package fusion.aim.simplemvpusingkotlin.models

import retrofit2.http.Body

data class User(val userId: Int, val id: Int,val title:String,val body: String)