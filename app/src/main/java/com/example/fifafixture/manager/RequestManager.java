package com.example.fifafixture.manager;

import android.content.Context;

import com.example.fifafixture.ResponseListener;
import com.example.fifafixture.model.FixtureResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class RequestManager {
    Context context;
    Retrofit retrofit= new Retrofit.Builder()
            .baseUrl("https://api-football-v1.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public RequestManager(Context context) {
        this.context = context;
    }


    public void getFixture(ResponseListener listener,int id){
      CallFixture callFixture= retrofit.create(CallFixture.class);
      Call<FixtureResponse>call=callFixture.callFixtures(id);
      call.enqueue(new Callback<FixtureResponse>() {
          @Override
          public void onResponse(Call<FixtureResponse> call, Response<FixtureResponse> response) {
              if (!response.isSuccessful()){
                  listener.didError(response.message());
                  return;
              }
              listener.didFetch(response.body(), response.message());
          }

          @Override
          public void onFailure(Call<FixtureResponse> call, Throwable t) {
             listener.didError(t.getMessage());
          }
      });
    }


    private interface CallFixture{
        @GET("v2/seasons/{id}/fixtures")
        @Headers(
                {"Accept: application/json",
                "X-RapidAPI-Key:4a220517a4msh35005f4307b257bp1fae4ejsn98",
                "X-RapidAPI-Host:api-football-v1.p.rapidapi.com"
                }
        )
        Call<FixtureResponse> callFixtures(
                 @Path("id") int id
        );
    }
}
