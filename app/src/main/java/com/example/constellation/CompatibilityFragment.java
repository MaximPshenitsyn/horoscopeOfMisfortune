package com.example.constellation;

import static com.example.constellation.MainActivity.service;
import static com.example.constellation.MainActivity.userData;
import static com.example.constellation.SignFragment.mySign;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.constellation.dto.CompatibilityDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.UserInfoDto;
import com.example.constellation.dto.enums.SignEnum;

import java.net.ConnectException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompatibilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompatibilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String signSelected;

    public CompatibilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompatibilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompatibilityFragment newInstance(String param1, String param2) {
        CompatibilityFragment fragment = new CompatibilityFragment();
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
        return inflater.inflate(R.layout.fragment_compatibility, container, false);
    }

    private SignEnum mapSigns(String rus) {
        switch (rus) {
            case "Овен":
                return SignEnum.Aries;
            case "Телец":
                return SignEnum.Taurus;
            case "Близнецы":
                return SignEnum.Gemini;
            case "Рак":
                return SignEnum.Cancer;
            case "Лев":
                return SignEnum.Leo;
            case "Дева":
                return SignEnum.Virgo;
            case "Весы":
                return SignEnum.Libra;
            case "Скорпион":
                return SignEnum.Scorpio;
            case "Стрелец":
                return SignEnum.Sagittarius;
            case "Козерог":
                return SignEnum.Capricorn;
            case "Водолей":
                return SignEnum.Aquarius;
            case "Рыбы":
                return SignEnum.Pisces;
            default:
                return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView compatibilityHeader = (TextView) getView().findViewById(R.id.compatibilityHeader);
        Spinner spinner = (Spinner) getView().findViewById(R.id.compatibilityOther);
        TextView compatibilityText = (TextView) getView().findViewById(R.id.compatibilityText);
        RadioButton isMale = (RadioButton) getView().findViewById(R.id.isMale);
        RadioButton isFemale = (RadioButton) getView().findViewById(R.id.isFemale);

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.signs,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] signs = getResources().getStringArray(R.array.signs);
                signSelected = signs[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                signSelected = null;
            }
        });

        isMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signSelected == null) {
                    compatibilityText.setText("Не выбран знак зодиака!");
                    return;
                }
                try {
                    CompatibilityDto result = service.getCompatibility(true, mySign, mapSigns(signSelected));
                    compatibilityText.setText(formCompatibilityText(result));
                    compatibilityHeader.setText(result.getPercentage());
                } catch (ConnectException e) {
                    compatibilityHeader.setText("Ошибка!");
                    compatibilityText.setText(e.getMessage());
                }
            }
        });


        isFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signSelected == null) {
                    compatibilityText.setText("Не выбран знак зодиака!");
                    return;
                }
                try {
                    CompatibilityDto result = service.getCompatibility(false, mySign, mapSigns(signSelected));
                    compatibilityText.setText(formCompatibilityText(result));
                    compatibilityHeader.setText(result.getPercentage());
                } catch (ConnectException e) {
                    compatibilityHeader.setText("Ошибка!");
                    compatibilityText.setText(e.getMessage());
                }
            }
        });


        // ON BUTTON CLICK

        //
    }

    private String formCompatibilityText(CompatibilityDto result) {
        return result.getFamilyCompatibility() + "\n" + result.getHappinessInMarriage() + "\n" +
                result.getSexualCompatibility() + "\n" + result.getGoodLuckCompatibility() + "\n" +
                result.getForKidsCompatibility();
    }
}