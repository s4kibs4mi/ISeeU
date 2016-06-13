package net.codersgarage.iseeu.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.codersgarage.iseeu.R;

public class LuncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
