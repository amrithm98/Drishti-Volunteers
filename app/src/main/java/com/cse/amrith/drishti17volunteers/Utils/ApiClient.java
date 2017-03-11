package com.cse.amrith.drishti17volunteers.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amrith on 3/11/17.
 */

public class ApiClient {
    public static final String  HOST_URL = "http://server.drishticet.org/" ;
    public static final String  NODE_PORT = "";
    public static final String  BASE_URL = HOST_URL ;

    public static RestApiInterface getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestApiInterface.class);
    }
}
