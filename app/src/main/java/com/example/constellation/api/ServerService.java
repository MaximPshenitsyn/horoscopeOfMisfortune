package com.example.constellation.api;

import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.HoroscopeRequestDto;
import com.example.constellation.dto.ResponseDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.SignRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerService {
    @GET("/")
    Call<String> getIndex();

    @POST("/post")
    Call<Object> getPostValue();

    @POST("/sign/sun")
    Call<ResponseDto<SignDto>> getSignInSun(@Body SignRequestDto req);

    @POST("/horoscope/basic")
    Call<ResponseDto<HoroscopeDto>> getHoroscope(@Body HoroscopeRequestDto req);
}
