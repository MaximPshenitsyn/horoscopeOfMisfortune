package com.example.constellation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.constellation.databinding.ActivityRootBinding;
import com.example.constellation.design.DesignFactory;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.UserInfoDto;
import com.example.constellation.dto.enums.ElementEnum;
import com.example.constellation.dto.enums.SignEnum;
import com.example.constellation.service.AppFacade;
import com.example.constellation.service.AppService;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class MainActivity extends AppCompatActivity {

    ActivityRootBinding binding;

    public static AppService service;

    private SharedPreferences sPref;
    public static UserInfoDto userData;
    private static final String DATE_KEY = "date_of_birth";
    private static final String NAME_KEY = "name";

    private Button startButton;
    private EditText inputDate;
    private EditText inputName;

    private void load() {
        setContentView(R.layout.activity_root);
        binding = ActivityRootBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragments(new SignFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.signMenu:
                    replaceFragments(new SignFragment());
                    break;
                case R.id.horoscopeMenu:
                    replaceFragments(new HoroscopeFragment());
                    break;
                case R.id.compatibilityMenu:
                    replaceFragments(new CompatibilityFragment());
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new AppFacade();

        setContentView(R.layout.activity_main);
        try {
            // throw new IOException("");
            userData = readUserData();
            load();
        } catch (Exception e) {
            e.printStackTrace();

            startButton = findViewById(R.id.startButton);
            startButton.setOnClickListener(item -> {
                inputName = findViewById(R.id.inputName);
                inputDate = findViewById(R.id.inputDate);
                String[] date = inputDate.getText().toString().split("/");
                LocalDate birthDate;
                if (date.length == 3) {
                    try {
                        birthDate = LocalDate.of(Integer.parseInt(date[2]),
                                Integer.parseInt(date[1]) - 1,
                                Integer.parseInt(date[0]));
                    } catch (Exception ex) {
                        inputName.setText("Дата должна быть в формате дд/мм/гггг!");
                        return;
                    }
                } else {
                    inputName.setText("Дата должна быть в формате дд/мм/гггг!");
                    return;
                }
                userData = new UserInfoDto(inputName.getText().toString(), birthDate);
                try {
                    writeUserData();
                    load();
                } catch (IOException ex) {
                    inputName.setText(ex.getMessage());
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserInfoDto readUserData() throws IOException {
        sPref = getPreferences(MODE_PRIVATE);
        String dateOfBirth = sPref.getString(DATE_KEY, null);
        String name = sPref.getString(NAME_KEY, null);
        if (dateOfBirth == null || name == null) {
            throw new IOException("Saved data corrupted!");
        }
        String[] date = dateOfBirth.split("-");
        LocalDate birthDate = LocalDate.of(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                Integer.parseInt(date[2]));
        return new UserInfoDto(name, birthDate);
    }

    public void writeUserData() throws IOException {
        if (userData == null) {
            throw new IOException("No user data provided!");
        }
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(DATE_KEY, userData.getDateOfBirth().toString());
        editor.putString(NAME_KEY, userData.getName());
        editor.apply();
    }

    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, fragment);
        fragmentTransaction.commit();
    }

}