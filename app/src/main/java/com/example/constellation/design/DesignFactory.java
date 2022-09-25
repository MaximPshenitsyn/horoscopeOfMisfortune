package com.example.constellation.design;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.constellation.R;
import com.example.constellation.SignFragment;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.UserInfoDto;
import com.example.constellation.service.AppFacade;
import com.example.constellation.service.AppService;

import java.net.ConnectException;

public class DesignFactory extends AppCompatActivity {

    private final UserInfoDto userData;

    public DesignFactory(UserInfoDto userData) {
        this.userData = userData;
    }

    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, fragment);
        fragmentTransaction.commit();
    }

    public void setSignMenuActivity() {
        TextView signName = findViewById(R.id.signName);
        TextView signDesc = findViewById(R.id.signDesc);
        replaceFragments(new SignFragment());
        AppService service = new AppFacade();
        try {
            SignDto result = service.getSignInSun(userData);
            signName.setText(result.getSignName().toString());
            signDesc.setText(result.getSignDesc());
        } catch (ConnectException e) {
            signDesc.setText(e.getMessage());
        }
    }
}
