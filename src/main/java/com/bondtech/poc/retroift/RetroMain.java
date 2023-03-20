package com.bondtech.poc.retroift;

import com.bondtech.poc.retroift.model.User;
import com.bondtech.poc.retroift.service.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.Executors;

public class RetroMain {
    public static void main(String[] args) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //.callbackExecutor(Executors.newSingleThreadExecutor())
                .client(httpClient.build())
                .build();

        UserService service = retrofit.create(UserService.class);
        callGetUserAsync(service);
        callGetUserSync(service);

    }

    private static void callGetUserSync(UserService service){
        Call<User> callSync = service.getUser("eugenp");

        try {
            Response<User> response = callSync.execute();
            User user = response.body();
            System.out.println(user.toString());
            //callSync.timeout();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
    private static void callGetUserAsync(UserService service){
        Call<User> callAsync = service.getUser("eugenp");

        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
    }
}
