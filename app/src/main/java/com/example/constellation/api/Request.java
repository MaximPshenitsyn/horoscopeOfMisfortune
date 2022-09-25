package com.example.constellation.api;

import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.HoroscopeRequestDto;
import com.example.constellation.dto.ResponseDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.SignRequestDto;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://httpbin.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ServerService service = retrofit.create(ServerService.class);

    public static Call<String> getIndex() {
        return service.getIndex();
    }

    public static Call<Object> getPostValue() {
        return service.getPostValue();
    }

    public static Call<ResponseDto<SignDto>> getSignInSun(String dateOfBirth) {
        return service.getSignInSun(new SignRequestDto(dateOfBirth));
    }

    public static Call<ResponseDto<HoroscopeDto>> getHoroscope(String dateOfBirth) {
        return service.getHoroscope(new HoroscopeRequestDto(dateOfBirth, 0));
    }

}
