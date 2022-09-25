package com.example.constellation;

import static com.example.constellation.MainActivity.service;
import static com.example.constellation.MainActivity.userData;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.constellation.api.Request;
import com.example.constellation.dto.ResponseDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.enums.SignEnum;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static SignEnum mySign;
    private TextView signName;
    private TextView signDesc;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignFragment newInstance(String param1, String param2) {
        SignFragment fragment = new SignFragment();
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
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signName = (TextView) getView().findViewById(R.id.signName);
        signDesc = (TextView) getView().findViewById(R.id.signDesc);
        try {
            new UrlThread().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class UrlThread extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            Call<ResponseDto<SignDto>> call = Request.getSignInSun(userData.getDateOfBirth().toString());
            call.enqueue(new Callback<ResponseDto<SignDto>>() {
                @Override
                public void onResponse(Call<ResponseDto<SignDto>> call, Response<ResponseDto<SignDto>> response) {
                    System.out.println(response.code());
                    try {
                        assert response.body() != null;
                        signName.setText(response.body().getData().getSignName().toString());
                        signDesc.setText(response.body().getData().getSignDesc());
                    } catch (Throwable t) {
                        signName.setText("Ошибка");
                        signDesc.setText(t.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseDto<SignDto>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }
    }
}