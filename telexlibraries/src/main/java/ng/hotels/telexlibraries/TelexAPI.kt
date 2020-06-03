package ng.hotels.telexlibraries

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface TelexAPI {


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/{organization}/{team}")
    fun postTickets(@Path("organization") organization: String,
                         @Path("team") team: String,
                         @Field("email") email: String,
                         @Field("name") name: String,
                         @Field("title") title: String,
                         @Field("description") description: String,
                         @Field("priority") priority: String): Call<JsonObject>


}