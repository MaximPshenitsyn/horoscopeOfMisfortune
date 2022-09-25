package com.example.constellation;

import static com.example.constellation.MainActivity.service;
import static com.example.constellation.MainActivity.userData;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.constellation.api.Request;
import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.ResponseDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.enums.PeriodEnum;

import java.net.ConnectException;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoroscopeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoroscopeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView horoscopeText;
    private TextView horoscopeDate;

    public HoroscopeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HoroscopeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HoroscopeFragment newInstance(String param1, String param2) {
        HoroscopeFragment fragment = new HoroscopeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horoscope, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        horoscopeText = (TextView) getView().findViewById(R.id.horoscopeText);
        horoscopeDate = (TextView) getView().findViewById(R.id.horoscopeDate);
        horoscopeDate.setText(LocalDate.now().toString());
        try {
            new UrlThread().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            horoscopeDate.setText("Ошибка");
            horoscopeText.setText(e.toString());
        }
    }

    private class UrlThread extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Call<ResponseDto<HoroscopeDto>> call = Request.getHoroscope(
                    userData.getDateOfBirth().toString());
            call.enqueue(new Callback<ResponseDto<HoroscopeDto>>() {
                @Override
                public void onResponse(Call<ResponseDto<HoroscopeDto>> call, Response<ResponseDto<HoroscopeDto>> response) {
                    System.out.println(response.code());
                    try {
                        assert response.body() != null;
                        String result = response.body().getData().getText();
                        horoscopeText.setText(result);
                    } catch (Throwable t) {
                        horoscopeDate.setText("Ошибка");
                        horoscopeText.setText(t.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseDto<HoroscopeDto>> call, Throwable t) {
                    t.printStackTrace();
                    horoscopeDate.setText("Ошибка");
                    horoscopeText.setText(t.toString());
                }
            });

            return null;
        }
    }
}