package com.bondtech.poc.retroift.service;


import com.bondtech.poc.retroift.model.Model;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ChatGPTService {

    @GET("v1/models")
    Call<List<Model>> listModels();

    @GET("v1/files")
    Call<Object> listFiles();
}
