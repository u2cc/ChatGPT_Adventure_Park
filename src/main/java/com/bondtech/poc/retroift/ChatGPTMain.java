package com.bondtech.poc.retroift;

import com.bondtech.poc.retroift.adaptors.ModelDeserializer;
import com.bondtech.poc.retroift.model.Model;
import com.bondtech.poc.retroift.service.ChatGPTService;
import com.bondtech.poc.retroift.interceptors.AuthenticationInterceptor;


import com.bondtech.poc.retroift.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ChatGPTMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGPTMain.class);
    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(
                        new AuthenticationInterceptor(System.getenv().get("APIKey")))
                .build();

        Gson gson = new GsonBuilder().registerTypeAdapter(List.class, new ModelDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.callbackExecutor(Executors.newSingleThreadExecutor())
                .client(httpClient)
                .build();

        ChatGPTService service = retrofit.create(ChatGPTService.class);
        callListModelsASync(service);
        callListModelsSync(service);
        callListFilesSync(service);

    }

    private static void callListModelsSync(ChatGPTService service){
        LOGGER.info("callListModelsSync:");
        Call<List<Model>> callSync = service.listModels();
        try {
            Response<List<Model>> response = callSync.execute();
            LOGGER.info(Utils.printResponse(response));
        } catch (Exception ex) { ex.printStackTrace(); }
    }
    private static void callListModelsASync(ChatGPTService service){
        LOGGER.info("callListModelsASync:");
        Call<List<Model>> callAsync = service.listModels();
        callAsync.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                try {
                    LOGGER.info(Utils.printResponse(response));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
    }

    private static void callListFilesSync(ChatGPTService service){
        LOGGER.info("callListFilesSync:");
        Call<Object> callSync = service.listFiles();
        try {
            Response<Object> response = callSync.execute();
            LOGGER.info(Utils.printResponse(response));
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
