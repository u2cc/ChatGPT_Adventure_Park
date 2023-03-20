package com.bondtech.poc.retroift.util;

import retrofit2.Response;

import java.io.IOException;

public class Utils {
    private Utils(){}
    public static String printResponse(Response response) throws IOException {
        if(response.isSuccessful()){
           return response.body().toString();
        }else{
            return response.errorBody().string();
        }

    }
}
