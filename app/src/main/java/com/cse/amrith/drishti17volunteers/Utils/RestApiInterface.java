package com.cse.amrith.drishti17volunteers.Utils;

import com.cse.amrith.drishti17volunteers.Models.Admin;
import com.cse.amrith.drishti17volunteers.Models.EventAdmin;
import com.cse.amrith.drishti17volunteers.Models.EventModel;
import com.cse.amrith.drishti17volunteers.Models.PaymentModel;
import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by amrith on 3/11/17.
 */

public interface RestApiInterface {
    @FormUrlEncoded
    @POST("student/login")
    Call<Student> login(@Field("idToken") String idToken);

    @FormUrlEncoded
    @POST("/dcms-admin/auth/login")
    Call<Admin> adminLogin(@Field("idToken") String idToken);

    @GET("/dcms-admin/volunteer/registeredEvents/{id}")
    Call<List<RegisteredEvents>> eventStatus(@Path("id") String id);

    @GET("/dcms-admin/student/{id}")
    Call<Student> studentDetails(@Header("x-auth-token") String token, @Path("id") String id);

    @GET("/public/student/{id}")
    Call<Student> studentDetailsPublic(@Path("id") String id);

    @FormUrlEncoded
    @POST("/dcms-admin/volunteer/addScore/{id}")
    Call<String> addScore(@Header("x-auth-token") String token, @Path("id") String id, @Field("addScore") int score, @Field("reason") String reason);

//    Call<String> confirmPayment(@Header("x-auth-token")String token,@Path("id")String id,@Field("events")PaymentModel [] obj);

    @GET("/dcms-admin/event/registeredCount/{id}")
    Call<EventAdmin> getStudentList(@Header("x-auth-token") String token, @Path("id") int id);

    @GET("/public/event")
    Call<List<EventModel>> getEvents();

    @FormUrlEncoded
    @POST("/dcms-admin/volunteer/confirmPayment/{id}")
    Call<PaymentModel> confirmPayment(@Header("x-auth-token") String token, @Path("id") long id, @Field("eventId") int eventId, @Field("paid") boolean paid);

    @FormUrlEncoded
    @PUT("dcms-admin/notification")
    Call<String> sendNotif(@Header("x-auth-token") String token,@Field("title")String title,@Field("body")String body);
}
