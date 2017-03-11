package com.cse.amrith.drishti17volunteers.Utils;

import com.cse.amrith.drishti17volunteers.Models.Admin;
import com.cse.amrith.drishti17volunteers.Models.Student;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

}
