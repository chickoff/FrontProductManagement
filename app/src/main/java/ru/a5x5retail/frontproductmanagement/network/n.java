package ru.a5x5retail.frontproductmanagement.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class n {

    Retrofit retrofit;
    public n() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.213:50501/FrontProductManagementService/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        inface f = retrofit.create(inface.class);
        f.teset().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }




    public  interface  inface {
        @GET("nm/d/gf/")
        Call<String> teset();
    }
}
