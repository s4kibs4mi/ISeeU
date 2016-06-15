package net.codersgarage.iseeu.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import net.codersgarage.iseeu.R;
import net.codersgarage.iseeu.models.Settings;
import net.codersgarage.iseeu.settings.SettingsProvider;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText hostAddressValue;
    private EditText hostPortValue;
    private CheckBox autoLoginValue;
    private Button connectBtn;

    private SettingsProvider settingsProvider;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);

        try {
            getActionBar().hide();
        } catch (Exception ex) {
            try {
                getSupportActionBar().hide();
            } catch (Exception ex2) {

            }
        }

        hostAddressValue = (EditText) findViewById(R.id.hostAddressValue);
        hostPortValue = (EditText) findViewById(R.id.hostPortValue);
        autoLoginValue = (CheckBox) findViewById(R.id.autoLoginValue);
        connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(this);

        settingsProvider = new SettingsProvider(this);
        settings = settingsProvider.getSettings();

        if (settings != null) {
            hostAddressValue.setText(settings.getHost());
            hostPortValue.setText(String.valueOf(settings.getPort()));
            autoLoginValue.setChecked(settings.isAutoLogin());
        }
    }

    @Override
    public void onClick(View v) {
        if (!hostAddressValue.getText().toString().isEmpty() && !hostPortValue.getText().toString().isEmpty()) {
            Settings settings = new Settings();
            settings.setHost(hostAddressValue.getText().toString());
            settings.setPort(Integer.parseInt(hostPortValue.getText().toString()));
            settings.setAutoLogin(autoLoginValue.isChecked());

            settingsProvider.addSettings(settings);

            Log.d("Input := ", hostAddressValue.getText().toString() +
                    hostPortValue.getText().toString());

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isBegin", true);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Value Required", Toast.LENGTH_SHORT).show();
        }
    }
}
