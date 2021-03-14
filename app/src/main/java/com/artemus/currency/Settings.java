package com.artemus.currency;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    private Switch theme_switcher;
    SharedPref sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightMode())
        {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.appbar);
        toolbar.setTitle("Настройки");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        theme_switcher = findViewById(R.id.switch1);
        if (sharedPref.loadNightMode())
        {
            theme_switcher.setChecked(true);
        }
        theme_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sharedPref.setNightModeState(true);
                    restartApp();
                }
                else
                {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });
    }

    public void restartApp()
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
        finish();
    }
}
